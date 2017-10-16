package cn.com.flaginfo.platform.export.common.thread;


/**
 * 异步任务回调接口,可定制任务完成、失败、异常后的具体业务逻辑
 * 
 * @author ming.tan@flaginfo.com.cn
 * @date 2014年11月12日 上午9:48:28
 */
public interface WorkerCallBack<T> {

	public void complete(T param);

	public void failed(Throwable t);

}
