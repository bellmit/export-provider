package cn.com.flaginfo.platform.export.common.base;

import com.google.common.collect.ImmutableMap;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局常量
 * 
 * @author Rain
 *
 */
public class Constants {

	/**
	 * 人
	 */
	public static final String YXT_MEMBER_TYPE_PERSON = "1";
	/**
	 * 组
	 */
	public static final String YXT_MEMBER_TYPE_GROUP = "0";
	/**
	 * 通用的值:NO
	 */
	public static final String NO = "0";
	/**
	 * 通用的值:YES
	 */
	public static final String YES = "1";

	/**
	 * 微信平台
	 */
	public static final String WX_PF = "0";

	/**
	 * UTF-8字符集
	 */
	public static final Charset UTF8 = Charset.forName("UTF-8");

	/** 待发送 */
	public static final String SEND_TASK_HOLD = "0";
	/** 待审核 */
	public static final String SEND_TASK_WAITING = "1";
	/** 发送失败 */
	public static final String SEND_TASK_FAIL = "2";
	/** 发送中 */
	public static final String SEND_TASK_SENDING = "3";
	/** 发送成功 */
	public static final String SEND_TASK_OVER = "4";

	public final static Map<String, String> TASK_STATUS_MAP = new HashMap<String, String>();
	
	public final static Map<String,String> ACCEPTANCE_PROCESS_STATUS_MAP = new HashMap<String,String>();
	
	static {
		TASK_STATUS_MAP.put(SEND_TASK_HOLD, "待发送");
		TASK_STATUS_MAP.put(SEND_TASK_WAITING, "待审核");
		TASK_STATUS_MAP.put(SEND_TASK_FAIL, "发送失败");
		TASK_STATUS_MAP.put(SEND_TASK_SENDING, "发送中");
		TASK_STATUS_MAP.put(SEND_TASK_OVER, "发送成功");
	}
	
	/**
	 * 提交审核
	 */
	public static final String ACCEPTANCE_PROCESS_TYPE_SUBMIT="1";
	/**
	 * 审核通过
	 */
	public static final String ACCEPTANCE_PRCESS_TYPE_AUIT_PASS="2";
	/**
	 * 审核不通过
	 */
	public static final String ACCEPTANCE_PRCESS_TYPE_AUIT_NOT_PASS="3";
	/**
	 * 资质审核通过
	 */
	public static final String ACCEPTANCE_PRCESS_TYPE_AUIT_QUAL_PASS="4";
	/**
	 * 资质审核不通过
	 */
	public static final String ACCEPTANCE_PRCESS_TYPE_AUIT_QUAL_NOT_PASS="5";
	/**
	 * 业务配置
	 */
	public static final String ACCEPTANCE_PRCESS_TYPE_BUS_SET="6";
	
	/**
	 * 删除
	 */
	public static final String ACCEPTANCE_PRCESS_TYPE_DELETE="9";


	static {
		ACCEPTANCE_PROCESS_STATUS_MAP.put(ACCEPTANCE_PROCESS_TYPE_SUBMIT, "提交审核");
		ACCEPTANCE_PROCESS_STATUS_MAP.put(ACCEPTANCE_PRCESS_TYPE_AUIT_PASS, "审核通过");
		ACCEPTANCE_PROCESS_STATUS_MAP.put(ACCEPTANCE_PRCESS_TYPE_AUIT_NOT_PASS, "审核不通过");
		ACCEPTANCE_PROCESS_STATUS_MAP.put(ACCEPTANCE_PRCESS_TYPE_AUIT_QUAL_PASS, "资质审核通过");
		ACCEPTANCE_PROCESS_STATUS_MAP.put(ACCEPTANCE_PRCESS_TYPE_AUIT_QUAL_NOT_PASS, "资质审核不通过");
		ACCEPTANCE_PROCESS_STATUS_MAP.put(ACCEPTANCE_PRCESS_TYPE_BUS_SET, "业务配置");
		ACCEPTANCE_PROCESS_STATUS_MAP.put(ACCEPTANCE_PRCESS_TYPE_DELETE, "删除");
	}

	
	/**
	 * 一信通安装信息
	 */
	public static final String YXT_INSTALLED = "1";
	public static final String YXT_NOT_INSTALL = "0";
	/**
	 * 一信通授权信息
	 */
	public static final String YXT_AUTHED = "1";
	public static final String YXT_NOT_AUTH = "0";
	/** 预约发送任务状态：待审核 */
	public static final String CRON_SEND_TASK_WAITING = "0";
	/** 预约发送任务状态：审核通过，待发送 */
	public static final String CRON_SEND_TASK_HOLD = "1";
	/** 预约发送任务状态：审核不通过 */
	public static final String CRON_SEND_TASK_NOT_PASS = "2";
	/** 预约发送任务状态：发送完成 */
	public static final String CRON_SEND_TASK_SENDED = "3";
	/** 预约发送任务状态：已取消 */
	public static final String CRON_SEND_TASK_CANCEL = "4";

	// 审核通过
	public static final String IM_IMESSAGE_STATUS_NORMARL = "1";
	public static final String IM_IMESSAGE_STATUS_DELETE = "2";
	// 创建中
	public static final String IM_IMESSAGE_STATUS_CREATING = "3";
	// 待审核
	public static final String IM_IMESSAGE_STATUS_WAITING = "4";
	// 审核没有通过
	public static final String IM_IMESSAGE_STATUS_NOT_PASS = "5";

	/** 信息类型：文本 **/
	public static final String MESSAGE_TYPE_TEXT = "text";
	/** 信息类型：html **/
	public static final String MESSAGE_TYPE_HTML = "html";
	/** 信息类型：图片 **/
	public static final String MESSAGE_TYPE_IMAGE = "image";
	/** 信息类型：缩略图 **/
	public static final String MESSAGE_TYPE_THUMB = "thumb";
	/** 信息类型：语音 **/
	public static final String MESSAGE_TYPE_VOICE = "voice";
	/** 信息类型：视频 **/
	public static final String MESSAGE_TYPE_VIDEO = "video";
	/** 信息类型 文件 **/
	public static final String MESSAGE_TYPE_FILE = "file";
	/** 信息类型 图文 **/
	public static final String MESSAGE_TYPE_NEWS = "news";
	/** 信息类型 超链接 **/
	public static final String MESSAGE_TYPE_LINK = "link";

