package com.mongodb;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import restful.Encryption;
/**
 * Copyright 2021 Charles Swires All Rights Reserved
 * @author charl
 *
 */
@Document(collection="Entries")
public class MongoBean {
    final static String publicKey;
    final static String privateKey;
    static {
        publicKey=System.getenv("ADDRESSBOOK_PUBLIC_KEY");
        privateKey=System.getenv("ADDRESSBOOK_PRIVATE_KEY");
    }
	public MongoBean() {
	}

	
    @Id
    private String id;
    //in the clear
    private String title;
    private String firstname;
    private String surname;
    private Date CreateStamp;
    //encrypted
    private String DOB;
    private String JobTitle;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    
    public String getDOB() {
        return (DOB != null && !DOB.isEmpty()? Encryption.decrypt(privateKey, DOB):null);
    }
    public void setDOB(String address) {
        this.DOB = (DOB != null && !DOB.isEmpty()? Encryption.encrypt(publicKey, DOB):null);
    }
    public String getJobTitle() {
        return (JobTitle != null && !JobTitle.isEmpty()? Encryption.decrypt(privateKey, JobTitle):null);
    }   
    public void setJobTitle(String JobTitle) {
        this.JobTitle = (JobTitle != null && !JobTitle.isEmpty()? Encryption.encrypt(publicKey, JobTitle):null);
    }
    public Date getCreateStamp() {
        return CreateStamp;
    }
    public void setCreateStamp(Date createStamp) {
        CreateStamp = createStamp;
    }

}
