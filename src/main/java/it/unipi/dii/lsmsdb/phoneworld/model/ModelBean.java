package it.unipi.dii.lsmsdb.phoneworld.model;

import java.util.HashMap;
import java.util.Map;

public class ModelBean {

    private Map<String, Object> mapBean = new HashMap<String, Object>();

    public void putBean(String key, Object o) {
        this.mapBean.put(key, o);
    }

    public Object getBean(String key) {
        return mapBean.get(key);
    }
}
