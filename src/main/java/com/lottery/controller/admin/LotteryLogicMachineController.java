package com.lottery.controller.admin;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottery.common.AdminPage;
import com.lottery.common.ResponseData;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.util.JsonUtil;
import com.lottery.core.dao.LotteryLogicMachineDAO;
import com.lottery.core.domain.ticket.LogicMachinePK;
import com.lottery.core.domain.ticket.LotteryLogicMachine;
import com.lottery.core.service.LotteryLogicMachineService;



@Controller
@RequestMapping("/logicMachine")
public class LotteryLogicMachineController {
	private final Logger logger=LoggerFactory.getLogger(getClass());
	
	@Autowired
	private LotteryLogicMachineDAO machineDAO;
	@Autowired
	private LotteryLogicMachineService machineService;
	
	@RequestMapping(value = "/list")
	public @ResponseBody AdminPage<LotteryLogicMachine> list(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "terminalType", required = false) Integer terminalType,
			@RequestParam(value = "lotteryType", required = false) Integer lotteryType,
			@RequestParam(value = "startLine", required = false, defaultValue = "0") int startLine,
			@RequestParam(value = "endLine", required = false, defaultValue = "30") int endLine) {
		AdminPage<LotteryLogicMachine> page = new AdminPage<LotteryLogicMachine>(startLine, endLine, " order by pk.terminalType asc");
		machineDAO.findPageById(id, terminalType, lotteryType, page);
		return page;
	}

	@RequestMapping(value = "/merge", method = RequestMethod.POST)
	public @ResponseBody ResponseData merge(@RequestParam String body) {
		ResponseData rd = new ResponseData();
		ErrorCode result = ErrorCode.Success;
		try {
			LotteryLogicMachine lm = JsonUtil.flexToObject(LotteryLogicMachine.class, body);
			machineService.saveOrUpdate(lm);
		} catch (Exception e) {
			logger.error("创建修改异常", e);
			result = ErrorCode.Faile;
			rd.setValue(e.getMessage());
		}
		rd.setErrorCode(result.value);
		return rd;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody ResponseData delete(@RequestParam String ids,
			@RequestParam String lotterytypes,
			@RequestParam String terminaltypes) {
		ResponseData rd = new ResponseData();
		ErrorCode result = ErrorCode.Success;
		try {
			List<LogicMachinePK> pks = new ArrayList<LogicMachinePK>();
			String[] id = ids.split(",");
			String[] lotterytype = lotterytypes.split(",");
			String[] terminaltype = terminaltypes.split(",");
			for (int i = 0; i < id.length; i++) {
				pks.add(new LogicMachinePK(Long.parseLong(id[i]), Integer.parseInt(terminaltype[i]), Integer.parseInt(lotterytype[i])));
			}
			machineService.delete(pks);
		} catch (Exception e) {
			logger.error("logicMachine delete异常", e);
			result = ErrorCode.Faile;
			rd.setValue(e.getMessage());
		}
		rd.setErrorCode(result.value);
		return rd;
	}
}
