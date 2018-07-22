package com.lottery.common;

import java.util.HashMap;
import java.util.Map;


public class Constants {
    public static Map<String,String>zfbError=new HashMap<String,String>();
    public static Map<String,String>elinkError=new HashMap<String,String>();
    public static Map<String,String>elinkFailedError=new HashMap<String,String>();
    public static Map<String,String>elinPckFailedError=new HashMap<String,String>();
    public static Map<String,String>zfbDrawError=new HashMap<String,String>();
    public static Map<String,String>convertError=new HashMap<String,String>();
    public static Map<String,String>fcbysendError=new HashMap<String,String>();
    public static Map<String,String>zchSendError=new HashMap<String,String>();
    public static Map<String,String>zchSendErrorFailed=new HashMap<String,String>();
    public static Map<String,String>huaySendError=new HashMap<String,String>();
    public static Map<String,String>huaySendFailedError=new HashMap<String,String>();
    public static Map<String,String>yinmaSendFailedError=new HashMap<String,String>();
    public static Map<String,String>yinmaSendError=new HashMap<String,String>();
    public static Map<String,String>qhSendError=new HashMap<String,String>();
    public static Map<String,String>qhExptSendError=new HashMap<String,String>();
    public static Map<String,String>qhSendFaildError=new HashMap<String,String>();
    public static Map<String,String>heiljSendError=new HashMap<String,String>();
    public static Map<String,String>heiljSendFailedError=new HashMap<String,String>();
    public static Map<String,String>tianjinCenterSendError=new HashMap<String,String>();
    public static Map<String,String>tianjinCenterFailedError=new HashMap<String,String>();
    
    public static Map<String,String>anruiSendErrorFailed=new HashMap<String,String>();
    public static Map<String,String>anruiSendError=new HashMap<String,String>();
    
    public static Map<String,String>gzcpSendErrorFailed=new HashMap<String,String>();
    public static Map<String,String>gzcpSendError=new HashMap<String,String>();
    
    public static Map<String,String>guoxinSendErrorFailed=new HashMap<String,String>();
    public static Map<String,String>guoxinSendError=new HashMap<String,String>();
    public static Map<String,String>gaodeSendError=new HashMap<String,String>();
    
    public static Map<String,String>huaiSendErrorFailed=new HashMap<String,String>();
    public static Map<String,String>huaiSendError=new HashMap<String,String>();
    public static Map<String,String>huancaiErrorFailed=new HashMap<String,String>();
    public static Map<String,String>ruiyingErrorFailed=new HashMap<String,String>();
    public static Map<String,String>shcpSendError=new HashMap<String,String>();
    public static Map<String,String>yeepayWapError=new HashMap<String,String>();
    public static Map<String,String>zhangyiSendErrorFailed=new HashMap<String,String>();
    public static Map<String,String>zhangyiSendError=new HashMap<String,String>();
    
