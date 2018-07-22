package com.lottery.core.domain.event;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "event_detail")
/**
 * 在virtual_lottery
 */
public class EventDetail implements Serializable {


    private static final long serialVersionUID = 1946336951501222157L;

    @Id
	@Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	@Column(name = "userno", nullable = false)
	private String userno; // userno
	@Column(name = "event_id", nullable = false)
	private Long eventId;
    @Column(name = "date_time")
    private Date dateTime; // 生成时间
    @Column(name = "orderid")
    private String orderid; // 订单号
    @Column(name = "prize")
    private double prize; // 奖励数
    @Column(name = "ext")
    private String ext; // 扩展字段

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserno() {
        return userno;
    }

    public void setUserno(String userno) {
        this.userno = userno;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public double getPrize() {
        return prize;
    }

    public void setPrize(double prize) {
        this.prize = prize;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    @Override
    public String toString() {
        return "EventDetail{" +
                "id=" + id +
                ", userno='" + userno + '\'' +
                ", eventId='" + eventId + '\'' +
                ", dateTime=" + dateTime +
                ", orderid='" + orderid + '\'' +
                ", prize=" + prize +
                ", ext='" + ext + '\'' +
                '}';
    }
}