	/**
	 * 审核策略类型-平台
	 */
	public static final String AUDIT_POLICY_TYPE_PLATFORM = "1";

	/**
	 * 审核策略类型-接口
	 */
	public static final String AUDIT_POLICY_TYPE_INTERFACE = "2";

	/**
	 * 严格审核
	 */
	public static final String AUDIT_POLICY_STRICT = "1";

	/**
	 * 较严格
	 */
	public static final String AUDIT_POLICY_STRICTER = "2";

	/**
	 * 一般审核
	 */
	public static final String AUDIT_POLICY_NORMAL = "3";
	/**
	 * 较轻审核
	 */
	public static final String AUDIT_POLICY_LIGHTER = "4";
	/**
	 * 免审核
	 */
	public static final String AUDIT_POLICY_NONAUDIT = "5";

	/**
	 * 自定义审核
	 */
	public static final String AUDIT_POLICY_CUSTOM = "99";

	/**
	 * 需要人工审核
	 */
	public static final String AUDIT_ARTIFICIAL = "3";

	/**
	 * 无需审核
	 */
	public static final String AUDIT_NO = "1";

	/**
	 * 审核不通过
	 */
	public static final String AUDIT_NO_PASS = "5";

	/**
	 * 已经审核
	 */
	public static final String AUDIT_HAS_AUDIT = "2";

	/**
	 * 审核异常
	 */
	public static final String AUDIT_ERROR = "9";

	/**
	 * 人工审核通过
	 */
	public static final String MANUALLY_AUDIT_PASS = "1";

	/**
	 * 人工审核不通过
	 */
	public static final String MANUALLY_AUDIT_NO_PASS = "0";

	public static final String AUDIT_RESULT_SUCCESS = "10";

	public static final String AUDIT_RESULT_HAS_AUDIT = "11";

	public static final String AUDIT_RESULT_FAIL = "19";

	public static final String AUDIT_RESULT_IM_DELETE = "12";

	public static Map<String, String> AUDIT_MAP = new HashMap<String, String>();
	public static Map<String, String> AUDIT_POLICY_MAP = new HashMap<String, String>();

	static {
		AUDIT_MAP.put(AUDIT_ARTIFICIAL, "需要人工审核");
		AUDIT_MAP.put(AUDIT_NO, "无需人工审核");
		AUDIT_MAP.put(AUDIT_NO_PASS, "审核不通过");
		AUDIT_MAP.put(AUDIT_HAS_AUDIT, "已经人工审核");
		AUDIT_MAP.put(AUDIT_ERROR, "审核异常");

		AUDIT_MAP.put(AUDIT_RESULT_SUCCESS, "审核成功");
		AUDIT_MAP.put(AUDIT_RESULT_HAS_AUDIT, "已经审核通过");
		AUDIT_MAP.put(AUDIT_RESULT_FAIL, "审核失败，请联系研发");
		AUDIT_MAP.put(AUDIT_RESULT_IM_DELETE, "审核失败，信息已经被删除，发送任务置为失败");

		AUDIT_POLICY_MAP.put(AUDIT_POLICY_STRICT, "严格审核");
		AUDIT_POLICY_MAP.put(AUDIT_POLICY_STRICTER, "较严审核");
		AUDIT_POLICY_MAP.put(AUDIT_POLICY_NORMAL, "一般审核");
		AUDIT_POLICY_MAP.put(AUDIT_POLICY_LIGHTER, "较轻审核");
		AUDIT_POLICY_MAP.put(AUDIT_POLICY_NONAUDIT, "免审核");
		AUDIT_POLICY_MAP.put(AUDIT_POLICY_CUSTOM, "自定义审核");
	}

	public static final String ADMIN_ROLE = "超级管理员";

	public static final String NORMAL_USER_ROLE = "无权限";

	/**
	 * 预付费
	 */
	public static final String FEE_TYPE_BEFORE = "0";

	/**
	 * 后付费
	 */
	public static final String FEE_TYPE_AFTER = "1";
	
	public static Map<String,String> FEE_TYPE_MAP_DESC = new HashMap<String,String>();
	
	static {
		FEE_TYPE_MAP_DESC.put(FEE_TYPE_BEFORE, "预付费");
		FEE_TYPE_MAP_DESC.put(FEE_TYPE_AFTER, "后付费");

	}
	//账户类型 0 正式账户 1 测试账户 2 演示账户 3 联通账户
	public static final String ACCOUNT_TYPE_NORMAL="0";
	public static final String ACCOUNT_TYPE_TEST="1";
	public static final String ACCOUNT_TYPE_DEMO="2";
	public static final String ACCOUNT_TYPE_UNICOM="3";
	
	public static Map<String,String> ACCOUNT_TYPE_MAP = new HashMap<String,String>();
	static {
		ACCOUNT_TYPE_MAP.put(ACCOUNT_TYPE_NORMAL, "正式账户");
		ACCOUNT_TYPE_MAP.put(ACCOUNT_TYPE_TEST, "测试账户");
		ACCOUNT_TYPE_MAP.put(ACCOUNT_TYPE_DEMO, "演示账户");
		ACCOUNT_TYPE_MAP.put(ACCOUNT_TYPE_UNICOM, "联通账户");
	}
	

	/**
	 * 充值付费
	 */
	public static final String FEE_TYPE_REFUND = "2";

	public static final ImmutableMap<String, String> FEE_TYPE_MAP = ImmutableMap.of(FEE_TYPE_BEFORE, "预付费",
			FEE_TYPE_AFTER, "后付费", FEE_TYPE_REFUND, "充值付费");
	/**
	 * 敏感词--禁止类
	 */
	public static final String SENSITIVE_TYPE_PROHIBITED = "2";

	/**
	 * 敏感词--提醒类
	 */
	public static final String SENSITIVE_TYPE_REMIND = "1";

	/**
	 * 敏感词--精确查找
	 */
	public static final String SENSITIVE_RULE_ACCURATE = "1";

	/**
	 * 敏感词--模糊查找
	 */
	public static final String SENSITIVE_RULE_FUZZYWORD = "2";

