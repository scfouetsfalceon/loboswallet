package org.scoutsfalcon.loboswallet.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Joven extends Object {
    private String code;
    private String name;
    private String lastName;
    private String region;
    private String district;
    private String group;
    private Integer account;
    public Boolean sex;

    public Joven(String code) {
        this.code = code;
    }

    public Joven(String code, String name, String lastName, String region, String district, String group, Integer account, Boolean sex) {
    /* public Object Joven(JSONObject data) throws JSONException {
        code = data.getString("code");
        name = data.getString("nombre");
        lastName = data.getString("apellido");
        region = data.getString("region");
        district = data.getString("distrito");
        group = data.getString("grupo");
        account = data.getInt("saldo");
        sex = data.getBoolean("sexo");

        return this;*/
        this.code = code;
        this.name = name;
        this.lastName = lastName;
        this.region = region;
        this.district = district;
        this.group = group;
        this.account = account;
        this.sex = sex;
    }

    public String getCode() { return code; }

    public Boolean getSex() { return sex; }

    public String getName() { return name; }

    public String getLastName() { return lastName; }

    public String getRegion() { return region; }

    public String getDistrict() { return district; }

    public String getGroup() { return group; }

    public Integer getAccount() { return account; }

    public String getData() {
        String resultado = "";
        if (code.isEmpty()) {
            resultado = "** Estación vacía **";
        } else {
            resultado = String.format("%s - %s %s", code, name, lastName);
        }
        return resultado;
    }

}
