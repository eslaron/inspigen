package org.sobiech.inspigen.core.models.dto;

//Klasa do transportu wybranych informacji o za³¹cznikach
public class AttachmentDto {

    private Long id;            //id za³¹cznika

    private String fileName;    //nazwa pliku

    private String fileType;    //typ pliku

    private String file;        //plik

    private String blobUrl;    //odnoœnik do bloba

    private int user_id;        //id u¿ytkownika, ktory doda³ ten za³acznik

    private int event_id;       //id wydarzenia, do ktorego nale¿y za³¹cznik

    public AttachmentDto() {
    }

    //Gettery i settery

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
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