package com.lottery.lottype.jc;

import java.util.ArrayList;
import java.util.List;

public class JingcaiBetcodeUtil {

	public static int getNeedMatchNumByType(int type) {
		int num = 0;
		switch(type) {
		case 1001:
			num = 1;
			break;
		case 2001:
			num = 2;
			break;
		case 3001:
			num = 3;
			break;
		case 3003:
			num = 3;
			break;
		case 3004:
			num = 3;
			break;
		case 4001:
			num = 4;
			break;
		case 4004:
			num = 4;
			break;
		case 4005:
			num = 4;
			break;
		case 4006:
			num = 4;
			break;
		case 4011:
			num = 4;
			break;
		case 5001:
			num = 5;
			break;
		case 5005:
			num = 5;
			break;
		case 5006:
			num = 5;
			break;
		case 5010:
			num = 5;
			break;
		case 5016:
			num = 5;
			break;
		case 5020:
			num = 5;
			break;
		case 5026:
			num = 5;
			break;
		case 6001:
			num = 6;
			break;
		case 6006:
			num = 6;
			break;
		case 6007:
			num = 6;
			break;
		case 6015:
			num = 6;
			break;
		case 6020:
			num = 6;
			break;
		case 6022:
			num = 6;
			break;
		case 6035:
			num = 6;
			break;
		case 6042:
			num = 6;
			break;
		case 6050:
			num = 6;
			break;
		case 6057:
			num = 6;
			break;
		case 7001:
			num = 7;
			break;
		case 7007:
			num = 7;
			break;
		case 7008:
			num = 7;
			break;
		case 7021:
			num = 7;
			break;
		case 7035:
			num = 7;
			break;
		case 7120:
			num = 7;
			break;
		case 8001:
			num = 8;
			break;
		case 8008:
			num = 8;
			break;
		case 8009:
			num = 8;
			break;
		case 8028:
			num = 8;
			break;
		case 8056:
			num = 8;
			break;
		case 8070:
			num = 8;
			break;
		case 8247:
			num = 8;
			break;
		case 2003:
			num = 2;
			break;
		case 3006:
			num = 3;
			break;
		case 3007:
			num = 3;
			break;
		case 4010:
			num = 4;
			break;
		case 4014:
			num = 4;
			break;
		case 4015:
			num = 4;
			break;
		case 5015:
			num = 5;
			break;
		case 5025:
			num = 5;
			break;
		case 5030:
			num = 5;
			break;
		case 5031:
			num = 5;
			break;
		case 6021:
			num = 6;
			break;
		case 6041:
			num = 6;
			break;
		case 6056:
			num = 6;
			break;
		case 6062:
			num = 6;
			break;
		case 6063:
			num = 6;
			break;
		case 7127:
			num = 7;
			break;
		case 8255:
			num = 8;
			break;
		}
		return num;
	}
	
	
	public static List<Integer> getCombinationsByType(int type) {
		List<Integer> combinations = new ArrayList<Integer>();
		
		switch(type) {
		case 1001:
			combinations.add(1);
			break;
		case 2001:
			combinations.add(2);
			break;
		case 3001:
			combinations.add(3);
			break;
		case 3003:
			combinations.add(2);
			break;
		case 3004:
			combinations.add(2);
			combinations.add(3);
			break;
		case 4001:
			combinations.add(4);
			break;
		case 4004:
			combinations.add(3);
			break;
		case 4005:
			combinations.add(3);
			combinations.add(4);
			break;
		case 4006:
			combinations.add(2);
			break;
		case 4011:
			combinations.add(2);
			combinations.add(3);
			combinations.add(4);
			break;
		case 5001:
			combinations.add(5);
			break;
		case 5005:
			combinations.add(4);
			break;
		case 5006:
			combinations.add(4);
			combinations.add(5);
			break;
		case 5010:
			combinations.add(2);
			break;
		case 5016:
			combinations.add(3);
			combinations.add(4);
			combinations.add(5);
			break;
		case 5020:
			combinations.add(2);
			combinations.add(3);
			break;
		case 5026:
			combinations.add(2);
			combinations.add(3);
			combinations.add(4);
			combinations.add(5);
			break;
		case 6001:
			combinations.add(6);
			break;
		case 6006:
			combinations.add(5);
			break;
		case 6007:
			combinations.add(5);
			combinations.add(6);
			break;
		case 6015:
			combinations.add(2);
			break;
		case 6020:
			combinations.add(3);
			break;
		case 6022:
			combinations.add(4);
			combinations.add(5);
			combinations.add(6);
			break;
		case 6035:
			combinations.add(2);
			combinations.add(3);
			break;
		case 6042:
			combinations.add(3);
			combinations.add(4);
			combinations.add(5);
			combinations.add(6);
			break;
		case 6050:
			combinations.add(2);
			combinations.add(3);
			combinations.add(4);
			break;
		case 6057:
			combinations.add(2);
			combinations.add(3);
			combinations.add(4);
			combinations.add(5);
			combinations.add(6);
			break;
		case 7001:
			combinations.add(7);
			break;
		case 7007:
			combinations.add(6);
			break;
		case 7008:
			combinations.add(6);
			combinations.add(7);
			break;
		case 7021:
			combinations.add(5);
			break;
		case 7035:
			combinations.add(4);
			break;
		case 7120:
			combinations.add(2);
			combinations.add(3);
			combinations.add(4);
			combinations.add(5);
			combinations.add(6);
			combinations.add(7);
			break;
		case 8001:
			combinations.add(8);
			break;
		case 8008:
			combinations.add(7);
			break;
		case 8009:
			combinations.add(7);
			combinations.add(8);
			break;
		case 8028:
			combinations.add(6);
			break;
		case 8056:
			combinations.add(5);
			break;
		case 8070:
			combinations.add(4);
			break;
		case 8247:
			combinations.add(2);
			combinations.add(3);
			combinations.add(4);
			combinations.add(5);
			combinations.add(6);
			combinations.add(7);
			combinations.add(8);
			break;
		case 2003:
			combinations.add(1);
			combinations.add(2);
			break;
		case 3006:
			combinations.add(1);
			combinations.add(2);
			break;
		case 3007:
			combinations.add(1);
			combinations.add(2);
			combinations.add(3);
			break;
		case 4010:
			combinations.add(1);
			combinations.add(2);
			break;
		case 4014:
			combinations.add(1);
			combinations.add(2);
			combinations.add(3);
			break;
		case 4015:
			combinations.add(1);
			combinations.add(2);
			combinations.add(3);
			combinations.add(4);
			break;
		case 5015:
			combinations.add(1);
			combinations.add(2);
			break;
		case 5025:
			combinations.add(1);
			combinations.add(2);
			combinations.add(3);
			break;
		case 5030:
			combinations.add(1);
			combinations.add(2);
			combinations.add(3);
			combinations.add(4);
			break;
		case 5031:
			combinations.add(1);
			combinations.add(2);
			combinations.add(3);
			combinations.add(4);
			combinations.add(5);
			break;
		case 6021:
			combinations.add(1);
			combinations.add(2);
			break;
		case 6041:
			combinations.add(1);
			combinations.add(2);
			combinations.add(3);
			break;
		case 6056:
			combinations.add(1);
			combinations.add(2);
			combinations.add(3);
			combinations.add(4);
			break;
		case 6062:
			combinations.add(1);
			combinations.add(2);
			combinations.add(3);
			combinations.add(4);
			combinations.add(5);
			break;
		case 6063:
			combinations.add(1);
			combinations.add(2);
			combinations.add(3);
			combinations.add(4);
			combinations.add(5);
			combinations.add(6);
			break;
		case 7127:
			combinations.add(1);
			combinations.add(2);
			combinations.add(3);
			combinations.add(4);
			combinations.add(5);
			combinations.add(6);
			combinations.add(7);
			break;
		case 8255:
			combinations.add(1);
			combinations.add(2);
			combinations.add(3);
			combinations.add(4);
			combinations.add(5);
			combinations.add(6);
			combinations.add(7);
			combinations.add(8);
			break;
		}
		
		return combinations;
	}
	
	
	
	
	
	
	/**
	 * 根据玩法返回此玩法需要开奖的最少赛果数
	 * @param type
	 * @return
	 */
	public static Integer getMinResultNumByType(int type) {
		int min = 1;
		
		switch(type) {
		case 1001:
			min = 1;
			break;
		case 2001:
			min = 1;
			break;
		case 3001:
			min = 1;
			break;
		case 3003:
			min = 2;
			break;
		case 3004:
			min = 2;
			break;
		case 4001:
			min = 1;
			break;
		case 4004:
			min = 2;
			break;
		case 4005:
			min = 2;
			break;
		case 4006:
			min = 3;
			break;
		case 4011:
			min = 3;
			break;
		case 5001:
			min = 1;
			break;
		case 5005:
			min = 2;
			break;
		case 5006:
			min = 2;
			break;
		case 5010:
			min = 4;
			break;
		case 5016:
			min = 3;
			break;
		case 5020:
			min = 4;
			break;
		case 5026:
			min = 4;
			break;
		case 6001:
			min = 1;
			break;
		case 6006:
			min = 2;
			break;
		case 6007:
			min = 2;
			break;
		case 6015:
			min = 5;
			break;
		case 6020:
			min = 4;
			break;
		case 6022:
			min = 3;
			break;
		case 6035:
			min = 5;
			break;
		case 6042:
			min = 4;
			break;
		case 6050:
			min = 5;
			break;
		case 6057:
			min = 5;
			break;
		case 7001:
			min = 1;
			break;
		case 7007:
			min = 2;
			break;
		case 7008:
			min = 2;
			break;
		case 7021:
			min = 3;
			break;
		case 7035:
			min = 4;
			break;
		case 7120:
			min = 6;
			break;
		case 8001:
			min = 1;
			break;
		case 8008:
			min = 2;
			break;
		case 8009:
			min = 2;
			break;
		case 8028:
			min = 3;
			break;
		case 8056:
			min = 4;
			break;
		case 8070:
			min = 5;
			break;
		case 8247:
			min = 7;
			break;
		}
		
		return min;
	}
}
