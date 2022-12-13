package com.csci201.backend.model;
import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.io.Serializable;
import javax.persistence.Entity;

@Entity
@Table(name = "joblist")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobModel implements Serializable {

    @Id
    @JsonProperty("jobId")
    private int jobId;

    @JsonProperty("companyName")
    private String companyName;

    @JsonProperty("jobLink")
    private String jobLink;

    @JsonProperty("location")
    private String location;

    @JsonProperty("position")
    private String position;

    @JsonProperty("valid")
    private Boolean valid;

    public JobModel(){

    }

    // Setter and Getters
    public int getJobId() {
        return this.jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getJobLink() {
        return this.jobLink;
    }

    public void setJobLink(String jobLink) {
        this.jobLink = jobLink;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Boolean getValid() {
        return this.valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }
}