	/**
	 * 返回结果 执行成功200
	 */
	public final static String RETURN_SUCC = "200";

	/**
	 * 返回结果 执行成功201
	 */
	public final static String RETURN_NO_SP = "201";

	/**
	 * 返回结果 数据异常统一返回502
	 */
	public final static String RETURN_DATA_ERROR = "502";

	/**
	 * 返回结果 数据重复503
	 */
	public final static String RETURN_DOUBLE_ERROR = "503";

	/**
	 * 返回结果 数据违反长度约束503
	 */
	public final static String RETURN_MAX_ERROR = "508";

	/**
	 * 返回结果 数据为空500
	 */
	public final static String RETURN_NULL_ERROR = "500";

	/**
	 * 返回结果 执行失败499
	 */
	public final static String RETURN_EXE_FAIL = "499";

	public final static Map<String, String> RETURN_MSG_MAP = new HashMap<String, String>();

	static {
		RETURN_MSG_MAP.put(RETURN_SUCC, "操作成功");
		RETURN_MSG_MAP.put(RETURN_DATA_ERROR, "数据异常");
		RETURN_MSG_MAP.put(RETURN_DOUBLE_ERROR, "数据重复");
		RETURN_MSG_MAP.put(RETURN_NULL_ERROR, "数据字段有空");
		RETURN_MSG_MAP.put(RETURN_EXE_FAIL, "操作异常");
	}

	/**
	 * 账户扣费类型 0发送扣费;1批价返还;5:充值;6:充值扣除;7:订购;
	 */
	public final static String ACCOUNT_ITEM_TYPE_SEND = "1";

	/**
	 * 账户扣费类型 1 发送 2 语音 3 流量 4订购 5充值 6充值扣除 7发送返还
	 */
	public final static String ACCOUNT_ITEM_TYPE_RETURN = "7";

	/**
	 * 账户扣费类型 0发送扣费;1批价返还;5:充值;6:充值扣除;7:订购;
	 */
	public final static String ACCOUNT_ITEM_TYPE_RECHARGE = "5";

	/**
	 * 账户扣费类型 0发送扣费;1批价返还;5:充值;6:充值扣除;7:订购;
	 */
	public final static String ACCOUNT_ITEM_TYPE_RECHAREG_ADJUEST = "6";

	/**
	 * 正
	 */
	public final static int POSITIVE = 1;

	/**
	 * 负
	 */
	public final static int MINUS = -1;

	/**
	 * 账户扣费类型 0发送扣费;1批价返还;5:充值;6:充值扣除;7:订购;
	 */
	public final static String ACCOUNT_ITEM_TYPE_SUBSCRIBE = "7";

	public final static String PACKAGE_RERIOD_MONTH = "M";

	public final static String PACKAGE_RERIOD_ONCE = "Y";

	/**
	 * 系统-企业ID=0
	 */
	public static final String SYS_SP_ID = "0";

	/**
	 * 企业类型:个人
	 */
	public static final String SP_TYPE_PERSON = "0";

	/**
	 * 企业类型:企业
	 */
	public static final String SP_TYPE_ENTERPRISE = "1";

	/**
	 * 受理单编号生成key
	 */
	public static final String ACCEPTANCE_NO_KEY = "acceptanceNo";
	/**
	 * 企业编号生成KEY
	 */
	public static final String SP_CODE_KEY = "sp_code";
	/**
	 * e信卡卡号
	 */
	public static final String CARD_CODE_KEY = "card_code";
	
	/**
	 * 企业账号生产Key
	 */
	public static final String SP_LOGINNAME_KEY = "sp_loginname";

	/**
	 * 企业状态
	 * 
	 */
	public static final String SP_STATUS_OK = "0";// 正常
	public static final String SP_STATUS_CLOSE = "1";// 销户
	public static final String SP_STATUS_FROZEN = "2";// 冻结
	public static final String SP_STATUS_OPEN = "3";// 新开户
	public static final String SP_STATUS_AUDITING = "4";// 资质审核
	public static final String SP_STATUS_AUDITING_FAIL = "5";// 资质审核失败

	/**
	 * 企业账户状态
	 */
	public static final String SP_ACCOUNT_OK = "0";// 正常

	public static final String SP_ACCOUNT_STOP = "2";// 停机

	public static final String SP_ACCOUNT_FROZEN = "3";// 冻结

	public static final String SP_ACCOUNT_CLOSE = "4";// 关闭
	
	
	
	public static final String OP_TYPE_ADD ="add";
	public static final String OP_TYPE_UPDATE ="update";
	public static final String OP_TYPE_DELETE ="delete";

	
	
	public static final Map<String,String> SP_ACCOUNT_STATUS_MAP = new HashMap<String,String>();
	
	static {
		SP_ACCOUNT_STATUS_MAP.put(SP_ACCOUNT_OK, "正常");
		SP_ACCOUNT_STATUS_MAP.put(SP_ACCOUNT_STOP, "停机");
		SP_ACCOUNT_STATUS_MAP.put(SP_ACCOUNT_FROZEN, "冻结");
		SP_ACCOUNT_STATUS_MAP.put(SP_ACCOUNT_CLOSE, "关闭");
	}

	public static final Map<String, String> SP_STATUS_MAP = new HashMap<String, String>();
	static {
		SP_STATUS_MAP.put(SP_STATUS_OK, "正常");
		SP_STATUS_MAP.put(SP_STATUS_CLOSE, "销户");
		SP_STATUS_MAP.put(SP_STATUS_OPEN, "新开户");
		SP_STATUS_MAP.put(SP_STATUS_FROZEN, "冻结");
		SP_STATUS_MAP.put(SP_STATUS_AUDITING, "审核");
		SP_STATUS_MAP.put(SP_STATUS_AUDITING_FAIL, "审核失败");

	}

	/**
	 * 企业类型：0 正常 1 测试账户 2 演示账户 3 联通账户
	 */
	public static final String SP_TYPE_NORMAL = "0";
	public static final String SP_TYPE_TEST = "1";
	public static final String SP_TYPE_DEMO = "2";
	public static final String SP_TYPE_UNICOM = "3";
	public static final ImmutableMap<String, String> SP_TYPE_MAP = ImmutableMap.of(SP_TYPE_NORMAL, "正式账户", SP_TYPE_TEST,
			"测试账户", SP_TYPE_DEMO, "演示账户 ", SP_TYPE_UNICOM, "联通账户");

