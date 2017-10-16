package cn.com.flaginfo.platform.export.model.excel.policy;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import cn.com.flaginfo.platform.common.client.JsonApiClient;
import cn.com.flaginfo.platform.common.support.Constants;
import cn.com.flaginfo.platform.common.util.StringUtil;
import cn.com.flaginfo.platform.common.util.SystemMessage;
import cn.com.flaginfo.platform.export.manager.ExportManager;
import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.func.ContactsAttrFunction;
import cn.com.flaginfo.platform.export.model.excel.func.ContactsGroupFunction;
import cn.com.flaginfo.platform.export.model.excel.func.Function;

public class ContactsPolicy extends Policy{

//	ExportManager manager = ManagerProxy.getInstance(ExportManager.class);
	@Autowired
	private ExportManager manager;
	private String[] titles = null;
	private String[] keys = null;
	private Map<String,Function<Object, Object>> funcMap = new HashMap<>();
	private ExportTask task;
	
	private Map<String,Object> groupIdMap = new HashMap<>();//{groupId,{groupInfo}}
	
	public ContactsPolicy (ExportTask task){
		this.task = task;
		initPolicy();
	}
	
	@Override
	public void initPolicy() {
		this.setApiUrl("contacts_member_list_aggregate");
		this.setApiVersion("1.0");
		//初始化需要导出的属性（数据列）
		initAttr();
		initColumns(titles, keys, funcMap);
		this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("contactsTask"));
	}
	
	public void initAttr(){
		Map<String,Object> requestParam = task.getRequestParam();
		List<String> attrList = (List<String>) requestParam.get("attrList");
		JsonApiClient jsonClient = new JsonApiClient();
		String apiKey = SystemMessage.getString("server_api_key");
		jsonClient.setApiKey(apiKey);
		jsonClient.setVersion("1.0");
        jsonClient.setReqUrl("contacts_member_attr_list_all");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("contactsId", requestParam.get("contactsId"));
        jsonClient.setReqMap(params);
        Map<String, Object> result = jsonClient.post(Map.class);
        List<String> titleList = new ArrayList<>();
        List<String> keyList = new ArrayList<>();
        if(!StringUtil.isNullOrEmptyInMap(result, "list")) {
            List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("list");
            for (Map<String, Object> map : list) {
                String enName = (String)map.get("enName");
                String name = (String)map.get("name");
                //是否自定义导出内容
                if (attrList!=null && !attrList.contains(enName)) {
                    continue;
                }
                titleList.add(name);
                keyList.add(enName);
                
                //多选项处理
                if (Constants.YES.equals(map.get("multiValue"))) {
                	ContactsAttrFunction attrFunc = new ContactsAttrFunction();
                    List<Map<String,String>> options = (List<Map<String,String>>)map.get("options");
                    Map<String,String> optionMap = new HashMap<>();
                    for (Map<String, String> m : options) {
                    	optionMap.put(m.get("optionId"), m.get("optionName"));
					}
                    attrFunc.setOptionMap(optionMap);
                    funcMap.put(enName, attrFunc);
                }
            }
        }
        titleList.add("通讯录组织");
        keyList.add("groupList");
        ContactsGroupFunction groupFunc = new ContactsGroupFunction();
        groupFunc.setGroupMap(getGroupMap());
        funcMap.put("groupList", groupFunc);
        titles = titleList.toArray(new String[]{});
        keys = keyList.toArray(new String[]{});
	}
	
	public Map<String,String> getGroupMap(){
		Map<String,Object> requestParam = task.getRequestParam();
		JsonApiClient jsonClient = new JsonApiClient();
		jsonClient.setVersion("1.0");
        jsonClient.setReqUrl("contacts_group_list");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("contactsId", requestParam.get("contactsId"));
        jsonClient.setReqMap(params);
        Map<String, Object> result = jsonClient.post(Map.class);
        Map<String,String> groupMap = new HashMap<>();
        if(!StringUtil.isNullOrEmptyInMap(result, "list")) {
            List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("list");
            for (Map<String, Object> map : list) {
            	groupIdMap.put((String)map.get("id"), map);
            }
            for (Map<String, Object> map : list) {
				String groupId = (String)map.get("id");
				String content = getGroupPath(groupId, "");
				groupMap.put(groupId, content!=null?content.substring(1):"");
			}
        }
        return groupMap;
	}
	
	public String getGroupPath(String groupId,String childPath){
		Map<String,String> group = (Map<String,String>)groupIdMap.get(groupId);
		String pid = group.get("pid");
		String groupName = group.get("name");
		String path = "/" + groupName;
		if("0".equals(pid) || StringUtil.isNullOrEmpty(pid)){
			return path + childPath;
		}
		String groupPath = getGroupPath(pid, path);
		return groupPath;
	}

	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		// TODO Auto-generated method stub
		return null;
	}

}
