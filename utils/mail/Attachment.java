package com.xgd.boss.core.mail;

import java.io.File;

public class Attachment {

	private String fileName;
	
	private File file;

	public Attachment(String fileName, File file) {
		super();
		this.fileName = fileName;
		this.file = file;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	
}
