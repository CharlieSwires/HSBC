package restful;

import java.time.LocalDate;

public class RequestBean {
    public RequestBean() {
        
    }
    //in the clear
    private String title;
    private String firstname;
    private String surname;
    //encrypted
    private LocalDate DOB;
    private String JobTitle;
    
    @Override
    public String toString() {
        return "RequestBean [title=" + title + ", firstname=" + firstname + ", surname=" + surname
                + ", DOB=" + DOB + ", JobTitle=" + JobTitle + "]";
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
 
}
