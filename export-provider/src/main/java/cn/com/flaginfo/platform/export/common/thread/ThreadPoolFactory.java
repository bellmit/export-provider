package cn.com.flaginfo.platform.export.common.thread;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池工程类
 * 保证在所有的地方保证获取同一个名称的线程池为同一个实例
 * @author Rain
 *
 */
public class ThreadPoolFactory {
	
	private ConcurrentHashMap<String,FileThreadPool> pool = 
			new ConcurrentHashMap<String,FileThreadPool>();
	
	public ConcurrentHashMap<String, FileThreadPool> getPool() {
		return pool;
	}

	private static ThreadPoolFactory factory = new ThreadPoolFactory();
	
	private ThreadPoolFactory(){
		
		Runtime.getRuntime().addShutdownHook(new Thread(){
			@Override
			public void run() {
				Set<Entry<String, FileThreadPool>> set = pool.entrySet();
				for(Entry e:set){
					((FileThreadPool)e.getValue()).shutdownNow();
				}
				super.run();
			}
		});
		
	}
	
	/**
	 * 单例
	 * @return
	 */
	public static ThreadPoolFactory getInstance(){
		return factory;
	}
	
	public FileThreadPool getThreadPool(String poolName,FileThreadPool.PoolConfig poolConfig){
		
		FileThreadPool p = pool.get(poolName);
		if(p==null){
			synchronized (pool) {
				if(pool.get(poolName)==null){
					poolConfig.setWorkerQueue(new LinkedBlockingQueue<Runnable>());
					p = new FileThreadPool(poolName,poolConfig);
					pool.put(poolName,p);
				}
			}
			
		}
		return p;
	}
	
	/**
	 * 从线程池内存中获取线程实例
	 * @param poolName
	 * @return
	 */
	public FileThreadPool getExistsThreadPool(String poolName){
		FileThreadPool p = pool.get(poolName);
		return p;
	}
	
	public static void main(String[] args) throws Exception{
		ThreadPoolFactory f = ThreadPoolFactory.getInstance();
		ThreadPoolExecutor e = f.getThreadPool("tdPool", FileThreadPool.POOL_MIDDLE);
		Map m = new HashMap();
		m.put("oooooooooooooooo", "kkkkkkkkkkkkkkkkk");
		Thread.sleep(10000000);
		//e.shutdown();
	}
	
}
