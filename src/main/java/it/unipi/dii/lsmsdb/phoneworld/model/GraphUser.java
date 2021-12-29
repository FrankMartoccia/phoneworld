package it.unipi.dii.lsmsdb.phoneworld.model;

import java.util.ArrayList;
import java.util.List;

public class GraphUser {

    private String id;
    private String username;
    private List<GraphPhone> watchlist = new ArrayList<>();
    private List<GraphUser> followedUsers = new ArrayList<>();

    public GraphUser(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "GraphUser{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

}
