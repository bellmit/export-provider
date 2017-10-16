package cn.com.flaginfo.platform.export.model.excel.policy;

import java.util.HashMap;
import java.util.Map;

import cn.com.flaginfo.platform.export.consumer.ManagerProxy;
import cn.com.flaginfo.platform.export.manager.ExportManager;
import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.func.Function;
import cn.com.flaginfo.platform.export.model.excel.func.ProductIdFunction;
import cn.com.flaginfo.platform.export.model.excel.func.TaskStatusFunction;

public class TaskListPolicy extends Policy{

	private ExportManager manager = ManagerProxy.getInstance(ExportManager.class);
	private String[] titles = null;
	private String[] keys = null;
	private ExportTask task;

	private Map<String, Function<Object,Object>> funcMap = new HashMap<>();
	public TaskListPolicy(ExportTask task){
		this.task = task;
		initPolicy();
	}
	
	/**
								{
	"_id" : ObjectId("57d8bb6bc833064b56f05b5d"),
	"app" : "0",
	"sendSource" : "1",
	"messageId" : "16091410522610000042",
	"productId" : "3", 1 短信， 2 彩信 3 e信
	"taskId" : "16091410522610000043",
	"allMdn" : 1,
	"allNum" : 1,
	"succNum" : 0,
	"failNum" : 0,
	"arriveSucc" : 0,
	"arriveFail" : 0,
	"recieveNum" : {
		"uSuccNum" : 0,
		"mSuccNum" : 0,
		"tSuccNum" : 0,
		"uFailNum" : 0,
		"mFailNum" : 0,
		"tFailNum" : 0
	},
	"arriveNum" : {
		"uSuccNum" : 0,
		"mSuccNum" : 0,
		"tSuccNum" : 0,
		"uFailNum" : 0,
		"mFailNum" : 0,
		"tFailNum" : 0
	},
	"textTemplateId" : "",
	"mediaId" : "16090618335915179225",
	"mediaUrl" : "http://mpv.videocc.net/5abb4e62d6/f/5abb4e62d68b475c8b1c7c813dfc7cff_1.mp4",
	"mediaType" : "video",
	"createTime" : "2016-09-14 10:52:26",
	"spId" : "16050516311810006138",
	"userId" : "16050516311810006139",
	"platform" : "1",
	"aduitStatus" : "0",
	"text" : "【开发部】测试发送",
	"sendType" : "1",
	"networkMdn" : {
		"umdn" : 0,
		"mmdn" : 1,
		"tmdn" : 0,
		"imdn" : 0,
		"invalidMdn" : 0,
		"limitMdn" : 0,
		"total" : 1
	},
	"networkNum" : {
		"unum" : 0,
		"mnum" : 1,
		"tnum" : 0,
		"inum" : 0,
		"total" : 1
	},
	"status" : "1", 1 
	"remark" : null,
	"endTime" : "2016-09-14 10:52:29",
	"preFeeTime" : "2016-09-14 10:52:29"
},taskStatus : {"0":"待发送","1":"待审核","2":"发送失败","3":"发送中","4":"发送成功"},
    arriveResult : {"0":"成功","-1":"等待回执"},
    businessType : {"1":"短信","2":"彩信","3":"E信","4":"彩E信","5":"语音","6":"流量","7":"功能费","8":"长主题"}
	 */
	@Override
	public void initPolicy() {
		this.setApiUrl("cmc_task_list");
		this.setApiVersion("1.0");
		titles = new String[]{ "任务编号", "企业编号","企业名称","类型","任务状态","总人数","总条数","提交成功数","提交失败数","成功到达数","失败到达数","提交时间","完成时间"};
		keys = new String[]{"taskId","spCode","spName","productId","status","allMdn","allNum","succNum","failNum","arriveSucc","arriveFail","createTime","endTime"};
		funcMap.put("status", new TaskStatusFunction());
		funcMap.put("productId", new ProductIdFunction());
		initColumns(titles, keys, funcMap);
		this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("taskListTask"));
	}

	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		// TODO Auto-generated method stub
		return null;
	}
}
