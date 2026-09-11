package com.MyProject.mediationplatform.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    @JsonProperty(value = "Name")
    private String name;
    @JsonProperty(value = "Email")
    private String email;
    @JsonProperty(value = "MobilePhone")
    private String mobilePhone;
    @JsonProperty(value = "Phone")
    private String phone;
    @JsonProperty(value = "NationalId")
    private String cuid;

    public User(String name, String email, String mobilePhone, String phone, String cuid) {
        this.name = name;
        this.email = email;
        this.mobilePhone = mobilePhone;
        this.phone = phone;
        this.cuid = cuid;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public String getPhone() {
        return phone;
    }

    public String getCuid() {
        return cuid;
    }
}
