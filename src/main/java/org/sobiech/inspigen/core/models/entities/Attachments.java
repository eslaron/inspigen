package org.sobiech.inspigen.core.models.entities;

import java.io.Serializable;
import java.sql.Blob;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity  
@Table(name="ig_event_attachments")
public class Attachments extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 9211655507105029935L;
	
	private String fileName;
	
	private String fileType;
	
	private Blob file;
	
	private int event_id;
	
	public Attachments() {}
	
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

	public Blob getFile() {
		return file;
	}

	public void setFile(Blob file) {
		this.file = file;
	}

	public int getEvent_id() {
		return event_id;
	}

	public void setEvent_id(int event_id) {
		this.event_id = event_id;
	}	
}

