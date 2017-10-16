package cn.com.flaginfo.platform.export.model;

/**
 * 用户信息holder，用户后台程序使用
 * @author Rain
 *
 */
public class OperatorHolder {
	
	public static ThreadLocal<Operator> userInfoLocal = new ThreadLocal<Operator>();
	
	public static void set(Operator userInfo){
		userInfoLocal.set(userInfo);
	}
	
	/**
	 * 获取用户信息，是否能为空
	 * @param canNull
	 * @return
	 */
	public static Operator get(boolean canNull){
		Operator userInfo = userInfoLocal.get();
		if(userInfo==null && !canNull){
			throw new RuntimeException("login userInfo is null");
		}
		return userInfo;
	}
	
	public static Operator get(){
		return get(true);
	}
	
	
	public static void remove(){
		userInfoLocal.remove();
	}
	
	
	
	
}
