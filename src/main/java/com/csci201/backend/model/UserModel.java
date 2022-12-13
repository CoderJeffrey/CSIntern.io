package com.csci201.backend.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "userInfo")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "user_id")
    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("password")
    private String password;

    @Column(name = "userName")
    @JsonProperty("name")
    private String name;


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "favorite_records", joinColumns = { @JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "item_id")})
    Set<JobModel> itemSet = new HashSet<>();

    public Set<JobModel> getItemSet() {
        return itemSet;
    }

    public void setItemSet(Set<JobModel> itemSet) {
        this.itemSet = itemSet;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) { this.name = name; }

    public String getName() { return name; }
}
