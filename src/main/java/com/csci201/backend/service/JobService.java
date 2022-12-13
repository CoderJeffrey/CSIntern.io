package com.csci201.backend.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csci201.backend.dao.JobRepository;
import com.csci201.backend.model.JobModel;

@Service
public class JobService {
    
    @Autowired
    JobRepository jobRepository;

    public void printAllJobs(){
        System.out.println("here is Service Layer");
        jobRepository.printAllJobs();
    }

     // Get all Jobs
     public List<JobModel> getAllJobs() throws SQLException{
        return jobRepository.getAllJobs();
    }

    // Get single Jobs by index
    public JobModel getJob(int index){
        return jobRepository.getJob(index);
    }

    // Update single Job in Jobs
    public void updateJob(int index, JobModel job){
        jobRepository.updateJob(index, job);;
    }

    // Delete single Job in Jobs
    public void deleteJob(int index){
        jobRepository.deleteJob(index);;
    }

    // Add single Job in Jobs
    public void addJob(JobModel job){
        jobRepository.addJob(job);;
    }

    // Find the index of JobModel in jobs (based on companyName)
    public JobModel getSingleJobByName(String companyName){
        return jobRepository.getSingleJobByName(companyName);
    }

    // Get all appliedJobs
    public List<JobModel> getAppliedJobsByUserId(int id) throws SQLException{
        return jobRepository.getAppliedJobsByUserId(id);
    }

    // Delete single applied Job
    public int deleteAppliedJobsByUserId(int userId, int jobId) throws SQLException{
        return jobRepository.deleteAppliedJobsByUserId(userId, jobId);
    }

    // Add single applied Job
    public int addAppliedJobsByUserId(int userId, int jobId) throws SQLException{
        return jobRepository.addAppliedJobsByUserId(userId, jobId);
    }

}
