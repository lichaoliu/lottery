package com.lottery.ticket.vender.impl.anrui;

import com.lottery.ticket.vender.impl.BaseConfig;

/**
 * Created by fengqinyun on 15/4/19.
 */
public class AnRuiConfig extends BaseConfig {


    private String jczqMatch;//竞彩足球赛程url

    private String jclqMatch;//竞彩篮球url

    public String getJczqMatch() {
        return jczqMatch;
    }

    public void setJczqMatch(String jczqMatch) {
        this.jczqMatch = jczqMatch;
    }

    public String getJclqMatch() {
        return jclqMatch;
    }

    public void setJclqMatch(String jclqMatch) {
        this.jclqMatch = jclqMatch;
    }
}
