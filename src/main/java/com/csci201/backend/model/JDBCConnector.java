package com.csci201.backend.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.csci201.backend.Constants;
import com.mysql.cj.protocol.Resultset;

@Component
public class JDBCConnector {
    // required components for 
    String endpoint;
    String port;
    String user;
    String password;
    
    // Connection instance
    Connection conn = null;

    // SQL Query Statement
    Statement st = null;

    // Result from SQL Query (Statement)
    ResultSet rs = null;

    public JDBCConnector() throws SQLException{
        endpoint = "localhost";
        port = "3306";
        user = "root";

        // replace with your own password
        password = "";
        
        conn = DriverManager.getConnection("jdbc:mysql://" + endpoint + ":" + port + "/joblistings", 
                                            user, 
                                            password);
                        
        st = conn.createStatement();
        System.out.println("Connected Successfully ***********");
    }

    public List<JobModel> getAlljobs() throws SQLException{
        List<JobModel> jobs = new ArrayList<JobModel>();
        ResultSet rs = st.executeQuery("Select * from joblist");

        // Set up a new JobModel
        JobModel job = null;

        while(rs.next()) {
            job = new JobModel();
            job.setJobId(rs.getInt("jobId"));

            String companyName = rs.getString("companyName");
            job.setCompanyName(companyName);

            job.setJobLink(rs.getString("jobLink"));
            job.setLocation(rs.getString("Location"));
            job.setPosition(rs.getString("Position"));
            job.setValid(rs.getBoolean("Valid"));
            jobs.add(job);
        }
        
        rs.close();
        return jobs;
    }

    public JobModel getSingleJobByName(String name) throws SQLException{
        JobModel job = null;
        String statement = String.format("Select * from joblist where companyName = \'%s\'", name);
        ResultSet rs = st.executeQuery(statement);
        
        if(rs.next() == false){
            return null;
        } else {
            do {
                job = new JobModel();
                job.setJobId(rs.getInt("jobId"));
                job.setCompanyName(rs.getString("companyName"));
                job.setJobLink(rs.getString("jobLink"));
                job.setLocation(rs.getString("Location"));
                job.setPosition(rs.getString("Position"));
                job.setValid(rs.getBoolean("Valid"));
            } while (rs.next());
        }
        

        if(rs != null) rs.close();
        return job;
    }


    public JobModel getJobsId(int jobId) throws SQLException{
        JobModel job = null;
        String statement = String.format("Select * from joblist where jobId = %d", jobId);
        
        Statement st2 = conn.createStatement();
        ResultSet rs2 = st2.executeQuery(statement);
        System.out.println("Statement: " + statement);
        if(rs2.next() == false){
            return null;
        } else {
            do {
                job = new JobModel();
                job.setJobId(rs2.getInt("jobId"));
                job.setCompanyName(rs2.getString("companyName"));
                job.setJobLink(rs2.getString("jobLink"));
                job.setLocation(rs2.getString("Location"));
                job.setPosition(rs2.getString("Position"));
                job.setValid(rs2.getBoolean("Valid"));
            } while (rs2.next());
        }

        if(rs2 != null) rs2.close();
        return job;
    }

    // Get all applied Jobs of a specific user Id
    public List<JobModel> getAppliedJobsByUserId(int id) throws SQLException{
        JobModel job = null;
        String statement = String.format("Select jobId from appliedTable where userId = %d", id);
        List<JobModel> appliedJobs = new ArrayList<JobModel>();

        System.out.println("@Statement: " + statement);
        int jobid = -1;
        
        ResultSet rs = st.executeQuery(statement);
        if(rs.next() == false){
            return null;
        } else {
            do {
                job = new JobModel();
                jobid = rs.getInt("jobId");
                System.out.println(jobid);
                appliedJobs.add(getJobsId(jobid));
            } while (rs.next());
        }

        rs.close();
        return appliedJobs;
    }

    // Delete an applied Jobs of a specific user Id
    public int deleteAppliedJobsByUserId(int userId, int jobId){
        String disableSafeUpdate = "SET SQL_SAFE_UPDATES = 0;";
        String enbleSafeUpdate = "SET SQL_SAFE_UPDATES = 1;";
        String statement = String.format("DELETE FROM joblistings.appliedTable WHERE userId = %d and jobId = %d;", 
                                            userId, 
                                            jobId);
        int deleted = 0;
        // delete
        try {
            // disable Safe Update
            st.executeUpdate(disableSafeUpdate);
            
            // Execute Quary
            deleted = st.executeUpdate(statement);
            if (deleted == 0) {
                System.out.println("The request applied Job is not found, incorrect UserId");
            } else {
                System.out.println("Successfully delete the request Applied Job");
            }

            // enable Safe Update
            st.executeUpdate(enbleSafeUpdate);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return deleted;
    }

    // Update an applied Jobs of a specific user Id


    // Add single applied Job
    public int addAppliedJobsByUserId(int userId, int jobId){
        // INSERT INTO `joblistings`.`appliedTable` (`userId`, `userName`, `jobId`) VALUES ('200', 'user4', '26');
        // check whether the user Id have applied for the Job (not allowed for duplicates)

        String checkApplied = String.format("SELECT * FROM joblistings.appliedTable WHERE userId = %d and jobid = %d;",
                                            userId, 
                                            jobId);
        int addedStatus = 0;
        Statement appliedSt = null;
        try { 
            appliedSt = conn.createStatement();
            ResultSet appliedrs = appliedSt.executeQuery(checkApplied);
            if(appliedrs.next() == true){
                // the userId and JobId is in the database
                    return -100;
            } else {
                // the userId and JobId is not in the database
                String statement = String.format("INSERT INTO joblistings.appliedTable(userId, jobId) VALUES (%d, %d);", 
                userId, 
                jobId);
               
                try {
                    addedStatus = st.executeUpdate(statement);
                    if (addedStatus == 0) {
                        addedStatus = Constants.APPLIED_JOB_NOT_ADDED;
                        System.out.println("The added request is not found, incorrect UserId/UserName/UserId");
                        
                    } else {
                        addedStatus = Constants.APPLIED_JOB_ADDED;
                        System.out.println("Successfully add the request Applied Job");
                    }
                } catch (SQLException e) {
                    addedStatus = Constants.APPLIED_JOB_NOT_ADDED;
                    e.printStackTrace();
                }
            }
        } catch (SQLException e1) {
            addedStatus = Constants.APPLIED_JOB_NOT_ADDED;
            e1.printStackTrace();
        }
        
        return addedStatus;
    }
    
}
