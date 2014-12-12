package com.hl.entity;

import java.io.Serializable;

public class ImageInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fileName;
	private byte[] fileBody;

	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public byte[] getFileBody() {
		return fileBody;
	}
	public void setFileBody(byte[] fileBody) {
		this.fileBody = fileBody;
	}
	
}