	public static final String spStatus = "1";// , "spStatus");//1 企业状态变更
												// im_sp_info
	public static final String spSign = "2"; // "spSign");// 2 签名
												// IM_SP_INFO_EXTEND
	public static final String spFeeTelephone = "3"; // "spFeeTelephone");//
														// im_sp_info 3 计费手机号
	public static final String unicomPrefixNum = "41"; // "unicomPrefixNum");//联通前缀
														// IM_SP_INFO_EXTEND
	public static final String unicomSuffixNum = "42"; // "unicomSuffixNum");//联通后缀
	public static final String mobilPrefixNum = "43"; // "mobilPrefixNum");//移动前缀
	public static final String mobilSuffixNum = "44";// "mobilSuffixNum");//移动后缀
	public static final String telPrefixNum = "45";// "telPrefixNum");//电信前缀
	public static final String telSuffixNum = "46";// "telSuffixNum");//电信后缀
	public static final String isSyncCost = "5";// "isSyncCost");// 5 同步成本设置
												// unicom_gm 表中有数据 是 0 不开启；没有是 1
												// 开启
	public static final String spFeeType = "6";// "spFeeType");// 6 计费类型
												// im_sp_info
	public static final String spType = "7";// "spType");// 7 账户类型 im_sp_info
	public static final String contactname = "8";// "contactname");// 8 联系人姓名
													// im_sp_info
	public static final String spTelephone = "9";// "spTelephone");// 9 联系人手机
													// im_sp_info
	public static final String spCustomermanager = "10";// "spCustomermanager");//
														// 10 客户经理 im_sp_info
	public static final String name = "11";// "name");// 11 渠道商销售姓名
											// salesManagerName im_sp_vs_sale ?
	public static final String mdn = "12";// "mdn");// 12 渠道商销售号码 找不到
											// salesManagerMdn im_sp_vs_sale ?
	public static final String isNoaudit = "13";// "");//白名单
	/**
	 * 代理商状态
	 */
	public static final String AGENT_STATUS_OK = "0";// 正常
	public static final String AGENT_STATUS_LOCK = "1";// 冻结
	public static final String AGENT_STATUS_CLOSE = "0";// 销户

	public static final String SP_ACCEPTANCE_INIT = "0";
	public static final String SP_ACCEPTANCE_WAIT_AUDIT = "1";
	public static final String SP_ACCEPTANCE_AUDIT_NOPASS = "2";
	public static final String SP_ACCEPTANCE_OPEN_FAIL = "3";
	public static final String SP_ACCEPTANCE_WAIT_QUALIFICATION = "4";
	public static final String SP_ACCEPTANCE_QUALIFICATION_NOPASS = "5";
	public static final String SP_ACCEPTANCE_WAIT_CONFIG = "6";
	public static final String SP_ACCEPTANCE_OPEN_SUCC = "7";
	public static final String SP_ACCEPTANCE_DELETE = "9";
	
	
	public static final Map<String, String> SP_ACCEPTANCE_STATUS_MAP = new HashMap<String, String>();
	static {
		SP_ACCEPTANCE_STATUS_MAP.put(SP_ACCEPTANCE_INIT, "草稿");
		SP_ACCEPTANCE_STATUS_MAP.put(SP_ACCEPTANCE_WAIT_AUDIT, "等待开户审核");
		SP_ACCEPTANCE_STATUS_MAP.put(SP_ACCEPTANCE_AUDIT_NOPASS, "开户审核不通过");
		SP_ACCEPTANCE_STATUS_MAP.put(SP_ACCEPTANCE_OPEN_FAIL, "开户失败");
		SP_ACCEPTANCE_STATUS_MAP.put(SP_ACCEPTANCE_WAIT_QUALIFICATION, "等待资质审核");
		SP_ACCEPTANCE_STATUS_MAP.put(SP_ACCEPTANCE_QUALIFICATION_NOPASS, "资质审核不通过");
		SP_ACCEPTANCE_STATUS_MAP.put(SP_ACCEPTANCE_WAIT_CONFIG, "等待业务配置");
		SP_ACCEPTANCE_STATUS_MAP.put(SP_ACCEPTANCE_OPEN_SUCC, "开户成功");
		SP_ACCEPTANCE_STATUS_MAP.put(SP_ACCEPTANCE_DELETE, "删除");


	}

	public static final String SP_ACCEPTANCE_PROCESS_APPLY = "1";//提交审核
	public static final String SP_ACCEPTANCE_PROCESS_AUDIT = "2";//开户审核
	public static final String SP_ACCEPTANCE_PROCESS_QUALIFICATION = "3";//资质审核
	public static final String SP_ACCEPTANCE_PROCESS_CONFIG = "4";//配置业务
	public static final String SP_ACCEPTANCE_PROCESS_DELETE = "9";//删除 

	public static final String SP_ACCEPTANCE_SOURCE_OP = "0";// 运营
	public static final String SP_ACCEPTANCE_SOURCE_OTHER = "1";// 其他
	public static final String SP_ACCEPTANCE_SOURCE_DP = "2";// 代理商
	public static final String SP_ACCEPTANCE_SOURCE_API = "3";// 联通

	// 业务类型常量，与数据库一致
	public static final String NM_SERVICE_TYPE_ADVERT = "1";
	public static final String NM_SERVICE_TYPE_BILL = "2";
	public static final String NM_SERVICE_TYPE_QUIZ = "3";
	public static final String NM_SERVICE_TYPE_DOWNLOAD = "4";
	public static final String NM_SERVICE_TYPE_INVEST = "5";
	public static final String NM_SERVICE_TYPE_PRODUCT = "6";
	public static final String NM_SERVICE_TYPE_PUBLIC = "7";

	public static final String TABLE_PREFIX_VOTE = "sp_vote_mdn_regular_";
	public static final String TEMP_TABLE_CONSUME_HR = "TEMP_CONSUME_HR_";
	public static final String TEMP_TABLE_CONSUME_DAY = "TEMP_CONSUME_DAY_";
	public static final String TABLE_PREFIX_SP_SEND_STAT = "SP_SEND_RECIEPT_";

