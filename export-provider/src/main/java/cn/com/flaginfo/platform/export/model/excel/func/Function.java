/**
 * 
 */
package cn.com.flaginfo.platform.export.model.excel.func;

/**
 * <pre>
 * 导出列转换函数，根据列值转换到实际需要的值。
 * 
 * 比如数据库字段platform 查出来值为1，通过转换函数得到省份名称:帜讯。
 * 
 * public String apply(String value,Object param){
 *     Map<String,String> platformMap = new HashMap<String,String>();
 *     platform.put("1","帜讯");
 *     return platformMap.get(value);
 * }
 * 
 * </pre>
 * @author chengbin.luo
 * 
 */
public interface Function<T, P> {

	/**
	 * 转换列值
	 * 
	 * @param value
	 *            原始值
	 * @param param
	 *            转换过程中可能需要的参数
	 * @return 转换后的值
	 */
	public String apply(T value, P param);

}
