package com.mediaflex.os.resources.exceptions;

import java.io.Serializable;

public class FieldMessade implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String fieldName;
	private String messge;
	public FieldMessade() {
		super();
		// TODO Auto-generated constructor stub
	}
	public FieldMessade(String fieldName, String messge) {
		super();
		this.fieldName = fieldName;
		this.messge = messge;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getMessge() {
		return messge;
	}
	public void setMessge(String messge) {
		this.messge = messge;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
