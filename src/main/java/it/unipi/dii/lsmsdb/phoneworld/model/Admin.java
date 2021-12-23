package it.unipi.dii.lsmsdb.phoneworld.model;

public class Admin extends GenericUser{

    public Admin() {
    }

    public Admin(String id, String username, String password, String salt, String sha, boolean admin) {
        super(id, username, password, salt, sha, admin);
    }
}
