package it.unipi.dii.lsmsdb.phoneworld.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@TypeAlias("admin")
public class Admin extends GenericUser{

    public Admin() {
    }

    public Admin(String username, String password, String salt, String sha, boolean admin) {
        super(username, password, salt, sha, admin);
    }

    @Id
    private String id;

    public String getId() {
        return id;
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
