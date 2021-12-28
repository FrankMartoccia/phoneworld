package it.unipi.dii.lsmsdb.phoneworld.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

@Node
public class GraphUser {

    @Id
    private String id;
    private String username;
    @Relationship(type="ADDS", direction = Relationship.Direction.OUTGOING)
    private List<GraphPhone> watchlist = new ArrayList<>();
    @Relationship(type="FOLLOWS", direction = Relationship.Direction.OUTGOING)
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
