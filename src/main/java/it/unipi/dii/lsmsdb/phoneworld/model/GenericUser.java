package it.unipi.dii.lsmsdb.phoneworld.model;

public abstract class GenericUser {

    protected String id;
    protected String username;
    protected String password;
    protected String salt;
    protected String sha;
    protected boolean admin;

    protected GenericUser() {
    }

    protected GenericUser(String id, String username) {
        this.id = id;
        this.username = username;
    }

    protected GenericUser(String id, String username, String password, String salt, String sha, boolean admin) {
        this.id = id;
        this.username = username;
        this.password = password;
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

    public String getPassword() {
        return password;
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
}
