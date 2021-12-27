package it.unipi.dii.lsmsdb.phoneworld.repository;

import it.unipi.dii.lsmsdb.phoneworld.model.Phone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PhoneMongo {

    public PhoneMongo() {
    }

    private final static Logger logger = LoggerFactory.getLogger(PhoneMongo.class);

    @Autowired
    private IPhoneMongo phoneMongo;

    public IPhoneMongo getPhoneMongo() {
        return phoneMongo;
    }

    public String addPhone(Phone phone) {
        String id;
        try {
            phoneMongo.save(phone);
            id = phone.getId();
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
            id = "";
        }
        return id;
    }

    public List<Phone> findPhones(String name) {
        List<Phone> phones = new ArrayList<>();
        try {
            if (name.isEmpty()) {
                phones.addAll(phoneMongo.findAll());
            } else {
                phones.addAll(phoneMongo.findByNameContaining(name));
            }
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
        }
        return phones;
    }

    public Optional<Phone> findPhoneById(String id) {
        Optional<Phone> phone = Optional.empty();
        try {
            phone = phoneMongo.findById(id);
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
        }
        return phone;
    }

}
