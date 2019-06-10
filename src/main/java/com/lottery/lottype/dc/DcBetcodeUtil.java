package com.lottery.lottype.dc;

import java.util.ArrayList;
import java.util.List;

public class DcBetcodeUtil {

	public static int getNeedMatchNumByType(int type) {
		int num = 0;
		switch(type) {
		case 101:
			num = 1;
			break;
		case 201:
			num = 2;
			break;
		case 203:
			num = 2;
			break;
		case 301:
			num = 3;
			break;
		case 304:
			num = 3;
			break;
		case 307:
			num = 3;
			break;
		case 401:
			num = 4;
			break;
		case 405:
			num = 4;
			break;
		case 411:
			num = 4;
			break;
		case 415:
			num = 4;
			break;
		case 501:
			num = 5;
			break;
		case 506:
			num = 5;
			break;
		case 516:
			num = 5;
			break;
		case 526:
			num = 5;
			break;
		case 531:
			num = 5;
			break;
		case 601:
			num = 6;
			break;
		case 607:
			num = 6;
			break;
		case 622:
			num = 6;
			break;
		case 642:
			num = 6;
			break;
		case 657:
			num = 6;
			break;
		case 663:
			num = 6;
			break;
		case 701:
			num = 7;
			break;
		case 801:
			num = 8;
			break;
		case 901:
			num = 9;
			break;
		case 1001:
			num = 10;
			break;
		case 1101:
			num = 11;
			break;
		case 1201:
			num = 12;
			break;
		case 1301:
			num = 13;
			break;
		case 1401:
			num = 14;
			break;
		case 1501:
			num = 15;
			break;
		}
		return num;
	}
	
	
	
	public static List<Integer> getCombinationsByType(int type) {
		List<Integer> combinations = new ArrayList<Integer>();
		
		switch(type) {
		case 101:
			combinations.add(1);
			break;
		case 201:
			combinations.add(2);
			break;
		case 203:
			combinations.add(1);
			combinations.add(2);
			break;
		case 301:
			combinations.add(3);
			break;
		case 304:
			combinations.add(2);
			combinations.add(3);
			break;
		case 307:
			combinations.add(1);
			combinations.add(2);
			combinations.add(3);
			break;
		case 401:
			combinations.add(4);
			break;
		case 405:
			combinations.add(3);
			combinations.add(4);
			break;
		case 411:
			combinations.add(2);
			combinations.add(3);
			combinations.add(4);
			break;
		case 415:
			combinations.add(1);
			combinations.add(2);
			combinations.add(3);
			combinations.add(4);
			break;
		case 501:
			combinations.add(5);
			break;
		case 506:
			combinations.add(4);
			combinations.add(5);
			break;
		case 516:
			combinations.add(3);
			combinations.add(4);
			combinations.add(5);
			break;
		case 526:
			combinations.add(2);
			combinations.add(3);
			combinations.add(4);
			combinations.add(5);
			break;
		case 531:
			combinations.add(1);
			combinations.add(2);
			combinations.add(3);
			combinations.add(4);
			combinations.add(5);
			break;
		case 601:
			combinations.add(6);
			break;
		case 607:
			combinations.add(5);
			combinations.add(6);
			break;
		case 622:
			combinations.add(4);
			combinations.add(5);
			combinations.add(6);
			break;
		case 642:
			combinations.add(3);
			combinations.add(4);
			combinations.add(5);
			combinations.add(6);
			break;
		case 657:
			combinations.add(2);
			combinations.add(3);
			combinations.add(4);
			combinations.add(5);
			combinations.add(6);
			break;
		case 663:
			combinations.add(1);
			combinations.add(2);
			combinations.add(3);
			combinations.add(4);
			combinations.add(5);
			combinations.add(6);
			break;
		case 701:
			combinations.add(7);
			break;
		case 801:
			combinations.add(8);
			break;
		case 901:
			combinations.add(9);
			break;
		case 1001:
			combinations.add(10);
			break;
		case 1101:
			combinations.add(11);
			break;
		case 1201:
			combinations.add(12);
			break;
		case 1301:
			combinations.add(13);
			break;
		case 1401:
			combinations.add(14);
			break;
		case 1501:
			combinations.add(15);
			break;
		}
		
		return combinations;
	}
		
}
