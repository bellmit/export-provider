package cn.com.flaginfo.platform.export.common.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import cn.com.flaginfo.platform.common.bean.BeanUtil;
import cn.com.flaginfo.platform.common.client.JsonApiClient;
import cn.com.flaginfo.platform.common.jdbc.DBOperation;
import cn.com.flaginfo.platform.common.jdbc.IDGen;
import cn.com.flaginfo.platform.common.jdbc.OperationFactory;
import cn.com.flaginfo.platform.common.jdbc.Table;
import cn.com.flaginfo.platform.common.redis.Cache;
import cn.com.flaginfo.platform.common.support.SessionHolder;
import cn.com.flaginfo.platform.common.util.StringUtil;
import cn.com.flaginfo.platform.common.util.SystemMessage;

/**
 * 基础方法不涉及业务熟悉
 * @author Rain
 *
 */
public class BaseService {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IDGen idGen;
	
	protected DBOperation createDBOperation(String sql){
		return OperationFactory.getDBOperator().appendSql(sql);
	}
	
	protected DBOperation createDBOperation(String sql,String dbAlias){
		return OperationFactory.getDBOperator().appendSql(sql).setDBAlias(dbAlias);
	}
	
	protected DBOperation createDBOperation(){
		return createDBOperation("");
	}
	
	public <T> T getSessionHolder(Class<T> clazz){
		return SessionHolder.get(clazz);
	}
	
	public Map getMap(String table,Object id){
		if(id == null){
			return null;
		}
		return createDBOperation("select * from "+table+" where id=:id")
		.setParameter("id",id,true)
		.uniqueResult(Map.class)
		;
	}
	
	
	public <T> T get(Class<T> clazz,Object id){
		if(id == null){
			return null;
		}
		Table table = clazz.getAnnotation(Table.class);
		return createDBOperation("select * from "+table.name()+" where id=:id")
		.setParameter("id",id,true)
		.uniqueResult(clazz)
		;
	}
	
	/**
	 * 保存没有主键的对象
	 * @param o
	 */
	public void saveNoId(Object o){
		Table table = o.getClass().getAnnotation(Table.class);
		this.createDBOperation().save(o,table.name());
	}
	
	
	/**
	 * 保存没有主键的对象
	 * @param o
	 */
	public void saveNoId(Map<String,Object> o,String tableName){
		this.createDBOperation().save(o,tableName);
	}
	
	/**
	 * 保存对象，默认主键字段为ID，如果没有该值，自动加入
	 * @param o
	 * @param tableName
	 * @return
	 */
	public String save(Object o,String tableName){
		Assert.notNull(o);
		Map<String,Object> insertObject = null;
		if(o instanceof Map){
			insertObject = (Map)o;
		}else{
			insertObject = BeanUtil.convertBeanToDBMap(o);
		}
		String id = (String)insertObject.get("id");
		if(StringUtil.isNullOrEmpty(id)){
			id = idGen.nextId();
			insertObject.put("id",id);
		}
		this.createDBOperation().save(insertObject, tableName);
		return id;
	}
	
	public String save(Object o){
		Table table = o.getClass().getAnnotation(Table.class);
		return this.save(o,table.name());
	}
	
	public void update(Object o){
		Table table = o.getClass().getAnnotation(Table.class);
		createDBOperation().update(o,table.name());
	}
	
	public void update(Map<String,Object> setValues,Map<String,Object> condValues,String table){
		createDBOperation().update(setValues, condValues, table);
	}
	
	public void updateById(Map<String,Object> setValues,Map<String,Object> condValues,String table,boolean b){
		Map<String,Object> teMap=new HashMap<String,Object>();
		teMap.put("id", condValues.get("id"));
		createDBOperation().update(setValues, teMap, table);
	}
	
	public void update(Map<String,Object> setValues,String table){
		createDBOperation().update(setValues,table);
	}
	
	/**
     * 获取平台的短名称，例如zx,yn等
     * @param platform
     * @return
     */
    public String getPfShortName(String platform) {
    	Map<String,String> pfShortNameMap = getPfShortNameMap();
    	return pfShortNameMap.get(platform);
    }
    
    /**
     * 查询所有的平台的短名称
     * @return 返回Map<pf,shortName>
     */
    @Cache(cacheKey="pf_short_name",ignoreNull=true)
    public Map<String,String> getPfShortNameMap() {
    	
    	Map<String,String> pfShortNameMap = new HashMap<String,String>();
    	
		JsonApiClient client = new JsonApiClient();
		client.setApiKey(SystemMessage.getString("server_api_key"));
		client.setReqUrl("global_platform_get");
		client.setVersion("1.0");
		
		Map<String,Object> responseMap = client.post(Map.class);
		if(responseMap == null){
			return null;
		}
		List<Map> platformList = (List)responseMap.get("list");
		
		for(Map m:platformList){
			pfShortNameMap.put((String)m.get("id"),(String)m.get("shortName"));
		}
		
		return pfShortNameMap;
		
    }
	
}
