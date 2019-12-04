package com.zy.mvp.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Description: Created by yong on 2019/3/22 11:42.
 */
@Entity
public class User {
    @Id(autoincrement = true)
    private long id;
    private String key;
    private String phone;
    private String name;
    private String passwd;
    private String text;
    private String img;
    private String other;
    private String other2;
    private String createTime;

    @Generated(hash = 1223013944)
    public User(long id, String key, String phone, String name, String passwd,
                String text, String img, String other, String other2,
                String createTime) {
        this.id = id;
        this.key = key;
        this.phone = phone;
        this.name = name;
        this.passwd = passwd;
        this.text = text;
        this.img = img;
        this.other = other;
        this.other2 = other2;
        this.createTime = createTime;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswd() {
        return this.passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg() {
        return this.img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getOther() {
        return this.other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getOther2() {
        return this.other2;
    }

    public void setOther2(String other2) {
        this.other2 = other2;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
