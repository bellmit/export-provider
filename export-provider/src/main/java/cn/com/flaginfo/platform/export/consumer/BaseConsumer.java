package cn.com.flaginfo.platform.export.consumer;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.flaginfo.platform.common.util.MdnUtil;
import cn.com.flaginfo.platform.common.util.StringUtil;


public class BaseConsumer {

	protected Logger logger = LoggerFactory.getLogger(getClass());


	/**
	 * 判断是否为空
	 * 
	 * @param args
	 * @return
	 */
	protected boolean isEmpty(Object... args) {
		if (args == null || args.length == 0) {
			return true;
		}
		for (Object o : args) {
			if (o == null || "".equals(o)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断Map中的值是否为空
	 * 
	 * @param m
	 * @param args
	 * @return
	 */
	protected boolean isEmptyInMap(Map m, Object... keys) {
		if (m == null) {
			logger.info("map is null");
			return true;
		}
		if (keys == null || keys.length == 0) {
			return true;
		}
		for (Object o : keys) {
			if (isEmpty(m.get(o))) {
				logger.info("isEmptyInMap key:" + o + " is null");
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否为手机号码
	 * 
	 * @param mdn
	 *            待验证字符串
	 * @return
	 */
	protected boolean isMdn(String... mdns) {
		if (mdns == null || mdns.length == 0) {
			return false;
		}
		for (String mdn : mdns) {
			if (!MdnUtil.isMdn(mdn)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断Map中的值是否为手机号码
	 * 
	 * @param map
	 * @param keys
	 * @return
	 */
	protected boolean isMdnInMap(Map<String, String> map, String... keys) {
		if (map == null) {
			logger.info("map is null");
			return false;
		}
		if (keys == null || keys.length == 0) {
			return false;
		}
		for (String o : keys) {
			if (!isMdn(map.get(o))) {
				logger.info("isMdnInMap key:" + o + " is not mdn");
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断是否为数字字符串
	 * 
	 * @param numbers
	 * @return
	 */
	protected boolean isNumeric(String... numbers) {
		if (numbers == null || numbers.length == 0) {
			return false;
		}
		for (String number : numbers) {
			if (StringUtil.isNumeric(number)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断Map中的key的值是否为数字字符串
	 * 
	 * @param map
	 * @param keys
	 * @return
	 */
	protected boolean isNumericInMap(Map<String, String> map, String... keys) {
		if (map == null) {
			logger.info("map is null");
			return false;
		}
		if (keys == null || keys.length == 0) {
			return false;
		}
		for (String o : keys) {
			if (!isNumeric(map.get(o))) {
				logger.info("isNumericInMap key:" + o + " is not numeric");
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断是否字符串是否超过最大长度
	 * 
	 * @param str
	 * @param maxLength
	 * @return
	 */
	protected boolean isExceedMaxLength(String str, int maxLength) {
		return StringUtil.isExeedMaxLength(str, maxLength);
	}

	/**
	 * 获取代理的Manager
	 * 
	 * @param managerClazz
	 * @return
	 */
	protected <T> T getProxyManager(Class<T> managerClazz) {
		return (T) ManagerProxy.getInstance(managerClazz);
	}
	
	

}
