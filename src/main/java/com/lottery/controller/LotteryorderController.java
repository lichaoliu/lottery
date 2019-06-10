package com.lottery.controller;
/**
 * 订单查询
 * @author fengqinyun
 *
 * ResponseEntity
 * */
import com.lottery.common.PageBean;
import com.lottery.common.ResponseData;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.BetType;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.common.util.StringUtil;
import com.lottery.core.domain.ticket.LotteryOrder;
import com.lottery.core.domain.ticket.LotteryUploadFile;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.handler.OrderSplitHandler;
import com.lottery.core.service.LotteryOrderService;
import com.lottery.core.service.LotteryUploadFileService;
import com.lottery.core.service.TicketService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/lotteryOrder")
public class LotteryorderController {
	
	protected final Logger logger=LoggerFactory.getLogger(getClass());
    @Autowired
	private LotteryOrderService lotteryOrderService;
    @Autowired
    private OrderSplitHandler splitHandler;
    @Autowired
    private TicketService ticketService;
    @Autowired
    protected LotteryUploadFileService uploadFileService;
    
    
    @Deprecated
    @RequestMapping(value="/get",method=RequestMethod.POST)
	public @ResponseBody ResponseData getBet(@RequestParam("userno") String userno,@RequestParam(value="type",required=false,defaultValue="0") String type,
			@RequestParam(value="startLine",required=false,defaultValue="1") int startLine,
			@RequestParam(value="maxLine",required=false,defaultValue="15") int maxResult,
			@RequestParam(value = "startTime", required = false,defaultValue="") String startTime,
			@RequestParam(value = "endTime", required = false,defaultValue="") String endTime,
			@RequestParam(value = "lotteryType", required = false ,defaultValue="") String lotteryType){
		ResponseData rd=new ResponseData();
		try{
			PageBean<LotteryOrder> page=new PageBean<LotteryOrder>();
			page.setPageIndex(startLine);
			page.setMaxResult(maxResult);
			
			if(StringUtil.isEmpty(endTime)){
				Calendar c = Calendar.getInstance();
				c.setTime(new Date());
				c.add(Calendar.DATE, 1);
				endTime=CoreDateUtils.formatDate(c.getTime(), CoreDateUtils.DATE);
			}
			if(StringUtil.isEmpty(startTime)){
				Date end=CoreDateUtils.parseDate(endTime,CoreDateUtils.DATE);
				Calendar c=Calendar.getInstance();
				c.setTime(end);
				c.add(Calendar.MONTH, -3);
				startTime=CoreDateUtils.formatDate(c.getTime(), CoreDateUtils.DATE);
			}
			
			if((CoreDateUtils.parseDate(startTime, CoreDateUtils.DATE)).after(CoreDateUtils.parseDate(endTime, CoreDateUtils.DATE))){
				rd.setErrorCode(ErrorCode.select_date_error.getValue());
				return rd;
			}
			
			List<LotteryOrder> list =  lotteryOrderService.get(userno, startTime, endTime, page,type,lotteryType);
			page.setList(list);
			rd.setErrorCode(ErrorCode.Success.getValue());
			rd.setValue(page);
		}catch(LotteryException e){
			rd.setErrorCode(e.getErrorCode().getValue());
		}catch(Exception e){
			e.printStackTrace();
			rd.setErrorCode(ErrorCode.system_error.getValue());
		}
		return rd;
	}
    
    
    @RequestMapping(value="/getUserOrder",method=RequestMethod.POST)
	public @ResponseBody ResponseData getUserOrder(@RequestParam("userno") String userno,@RequestParam(value="type",required=false,defaultValue="0") String type,
			@RequestParam(value="startLine",required=false,defaultValue="1") int startLine,
			@RequestParam(value="maxLine",required=false,defaultValue="15") int maxResult,
			@RequestParam(value = "startTime", required = false,defaultValue="") String startTime,
			@RequestParam(value = "endTime", required = false,defaultValue="") String endTime,
			@RequestParam(value = "lotteryType", required = false ,defaultValue="") Integer[] lotteryType){
		ResponseData rd=new ResponseData();
		try{
			PageBean<LotteryOrder> page=new PageBean<LotteryOrder>();
			page.setPageIndex(startLine);
			page.setMaxResult(maxResult);
			
			if(StringUtil.isEmpty(endTime)){
				Calendar c = Calendar.getInstance();
				c.setTime(new Date());
				c.add(Calendar.DATE, 1);
				endTime=CoreDateUtils.formatDate(c.getTime(), CoreDateUtils.DATE);
			}
			if(StringUtil.isEmpty(startTime)){
				Date end=CoreDateUtils.parseDate(endTime,CoreDateUtils.DATE);
				Calendar c=Calendar.getInstance();
				c.setTime(end);
				c.add(Calendar.MONTH, -3);
				startTime=CoreDateUtils.formatDate(c.getTime(), CoreDateUtils.DATE);
			}
			
			if((CoreDateUtils.parseDate(startTime, CoreDateUtils.DATE)).after(CoreDateUtils.parseDate(endTime, CoreDateUtils.DATE))){
				rd.setErrorCode(ErrorCode.select_date_error.getValue());
				return rd;
			}
			
			List<Integer> lotteryTypes = new ArrayList<Integer>();
			
			if(lotteryType!=null&&lotteryType.length>0) {
				lotteryTypes = Arrays.asList(lotteryType);
			}
			
			List<LotteryOrder> list =  lotteryOrderService.get(userno, startTime, endTime, page,type,lotteryTypes);
			page.setList(list);
			rd.setErrorCode(ErrorCode.Success.getValue());
			rd.setValue(page);
		}catch(LotteryException e){
			rd.setErrorCode(e.getErrorCode().getValue());
		}catch(Exception e){
			e.printStackTrace();
			rd.setErrorCode(ErrorCode.system_error.getValue());
		}
		return rd;
	}

