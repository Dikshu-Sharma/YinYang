package com.soulharmony.model;

import java.util.List;
import java.util.Map;

public class User {

    private String _id;
    private String name;
    private String email;
    private String phone;
    private String password;
    private String gender;
    private Integer age;
    private Double latitude;
    private Double longitude;
    private String city;
    private Map<String, String> imagesUrlWithIndex;
    private List<String> userIdsToExclude;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Map<String, String> getImagesUrlWithIndex() {
        return imagesUrlWithIndex;
    }

    public void setImagesUrlWithIndex(Map<String, String> imagesUrlWithIndex) {
        this.imagesUrlWithIndex = imagesUrlWithIndex;
    }

    public List<String> getUserIdsToExclude() {
        return userIdsToExclude;
    }

    public void setUserIdsToExclude(List<String> userIdsToExclude) {
        this.userIdsToExclude = userIdsToExclude;
    }

    public User(String id, String name, String email, String phone, String password,
                String gender, Integer age, Double latitude, Double longitude, String city, Map<String, String> imagesUrlWithIndex, List<String> userIdsToExclude) {
        _id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.gender = gender;
        this.age = age;
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
        this.imagesUrlWithIndex = imagesUrlWithIndex;
        this.userIdsToExclude = userIdsToExclude;
    }
}
