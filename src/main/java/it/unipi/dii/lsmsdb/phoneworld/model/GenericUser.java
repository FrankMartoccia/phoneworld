package it.unipi.dii.lsmsdb.phoneworld.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public abstract class GenericUser {

    @Id
    //@org.springframework.data.neo4j.core.schema.Id
    protected String id;
    protected String username;
    protected String salt;
    protected String sha;
    protected boolean admin;

    protected GenericUser() {
    }

    protected GenericUser(String username) {
        this.username = username;
    }

    protected GenericUser(String username, String salt, String sha, boolean admin) {
        this.username = username;
        this.salt = salt;
        this.sha = sha;
        this.admin = admin;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getSalt() {
        return salt;
    }

    public String getSha() {
        return sha;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