	public static final String TABLE_PREFIX_SEND_TASK = "SP_SEND_TASK_";

	public static final String TABLE_PREFIX_CONSUME = "SP_CHARGE_CONSUME_";
	public static final String TABLE_PREFIX_IMESSAGE = "SP_IMESSAGE_";
	public static final String TABLE_PREFIX_IMESSAGE_MAP = "SP_IMESSAGE_MAP_";
	public static final String TABLE_PREFIX_SP_USER = "SP_NET_USER_";
	public static final String TABLE_PREFIX_WHITE_OBJET = "SP_white_and_object_";
	public static final String TABLE_IMPORT_USER_LOG = "im_import_users_log";

	/**
	 * 运营商折扣率
	 */
	public static final String POLICY_TYPE_OPERATOR = "1";

	/**
	 * 按漫游类型折扣
	 */
	public static final String POLICY_TYPE_ROAMING = "2";

	/**
	 * 按商品折扣
	 */
	public static final String POLICY_TYPE_GOODS = "3";

	/**
	 * 帜讯
	 */
	public static final String PLATFORM_ZX = "1";

	/**
	 * 湖北
	 */
	public static final String PLATFORM_BH = "2";

	/**
	 * 浙江
	 */
	public static final String PLATFORM_ZJ = "3";

	/**
	 * 江西
	 */
	public static final String PLATFORM_JX = "4";

	/**
	 * 上海
	 */
	public static final String PLATFORM_SH = "5";

	/**
	 * 江苏
	 */
	public static final String PLATFORM_JS = "6";

	/**
	 * 北京
	 */
	public static final String PLATFORM_BJ = "7";

	/**
	 * 天津
	 */
	public static final String PLATFORM_TJ = "8";

	/**
	 * 重庆
	 */
	public static final String PLATFORM_CQ = "9";

	/**
	 * 河北
	 */
	public static final String PLATFORM_HB = "10";

	/**
	 * 北京
	 */
	public static final String PLATFORM_HN = "11";

	/**
	 * 云南
	 */
	public static final String PLATFORM_YN = "12";

	/**
	 * 辽宁
	 */
	public static final String PLATFORM_LN = "13";

	/**
	 * 黑龙江
	 */
	public static final String PLATFORM_HL = "14";

	/**
	 * 湖南
	 */
	public static final String PLATFORM_HU = "15";

	/**
	 * 甘肃
	 */
	public static final String PLATFORM_GS = "21";

	/**
	 * 福建
	 */
	public static final String PLATFORM_FJ = "26";

	/**
	 * 四川
	 */
	public static final String PLATFORM_SC = "29";

	/**
	 * 海南
	 */
	public static final String PLATFORM_HAINAN = "32";
	/**
	 * 宁夏
	 */
	public static final String PLATFORM_NX = "33";

	/**
	 * 陕西
	 */
	public static final String PLATFORM_SHANXI = "24";

	/**
	 * 山东
	 */
	public static final String PLATFORM_SD = "17";
	
	
	/**
	 * 四川短信通 
	 */
	public static final String PLATFORM_SCDXT="101";

	/**
	 * 广东
	 */
	public static final String PLATFORM_GD = "28";

	/**
	 * 业务类型ID 短信
	 */
	public static final String PRODUCT_DX = "1";

	/**
	 * 业务类型ID 国际短信
	 */
	public static final String PRODUCT_INTL = "8";

	/**
	 * 业务类型：彩信
	 */
	public static final String PRODUCT_CX = "2";
	
	/**
	 * 业务类型ID 物联网卡月租
	 */
	public static final String PRODUCT_WLW = "15";
	
	/**
	 * 业务类型ID 短信上行
	 */
	public static final String PRODUCT_DXSX = "16";
	
	/**
	 * 业务类型ID 三要素验真
	 */
	public static final String PRODUCT_SYS = "17";
	
	/**
	 * 业务类型ID 视频流量
	 */
	public static final String PRODUCT_SPLL = "18";

	/**
	 * 业务类型ID E信
	 */
	public static final String PRODUCT_EX = "3";
	public static final String PRODUCT_HH = "0";// 混合套餐，按E信算

	/**
	 * 业务类型微信
	 */
	public static final String PRODUCT_WX = "4";

	/**
	 * 业务类型彩E信
	 */
	public static final String PRODUCT_CEX = "5";

	/**
	 * 业务类型yxt
	 */
	public static final String PRODUCT_YXT = "6";

	/**
	 * 业务类型彩流量
	 */
	public static final String PRODUCT_LL = "7";

	/**
	 * 业务类型：E信
	 */
	public static final String PROTOCAL_EX = "1";

	/**
	 * 业务类型ID 短信
	 */
	public static final String PROTOCAL_DX = "2";

	/**
	 * 业务类型ID 彩信
	 */
	public static final String PROTOCAL_CX = "3";

	/**
	 * 业务类型微信
	 */
	public static final String PROTOCAL_WX = "4";

	/**
	 * 业务类型彩E信
	 */
	public static final String PROTOCAL_CEX = "5";

	/**
	 * 业务类型ID 长主题
	 */
	public static final String PRODUCT_CZT = "-1";

	public static final Map PRODUCT_MAP = new HashMap();

	static {
		PRODUCT_MAP.put(PRODUCT_DX, "短信");
		PRODUCT_MAP.put(PRODUCT_CX, "彩信");
		PRODUCT_MAP.put(PRODUCT_EX, "E信");
		PRODUCT_MAP.put(PRODUCT_HH, "E信");
		PRODUCT_MAP.put(PRODUCT_WX, "微信");
		PRODUCT_MAP.put(PRODUCT_CZT, "长主题");
		PRODUCT_MAP.put(PRODUCT_CEX, "彩E信");
		PRODUCT_MAP.put(PRODUCT_YXT, "app");
		PRODUCT_MAP.put(PRODUCT_LL, "流量");
		PRODUCT_MAP.put(PRODUCT_INTL, "国际短信");
		PRODUCT_MAP.put(PRODUCT_WLW, "物联网卡月租 ");
		PRODUCT_MAP.put(PRODUCT_DXSX, "短信上行");
		PRODUCT_MAP.put(PRODUCT_SYS, "三要素验真");
		PRODUCT_MAP.put(PRODUCT_SPLL, "视频流量");

	}

