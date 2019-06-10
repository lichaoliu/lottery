package com.lottery.pay.impl;

import com.lottery.common.contains.PayPropertyConstant;
import com.lottery.common.contains.pay.PayChannel;
import com.lottery.core.domain.PayProperty;
import com.lottery.pay.BasePayConfig;
import com.lottery.pay.IPayConfig;
import com.lottery.pay.IPayConfigFactory;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public abstract class AbstractPayConfigFactory implements IPayConfigFactory {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Resource(name="payFactoryMap")
	protected Map<PayChannel,IPayConfigFactory> payFactoryMap;

	
	public IPayConfig getVenderConfig(List<PayProperty> payConfigList) {
		BasePayConfig zfbConfig=new BasePayConfig();
		if (payConfigList != null && payConfigList.size() > 0) {
			for (PayProperty payConfig : payConfigList) {
				commonConfig(zfbConfig, payConfig);
				}
			   return zfbConfig;
			 }
		  return null;
		}
	
	protected void commonConfig(BasePayConfig zfbConfig,PayProperty payConfig){
		if (StringUtils.isBlank(payConfig.getConfigName())) {
			logger.error("获取到的payConfig中的key为null");

			return;
		}
		if (PayPropertyConstant.PARTNER.equals(payConfig.getConfigName())) {
			if (StringUtils.isBlank(payConfig.getConfigValue())) {
				logger.error("payConfig中key={}的值为空",payConfig.getConfigValue());

				return;
			}
			zfbConfig.setPartner(payConfig.getConfigValue());
			
		}
		
		if (PayPropertyConstant.NOTICE_URL.equals(payConfig.getConfigName())) {
			if (StringUtils.isBlank(payConfig.getConfigValue())) {
				logger.error("payConfig中key={}的值为空",payConfig.getConfigValue());

				return;
			}
			zfbConfig.setNoticeUrl(payConfig.getConfigValue());
			
		 }
		if (PayPropertyConstant.KEY.equals(payConfig.getConfigName())) {
			if (StringUtils.isBlank(payConfig.getConfigValue())) {
				logger.error("payConfig中key={}的值为空",payConfig.getConfigValue());

				return;
			}
			zfbConfig.setKey(payConfig.getConfigValue());
			
		 }
		if (PayPropertyConstant.REQUEST_URL.equals(payConfig.getConfigName())) {
			if (StringUtils.isBlank(payConfig.getConfigValue())) {
				logger.error("payConfig中key={}的值为空",payConfig.getConfigValue());
				return;
			}
			zfbConfig.setRequestUrl(payConfig.getConfigValue());
			 
		 }
		if (PayPropertyConstant.RETURN_URL.equals(payConfig.getConfigName())) {
			if (StringUtils.isBlank(payConfig.getConfigValue())) {
				logger.error("payConfig中key={}的值为空",payConfig.getConfigValue());

				return;
			}
			zfbConfig.setReturnUrl(payConfig.getConfigValue());
		 }
		
		if (PayPropertyConstant.SEARCH_URL.equals(payConfig.getConfigName())) {
			if (StringUtils.isBlank(payConfig.getConfigValue())) {
				logger.error("payConfig中key={}的值为空",payConfig.getConfigValue());
				return;
			}
			zfbConfig.setSearchUrl(payConfig.getConfigValue());
		 }
		if (PayPropertyConstant.PRIVATE_KEY.equals(payConfig.getConfigName())) {
			if (StringUtils.isBlank(payConfig.getConfigValue())) {
				logger.error("payConfig中key={}的值为空",payConfig.getConfigValue());
				return;
			}
			zfbConfig.setPrivateKey(payConfig.getConfigValue());
		 }
		 if (PayPropertyConstant.PUBLIC_KEY.equals(payConfig.getConfigName())) {
			if (StringUtils.isBlank(payConfig.getConfigValue())) {
				logger.error("payConfig中key={}的值为空",payConfig.getConfigValue());

				return;
			}
			zfbConfig.setPublicKey(payConfig.getConfigValue());
		  }
		 if (PayPropertyConstant.SELLER.equals(payConfig.getConfigName())) {
			if (StringUtils.isBlank(payConfig.getConfigValue())) {
				logger.error("payConfig中key={}的值为空",payConfig.getConfigValue());

				return;
			}
			zfbConfig.setSeller(payConfig.getConfigValue());
		 }
		 if (PayPropertyConstant.FEE.equals(payConfig.getConfigName())) {
				if (StringUtils.isBlank(payConfig.getConfigValue())) {
					logger.error("payConfig中key={}的值为空",payConfig.getConfigValue());

					return;
				}
				zfbConfig.setFee(new BigDecimal(payConfig.getConfigValue()));
			 }
		 if (PayPropertyConstant.ACCOUNTNAME.equals(payConfig.getConfigName())) {
				if (StringUtils.isBlank(payConfig.getConfigValue())) {
					logger.error("payConfig中key={}的值为空",payConfig.getConfigValue());

					return;
				}
				zfbConfig.setAccountName(payConfig.getConfigValue());
			 }
		 if (PayPropertyConstant.SUBJECT.equals(payConfig.getConfigName())) {
				if (StringUtils.isBlank(payConfig.getConfigValue())) {
					logger.error("payConfig中key={}的值为空",payConfig.getConfigValue());

					return;
				}
				zfbConfig.setSubject(payConfig.getConfigValue());
			 }
		 if (PayPropertyConstant.DESCRIPTION.equals(payConfig.getConfigName())) {
				if (StringUtils.isBlank(payConfig.getConfigValue())) {
					logger.error("payConfig中key={}的值为空",payConfig.getConfigValue());

					return;
				}
				zfbConfig.setDescription(payConfig.getConfigValue());
			 }
		if (PayPropertyConstant.ENCRYPT_CER_PATH.equals(payConfig.getConfigName())) {
			if (StringUtils.isBlank(payConfig.getConfigValue())) {
				logger.error("payConfig中key={}的值为空",payConfig.getConfigValue());
				return;
			}
			zfbConfig.setEncryptCerPath(payConfig.getConfigValue());
		}
		if (PayPropertyConstant.PUBLIC_CER_PATH.equals(payConfig.getConfigName())) {
			if (StringUtils.isBlank(payConfig.getConfigValue())) {
				logger.error("payConfig中key={}的值为空",payConfig.getConfigValue());
				return;
			}
			zfbConfig.setPublicCerPath(payConfig.getConfigValue());
		}
		if (PayPropertyConstant.PRIVATE_CER_PATH.equals(payConfig.getConfigName())) {
			if (StringUtils.isBlank(payConfig.getConfigValue())) {
				logger.error("payConfig中key={}的值为空",payConfig.getConfigValue());
				return;
			}
			zfbConfig.setPrivateCerPath(payConfig.getConfigValue());
		}
		if (PayPropertyConstant.PASSWD.equals(payConfig.getConfigName())) {
			if (StringUtils.isBlank(payConfig.getConfigValue())) {
				logger.error("payConfig中key={}的值为空",payConfig.getConfigValue());
				return;
			}
			zfbConfig.setPasswd(payConfig.getConfigValue());
		}
		if (PayPropertyConstant.SHOW_URL.equals(payConfig.getConfigName())) {
			if (StringUtils.isBlank(payConfig.getConfigValue())) {
				logger.error("payConfig中key={}的值为空",payConfig.getConfigValue());
				return;
			}
			zfbConfig.setShowUrl(payConfig.getConfigValue());
		}
		if (PayPropertyConstant.IP.equals(payConfig.getConfigName())) {
			if (StringUtils.isBlank(payConfig.getConfigValue())) {
				logger.error("payConfig中ip={}的值为空",payConfig.getConfigValue());
				return;
			}
			zfbConfig.setIp(payConfig.getConfigValue());
		}
		if (PayPropertyConstant.IS_PAUSED.equals(payConfig.getConfigName())) {
			if (StringUtils.isBlank(payConfig.getConfigValue())) {
				logger.error("payConfig中key={}的值为空",payConfig.getConfigValue());
				return;
			}
			zfbConfig.setIsPaused(payConfig.getConfigValue());
		}



	}
	@PostConstruct
    protected 	abstract  void init();
   }