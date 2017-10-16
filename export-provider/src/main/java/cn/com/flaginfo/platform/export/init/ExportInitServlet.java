package cn.com.flaginfo.platform.export.init;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import cn.com.flaginfo.platform.common.util.DateUtil;
import cn.com.flaginfo.platform.common.util.JsonHelper;
import cn.com.flaginfo.platform.export.consumer.ManagerProxy;
import cn.com.flaginfo.platform.export.manager.ExportManager;
import cn.com.flaginfo.platform.export.model.ExportTask;

//@RunWith(PandoraBootRunner.class)
//@Configuration
//@Import(WebApplication.class)
//@EnableAutoConfiguration
//@ComponentScan
//@EntityScan
@Component
public class ExportInitServlet implements ApplicationRunner  {

	private static final Logger log = Logger.getLogger(ExportInitServlet.class);
	
	private ExportManager manager = ManagerProxy.getInstance(ExportManager.class);
	
	public void initialize(ConfigurableApplicationContext applicationContext) {
		log.info("加载数据库中未执行的导出任务......");
		try {
			List<Map<String,String>> exportList = manager.listExportTasks(null, null, ExportTask.STATUS_WAIT, null);
			log.info("加载任务数："+(exportList==null?0:exportList.size()));
			if (exportList!=null && exportList.size()>0) {
				for (Map<String,String> info : exportList) {
					log.info("submitTask:"+info);
					ExportTask task = getExportTask(info);
					if(task==null){
						log.info("task params error,taskId:"+info.get("id"));
						continue;
					}
					manager.submit(task);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("加载数据库中未执行的导出任务异常：" + e);
		}
		log.info("加载数据库中未执行的导出任务，完毕......");
	}
	
	public ExportTask getExportTask(Map<String,String> info){
		ExportTask task = manager.getTaskByType(info.get("type"));
		try{
			task.setId(info.get("id"));
			task.setSpId(info.get("spId"));
			task.setOperator(info.get("operator"));
			task.setOperatorId(info.get("operatorId"));
			task.setCreateTime(DateUtil.parseDate(info.get("createTime")));
			task.setStatus(info.get("status"));
			task.setRemark(info.get("remark"));
			task.setFileType(info.get("fileType"));
			task.setSource(info.get("source"));
			task.setIsDown(0);
			task.setExportTitle(info.get("exportTitle"));
			task.setType(info.get("type"));
			task.setExportCurPage(info.get("exportCurPage"));
			task.setExportPageLimit(info.get("exportPageLimit"));
			Map requestParam = JsonHelper.parseToMap(info.get("requestParam"));
			task.setRequestParam(requestParam);
			task.setHeadParam(JsonHelper.parseToMap(info.get("headParam")));
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		return task;
	}

	@Override
	public void run(ApplicationArguments arg0) throws Exception {
		log.info("启动加载自定义的MyWebApplicationInitializer"); 
		
	}


	
}