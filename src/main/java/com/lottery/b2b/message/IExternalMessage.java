package com.lottery.b2b.message;



public interface IExternalMessage {

    /**
     * @return 版本号
     */
    public String getVersion();

    /**
     * @return 接入商编码
     */
    public String getMerchant();
   
    
    /**
     * @return 请求的消息序列号
     */
    public String getMessageId();

    /**
     * @return 请求消息的命令
     */
    public String getCommand();

   
    
    /**
     * 时间戳格式：yyyyMMddHHmmss
     * @return 时间戳
     */
    public String getTimestamp();

    /**
     * @return 消息体正文对象
     */
    public String getMessageBody();
    /**
     * @return 内容校验码
     * **/
    public String getSignature();
  
}
