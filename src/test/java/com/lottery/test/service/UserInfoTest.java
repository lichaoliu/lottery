package com.lottery.test.service;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lottery.core.dao.UserInfoDao;
import com.lottery.core.domain.UserInfo;
import com.lottery.core.service.UserInfoService;
import com.lottery.test.SpringBeanTest;

public class UserInfoTest extends SpringBeanTest {
	@Autowired
	UserInfoService us;
	
	@Autowired
	private UserInfoDao userDao;
	
	@Test
	public void testUserInfo(){
		
//		String uuid = dao.getUserno();
//		info.setUserno(uuid);
//		info.setUsername(username);
//		info.setPasswd(password);
//		info.setIdcard(idCard);
//		info.setPhoneno(mobilePhone);
//		info.setRegisterTime(new Date());
//		info.setAgencyNo(agencyno);
//		info.setTicketTypeId(ticketTypeid);
		
		UserInfo userInfo = new UserInfo();
		
		userInfo.setUsername("zytestq");
		userInfo.setRealName("郭小二q");
		userInfo.setPasswd("998877");
		userInfo.setPhoneno("1875564444");
		userInfo.setStatus(1);
		userInfo.setRegisterTime(new Date());
		
		us.save(userInfo);
		
//		UserInfo ui = us.get("test3");
		
		
//		Map<String,Object> containtMap=new HashMap<String, Object>();
//		containtMap.put("username", "zytest");
//		UserInfo	info=userDao.findByUnique(containtMap);
//		
//		System.out.println("info:"+info);
		
		
//		System.out.println("ui:"+ui);
//		System.out.println(ui.getUserno());
		
		
	}
	
//	@Test
//	public void testModifyPasswd(){
//		UserInfo userInfo = us.updatePassword("9920131118847751", "d0cd2693b3506677e4c55e91d6365ttt", "d0cd2693b3506677e4c55e91d6365byy");
//	}
	
//	@Test
//	public void testUpdateUserInfo(){
//		us.updateUserInfo("9920131118847751", "test111@test1111.com", "");
//	}

}
