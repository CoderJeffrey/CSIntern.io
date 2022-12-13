package com.csci201.backend.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.csci201.backend.model.JDBCConnector;
import com.csci201.backend.model.JobModel;

@Repository
public class JobRepository {
    private List<JobModel> jobs = new ArrayList<>();

    @Autowired
    JDBCConnector jdbcConnector;

    // Get all Job related info in the SQL database
    JobRepository(){
    }

    // Get all Jobs
    public List<JobModel> getAllJobs() throws SQLException{
        return jdbcConnector.getAlljobs();
    }

    // Get single Jobs by index
    public JobModel getJob(int index){
        return jobs.get(index);
    }

    // Update single Job in Jobs
    public void updateJob(int index, JobModel job){
        jobs.set(index, job);
    }

    // Delete single Job in Jobs
    public void deleteJob(int index){
        jobs.remove(index);
    }

    // Add single Job in Jobs
    public void addJob(JobModel job){
        jobs.add(job);
    }


    // Find the index of JobModel in jobs (based on companyName)
    public JobModel getSingleJobByName(String companyName){
        // If the companyName is not in our Jobs
        JobModel job = null;
        try {
            job = jdbcConnector.getSingleJobByName(companyName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return job;
    }

    // Print out all jobs (testing)
    public void printAllJobs(){
        System.out.println("here is Repo Layer");
        for (int i = 0; i < jobs.size(); i++) {
            String message = "Current Job is: " + Integer.toString(jobs.get(i).getJobId()) + 
                                "| Company Name is: " + jobs.get(i).getCompanyName() + 
                                "| Location is: " + jobs.get(i).getLocation() + 
                                "| Position is: " + jobs.get(i).getPosition() +
                                "| Valid: " + Boolean.toString(jobs.get(i).getValid());
            System.out.println(message);
            System.out.flush();
        }
    }

    // Get all appliedJobs
    public List<JobModel> getAppliedJobsByUserId(int id) throws SQLException{
        return jdbcConnector.getAppliedJobsByUserId(id);
    }

    // Delete single applied Job
    public int deleteAppliedJobsByUserId(int userId, int jobId) throws SQLException{
        return jdbcConnector.deleteAppliedJobsByUserId(userId, jobId);
    }

    // Add single applied Job
    public int addAppliedJobsByUserId(int userId, int jobId) throws SQLException{
        return jdbcConnector.addAppliedJobsByUserId(userId, jobId);
    }
}