	/**
	 * 获取所有中奖的订单
	 * */
	@RequestMapping(value="/getAllPrize",method=RequestMethod.POST)
	public @ResponseBody ResponseData getAllPrize(@RequestParam("userno") String userno,
											 @RequestParam(value="startLine",required=false,defaultValue="1") int startLine,
											 @RequestParam(value="maxLine",required=false,defaultValue="15") int maxResult,
											 @RequestParam(value = "startTime", required = false,defaultValue="") String startTime,
											 @RequestParam(value = "endTime", required = false,defaultValue="") String endTime
											){
		ResponseData rd=new ResponseData();
		try{
			PageBean<LotteryOrder> page=new PageBean<LotteryOrder>();
			page.setPageIndex(startLine);
			page.setMaxResult(maxResult);

			List<LotteryOrder> list =lotteryOrderService.getAllPrize(userno,startTime,endTime,page);
			page.setList(list);
			rd.setErrorCode(ErrorCode.Success.getValue());
			rd.setValue(page);
		}catch(LotteryException e){
			rd.setErrorCode(e.getErrorCode().getValue());
		}catch(Exception e){
			e.printStackTrace();
			rd.setErrorCode(ErrorCode.system_error.getValue());
		}
		return rd;
	}
    /**
     * 查询投注订单
     * @param userno 用户名
     * @param startLine 开始页数，
     * @param maxResult 每页多少条
     * @param startTime 开始时间
     * @param endTime 结束时间
     * */
    @RequestMapping(value="/getOrderList",method=RequestMethod.POST)
   	public @ResponseBody ResponseData getLotteryOrderList(@RequestParam("userno") String userno,
   			@RequestParam(value="startLine",required=false,defaultValue="1") int startLine,
   			@RequestParam(value="maxLine",required=false,defaultValue="15") int maxResult,
   			@RequestParam(value = "startTime", required = false,defaultValue="") String startTime,
   			@RequestParam(value = "endTime", required = false,defaultValue="") String endTime){
   		ResponseData rd=new ResponseData();
   		try{
   			PageBean<LotteryOrder> page=new PageBean<LotteryOrder>();
   			page.setPageIndex(startLine);
   			page.setMaxResult(maxResult);
   			List<LotteryOrder> list =  lotteryOrderService.getOrderListByUserno(userno, startTime, endTime, page);
   			page.setList(list);
   			rd.setErrorCode(ErrorCode.Success.getValue());
   			rd.setValue(page);
   		}catch(LotteryException e){
   			rd.setErrorCode(e.getErrorCode().getValue());
   		}catch(Exception e){
   			e.printStackTrace();
   			rd.setErrorCode(ErrorCode.system_error.getValue());
   		}
   		return rd;
   	}
    
