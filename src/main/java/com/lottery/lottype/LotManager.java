package com.lottery.lottype;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LotManager {

	@Autowired
	private Map<String,Lot> lotsMap;
	public Lot getLot(String lotno) {
		return lotsMap.get(lotno);
	}
	

	
}
