package com.lottery.core;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * */
@Entity
@Table(name="id_generator")
public class IdGenerator implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6382884082822684461L;
	@Id
	@Column(name="id")
	private Long id;
	@Column(name="seqid")
	private Long seqid;
	public Long getSeqid() {
		return seqid;
	}
	public void setSeqid(Long seqid) {
		this.seqid = seqid;
	}
	@Column(name="seqname")
	private  String sequeueName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSequeueName() {
		return sequeueName;
	}
	public void setSequeueName(String sequeueName) {
		this.sequeueName = sequeueName;
	}
	

}