	public static final Map<String, String> PRODUCT_PROTOCAL_MAP = new HashMap<String, String>();

	static {
		PRODUCT_PROTOCAL_MAP.put(PRODUCT_DX, PROTOCAL_DX);
		PRODUCT_PROTOCAL_MAP.put(PRODUCT_EX, PROTOCAL_EX);
		PRODUCT_PROTOCAL_MAP.put(PRODUCT_WX, PROTOCAL_WX);
		PRODUCT_PROTOCAL_MAP.put(PRODUCT_CX, PROTOCAL_CX);
		PRODUCT_PROTOCAL_MAP.put(PRODUCT_CEX, PROTOCAL_CEX);
		PRODUCT_PROTOCAL_MAP.put(PRODUCT_YXT, PROTOCAL_EX);
		PRODUCT_PROTOCAL_MAP.put(PRODUCT_WLW, PRODUCT_WLW);
		PRODUCT_PROTOCAL_MAP.put(PRODUCT_DXSX, PRODUCT_DXSX);
		PRODUCT_PROTOCAL_MAP.put(PRODUCT_SYS, PRODUCT_SYS);
		PRODUCT_PROTOCAL_MAP.put(PRODUCT_SPLL, PRODUCT_SPLL);
	}

	/*
	 * static{ Field fs[]=Constants.class.getFields(); Map<String,Object> map =
	 * new HashMap<String,Object>(); for(Field f:fs){ String key = f.getName();
	 * try { Object o = f.get(key); map.put(key, o); } catch (Exception e) {
	 * e.printStackTrace(); //throw new DataException(e); } }
	 * 
	 * }
	 */

	/**
	 * 客户通讯录
	 */
	public static final String CONTACT_CUSTOMER = "1";
	/**
	 * 个人通讯录
	 */
	public static final String CONTACT_PERSIONAL = "2";
	/**
	 * 企业通讯录
	 */
	public static final String CONTACT_EPMA = "3";
	/**
	 * 微信
	 */
	public static final String CONTACT_WX = "4";
	/**
	 * 图片库
	 */
	public static final String MATERIAL_IMAGE = "1";
	/**
	 * 文件库
	 */
	public static final String MATERIAL_FILE = "2";
	/**
	 * 视频库
	 */
	public static final String MATERIAL_VIDEO = "3";
	/**
	 * 音频库
	 */
	public static final String MATERIAL_AUDIO = "4";
	/**
	 * 短语库
	 */
	public static final String MATERIAL_COMMONMSG = "5";

	// im_user_oauth 中oauth_key的值
	// 黑名单编辑开关key
	public static final String OAUTH_KEY_BLACK_EDIT = "black_edit";
	// 账户查看
	public static final String OAUTH_KEY_ACCOUNT_VIEW = "account_view";
	// 日志查询
	public static final String OAUTH_KEY_LOG_SEARCH = "log_search";
	// 通讯录编辑
	public static final String OAUTH_KEY_CONTACT_EDIT = "contact_edit";
	// 回复列表
	public static final String OAUTH_KEY_REPLY_LIST = "reply_list";

	/**
	 * 短信 2
	 */
	public static final String PRODUCT_TYPE_SMS = "2";
	/**
	 * E信 1
	 */
	public static final String PRODUCT_TYPE_EMS = "1";
	/**
	 * 彩信 3
	 */
	public static final String PRODUCT_TYPE_MMS = "3";
	/**
	 * 彩E信 4
	 */
	public static final String PRODUCT_TYPE_CEX = "4";

	/**
	 * APP
	 */
	public static final String PRODUCT_TYPE_APP = "6";

	/**
	 * 信息类型与业务对应关系
	 */
	public static final Map<String, String> TYPE_PRODUCT_MAP = new HashMap<String, String>();

	static {
		TYPE_PRODUCT_MAP.put(PRODUCT_TYPE_SMS, PRODUCT_DX);
		TYPE_PRODUCT_MAP.put(PRODUCT_TYPE_EMS, PRODUCT_EX);
		TYPE_PRODUCT_MAP.put(PRODUCT_TYPE_MMS, PRODUCT_CX);
		TYPE_PRODUCT_MAP.put(PRODUCT_TYPE_CEX, PRODUCT_CEX);
	}

	/** 网络类型1：联通；2：移动；3：电信 **/
	public static final String NETWORK_TYPE_UNICOM = "1";

	/** 网络类型1：联通；2：移动；3：电信 **/
	public static final String NETWORK_TYPE_MOBILE = "2";

	/** 网络类型1：联通；2：移动；3：电信 **/
	public static final String NETWORK_TYPE_TELECOM = "3";

	/** 网络类型1：联通；2：移动；3：电信;4:国际 **/
	public static final String NETWORK_TYPE_INTL = "4";

	/**
	 * 通道类型1：短信通道；2：彩信通道
	 */
	public static final String SP_CHANNEL_TYPE_SMS = "1";

	/**
	 * 通道类型1：短信通道；2：彩信通道
	 */
	public static final String SP_CHANNEL_TYPE_MMS = "2";

	/**
	 * 通道类型1：短信通道；2：彩信通道；3：国际通道
	 */
	public static final String SP_CHANNEL_TYPE_INTL = "3";
	/**
	 * 接口服务申请处理过程 1，提交审核；2，接口审核；3，接口申请表审核；4，删除
	 */
	public static final String API_PROCESS_STATUS_SUBMIT = "1";
	public static final String API_PROCESS_STATUS_AUDIT = "2";
	public static final String API_PROCESS_STATUS_FILE_AUDIT = "3";
	public static final String API_PROCESS_STATUS_DELETE = "4";
	public static final Map<String, String> API_PROCESS_MAP = new HashMap<String, String>();
	static {
		API_PROCESS_MAP.put(API_PROCESS_STATUS_SUBMIT, "提交审核");
		API_PROCESS_MAP.put(API_PROCESS_STATUS_AUDIT, "接口审核");
		API_PROCESS_MAP.put(API_PROCESS_STATUS_FILE_AUDIT, "接口申请表审核");
		API_PROCESS_MAP.put(API_PROCESS_STATUS_DELETE, "删除");
	}

