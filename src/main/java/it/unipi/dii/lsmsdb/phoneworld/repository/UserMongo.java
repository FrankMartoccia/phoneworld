package it.unipi.dii.lsmsdb.phoneworld.repository;

import it.unipi.dii.lsmsdb.phoneworld.model.Admin;
import it.unipi.dii.lsmsdb.phoneworld.model.GenericUser;
import it.unipi.dii.lsmsdb.phoneworld.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public void addUser(GenericUser user) {
        try {
            userMongo.save(user);
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
        }
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

    public void updateUser(String id, GenericUser newGenericUser, boolean admin) {
        try {
            Optional<GenericUser> genericUser = userMongo.findById(id);
            if (genericUser.isPresent()) {
                if (admin) {
                    Admin administrator = (Admin) genericUser.get();
                    administrator.setUsername(newGenericUser.getUsername());
                    administrator.setAdmin(newGenericUser.isAdmin());
                    administrator.setSha(newGenericUser.getSha());
                    administrator.setSalt(newGenericUser.getSalt());
                    this.addUser(administrator);
                } else {
                    User user = (User) genericUser.get();
                    User newUser = (User)newGenericUser;
                    user.setUsername(newUser.getUsername());
                    user.setAdmin(newUser.isAdmin());
                    user.setSha(newUser.getSha());
                    user.setSalt(newUser.getSalt());
                    user.setAge(newUser.getAge());
                    user.setCity(newUser.getCity());
                    user.setCountry(newUser.getCountry());
                    user.setEmail(newUser.getEmail());
                    user.setGender(newUser.getGender());
                    user.setFirstName(newUser.getFirstName());
                    user.setLastName(newUser.getLastName());
                    user.setDateOfBirth(newUser.getDateOfBirth());
                    user.setStreetName(newUser.getStreetName());
                    user.setStreetNumber(newUser.getStreetNumber());
                    this.addUser(user);
                } 
            } 
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
        }
    }

    public void deleteUserById(String id) {
        try {
            userMongo.deleteById(id);
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
        }
    }

    public void deleteUser(GenericUser user) {
        try {
            userMongo.delete(user);
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
        }
    }

}
