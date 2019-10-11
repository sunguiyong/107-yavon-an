package com.zt.igreen.module.data;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/3/29 0029.
 */

public class SearchResultBean implements Serializable{

    /**
     * id : 3
     * name : 办公桌
     * cover_image : http://lvzhihui.dev.com/storage/upload/material/201901/d0dbd5098657a3852caec28ab604f9fd.jpg
     */
    private int id;
    private String name;
    private String cover_image;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCover_image() {
        return cover_image;
    }
    public void setCover_image(String cover_image) {
        this.cover_image = cover_image;
    }
}
