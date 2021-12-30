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

    public List<GraphPhone> getWatchlist() {
        return watchlist;
    }

    public void setWatchlist(List<GraphPhone> watchlist) {
        this.watchlist = watchlist;
    }

    public void addToWatchlist(GraphPhone phone) {
        this.watchlist.add(phone);
    }

    public void removeFromWatchlist(String id) {
        if (!this.watchlist.isEmpty()) {
            watchlist.removeIf(phone -> phone.getId().equals(id));
        }
    }

    public List<GraphUser> getFollowedUsers() {
        return followedUsers;
    }

    public void setFollowedUsers(List<GraphUser> followedUsers) {
        this.followedUsers = followedUsers;
    }

    public void addFollowedUser(GraphUser user) {
        this.followedUsers.add(user);
    }

    public void removeFollow(String id) {
        if (!this.followedUsers.isEmpty()) {
            followedUsers.removeIf(user -> user.getId().equals(id));
        }
    }

    @Override
    public String toString() {
        return "GraphUser{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", watchlist=" + watchlist +
                ", followedUsers=" + followedUsers +
                '}';
    }

}