	/**
	 * 个人通讯录学历
	 */
	public static final Map<String, String> CONTACT_EDUCATION_MAP = new HashMap<String, String>();

	static {
		CONTACT_EDUCATION_MAP.put("初中及以下", "1");
		CONTACT_EDUCATION_MAP.put("高中", "2");
		CONTACT_EDUCATION_MAP.put("大专及本科", "3");
		CONTACT_EDUCATION_MAP.put("硕士研究生", "4");
		CONTACT_EDUCATION_MAP.put("博士研究生", "5");
		CONTACT_EDUCATION_MAP.put("其它", "6");
	}

	/**
	 * 通讯录成员状态
	 */
	public static final String CONTACTS_MEMBER_STATUS_NORMAL = "1";// 正常
	public static final String CONTACTS_MEMBER_STATUS_RECYCLE = "0";// 回收站
	public static final String CONTACTS_MEMBER_STATUS_DELETE = "9";// 删除

	/**
	 * 通讯录系统组
	 */
	public static final String CONTACTS_UNGROUPED_ID = "1";// 未分组
	public static final String CONTACTS_UNGROUPED_NAME = "未分组";// 未分组
	public static final String CONTACTS_RECYCLE_GROUP_ID = "2";// 回收站
	public static final String CONTACTS_RECYCLE_GROUP_NAME = "回收站";// 回收站

	/**
	 * 通讯录成员查询类型
	 */
	public static final String CONTACT_MEMBER_QUERY_TYPE_YXT = "1";// 企业通讯录
	public static final String CONTACT_MEMBER_QUERY_TYPE_WX = "2";// 微信通讯录

	/**
	 * 通讯录成员添加方式
	 */
	public static final String CONTACT_MEMBER_ADD_TYPE_SINGLE = "1";// 单条添加
	public static final String CONTACT_MEMBER_ADD_TYPE_IMPORT = "2";// 导入
	/**
	 * 通讯录导入日志类型
	 */
	public static final String CONTACT_IMPORT_LOG_TYPE_YXT = "4";// 通讯录导入日志类型

	/**
	 * 用户短语类型
	 */
	public static final String SMS_COMMON_MESSAGE_TYPE = "4";

	/**
	 * 通道状态
	 */
	public static final Map<String, String> CHAN_STATUS = new HashMap<String, String>();
	public static final String CHAN_STATUS_ACTIVE = "1"; // 启用
	public static final String CHAN_STATUS_STOP = "0"; // 停用
	public static final String CHAN_STATUS_EXCEP = "3"; // 异常
	public static final String CHAN_STATUS_SUSPEND = "4"; // 短暂停用
	public static final String CHAN_STATUS_TO_TEST = "5"; // 待测试
	public static final String CHAN_STATUS_TESTING = "6"; // 测试中
	public static final String CHAN_STATUS_TO_ACTIVE = "7"; // 待启用
	public static final String CHAN_STATUS_DELETE = "9"; // 已删除

	// 审核标签：1企业标签，2临时标签
	public static final String AUDIT_TAG_SP = "1";
	public static final String AUDIT_TAG_TEMP = "2";

	/**
	 * 通讯录类型:个人
	 */
	public static final String CONTACTS_TYPE_PRIVATE = "1";

	/**
	 * 通讯录类型:共享
	 */
	public static final String CONTACTS_TYPE_PUBLIC = "2";

	/**
	 * 通讯录类型：企业
	 */
	public static final String CONTACTS_TYPE_SP = "3";

	/**
	 * 通讯录类型：微信
	 */
	public static final String CONTACTS_TYPE_WECHAT = "4";

	/**
	 * 通讯录分组类型：一般
	 */
	public static final String CONTACTS_GROUP_TYPE_STAND = "1";

	/**
	 * 通讯录分组类型：标签
	 */
	public static final String CONTACTS_GROUP_TYPE_TAG = "2";

	/**
	 * 免审核模板添加来源：企业
	 */
	public static final String NOAUDIT_TEMPLATE_SRC_SP = "1";

	/**
	 * 免审核模板添加来源：运营
	 */
	public static final String NOAUDIT_TEMPLATE_SRC_OP = "2";

	/**
	 * 免审核模板状态：待审核
	 */
	public static final String TEMPLATE_STATUS_WAIT_AUDIT = "1";
	/**
	 * 免审核模板状态：审核通过
	 */
	public static final String TEMPLATE_STATUS_AUDIT_SUCC = "2";
	/**
	 * 免审核模板状态：审核不通过
	 */
	public static final String TEMPLATE_STATUS_AUDIT_FAIL = "3";
	/**
	 * 免审核模板状态：删除
	 */
	public static final String TEMPLATE_STATUS_AUDIT_DEL = "9";

	/**
	 * 通道服务开关
	 */
	public static final int CHANNEL_SWITCHVALUE_OPEN = 1;
	public static final int CHANNEL_SWITCHVALUE_CLOSE = 0;
	public static final String CHANNEL_SWITCHKEY_NOAUTID = "noaudit"; // 免审白名单开关关键字
    public static final String CHANNEL_SWITCHKEY_SUBSIGN = "subsign"; // 子签名开关关键字
	public static final Map<String, String> CHANNEL_SWITCH_MAP = new HashMap<String, String>();
	static {
		CHANNEL_SWITCH_MAP.put(CHANNEL_SWITCHKEY_NOAUTID, "免审白名单");
		CHANNEL_SWITCH_MAP.put(CHANNEL_SWITCHKEY_SUBSIGN, "子签名");
	}

	/**
	 * 签名类型
	 */
	public static final int SP_SIGN_TYPE_SMS = 1; // 短信
	public static final int SP_SIGN_TYPE_MMS = 2; // 彩信

	/**
	 * 发送策略类型：企业
	 */
	public static final String SEND_POLICY_TYPE_SP = "sp";

	/**
	 * 发送策略类型：模板分类
	 */
	public static final String SEND_POLICY_TYPE_TPL_CATEGORY = "tpl-category";