    @RequestMapping(value="/getToday",method=RequestMethod.POST)
	public @ResponseBody ResponseData getToday(
			@RequestParam(value="startLine",required=false,defaultValue="0") int startLine,
			@RequestParam(value="maxLine",required=false,defaultValue="200") int maxResult,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime){
		ResponseData rd=new ResponseData();
		try{
			PageBean<LotteryOrder> page=new PageBean<LotteryOrder>();
			page.setPageIndex(startLine);
			page.setMaxResult(maxResult);
			List<LotteryOrder> list =  lotteryOrderService.getToday(page);
			rd.setErrorCode(ErrorCode.Success.getValue());
			rd.setValue(list);
		}catch(Exception e){
			logger.error("查询最近中奖出错",e);
			rd.setErrorCode(ErrorCode.system_error.getValue());
		}
		return rd;
	}
    
    
    /**
     * 重新拆票
     * @param orderid
     * @return
     */
    @RequestMapping(value = "/split", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData split(
			@RequestParam(value = "orderid", required = true) String orderid) {
		ResponseData rd = new ResponseData();
		try {
			splitHandler.handler(orderid);
			rd.setErrorCode(ErrorCode.Success.getValue());
		} catch (LotteryException e) {
			rd.setErrorCode(e.getErrorCode().getValue());
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
		}

		return rd;
	}
    /**
     * 投注详情
     * @param orderid
     * @return
     */
    @RequestMapping(value = "/getInfo", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData getOrderInfo(
			@RequestParam(value = "orderid", required = true) String orderid) {
		ResponseData rd = new ResponseData();
		try {
			LotteryOrder lotteryOrder = lotteryOrderService.get(orderid);
			if(lotteryOrder!=null&&lotteryOrder.getBetType()==BetType.upload.value){
              LotteryUploadFile upload=uploadFileService.get(lotteryOrder.getId());
              if(upload!=null){
            	  lotteryOrder.setContent(upload.getContent());  
              }
			}
			rd.setValue(lotteryOrder);
			rd.setErrorCode(ErrorCode.Success.getValue());
		} catch (LotteryException e) {
			rd.setErrorCode(e.getErrorCode().getValue());
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}
    /**
     * 通过订单号查询票
     * @param orderid
     * @return
     */
    @RequestMapping(value = "/getTicketInfo", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData getTicketInfo(
			@RequestParam(value = "orderid", required = true) String orderid) {
		ResponseData rd = new ResponseData();
		try {
			List<Ticket> ticket = ticketService.getByorderId(orderid);
			rd.setValue(ticket);
			rd.setErrorCode(ErrorCode.Success.getValue());
		} catch (LotteryException e) {
			rd.setErrorCode(e.getErrorCode().getValue());
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}
    
    
    
    /**
     * 查询投注订单
     * @param userno 用户名
     * @param startLine 开始页数，
     * @param maxResult 每页多少条
     * @param startTime 开始时间
     * @param endTime 结束时间
     * */
    @RequestMapping(value="/getTicketList",method=RequestMethod.POST)
   	public @ResponseBody ResponseData getTicketList(@RequestParam("orderId") String orderId,
   			@RequestParam(value="startLine",required=false,defaultValue="1") int startLine,
   			@RequestParam(value="maxLine",required=false,defaultValue="15") int maxResult){
   		ResponseData rd=new ResponseData();
   		try{
   			PageBean<Ticket> page=new PageBean<Ticket>();
   			page.setPageIndex(startLine);
   			page.setMaxResult(maxResult);
   			List<Ticket> tickets = ticketService.getByorderIdIgnoreStatus(orderId, page);
   			page.setList(tickets);
   			rd.setErrorCode(ErrorCode.Success.getValue());
   			rd.setValue(page);
   		}catch(LotteryException e){
   			rd.setErrorCode(e.getErrorCode().getValue());
   		}catch(Exception e){
   			e.printStackTrace();
   			rd.setErrorCode(ErrorCode.system_error.getValue());
   		}
   		return rd;
   	}
}
