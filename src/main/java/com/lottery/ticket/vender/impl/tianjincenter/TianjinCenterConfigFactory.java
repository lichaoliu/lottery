package com.lottery.ticket.vender.impl.tianjincenter;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.core.domain.terminal.TerminalProperty;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.vender.AbstractVenderConfigFactory;
@Service
public class TianjinCenterConfigFactory extends AbstractVenderConfigFactory {

	protected static String  SERVICE_PORT="service_port";
	
	protected static String FTP_PORT="ftp_port";
	protected static String FTP_USER="ftp_user";
	protected static String FTP_PASS="ftp_pass";
	protected static String FTP_IP="ftp_ip";
	protected static String FTP_DIR="ftp_dir";
	protected static String NOTICE_KEY="notice_key";
	@Override
	protected	TerminalType getTerminalType() {
		return TerminalType.T_TJFC_CENTER;
	}
	@Override
	protected void init(){
		configFactoryMap.put(TerminalType.T_TJFC_CENTER, this);
	}
	public IVenderConfig getVenderConfig(List<TerminalProperty> terminalPropertyList){
		if(terminalPropertyList!=null&&terminalPropertyList.size()>0){
			TJFCConfig config=new TJFCConfig();
			try{
				for(TerminalProperty property:terminalPropertyList){
					if(FTP_IP.equals(property.getTerminalKey())){
	    				if(StringUtils.isBlank(property.getTerminalValue())){
	    					logger.error("TerminalProperty中key={}的值为空",property.getTerminalKey());
	    					config=null;
	    					break;
	    				}
	    				config.setFtpIp(property.getTerminalValue());
	    			}
					if(FTP_PORT.equals(property.getTerminalKey())){
						
	    				if(StringUtils.isBlank(property.getTerminalValue())){
	    					logger.error("TerminalProperty中key={}的值为空",property.getTerminalKey());
	    					config=null;
	    					break;
	    				}
	    				config.setFtpPort(Integer.valueOf(property.getTerminalValue()));
	    			}
					if(FTP_USER.equals(property.getTerminalKey())){
	    				if(StringUtils.isBlank(property.getTerminalValue())){
	    					logger.error("TerminalProperty中key={}的值为空",property.getTerminalKey());
	    					config=null;
	    					break;
	    				}
	    				config.setFtpUser(property.getTerminalValue());
	    				}
					if(FTP_PASS.equals(property.getTerminalKey())){
	    				if(StringUtils.isBlank(property.getTerminalValue())){
	    					logger.error("TerminalProperty中key={}的值为空",property.getTerminalKey());
	    					config=null;
	    					break;
	    				}
	    				config.setFtpPass(property.getTerminalValue());
	    			}
					if(SERVICE_PORT.equals(property.getTerminalKey())){
	    				if(StringUtils.isBlank(property.getTerminalValue())){
	    					logger.error("TerminalProperty中key={}的值为空",property.getTerminalKey());
	    					config=null;
	    					break;
	    				}
	    				config.setServicePort(Integer.valueOf(property.getTerminalValue()));
	    			}
					if(FTP_DIR.equals(property.getTerminalKey())){
	    				if(StringUtils.isBlank(property.getTerminalValue())){
	    					logger.error("TerminalProperty中key={}的值为空",property.getTerminalKey());
	    					config=null;
	    					break;
	    				}
	    				config.setFtpDir(property.getTerminalValue());
	    			}
					if(NOTICE_KEY.equals(property.getTerminalKey())){
	    				if(StringUtils.isBlank(property.getTerminalValue())){
	    					logger.error("TerminalProperty中key={}的值为空",property.getTerminalKey());
	    					config=null;
	    					break;
	    				}
	    				config.setNoticeKey(property.getTerminalValue());
	    			}
					configCommon(config, property);
				}
				return config;
			}catch(Exception e){
				logger.error("获取天津福彩出错",e);
			}
		}
		return null;
	}
}
