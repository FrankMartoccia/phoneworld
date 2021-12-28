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

    public void addPhone(Phone phone) {
        try {
            phoneMongo.save(phone);
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
        }
    }

    public List<Phone> findPhones(String name) {
        List<Phone> phones = new ArrayList<>();
        try {
            if (name.isEmpty()) {
                phones.addAll(phoneMongo.findAll());
            } else {
                phones.addAll(phoneMongo.findByNameRegex(name));
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

    public void updatePhone(String id, Phone newPhone) {
        try {
            Optional<Phone> phone = phoneMongo.findById(id);
            if (phone.isPresent()) {
                phone.get().setBrand(newPhone.getBrand());
                phone.get().setName(newPhone.getName());
                phone.get().setPicture(newPhone.getPicture());
                phone.get().setReleaseDate(newPhone.getReleaseDate());
                phone.get().setBody(newPhone.getBody());
                phone.get().setOs(newPhone.getOs());
                phone.get().setStorage(newPhone.getStorage());
                phone.get().setDisplaySize(newPhone.getDisplaySize());
                phone.get().setDisplayResolution(newPhone.getDisplayResolution());
                phone.get().setCameraPixels(newPhone.getCameraPixels());
                phone.get().setVideoPixels(newPhone.getVideoPixels());
                phone.get().setRam(newPhone.getRam());
                phone.get().setChipset(newPhone.getChipset());
                phone.get().setBatterySize(newPhone.getBatterySize());
                phone.get().setBatteryType(newPhone.getBatteryType());
                phone.get().setSpecifications(newPhone.getSpecifications());

                this.addPhone(phone.get());
            }
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
        }
    }

    public void deletePhoneById(String id) {
        try {
            phoneMongo.deleteById(id);
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
        }
    }

    public void deletePhone(Phone phone) {
        try {
            phoneMongo.delete(phone);
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
        }
    }
}
