package it.unipi.dii.lsmsdb.phoneworld.model;

import org.springframework.data.annotation.Id;

public class Admin extends GenericUser{

    @Id
    private String id;

    public Admin() {
    }

    public Admin(String username, String password, String salt, String sha, boolean admin) {
        super(username, password, salt, sha, admin);
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", sha='" + sha + '\'' +
                ", admin=" + admin +
                '}';
    }
}
