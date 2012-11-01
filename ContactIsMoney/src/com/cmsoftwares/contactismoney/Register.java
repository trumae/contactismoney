package com.cmsoftwares.contactismoney;

import java.util.Date;

public class Register {
    private String description;
    private long value;
    private Date data;
    private Date created;
    private long idContact;
    
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getValue() {
		return value;
	}
	public void setValue(long value) {
		this.value = value;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public long getIdContact() {
		return idContact;
	}
	public void setIdContact(long idContact) {
		this.idContact = idContact;
	}
}
