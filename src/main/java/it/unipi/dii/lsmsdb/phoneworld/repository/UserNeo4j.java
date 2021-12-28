package it.unipi.dii.lsmsdb.phoneworld.repository;

import it.unipi.dii.lsmsdb.phoneworld.model.GraphUser;
import it.unipi.dii.lsmsdb.phoneworld.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserNeo4j {

    public UserNeo4j() {
    }

    private final static Logger logger = LoggerFactory.getLogger(UserNeo4j.class);

    @Autowired
    private IUserNeo4j userNeo4j;

    public IUserNeo4j getUserNeo4j() {
        return userNeo4j;
    }

    public void addUser(GraphUser user) {
        try {
            userNeo4j.save(user);
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
        }
    }

    public Optional<GraphUser> findUserById(String id) {
        Optional<GraphUser> user = Optional.empty();
        try {
            user = userNeo4j.findById(id);
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
        }
        return user;
    }

    public void updateUser(String id, User newUser) {
        try {
            Optional<GraphUser> user = userNeo4j.findById(id);
            if (user.isPresent()) {
                user.get().setUsername(newUser.getUsername());

                this.addUser(user.get());
            }
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
        }
    }

    public void deleteUserById(String id) {
        try {
            userNeo4j.deleteById(id);
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
        }
    }

    public void deleteUser(GraphUser user) {
        try {
            userNeo4j.delete(user);
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
        }
    }

}