    public static Map<String,String>ownerSendErrorFailed=new HashMap<String,String>();
    public static Map<String,String>ownerSendError=new HashMap<String,String>();
    public static Map<String,String>macSendError=new HashMap<String, String>();
    public static Map<String,String>macSendFailedError=new HashMap<String, String>();
	static{
		
        //支付宝查询接口错误类别
		zfbError.put("HAS_NO_PRIVILEGE", "没有该接口的权限");
		zfbError.put("ILLEGAL_ARGUMENT", "参数或参数对应的值的格式不正确");
		zfbError.put("ILLEGAL_PARTNER", "合作身份者 ID(partner)填写错误");
		zfbError.put("ILLEGAL_SIGN", "参数格式或交易安全校验码（key）不正确，编码问题");
		zfbError.put("ILLEGAL_SERVICE", "服务名称错误、请求参数全部丢失");
		zfbError.put("ILLEGAL_SIGN_TYPE", "签名类型错误");
		zfbError.put("ERROR_OTHER_CERTIFY_LEVEL_LIMIT", "收款方认证等级受限,无法完成当前操作");
		zfbError.put("ACCOUN_NAME_NOT_MATCH", "用户姓名和收款名称不匹配");
		zfbError.put("ILLEGAL_SECURITY_PROFILE", "未找到匹配的密钥配置");
		zfbError.put("ILLEGAL_DIGEST_TYPE", "摘要类型不正确");
		zfbError.put("TRANSFER_AMOUNT_NOT_ENOUGH", "转账余额不足，批次失败");
		zfbError.put("ILLEGAL_DYN_MD5_KEY", "动态密钥信息错误");
		zfbError.put("ILLEGAL_AGENT", "代理ID不正确");
		zfbError.put("ILLEGAL_ENCRYPT", "加密不正确");
		zfbError.put("RECEIVE_SINGLE_MONEY_ERROR", "收款金额超限");
		
		
		elinkError.put("02","签名验证失败");
		elinkError.put("01","请求报文错误");
		elinkError.put("04","会话超时");
		
		elinPckFailedError.put("34","查无此交易");
		elinPckFailedError.put("78","发卡行交易受限");
		
		elinkFailedError.put("03","交易失败，详情请咨询 95516");
		elinkFailedError.put("60","交易失败，详情请咨询您的发卡行");
		elinkFailedError.put("21","无效订单");
		elinkFailedError.put("23","请您确认输入的卡号与所选的银行与卡类型相符合");
		elinkFailedError.put("24","请您确认手机号是否填写正确");
		elinkFailedError.put("25","请确认您银行卡的有效期是否填写正确");
		elinkFailedError.put("26","请您确认身份证件号是否填写正确 ");
		elinkFailedError.put("27","贵银行卡未开通银联无卡业务，请到银行柜台开通  ");
		elinkFailedError.put("28","非常抱歉,目前本系统不支持该银行卡交易,请换其他银行 卡再试");
		elinkFailedError.put("31","查找原始交易 ");
		elinkFailedError.put("32","交易无效或无法完成");
		elinkFailedError.put("33","原始金额错误 ");
		elinkFailedError.put("41","交易受限");  
		elinkFailedError.put("42","交易金额超限 ");  
		elinkFailedError.put("51","短信验证码错误"); 
		elinkFailedError.put("52","您的短信发送过于频繁，请稍候再试");  
		elinkFailedError.put("53","您输入的短信验证码与手机号不匹配，请检查手机号或验证 码是否输入有误 "); 
		elinkFailedError.put("61","处理超时,请查询确认交易结果 "); 
		
		heiljSendError.put("1", "销售玩法错误");
		heiljSendError.put("3", "投注号码不合法");
		heiljSendError.put("4", "交易倍投数不合法");
		heiljSendError.put("5", "单票投注金额超出限额或错误");
		heiljSendError.put("7", "投注号码和选号方式不匹配");
		heiljSendError.put("8", "胆码与拖码未构成或仅为一注");
		heiljSendError.put("9", "胆码数过多");
		heiljSendError.put("10", "投注注数不合法");
		heiljSendError.put("11", "单笔交易超过最大票数");
		heiljSendError.put("12", "投注号码位数不合法");
		heiljSendError.put("13", "期号格式不正确");
		heiljSendError.put("97", "未知错误");
		heiljSendError.put("98", "已达到最大风险控制");
		heiljSendError.put("99", "彩票插入数据库发生错误");
		heiljSendError.put("03000076", "中间通讯服务错误");
		heiljSendError.put("03000077", "连接中间服务失败");
		heiljSendError.put("02000329", "金额错误");
		heiljSendFailedError.put("2", "该玩法现在已经封期");

		
		tianjinCenterSendError.put("1000","玩法英文名称不合法");
		tianjinCenterSendError.put("1001","逻辑机号不合法");
		tianjinCenterSendError.put("1002","期号不合法");
		tianjinCenterSendError.put("1003","注码不合法");
		tianjinCenterSendError.put("1004","注数不正确");
		tianjinCenterSendError.put("1005","重新计算校验码核对失败");
		tianjinCenterSendError.put("1006","数据传输失败，请重新发送该票");
		tianjinCenterSendError.put("1008","注销票不存在");
		tianjinCenterSendError.put("1009","该票已经注销");
		tianjinCenterSendError.put("1011","销售注码超过注数限制");
		tianjinCenterSendError.put("1012","未到开期时间");
		tianjinCenterSendError.put("1013","流水号非法");
		tianjinCenterSendError.put("1014","帐户金额不足");
		tianjinCenterSendError.put("1015","该票存在但不能注销（超过注销时限）");
		tianjinCenterSendError.put("1016","彩票数据无效");
		tianjinCenterSendError.put("1017","身份证号码错误");
		tianjinCenterSendError.put("1018","系统忙");
		tianjinCenterSendError.put("1019","玩法没有开通");
		tianjinCenterSendError.put("1030","逻辑机号正在使用");
		tianjinCenterSendError.put("1090","解析字符串失败");
		tianjinCenterSendError.put("170001","此逻辑机号正在使用");
		tianjinCenterSendError.put("170002","报文校验错误，非法数据");
		tianjinCenterSendError.put("170003","该玩法销售受限");
		tianjinCenterSendError.put("170004","用户此玩法无权限");
		tianjinCenterSendError.put("170005","与合并通信机通信失败");
		tianjinCenterSendError.put("170006","此笔交易正在执行");
		
		
		
		tianjinCenterFailedError.put("1007","已期结");
		tianjinCenterFailedError.put("102000203","销售失败");
		
		
		fcbysendError.put("2", "结果不存在");
		fcbysendError.put("102", "签名验证错误");
		fcbysendError.put("103", "请求命令不存在");
		fcbysendError.put("104", "解密数据出错");
		fcbysendError.put("199", "Ip 验证失败");
		
		fcbysendError.put("01001", "数据库插入错误");
		fcbysendError.put("040007", "没有空闲逻辑机");
		fcbysendError.put("040009", "手机号码不正确");
		fcbysendError.put("040010", "参数不全");
		fcbysendError.put("040014", "彩票信息错误");
		fcbysendError.put("040018", "彩种不存在");
		fcbysendError.put("070007", "用户校验错误");
		fcbysendError.put("070008", "账号校验错误");
		fcbysendError.put("200002", "amount不为 0");
		fcbysendError.put("200003", "注码错误，注码金额不相符");
		fcbysendError.put("200025", "场次不存在");
		fcbysendError.put("200026", "场次已过期");
		fcbysendError.put("200030", "竞彩不支持此玩法");
		fcbysendError.put("20100705", "该期不存在");
		fcbysendError.put("20100707", "期号格式不正确");
		fcbysendError.put("20100701", "冻结异常");
		fcbysendError.put("20100702", "扣款异常");
		fcbysendError.put("20100703", "解冻异常");
		fcbysendError.put("20100713", "限号");
		
		
		fcbysendError.put("2007", "代理商订单号已存在");
		
		zchSendError.put("0001","MD5 验证失败");
		zchSendError.put("0002","schema 验证失败");
		zchSendError.put("0003","参数错误");
		zchSendError.put("0005","网络异常");
		zchSendError.put("0999","系统未知错误");
		zchSendError.put("8002","账户不存在中彩汇");
		zchSendError.put("8003","帐户已被锁定");
		zchSendError.put("8004","帐户余额不足");
		zchSendError.put("8101","彩种不存在");
		zchSendError.put("8102","投注类型不正确");
		zchSendError.put("8103","实际金额与传入金额不符");
		zchSendError.put("8106","投注期次不存在");
		zchSendError.put("8107","投注期次未开售");
		zchSendError.put("8109","投注彩种暂停销售");
		zchSendError.put("8100","单票金额过 20000 限额");
		zchSendError.put("8111","投注号码格式不正确");
		zchSendError.put("8112","发单票数超过上限");
		zchSendError.put("8113","单票倍数超过上限");
		zchSendError.put("8114","扣款失败");
		zchSendError.put("8115","金额不能小于 1");
		zchSendError.put("8117","该玩法暂不支持");
		zchSendError.put("8119","投注场次不存在");
		zchSendError.put("8201","票号不存在");
		zchSendError.put("8202","查询的期次不存在");
		zchSendError.put("8203","查询的期次还未返奖");
		zchSendError.put("8204","超出接口查询条数限制");
		zchSendError.put("8205","查询的赛事不存在");
		zchSendError.put("8206","此彩种暂停销售");
		zchSendError.put("8118","投注场次已截止");
		zchSendError.put("8108","投注失败");
		zchSendError.put("8116","传入注数错误");
		zchSendError.put("8120","投注场次玩法未开");
		
		
		zchSendErrorFailed.put("8105","投注期次已截期");
		
	
		huaySendError.put("10000","代理权限不足，不能执行此操作");
		huaySendError.put("10003","用户名已经存在");
		huaySendError.put("10010","账户金额不足,无法完成此操作");
		huaySendError.put("10018","交易通讯超时");
		huaySendError.put("10019","参数过长");
		huaySendError.put("10032","交易已存在");
		huaySendError.put("12901","无效或者不完整XML数据格式");
		huaySendError.put("12902","要查询的数据还未开奖，请等开奖后再查");
		huaySendError.put("12903","传入参数信息错误");
		huaySendError.put("12904","一次提交数据过多");
		huaySendError.put("14000","系统目前不支持此玩法");
		huaySendError.put("14001","操作代码格式错误");
		huaySendError.put("14002","注数计算错误");
		huaySendError.put("14003","投注金额计算错误");
		huaySendError.put("14005","一票超过20000元限");
		
		yinmaSendError.put("0002","本次请求订单数超出最大值范围");
		yinmaSendError.put("0010","消息格式错误。");
		yinmaSendError.put("0011","timestamp时间戳格式错误。");
		yinmaSendError.put("0012","MD5签名不匹配");
		yinmaSendError.put("0013","不支持该交易类型");
		yinmaSendError.put("0014","调用委托投注接口的IP地址不是绑定的投注IP地址");
		yinmaSendError.put("0015","单个请求超出最大并发数");
		yinmaSendError.put("0016","单个请求与上次时间间隔不能小于最小时间间隔。");
		yinmaSendError.put("1001","必须填写正确的用户身份证号码");
		yinmaSendError.put("1002","必须填写正确的用户手机号码");
		yinmaSendError.put("1003","必须填写正确的用户真实姓名");
		yinmaSendError.put("1004","订单已存在");
		yinmaSendError.put("1005","不支持的彩种");
		yinmaSendError.put("1006","奖期不存在");
		yinmaSendError.put("1007","奖期非投注状态");
		yinmaSendError.put("1008","不支持的投注玩法");
		yinmaSendError.put("1009","投注号码格式错误");
		yinmaSendError.put("1010","订单投注金额超出上限");
		yinmaSendError.put("1011","倍投的倍数超出范围");
		yinmaSendError.put("1012","多期投注的期数超出允许范围");
		yinmaSendError.put("1013","票面金额计算不正确");
		yinmaSendError.put("1014","代理商帐户余额不足");
		yinmaSendError.put("1015","代理商额度已经超出上限");
		yinmaSendError.put("1016","订单格式不正确");
		yinmaSendError.put("1017","订单中包含不存在或已过期的场次");
		yinmaSendError.put("1018","子玩法编号错误");
		yinmaSendError.put("1019","订单中包含竞彩过关投注不支持的场次");
		yinmaSendError.put("1020","竞彩期次号不正确");
		yinmaSendError.put("1021","该方案接收已完成");
		yinmaSendError.put("1022","不在规定的送票时间范围");
		yinmaSendError.put("2001","订单不存在");
		yinmaSendError.put("2002","不支持的彩种");
		yinmaSendError.put("2003","期次不存在");
		yinmaSendError.put("4001","投注代理商已经被冻结");
		yinmaSendError.put("4002","投注代理商已经被关闭");
		yinmaSendError.put("4003","投注代理商已经被暂停");
		yinmaSendError.put("4004","投注代理商帐户余额不足");
		yinmaSendError.put("4005","投注代理商不存在");
		yinmaSendError.put("4006","投注代理商额度已经超出上限");
		yinmaSendError.put("9999","系统未知异常");
		
		
		
		
		
		
		qhSendError.put("100002","解码错误 ");
		qhSendError.put("100003","无效参数");
		qhSendError.put("100005","无效账户 ");
		qhSendError.put("200001","投注金余额不足(重试)"); 
		qhSendError.put("200002","投注格式错误,金额错误"); 
		qhSendError.put("200003","投注序列号重复(废弃)");
		qhSendError.put("200004","消息序列号重复(废弃)");
		qhSendError.put("200005","无效玩法代码 ");
		qhSendError.put("200006","玩法未开期");
		qhSendError.put("200008","投注额度满 ");
		qhSendError.put("200009","暂时限号");
		qhSendError.put("200010","无此投注序列号");
		qhSendError.put("200011","无效赛事编号");
		qhSendError.put("200012","不能投注的单场赛事 ");
		qhSendError.put("200014","非销售时间(竟彩)");
		qhSendError.put("200015","身份信息错误，比如格式不对或不 满 18 岁");
		qhSendError.put("200016","限号(将下单失败)");  
		
		qhSendError.put("200022","无此消息序列号"); 
		qhSendError.put("200007","玩法已封期 ");
		qhSendError.put("200025","未开奖 ");
		
		
		qhExptSendError.put("100004","系统异常(重试)");
		qhExptSendError.put("100001","错误(重试)");
		
		qhSendFaildError.put("200013","赛事销售已经停止"); 
		qhSendFaildError.put("200020","出票失败 ");
		qhSendFaildError.put("200024","投注失败"); 
	
		
		
		
		
		
		huaiSendError.put("101","参数错误");
		huaiSendError.put("102","签名验证失败");
		huaiSendError.put("103","不支持的功能请求");
		huaiSendError.put("104","IP 限制");
		huaiSendError.put("105","商户被禁用");
		huaiSendError.put("106","系统繁忙，请再次尝试。");
		huaiSendError.put("201","查询订单不存在");
		huaiSendError.put("202","查询奖期不存在");
		huaiSendError.put("301","10秒内禁止重复发单");
		huaiSendError.put("302","投注格式不正确或玩法不支持 ");
		huaiSendError.put("303","订单投注倍数超最大限制");
		huaiSendError.put("304","投注票金额不正确 ");
		huaiSendError.put("305","投注期次过期或期次不存在");
		huaiSendError.put("307","订单创建失败");
		huaiSendError.put("308","余额不足");
		huaiSendError.put("403","彩种暂停销售");
		huaiSendError.put("404","期次取消");

		zhangyiSendErrorFailed.put("200009", "限号/球队暂停销售");
		zhangyiSendErrorFailed.put("200020", "出票失败");
		zhangyiSendErrorFailed.put("200204", "出票失败");
		zhangyiSendErrorFailed.put("300001", "出票失败(数据库)");
		zhangyiSendErrorFailed.put("200203", "限号");
		
		zhangyiSendError.put("100001", "程序错误");
		zhangyiSendError.put("100002", "解码错误");
		zhangyiSendError.put("100003", "无效参数（报警）");
		zhangyiSendError.put("100004", "系统异常（可在投）");
		zhangyiSendError.put("100005", "无效帐户");
		zhangyiSendError.put("100006", "执行错误");
		zhangyiSendError.put("100007", "不支持的接口");
		zhangyiSendError.put("100008", "系统维护");
		zhangyiSendError.put("200001", "投注金余额不足");
		zhangyiSendError.put("200002", "注码格式错误");
		zhangyiSendError.put("200005", "无效玩法代码");
		zhangyiSendError.put("200006", "玩法未开期");
		zhangyiSendError.put("200007", "玩法已经封期");
		zhangyiSendError.put("200008", "投注额度满");
		zhangyiSendError.put("200102", "人工控制");
		zhangyiSendError.put("200103", "已经结账");
		zhangyiSendError.put("200104", "无效IDC号");

		
		yinmaSendFailedError.put("2005", "出票失败");
		huaySendFailedError.put("14004","玩法已期结，无法完成本次投注");
		
		convertError.put("040006", "渠道余额不足");
		convertError.put("040001", "渠道不存在");
		convertError.put("040002", "渠道未开通");
		convertError.put("040005", "渠道账户不存在");
		convertError.put("200028", "彩种暂停销售");
		convertError.put("200029", "彩种停止销售");
		convertError.put("500", "投注系统服务器内部错误");
		convertError.put("501", "投注系统无响应");
		convertError.put("9999", "系统参数错误");
	
		zfbDrawError.put("文件上传成功!","文件上传成功!");
		zfbDrawError.put("QUERY_RESULT_IS_NULL","查询信息为空");
		zfbDrawError.put("USER_NOT_EXIST","提交的 email 用户不存在");
		zfbDrawError.put("USER_CONFIRM_SUCC", "批次已复核成功，不要重复复核");
		zfbDrawError.put("FILE_NOT_EXIST", "查询待复核的文件不存在");
		zfbDrawError.put("ACCOUNT_NOT_CONSISTENT", "付款账户和所填 email 不一致");
		zfbDrawError.put("USER_PASS_ERROR", "您的支付密码错误，请重新输入");
		zfbDrawError.put("USER_BLOCK_STATUS", "email账户被冻结");
		zfbDrawError.put("ILLEGAL_SIGN", "签名不正确");
		zfbDrawError.put("ILLEGAL_DYN_MD5_KEY", "动态密钥信息错误");
		zfbDrawError.put("ILLEGAL_ENCRYPT", "加密不正确");
		zfbDrawError.put("ILLEGAL_ARGUMENT", "参数不正确");
		zfbDrawError.put("ILLEGAL_SERVICE", "非法服务名称");
		zfbDrawError.put("ILLEGAL_USER", "用户 ID 不正确");
		zfbDrawError.put("ILLEGAL_PARTNER", "合作伙伴信息不正确");
		zfbDrawError.put("ILLEGAL_EXTERFACE", "接口配置不正确");
		zfbDrawError.put("ILLEGAL_PARTNER_EXTERFACE", "合作伙伴接口信息不正确");
		zfbDrawError.put("ILLEGAL_SECURITY_PROFILE", "未找到匹配的密钥配置");
		zfbDrawError.put("ILLEGAL_AGENT", "代理 ID 不正确");
		zfbDrawError.put("ILLEGAL_SIGN_TYPE", "签名类型不正确");
		zfbDrawError.put("ILLEGAL_CHARSET", "字符集不合法");
		zfbDrawError.put("ILLEGAL_CLIENT_IP", "客户端 IP 地址无权访问服务");
		zfbDrawError.put("HAS_NO_PRIVILEGE", "未开通此接口权限");
		zfbDrawError.put("ILLEGAL_DIGEST_TYPE", "摘要类型不正确");
		zfbDrawError.put("ILLEGAL_DIGEST", "文件摘要不正确");
		zfbDrawError.put("ILLEGAL_FILE_FORMAT", "文件格式不正确");
		zfbDrawError.put("ILLEGAL_ENCODING", "不支持该编码类型");
		zfbDrawError.put("ILLEGAL_REQUEST_REFERER", "防钓鱼检查不支持该请求来源");
		zfbDrawError.put("ILLEGAL_ANTI_PHISHING_KEY", "防钓鱼检查非法时间戳参数");
		zfbDrawError.put("ANTI_PHISHING_KEY_TIMEOUT", "防钓鱼检查时间戳超时");
		zfbDrawError.put("ILLEGAL_EXTER_INVOKE_IP", "防钓鱼检查非法调用 IP");
		
		
		zfbDrawError.put("DETAIL_VALIDATED_FAIL","该批次的所有明细校验全部失败");
		
		
		anruiSendErrorFailed.put("-1","投注失败");
		anruiSendErrorFailed.put("-3303","商品已停售");
		
		anruiSendError.put("-2","参数数据加密不一致");
		anruiSendError.put("-3","参数不完整");
		anruiSendError.put("-4","渠道ID不存在");
		anruiSendError.put("-11","应用程序异常");
		anruiSendError.put("-13","参数错误");
		anruiSendError.put("-14","参数格式不正确");
		anruiSendError.put("-15","文件没找到");
		anruiSendError.put("-19","其它未知错误");
		anruiSendError.put("-2001","额度不足");
		anruiSendError.put("-2017","投注帐户异常");
		anruiSendError.put("-3003","彩种不支持多期追号");
		anruiSendError.put("-3004","彩种不支持该玩法");
		anruiSendError.put("-3301","商品没找到");
		anruiSendError.put("-3401","比赛没找到");
		anruiSendError.put("-3302","商品不在销售状态");
		anruiSendError.put("-3408","注比赛不支持过关方式");
		anruiSendError.put("-3409","有无效的比赛");
		anruiSendError.put("-3410","对阵场次号重复");
		anruiSendError.put("-3411","对阵场次号不能为空");
		anruiSendError.put("-3412","赛事编号不能为空");
		anruiSendError.put("-3413","赛事名称不能为空");
		anruiSendError.put("-3414","主队名称不能为空");
		anruiSendError.put("-3415","客队名称不能为空");
		anruiSendError.put("-3550","投注金额和票金额不符");

		anruiSendError.put("-3304","上一奖期未开奖");
		anruiSendError.put("-3402","比赛没在销售状态");
		anruiSendError.put("-3550","投注金额和票金额不符");
		anruiSendError.put("-3501","投注内容格式不正确");
		anruiSendError.put("-3502","投注场次超过限制");
		anruiSendError.put("-3503","玩法不支持胆拖方式投注");
		anruiSendError.put("-3504","投注场次与过关数不符");
		anruiSendError.put("-3505","单张票投注金额不能超过20000元");
		anruiSendError.put("-3506","玩法投注注数验证失败");
		anruiSendError.put("-3507","交易投注注数验证失败");
		anruiSendError.put("-3510","彩票店没有开始销售");
		anruiSendError.put("-3511","多期订单类型无法识别");
		anruiSendError.put("-3512","多期订单信息与类型不匹配");
		anruiSendError.put("-3513","原始订单没找到");
		anruiSendError.put("-3514","多期订单中某期追号注数错误");
		anruiSendError.put("-3521","交易类型不支持");
		anruiSendError.put("-3531","该投注接口不支持指定投注方式");
		anruiSendError.put("-3532","不支持投注类型");
		anruiSendError.put("-3533","方案份数与投注类型不匹配");
		anruiSendError.put("-3534","方案金额与投注类型不匹配");
		anruiSendError.put("-3537","未填写比分");
		anruiSendError.put("-3538","未填写彩果");
		anruiSendError.put("-3539","投注内容超出长度限制！");
		anruiSendError.put("-3541","方案未找到");
		anruiSendError.put("-3542","方案已处理,不能撤单");
		anruiSendError.put("-3544","超过最大倍数");
		anruiSendError.put("-3545","参数超出个数限制");
		
		
		
		gzcpSendError.put("100001", "错误");
		gzcpSendError.put("100002", "解码错误");
		gzcpSendError.put("100003", "无效参数");
		gzcpSendError.put("100004", "系统异常");
		gzcpSendError.put("100005", "无效帐户");
		gzcpSendError.put("200001", "投注金余额不足");
		gzcpSendError.put("200002", "注码格式错误");
		gzcpSendError.put("200005", "无效玩法代码");
		gzcpSendError.put("200006", "玩法未开期");
		gzcpSendError.put("200007", "玩法已经封期");
		gzcpSendError.put("200008", "投注额度满");
		gzcpSendError.put("200009", "限号");
		gzcpSendError.put("200010", "无此投注序列号");
		gzcpSendError.put("200011", "无效赛事编号");
		gzcpSendError.put("200012", "不能投注的单场赛事");
		gzcpSendError.put("200014", "非销售时间(竞彩)");

		gzcpSendErrorFailed.put("200013","赛事销售已经停止");
		gzcpSendErrorFailed.put("200020","出票失败");
		gzcpSendErrorFailed.put("200024","已提交失败");
		
		
		shcpSendError.put("2","号码格式错误");
		shcpSendError.put("3","金额不正确");
		shcpSendError.put("4","期次不正确");
		shcpSendError.put("5","代理不存在");
		shcpSendError.put("6","代理余额不足");
		shcpSendError.put("9000","数据库错误");
		
		guoxinSendErrorFailed.put("0026","期次已截止销售");
		
		guoxinSendError.put("0001","MD5验证失败");
		guoxinSendError.put("0002","帐户余额不足");
		guoxinSendError.put("0003","期次无效 ");
		guoxinSendError.put("0004","彩种无效");
		guoxinSendError.put("0005","单票金额过20000限额");
		guoxinSendError.put("0006","福彩3D组六最多允许选择9个号码");
		guoxinSendError.put("0007","排列三组三最多允许选择9个号码");
		guoxinSendError.put("0008","排列三组六最多允许选择9个号码");
		guoxinSendError.put("0009","单票倍数超过99上限");
		guoxinSendError.put("0010","投注号码格式不正确");
		guoxinSendError.put("0011","玩法或投注方式不正确");
		guoxinSendError.put("0012","实际金额与传入金额不符");
		guoxinSendError.put("0013","投注号码包含限号号码");
		guoxinSendError.put("0015","查询的期次不存在");
		guoxinSendError.put("0016","查询的赛事不存在");
		guoxinSendError.put("0019","竞彩足球单票金额过2000000限额");
		guoxinSendError.put("0020","过关方式无效");
		guoxinSendError.put("0021","场次数超过15限额");
		guoxinSendError.put("0022","场次无效");
		guoxinSendError.put("0023","参数错误");
		guoxinSendError.put("0024","票数超过50限制");
		guoxinSendError.put("0025","期次暂停销售");
		guoxinSendError.put("0099","系统未知错误");
		
		
		ownerSendErrorFailed.put("40008", "彩期已过期");
		ownerSendError.put("3", "暂停销售");
		ownerSendError.put("20001", "注码金额错误");
		ownerSendError.put("9999", "未知错误");
		ownerSendError.put("40002", "用户不存在");
		ownerSendError.put("40007", "彩期不存在");
		ownerSendError.put("40013", "彩期不是当前期");
		ownerSendError.put("40004", "用户账户不存在");
		ownerSendError.put("40005", "用户可用余额不足");
		ownerSendError.put("70001", "ip错误");
		ownerSendError.put("70002", "解密错误");
		ownerSendError.put("70003", "解析错误");
		macSendError.put("1100", "登录失败");
		macSendError.put("1101", "参数错误");
		macSendError.put("1102", "签名验证失败");
		macSendError.put("1103", "不支持的功能请求");
		macSendError.put("1104", "签名验证失败，帐户被临时冻结");
		macSendError.put("1198", "系统繁忙");
		macSendError.put("1199", "调用彩票平台的IP地址不是代理商绑定的投注IP地址");
		macSendError.put("1001", "彩票玩法不存在或禁止销售");
		macSendError.put("1002", "彩票期号不存在或已过期");
		macSendError.put("1003", "代理商帐户余额不足");
		macSendError.put("1004", "代理商不存在或已经被冻结");
		macSendError.put("1005", "投注号码格式错误");
		macSendError.put("1006", "投注金额不相符");
		macSendError.put("1009", "代理商订单号格式错误");
		macSendError.put("1010", "交易参数包数据错误");
		macSendError.put("1011", "代理商未开通该彩票玩法");
		macSendError.put("1012", "投注倍数超过最大倍数限制");
		macSendError.put("1013", "单次投注注数超过规定限额");
		
		macSendError.put("1017", "查询结果为空");
		macSendError.put("1018", "票号为空");
		macSendError.put("1001", "期号不存");
		macSendError.put("1004", "代理商不存在或已经被冻结");
		macSendError.put("1005", "投注指令错误");
		macSendError.put("1006", "余额不足");
		macSendError.put("2001", "系统异常，请联系管理员");
		
	
		macSendError.put("8002", "系统不支持的功能，请使用客户端软件操作");
		macSendError.put("2000", "系统未知异常");

		macSendFailedError.put("1007", "系统限号，投注失败");
		macSendFailedError.put("2003", "已撤单");
		macSendFailedError.put("1002", "本期投注时间已结束");
		macSendFailedError.put("1016", "投注失败，订单取消");
		macSendFailedError.put("2004", "方案失败");
		
		huancaiErrorFailed.put("0001","参数错误");
		huancaiErrorFailed.put("0002","XML 格式错误");
		huancaiErrorFailed.put("0003","交易码不一致");
		huancaiErrorFailed.put("0004","MD5 校验失败");
		huancaiErrorFailed.put("0005","交易码不存在");
		huancaiErrorFailed.put("1001","账号不存在码不存在");
		huancaiErrorFailed.put("1002","账号冻结");
		huancaiErrorFailed.put("1003","余额不足");
		huancaiErrorFailed.put("2001","期次无效");
		huancaiErrorFailed.put("2002","投注号码格式错误");
		huancaiErrorFailed.put("2003","注数不符");
		huancaiErrorFailed.put("2004","金额不符");
		huancaiErrorFailed.put("2005","不支持的玩法");
		huancaiErrorFailed.put("2006","超过最大注数限制");
		huancaiErrorFailed.put("2007","玩法不符");
		huancaiErrorFailed.put("9999","系统错误");
		
		
		
		/** 交易状态码对应的描述  */
		ruiyingErrorFailed.put("100", "登录失败");
		ruiyingErrorFailed.put("101", "参数错误");
		ruiyingErrorFailed.put("102", "签名验证失败");
		ruiyingErrorFailed.put("103", "不支持的功能请求");
		ruiyingErrorFailed.put("104", "签名验证失败，帐户被临时冻结");
		ruiyingErrorFailed.put("105", "交易消息包XML结构错误");
		ruiyingErrorFailed.put("197", "系统维护时间（系统维护期间不响应用户投注和查询等所有请求）");
		ruiyingErrorFailed.put("198", "系统忙");
		ruiyingErrorFailed.put("199", "调用彩票平台的IP地址不是代理商绑定的投注IP地址");
		ruiyingErrorFailed.put("1001", "彩票玩法不存在或禁止销售");
		ruiyingErrorFailed.put("1002", "彩票期号不存在或已过期");
		ruiyingErrorFailed.put("1003", "代理商帐户余额不足");
		ruiyingErrorFailed.put("1004", "代理商不存在或已经被冻结");
		ruiyingErrorFailed.put("1005", "投注号码格式错误或超过长度限制");
		ruiyingErrorFailed.put("1006", "投注金额不相符。即代理商提交的投注金额跟销售平台计算出的投注金额不相等");
		ruiyingErrorFailed.put("1007", "系统限号，投注失败");
		ruiyingErrorFailed.put("1009", "代理商票流水号格式错误");
		ruiyingErrorFailed.put("1010", "交易参数包数据错误");
		ruiyingErrorFailed.put("1011", "代理商未开通该彩票玩法");
		ruiyingErrorFailed.put("1012", "投注倍数超过最大倍数限制");
		ruiyingErrorFailed.put("1013", "单票注数超过规定限制");
		ruiyingErrorFailed.put("1014", "单票金额超过规定限制");
		ruiyingErrorFailed.put("1015", "订单票数超过规定限制");
		ruiyingErrorFailed.put("1016", "订单未包含有效票");
		ruiyingErrorFailed.put("2001", "代理商票流水号不存在");
		ruiyingErrorFailed.put("2002", "投注中");
		ruiyingErrorFailed.put("2003", "投注失败（撤单返款）");
		ruiyingErrorFailed.put("2004", "查询结果为空");
		ruiyingErrorFailed.put("2005", "票号为空（已出票）");
		ruiyingErrorFailed.put("2006", "等待结算");
		ruiyingErrorFailed.put("2007", "代理商票流水号已存在，但投注失败");
		ruiyingErrorFailed.put("8001", "系统错误导致数据保存失败");
		ruiyingErrorFailed.put("8002", "系统不支持的功能，请使用客户端软件操作");
		ruiyingErrorFailed.put("9001", "数据库连接失败");
		ruiyingErrorFailed.put("9002", "数据库忙（请降低接口访问频次）");
		ruiyingErrorFailed.put("9999", "系统未知异常");
		
		yeepayWapError.put("30005000","交易风险较高，拒绝此次交易 ");
		yeepayWapError.put("30005001","交易风险较高，拒绝此次交易 "); 
		yeepayWapError.put("30005002","交易风险较高，未获取到商户 ");
		yeepayWapError.put("30005003","商户接入地址未报备，无法完成交易 ");  
		yeepayWapError.put("30005004","请求地址与商户接入地址不一致，无法完成交易 "); 
		yeepayWapError.put("30006001","交易订单信息不一致，拒绝此交易  "); 
		yeepayWapError.put("30006002","该支付方式未开通，请联系商户 "); 
		yeepayWapError.put("30009001","支付失败，请勿重复支付 ");  
		yeepayWapError.put("30010001","支付失败，请返回商户重新下单 ");
		yeepayWapError.put("30010002","支付失败，请稍候重试 ");  
		yeepayWapError.put("30010003","暂不支持此类型卡,请换卡重新支付 ");
		yeepayWapError.put("30011001","短信获取过于频繁，同一天同一手机号对同一订单下发短信不得超过10次 "); 
		yeepayWapError.put("30005005","暂不支持，请换卡支付 ");
		yeepayWapError.put("300110","订单已过期或已撤销 ");
		yeepayWapError.put("300113","订单金额太小");
		yeepayWapError.put("300109","该笔交易风险较高，拒绝此次交易 ");
		yeepayWapError.put("300103","该卡已过期或有效期错误，请联系发卡银行");
		yeepayWapError.put("200038","接口不支持商户提交的");
		yeepayWapError.put("200039","时间间隔超过31天");  
		yeepayWapError.put("200040","记录数量过多，请尝试缩短日期间隔");  
		yeepayWapError.put("300042","暂时无法解除已绑定银行卡");  
		yeepayWapError.put("00045","该卡已超过单笔支付限额");  
		yeepayWapError.put("300043","该卡已超过单日累计支付限额 "); 
		yeepayWapError.put("300999","该卡已超过单月累计支付限额 "); 
		yeepayWapError.put("300047","单卡超过单日累积支付次数上限 "); 
		yeepayWapError.put("300048","单卡超过单月累积支付次数上限 "); 
		yeepayWapError.put("300051","该卡为无效银行卡或暂未支持");  
		yeepayWapError.put("300076","绑卡信息已失效，请选择其他卡支付  ");
		yeepayWapError.put("300091","系统异常，请稍后重试  ");
		yeepayWapError.put("300092","查发卡方失败，请联系发卡银行");  
		yeepayWapError.put("300093","本卡在该商户不允许此交易，请联系收单机构");  
		yeepayWapError.put("300094","本卡被发卡方没收，请联系发卡银行"); 
		yeepayWapError.put("300095","有效期、CVV2或手机号有误");  
		yeepayWapError.put("300096","支付失败，请联系发卡银行");  
		yeepayWapError.put("300098","交易超限，请联系发卡银行");  
		yeepayWapError.put("300099","本卡未激活或睡眠卡，请联系发卡银行");  
		yeepayWapError.put("300109","该笔交易风险较高，拒绝此次交易  ");
		yeepayWapError.put("300110","订单已过期或已撤销"); 
	}
	
	
	
	public static int payType=100421;
    //支付宝成功状态
	public static String TRADE_SUCCESS="TRADE_SUCCESS";
	//融宝状态
	//成功
	public static String TRADE_FINISHED="TRADE_FINISHED";
	//等待卖家付款
	public static String WAIT_BUYER_PAY="WAIT_BUYER_PAY";
	//失败
	public static String TRADE_FAILURE="TRADE_FAILURE";
	
	
	public static String JINGCAI_DANGUAN_TYPE = "1001";
	
}