	/**
	 * 发送策略推动通道：1短信通道；
	 */
	public static final String SEND_POLICY_PUSH_CHANNEL_DX = "1";

	/**
	 * 发送策略推动通道：2app推送；
	 */
	public static final String SEND_POLICY_PUSH_CHANNEL_APP = "2";

	/**
	 * 发送策略推动通道：3微信
	 */
	public static final String SEND_POLICY_PUSH_CHANNEL_WX = "3";

	/**
	 * 黑名单添加来源：运营
	 */
	public static final String BLACK_SOURCE_OP = "0";
	/**
	 * 黑名单添加来源：企业
	 */
	public static final String BLACK_SOURCE_SP = "1";
	/**
	 * 黑名单添加来源：用户
	 */
	public static final String BLACK_SOURCE_USER = "2";

	/**
	 * 权限类型：应用
	 */
	public static final String AUTH_TYPE_APP = "0";

	/**
	 * 权限类型：业务
	 */
	public static final String AUTH_TYPE_BIZ = "1";

	/**
	 * 权限类型：查看
	 */
	public static final String AUTH_TYPE_GROUP_READ = "2";

	/**
	 * 权限类型：操作
	 */
	public static final String AUTH_TYPE_GROUP_WRITE = "3";

	// 角色-超管 0
	public static final String ROLE_SUPER_ADMIN = "0";
	// 角色-管理员 1
	public static final String ROLE_ADMIN = "1";

	public static String INTERNAL_APP = "100";

	public static String MEDIA_SPLIT_CHR = ";";

	/**
	 * 支付方式不存在
	 */
	public final static String PAYMENTTYPE_ERROR = "201";

	/**
	 * 支付网关不存在
	 */
	public final static String GATEWAY_ERROR = "202";

	/**
	 * 签名验证失败
	 */
	public final static String SIGN_ERROR = "203";

	/**
	 * 参数为空
	 */
	public final static String PARAMS_NULL = "204";

	/**
	 * 流水号重复
	 */
	public final static String SERIALNUM_ERROR = "205";

	/**
	 * 超出单次扣费上限
	 */
	public final static String FEE_ERROR = "206";

	/**
	 * 系统操作异常
	 */
	public final static String EXCEPTION_ERROR = "500";

	/**
	 * 导入列表状态 1成功， 0 失败， 2处理中
	 */
	public final static int IMPORT_LIST_STATUS_SUCC = 1;
	public final static int IMPORT_LIST_STATUS_FAIL = 0;
	public final static int IMPORT_LIST_STATUS_PROCESSING = 2;

	/**
	 * 导入列表类型 0黑名单， 1白名单
	 */
	public final static int IMPORT_LIST_TYPE_BLACK = 0;
	public final static int IMPORT_LIST_TYPE_WHILE = 1;

	/**
	 * 物联网卡状态
	 * 1 测试准备；2 库存；3 测试；4 激活准备；5 激活；6 失效；7 退卡；8 销户；9 换卡
	 */
	public final static String IOT_CARD_STATUS_BEFORE_TEST = "1";
	public final static String IOT_CARD_STATUS_STOCK = "2";
	public final static String IOT_CARD_STATUS_TESTING = "3";
	public final static String IOT_CARD_STATUS_BEFORE_ACTIVE = "4";
	public final static String IOT_CARD_STATUS_ACTIVE = "5";
	public final static String IOT_CARD_STATUS_INVALID = "6";
	public final static String IOT_CARD_STATUS_RETURN = "7";
	public final static String IOT_CARD_STATUS_CLOSED = "8";
	public final static String IOT_CARD_STATUS_CHANGE = "9";
	
	/**
	 * 支付订单状态
	 */
	public final static String ORDER_STATUS_NO = "0"; // 未支付
	public final static String ORDER_STATUS_SUCCESS = "1"; // 支付成功
	public final static String ORDER_STATUS_FAIL = "2"; // 支付失败
	public final static String ORDER_STATUS_PROCESS = "3"; // 支付中
	
	/**
	 * 通道组配置类型
	 */
	public static final String CHANNEL_GROUP_TYPE_SYS = "1"; // 系统通道组
	public static final String CHANNEL_GROUP_TYPE_CUST = "2"; // 自定义专属通道

	/**
	 * 验真任务明细状态
	 */
	public final static String INSPECT_TASK_STATUS_SUCCESS = "0"; // 成功
	public final static String INSPECT_TASK_STATUS_UNDO = "1"; // 待验真
	public final static String INSPECT_TASK_STATUS_FAIL = "2"; // 失败
	
	/**
	 * 验真任务明细结果
	 */
	public final static String INSPECT_TASK_CODE_CONSISTENT = "0"; // 一致
	public final static String[] INSPECT_TASK_CODE_INCONSISTENT = {"2","3","4"}; // 不一致
	
	/**
	 * 验真任务明细业务类型
	 */
	public final static String INSPECT_TASK_TYPE_THREE = "1"; // 1 三要素验真 
	public final static String INSPECT_TASK_TYPE_TWO = "2"; // 2二要素验真

	/**
	 * 通道运营商类型
	 */
	public final static String CHANNEL_NETWORK_CU = "1"; // 中国联通
	public final static String CHANNEL_NETWORK_CMCC = "2"; // 中国移动
	public final static String CHANNEL_NETWORK_CT = "3"; // 中国电信
	public final static String CHANNEL_NETWORK_ALL = "4"; // 全网通

	/**
	 * 通道类型
	 */
	public final static String CHANNEL_TYPE_THREE = "1"; // 三要素类型
	public final static String CHANNEL_TYPE_TWO = "2"; // 二要素类型
	
	/**
	 * cmcMessage发送类型
	 */
	public final static String CMC_MESSAGE_SEND_TYPE_IMMEDIATE= "1";//发送类型:立即发送
	public static final String CMC_MESSAGE_SEND_TYPE_BOOK = "2";//预约发送
	public static final String CMC_MESSAGE_SEND_TYPE_BIRTHDAY_BOOK = "3";//生日预约发送
	
	/**
	 * CMCMESSAGE发送平台
	 */
	public static final String CMC_MESSAGE_SOURCE_PF="1";//平台发送
	public static final String CMC_MESSAGE_SOURCE_API="2";//企业API接口
}
