package org.sobiech.inspigen.core.models.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity  																//oznaczenie klasy jako encji
@Table(name="ig_attachments")											//nazwa tabeli w bazie danych
public class Attachment extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 9211655507105029935L;
	
	private String fileName;			//nazwa pliku
	
	private String fileType;			//typ pliku
	
	private byte[] file;				//plik
	
	private String blobUrl;				//odnośnik do bloba
	
	private int user_id;				//id użytkownika do ktorego należy załącznik
	
	private int event_id;				//id wydarzenia do ktorego nalezy załącznik
	
	public Attachment() {}
	
	//Gettery i settery
	
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
	
	public String getBlobUrl() {
		return blobUrl;
	}

	public void setBlobUrl(String blobUrl) {
		this.blobUrl = blobUrl;
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
