package br.com.xpto.model;

import org.springframework.http.HttpStatus;

public class FileModel {
	
	private HttpStatus status;
	private String message;
	private String fileType;
	private long size;

	public FileModel(HttpStatus status, String message, String fileType, long size) {
		super();
		this.status = status;
		this.message = message;
		this.fileType = fileType;
		this.size = size;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
