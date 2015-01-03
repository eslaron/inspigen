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
}
