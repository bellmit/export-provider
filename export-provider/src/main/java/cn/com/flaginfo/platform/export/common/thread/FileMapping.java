package cn.com.flaginfo.platform.export.common.thread;

import java.io.Serializable;
import java.util.Map;

public class FileMapping implements Serializable{
	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;

	private Class workClazz;
	private Map objectMap;

	public Class getWorkClazz() {
		return workClazz;
	}

	public void setWorkClazz(Class workClazz) {
		this.workClazz = workClazz;
	}

	public Map getObjectMap() {
		return objectMap;
	}

	public void setObjectMap(Map objectMap) {
		this.objectMap = objectMap;
	}
}
