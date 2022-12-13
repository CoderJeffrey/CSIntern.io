package com.csci201.backend.controler;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.csci201.backend.Constants;
import com.csci201.backend.model.JobModel;
import com.csci201.backend.service.JobService;

@RestController
public class JobController{
    @Autowired
    JobService jobService = new JobService();
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/jobs")
    @ResponseBody
    public List<JobModel> getAllJobs() throws SQLException{
        // jobService.printAllJobs();
        return jobService.getAllJobs();
    }

    // Search Function
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/search/{companyName}")
    public JobModel searchCompany(@PathVariable String companyName){
        // Locate the job index by the Company Name
        JobModel job = jobService.getSingleJobByName(companyName);
        if(job == null) {
            // not found in jobs database
            System.out.println("*****The company is not in the databaset");
            return null;
        } else {
            // found in jobs database, return the job posting for the company
            return job;
        }
    }


    //Get all appliedJobs
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/searchUser/{userId}")
    public List<JobModel> getAppliedJobsByUserId(@PathVariable int userId) throws SQLException{
        return jobService.getAppliedJobsByUserId(userId);
    }


    // Delete single applied Job
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/deleteJob/{userId}/{jobId}")
    public int deleteAppliedJobsByUserId(@PathVariable int userId, @PathVariable int jobId) throws SQLException{
        int deletedStatus = jobService.deleteAppliedJobsByUserId(userId, jobId);
        if(deletedStatus != 0){
            System.out.println("Deleted Job");
        } else {
            System.out.println("Not Delete Job");
        }
        return deletedStatus;
    }

    // Add single applied Job
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/addJob/{userId}/{jobId}")
    public int addAppliedJobsByUserId(@PathVariable int userId, @PathVariable int jobId) throws SQLException{
        int addedStatus = jobService.addAppliedJobsByUserId(userId, jobId);
        if(addedStatus == Constants.APPLIED_JOB_ADDED){
            System.out.println("Added Job");
        } else if(addedStatus == Constants.APPLIED_JOB_IN_DATABASE){
            System.out.println("Job is in Database");
        } else if(addedStatus == Constants.APPLIED_JOB_NOT_ADDED) {
            System.out.println("Not Added Job");
        }
        return addedStatus;
    }

}
