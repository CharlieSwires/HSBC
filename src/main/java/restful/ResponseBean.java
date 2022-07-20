package restful;

import java.time.LocalDate;

public class ResponseBean {
    public ResponseBean() {
        
    }
    //in the clear
    private String id;
    private String title;
    private String firstname;
    private String surname;
    private String CreateStamp;
    //encrypted
    private LocalDate DOB;
    private String JobTitle;
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
    public String getCreateStamp() {
        return CreateStamp;
    }
    public void setCreateStamp(String createStamp) {
        CreateStamp = createStamp;
    }
    public LocalDate getDOB() {
        return DOB;
    }
    public void setDOB(LocalDate dOB) {
        DOB = dOB;
    }
    public String getJobTitle() {
        return JobTitle;
    }
    public void setJobTitle(String jobTitle) {
        JobTitle = jobTitle;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
 
}
