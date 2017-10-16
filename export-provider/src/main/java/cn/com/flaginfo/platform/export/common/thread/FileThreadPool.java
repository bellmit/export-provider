package cn.com.flaginfo.platform.export.common.thread;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义线程池
 * 称为文件线程池
 * 主要是为了重启的过程中数据丢失
 * @author Rain
 *
 */
public class FileThreadPool extends ThreadPoolExecutor {
	
	private static Logger logger = LoggerFactory.getLogger(FileThreadPool.class);
	public static final PoolConfig POOL_LARGE = new PoolConfig();
	public static final PoolConfig POOL_SMALL = new PoolConfig();
	public static final PoolConfig POOL_MIDDLE = new PoolConfig();
	
	private String poolName;
	private PoolConfig poolConfig;
	
	static{
		
		//大池子
		POOL_LARGE.setCorePoolSize(20);
		POOL_LARGE.setMaximumPoolSize(20);
		POOL_LARGE.setKeepAliveTime(30*60*1000); //30分钟
		POOL_LARGE.setUnit(TimeUnit.MILLISECONDS);
		//POOL_LARGE.setWorkerQueue(new LinkedBlockingQueue<Runnable>(20000));
		
		//小池子
		POOL_SMALL.setCorePoolSize(1);
		POOL_SMALL.setMaximumPoolSize(1);
		POOL_SMALL.setKeepAliveTime(15*60*1000); //15分钟
		POOL_SMALL.setUnit(TimeUnit.MILLISECONDS);
		//POOL_SMALL.setWorkerQueue(new LinkedBlockingQueue<Runnable>());
				
		//中池子
		POOL_MIDDLE.setCorePoolSize(10);
		POOL_MIDDLE.setMaximumPoolSize(10);
		POOL_MIDDLE.setKeepAliveTime(20*60*1000); //20分钟
		POOL_MIDDLE.setUnit(TimeUnit.MILLISECONDS);
		//POOL_MIDDLE.setWorkerQueue(new LinkedBlockingQueue<Runnable>());
	}

	
	/**
	 * 采取默认配置  
	 * POOL_LARGE大池子
	 * POOL_SMALL小池子
	 * POOL_MIDDLE中池子
	 * @param name 线程池名称
	 * @param poolConfig
	 */
	public FileThreadPool(String poolName,PoolConfig poolConfig) {
		super(poolConfig.getCorePoolSize(), poolConfig.getMaximumPoolSize(),
				poolConfig.getKeepAliveTime(), 
				poolConfig.getUnit(),poolConfig.getWorkerQueue());
		this.poolConfig = poolConfig;
		setPoolName(poolName);
	}
	
	
	public PoolConfig getPoolConfig() {
		return poolConfig;
	}



	public void setPoolConfig(PoolConfig poolConfig) {
		this.poolConfig = poolConfig;
	}



	public String getPoolName() {
		return poolName;
	}



	public void setPoolName(String name) {
		this.poolName = name;
	}



	@Override
	protected void beforeExecute(Thread t, Runnable r) {
		super.beforeExecute(t, r);
		
	}
	
	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		super.afterExecute(r, t);
	}
	
	@Override
	public Future<?> submit(Runnable task) {
		if(task instanceof Worker){
			((Worker) task).setPoolName(poolName);
			((Worker) task).submit();
		}
		Future f = super.submit(task);
		logger.info("===the pool:"+getPoolName()+" queue size:"+getQueue().size()
				+" ;cur thread size:"+getPoolSize()+" ;max thread size:"+getMaximumPoolSize()+";active thread count:"+getActiveCount());
		return f;
	}
	
	
	public static class PoolConfig{
		
		private BlockingQueue<Runnable> workerQueue;
		private int corePoolSize;
		private int maximumPoolSize;
		private int keepAliveTime;
		private TimeUnit unit;
		public BlockingQueue<Runnable> getWorkerQueue() {
			return workerQueue;
		}
		public void setWorkerQueue(BlockingQueue<Runnable> workerQueue) {
			this.workerQueue = workerQueue;
		}
		public int getCorePoolSize() {
			return corePoolSize;
		}
		public void setCorePoolSize(int corePoolSize) {
			this.corePoolSize = corePoolSize;
		}
		public int getMaximumPoolSize() {
			return maximumPoolSize;
		}
		public void setMaximumPoolSize(int maximumPoolSize) {
			this.maximumPoolSize = maximumPoolSize;
		}
		public int getKeepAliveTime() {
			return keepAliveTime;
		}
		public void setKeepAliveTime(int keepAliveTime) {
			this.keepAliveTime = keepAliveTime;
		}
		public TimeUnit getUnit() {
			return unit;
		}
		public void setUnit(TimeUnit unit) {
			this.unit = unit;
		}
		
	}
	
}
