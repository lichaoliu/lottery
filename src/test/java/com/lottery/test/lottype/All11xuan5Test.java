package com.lottery.test.lottype;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.lottery.common.util.StringUtil;
import com.lottery.core.domain.ticket.LotteryOrder;
import com.lottery.lottype.Gd11x5;
import com.lottery.lottype.Sd11x5;

public class All11xuan5Test {
	
	static Sd11x5 sd11x5 = new Sd11x5();
	static Gd11x5 gd11x5 = new Gd11x5();
	
	
	public static void main(String[] args) throws BiffException, IOException {
		List<LotteryOrder> orders = readExcel("F:\\guangdong.xls");
		
		Map<String,String> wincodes = new HashMap<String, String>();
//		wincodes.put("16041901", "08,01,09,10,04");
//		wincodes.put("16041902", "09,04,11,06,10");
//		wincodes.put("16041903", "07,01,05,10,03");
		
		wincodes.put("16041901", "05,11,02,08,09");
		wincodes.put("16041902", "03,08,02,04,09");
		wincodes.put("16041903", "01,10,06,07,03");
		
		
		for(LotteryOrder order:orders) {
			String prizeLevelInfo = gd11x5.getPrizeLevelInfo(order.getContent(), wincodes.get(order.getPhase()), new BigDecimal(order.getMultiple()), 200);
			
			if(StringUtil.isNotEmpt(prizeLevelInfo)) {
				System.out.println(order.getId()+" "+prizeLevelInfo);
			}
		}
		
	}

	public static List<LotteryOrder> readExcel(String file) throws BiffException, IOException {
		// 创建一个list 用来存储读取的内容
		List<LotteryOrder> list = new ArrayList<LotteryOrder>();

		// 获取Excel文件对象
		Workbook rwb = Workbook.getWorkbook(new FileInputStream(file));

		// 获取文件的指定工作表 默认的第一个
		Sheet sheet = rwb.getSheet(0);

		// 行数(表头的目录不需要，从1开始)
		for (int i = 1; i < sheet.getRows(); i++) {
			LotteryOrder order = new LotteryOrder();
			order.setId(sheet.getCell(0, i).getContents());
			order.setPhase(sheet.getCell(1, i).getContents());
			order.setMultiple(Integer.parseInt(sheet.getCell(2, i).getContents()));
			order.setContent(sheet.getCell(6, i).getContents());
			list.add(order);
		}

		return list;
	}

}
