package it.unipi.dii.lsmsdb.phoneworld.repository;

import it.unipi.dii.lsmsdb.phoneworld.model.GenericUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMongo {

    public UserMongo() {
    }

    private final static Logger logger = LoggerFactory.getLogger(PhoneMongo.class);

    @Autowired
    private IUserMongo userMongo;

    public IUserMongo getUserMongo() {
        return userMongo;
    }

    public String addUser(GenericUser user) {
        String id;
        try {
            userMongo.save(user);
            id = user.getId();
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
            id = "";
        }
        return id;
    }

    public List<GenericUser> findUsers(String username, boolean admin) {
        List<GenericUser> users = new ArrayList<>();
        try {
            if (username.isEmpty()) {
                users.addAll(userMongo.findByAdmin(admin));
            } else {
                users.addAll(userMongo.findByUsernameContainingAndAdmin(username, admin));
            }
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
        }
        return users;
    }

    public GenericUser findUserById(String id, boolean admin) {
        GenericUser user = null;
        try {
            user = userMongo.findByIdAndAdmin(id, admin);
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
        }
        return user;
    }


}
