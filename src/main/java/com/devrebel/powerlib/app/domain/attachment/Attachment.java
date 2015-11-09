package com.devrebel.powerlib.app.domain.attachment;

import com.devrebel.powerlib.core.common.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity  																//oznaczenie klasy jako encji
@Table(name="ig_attachments")											//nazwa tabeli w bazie danych
public class Attachment extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 9211655507105029935L;
	
	private String fileName;			//nazwa pliku
	
	private String fileType;			//typ pliku

	@Column(columnDefinition = "LONGBLOB")
	private byte[] file;				//plik

	private Long user_id;				//id użytkownika do ktorego należy załącznik

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

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
}
