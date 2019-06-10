package com.lottery.core.domain.print;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PrintSaleReportPK implements Serializable{
	
	private static final long serialVersionUID = 8252035993619355592L;
	@Column(name="machine_id")
	private String machineId;
	@Column(name = "report_date")
	private String reportDate;
	
	public PrintSaleReportPK() {
		super();
	}

	public PrintSaleReportPK(String machineId, String reportDate) {
		super();
		this.machineId = machineId;
		this.reportDate = reportDate;
	}
	
	public String getMachineId() {
		return machineId;
	}
	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}
	public String getReportDate() {
		return reportDate;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}

}
