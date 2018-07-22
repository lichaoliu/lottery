package com.lottery.core.domain.event;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "event_info")
/**
 * 在virtual_lottery
 */
public class EventInfo implements Serializable {

    private static final long serialVersionUID = -6461867791715793787L;

    @Id
	@Column(name = "event_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(name = "event_name", nullable = false)
	private String name; // 事件名称
	@Column(name = "event_description", nullable = false)
	private String description; // 事件描述
    @Column(name = "create_time")
    private Date createTime; // 生成时间
    @Column(name = "event_time_start")
    private Date startTime; // 开始时间
    @Column(name = "event_time_end")
    private Date endTime; // 结束时间
    @Column(name = "status", nullable = false) //是否启用 YesNoStatus
    private Integer status; // 事件状态
    @Column(name = "type", nullable = false)
    private Integer type; // 事件类型
    @Column(name="ext")
    private String ext;//事件扩展字段
    @Column(name = "preset_total")
    private Long presetotal;//预算完成数

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public Long getPresetotal() {
        return presetotal;
    }

    public void setPresetotal(Long presetotal) {
        this.presetotal = presetotal;
    }

    @Override
    public String toString() {
        return "EventInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createTime=" + createTime +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", status=" + status +
                ", type=" + type +
                ", ext='" + ext + '\'' +
                ", presetotal=" + presetotal +
                '}';
    }
}
