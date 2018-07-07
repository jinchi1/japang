package com.boundaryproj.bottletaste.UtilFolder.UserFolder;

import java.io.Serializable;

/**
 * Created by Lincoln on 07/01/16.
 */
public class User implements Serializable {
    /*
    user_id,nickname,profile_photo,phone,address,sub_address,
last_address,point,rank,type,created_at,recomendcode

{"success":true,"user_id":"",
"nickname":"","ptofile_photo":"","age":"","job":"",
"school":"","info":"",
"token":"","device":""}
     */
    String user_id, nickname, profile_photo,age,job,school,info,sex;

    public User() {
    }

    public User(String user_id, String nickname, String profile_photo, String age, String job, String school, String info, String sex) {
        this.user_id = user_id;
        this.nickname = nickname;
        this.profile_photo = profile_photo;
        this.age = age;
        this.job = job;
        this.school = school;
        this.info = info;
        this.sex = sex;

    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getNickname() {
        return nickname;
    }

    public String getProfile_photo() {
        return profile_photo;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
