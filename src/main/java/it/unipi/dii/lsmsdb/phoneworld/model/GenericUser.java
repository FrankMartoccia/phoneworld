package it.unipi.dii.lsmsdb.phoneworld.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public abstract class GenericUser {

    @Id
    protected String id;
    protected String username;
    protected String salt;
    protected String hashedPassword;
    protected boolean admin;

    protected GenericUser() {
    }

    protected GenericUser(String username, String salt, String hashedPassword, boolean admin) {
        this.username = username;
        this.salt = salt;
        this.hashedPassword = hashedPassword;
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

    public String getHashedPassword() {
        return hashedPassword;
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

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
