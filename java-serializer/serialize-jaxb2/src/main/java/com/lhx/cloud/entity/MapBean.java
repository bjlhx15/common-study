package com.lhx.cloud.entity;

import java.util.HashMap;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
public class MapBean {
    private HashMap<String, AccountBean> map;

    @XmlJavaTypeAdapter(MapAdapter.class)
    public HashMap<String, AccountBean> getMap() {
        return map;
    }
    public void setMap(HashMap<String, AccountBean> map) {
        this.map = map;
    }
}
