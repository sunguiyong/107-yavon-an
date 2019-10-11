package com.zt.igreen.module.data;

/**
 * Created by Administrator on 2019/2/21 0021.
 */

public class FavoriteBean {

    /**
     * material_id : 2
     * cover_image : http://lvzhihui.dev.com/storage/upload/material/201901/1cfa0ccf6de87001e11f1c51523b467a.jpg
     * material_name : 办公桌
     */

    private int material_id;
    private String cover_image;
    private String material_name;

    public int getMaterial_id() {
        return material_id;
    }

    public void setMaterial_id(int material_id) {
        this.material_id = material_id;
    }

    public String getCover_image() {
        return cover_image;
    }

    public void setCover_image(String cover_image) {
        this.cover_image = cover_image;
    }

    public String getMaterial_name() {
        return material_name;
    }

    public void setMaterial_name(String material_name) {
        this.material_name = material_name;
    }
}
