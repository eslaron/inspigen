package org.sobiech.inspigen.core.models.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity  
@Table(name="ig_attachments")
public class Attachment extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 9211655507105029935L;
	
	private String fileName;
	
	private String fileType;
	
	private byte[] file;
	
	private int user_id;
	
	private int event_id;
	
	public Attachment() {}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}
	
	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	
	public int getEvent_id() {
		return event_id;
	}

	public void setEvent_id(int event_id) {
		this.event_id = event_id;
	}
}
