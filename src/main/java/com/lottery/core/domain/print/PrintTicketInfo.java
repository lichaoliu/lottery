package com.lottery.core.domain.print;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by fengqinyun on 16/11/12.
 */
@Table(name="print_ticket_info")
@Entity
public class PrintTicketInfo implements Serializable {
    @Id
    @Column(name = "id")
    private String id;//票号
    @Column(name="serial_id")
    private String serialId;//真实票号(落地票号)
    @Column(name="print_stub")
    private String printStub;//原始票根
    @Column(name="prize_stub")
    private String prizeStub;//兑奖票根

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSerialId() {
        return serialId;
    }

    public void setSerialId(String serialId) {
        this.serialId = serialId;
    }

    public String getPrintStub() {
        return printStub;
    }

    public void setPrintStub(String printStub) {
        this.printStub = printStub;
    }



    public String getPrizeStub() {
        return prizeStub;
    }

    public void setPrizeStub(String prizeStub) {
        this.prizeStub = prizeStub;
    }


}
