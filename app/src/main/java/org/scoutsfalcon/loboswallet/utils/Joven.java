package org.scoutsfalcon.loboswallet.utils;

import org.json.JSONException;
import org.json.JSONObject;


public class Joven extends Object {
    private String name;
    private String lastName;
    private String region;
    private String district;
    private String group;

    public Object Joven(JSONObject data) throws JSONException {
        this.name = data.getString("nombre");
        this.lastName = data.getString("apellido");
        this.region = data.getString("region");
        this.district = data.getString("distrito");
        this.group = data.getString("grupo");

        return this;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getRegion() {
        return region;
    }

    public String getDistrict() {
        return district;
    }

    public String getGroup() {
        return group;
    }

}
