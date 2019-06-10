package com.lottery.common.contains.lottery;

import java.util.HashMap;
import java.util.Map;

/**
 * 所有返回码
 * 
 * @author Administrator
 * 
 */
public class ReCode {

	public static Map<String, String> bsCode;

	static {
		bsCode = new HashMap<String, String>();
		bsCode.put("170001", "此逻辑机号正在使用");
		bsCode.put("170002", "报文校对错误，非法数据");
		bsCode.put("170003", "该玩法销售受限");
		bsCode.put("170004", "用户此玩法无权限");
		bsCode.put("170005", "与合并通信机通信失败");
		bsCode.put("170006", "次笔交易正在执行");
		bsCode.put("1000", "玩法英文名称不合法");
		bsCode.put("1001", "逻辑机号不合法");
		bsCode.put("1002", "期号不合法");
		bsCode.put("1003", "注码不合法");
		bsCode.put("1004", "注数不正确");
		bsCode.put("1005", "重新计算校验核对失败");
		bsCode.put("1006", "数据传输失败，请重新发送该票");
		bsCode.put("1007", "已期结");
		bsCode.put("1008", "注销票不存在");
		bsCode.put("1009", "该票已经注销");
		bsCode.put("1010", "销售票已经存在");
		bsCode.put("1011", "销售注码超过注数限制");
		bsCode.put("1012", "未到开期时间");
		bsCode.put("1013", "流水号非法");
		bsCode.put("1014", "账号金额不足");
		bsCode.put("1015", "该票存在但不能注销（超过注销时限）");
		bsCode.put("1016", "彩票数据无效");
		bsCode.put("1017", "身份证号码错误");
		bsCode.put("1018", "系统忙");
		bsCode.put("1019", "玩法没有开通");
		bsCode.put("1030", "逻辑机号正在使用");
		bsCode.put("1090", "解析字符串失败");
		
		bsCode.put("180001", "此逻辑机号正在使用");
		bsCode.put("180002", "报文校对错误，非法数据");
		bsCode.put("180003", "该玩法销售受限");
		bsCode.put("180004", "该票兑奖时效过期");
		bsCode.put("180005", "与合并通信机通信失败");
		bsCode.put("180006", "此笔交易正在执行");
		bsCode.put("1020", "玩法英文名称不合法");
		bsCode.put("1021", "逻辑机号不合法");
		bsCode.put("1022", "已经兑奖");
		bsCode.put("1023", "该票已经弃奖");
		bsCode.put("1024", "兑奖期号不合法");
		bsCode.put("1025", "兑奖金额不正确");
		bsCode.put("1026", "数据传输失败");
		bsCode.put("1027", "已中大奖");
		bsCode.put("1028", "未中奖");
		bsCode.put("1029", "中心已期结");
		bsCode.put("190001", "报文信息不符合要求");
		bsCode.put("190002", "用户已经注册");
		bsCode.put("190003", "报文校验错误，非法数据");
		bsCode.put("190005", "与合并通信机通信失败");
		bsCode.put("1020", "逻辑机机号非法");
		bsCode.put("1021", "取账户金额出错");
		bsCode.put("1022", "已经兑奖");
		bsCode.put("1023", "该票已经弃奖");
		bsCode.put("1024", "兑奖期号不合法");
		bsCode.put("1025", "兑奖金额是否正确");
		bsCode.put("1026", "数据传输失败，请重新发送该票");
		bsCode.put("1027", "已中大奖");
		bsCode.put("1028", "未中奖");
	}

}
