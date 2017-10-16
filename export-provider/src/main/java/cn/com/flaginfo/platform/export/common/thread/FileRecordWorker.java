package cn.com.flaginfo.platform.export.common.thread;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

import cn.com.flaginfo.platform.common.util.FileUtil;
import cn.com.flaginfo.platform.export.model.Operator;
import cn.com.flaginfo.platform.export.model.OperatorHolder;


/**
 * 
 * 文件记录Worker 在提交的时候记录处理文件 完成任务的时候删除记录文件 
 * 例如:可以使用memcached作为缓存对象 record
 * @author Rain
 *
 */
public abstract class FileRecordWorker implements Worker {

	private static final long serialVersionUID = 2L;

	private boolean succ = false;

	private String poolName;
	/**
	 * work名称，保证同一个线程池中唯一
	 */
	private String workName;

	protected Map<? extends Serializable, ? extends Serializable> params;

	protected WorkerCallBack callBack;
	
	protected Operator userInfo;

	private FileMapping mapping = new FileMapping();
	private static Logger logger = Logger.getLogger(FileRecordWorker.class);

	public static String CACHE_FILE_DOING = "worker/doing";
	public static String CACHE_FILE_FAIL = "worker/do_fail";
	public static String CACHE_FILE_REDO = "worker/re_do";

	static {
		CACHE_FILE_DOING = FileUtil.getWebRootPath() + CACHE_FILE_DOING;
		CACHE_FILE_FAIL = FileUtil.getWebRootPath() + CACHE_FILE_FAIL;
		CACHE_FILE_REDO = FileUtil.getWebRootPath() + CACHE_FILE_REDO;
	}

	public FileRecordWorker() {

	}

	@Override
	public void run() {
		long start = System.currentTimeMillis();
		try {
			//添加线程日志
			if (userInfo != null) {
				StringBuffer logVar = new StringBuffer();
				logVar.append("worker-")
					.append(userInfo.getIp()==null ? "unkownIp" : userInfo.getIp())
					.append("-")
					.append(userInfo.getSpId())
					.append("-")
					.append(userInfo.getUserName() == null ? "unkownUser": userInfo.getUserName())
					;
				MDC.put("var", logVar.toString());
				OperatorHolder.set(userInfo);
			}
			
			execute();
			if (this.callBack != null){
				callBack.complete(params);
			}
			end();
		} catch (Exception e) {
			try {
				if (this.callBack != null){
					callBack.failed(e);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			try {
				fail();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			//待扩展，错误信息提交给监控系统

		} finally {
			try {
				OperatorHolder.remove();
				if (userInfo != null) {
					MDC.remove("var");
				}
				logger.info("worker:"+getWorkName()+" cost:"+(System.currentTimeMillis()-start)+"ms");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public String getWorkName() {
		if (workName == null) {
			workName = UUID.randomUUID().toString();
		}
		return workName;
	}

	@Override
	public void setMessage(Map params) {
		this.params = params;
	}

	@Override
	public Map getMessage() {
		return this.params;
	}

	@Override
	public String getPoolName() {
		return poolName;
	}

	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}

	@Override
	public void submit() {
		this.mapping.setObjectMap(getMessage());
		this.mapping.setWorkClazz(getClass());
		FileUtil.writerObject(CACHE_FILE_DOING + File.separator + getPoolName() + File.separator + getWorkName(),
				this.mapping);
		this.userInfo = OperatorHolder.get();
	}

	@Override
	public void end() {
		FileUtil.delete(CACHE_FILE_DOING + File.separator + getPoolName() + File.separator + getWorkName());
	}

	public void fail() {
		FileUtil.moveFileToDirectory(
				CACHE_FILE_DOING + File.separator + getPoolName() + File.separator + getWorkName(), CACHE_FILE_FAIL
						+ File.separator + getPoolName());
	}

	@Override
	public boolean isSucc() {
		return this.succ;
	}

	@Override
	public void setSucc(boolean succ) {
		this.succ = succ;
	}

	public static List<Worker> getNoExecuteWorker(String name) {

		File failDir = new File(CACHE_FILE_FAIL + File.separator + name);

		List failList = getExecutePreWorker(failDir);
		File noExecuteDir = new File(CACHE_FILE_DOING + File.separator + name);
		List noExecuteList = getExecutePreWorker(noExecuteDir);
		if (failDir.exists()) {
			if (!noExecuteDir.exists()) {
				noExecuteDir.mkdirs();
			}
			FileUtil.moveDirectoryToDirectory(failDir, noExecuteDir);
		}

		List list = new ArrayList();
		if (failList != null) {
			list.addAll(failList);
		}
		if (noExecuteList != null) {
			list.addAll(noExecuteList);
		}
		return list;
	}

	/**
	 * 获取由于重启没有执行完的Worker
	 * @param dir
	 * @return
	 */
	public static List<Worker> getExecutePreWorker(File dir) {
		if (dir == null || dir.exists()) {
			return null;
		}
		File workerFile[] = dir.listFiles();
		List<Worker> list = new ArrayList();
		if (workerFile != null) {
			for (File w : workerFile) {
				FileMapping fm = FileUtil.readObject(w.getAbsolutePath(), FileMapping.class);
				if (fm == null) {
					logger.info("file can't read to FileMappering:" + w.getAbsolutePath());
					FileUtil.delete(w.getAbsolutePath());
					continue;
				}
				try {
					Worker worker = (Worker) fm.getWorkClazz().newInstance();
					worker.setMessage(fm.getObjectMap());
					worker.setWorkName(w.getName());
					list.add(worker);
					// FileUtil.delete(w.getAbsolutePath());
					// FileUtil.moveFileToDirectory(w,new
					// File(CACHE_FILE_PATH+File.separator+name));
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
			logger.info("poolName:" + dir.getName() + ":worker size=" + list.size());
			dir.delete();
		}
		return list;
	}

	/**
	 * 绑定用户信息到线程变量中
	 *//*
	protected void bindUserInfo() {
		Map params = getMessage();
		if (params == null || params.isEmpty())
			return;

		String userId = (String) params.get("userId");
		String spId = (String) params.get("spId");
		String userName = (String) params.get("userName");

		UserInfo userInfo = new UserInfo();
		userInfo.setSpId(spId);
		userInfo.setUserId(userId);
		userInfo.setUserName(userName);
		UserInfoHolder.set(userInfo);
	}*/

	@Override
	public void setCallBack(WorkerCallBack callBack) {
		this.callBack = callBack;
	}

	@Override
	public WorkerCallBack getCallBack() {
		return this.callBack;
	}

}
