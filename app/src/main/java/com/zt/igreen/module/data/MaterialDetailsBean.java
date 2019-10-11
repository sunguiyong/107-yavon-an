package com.zt.igreen.module.data;

import java.util.List;

/**
 * Created by Administrator on 2019/4/1 0001.
 */

public class MaterialDetailsBean {

    /**
     * id : 2
     * name : 办公桌
     * sn : sn1001
     * description : 漂亮、实用
     * characteristic : 淡定淡定
     * application_scene : 嘎嘎嘎嘎嘎
     * specification : ["很好","不错"]
     * supplier : 彩霞
     * is_favorite : true
     * images : [{"id":2,"image":"http://lvzhihui.dev.com/storage/upload/material/201901/1cfa0ccf6de87001e11f1c51523b467a.jpg"}]
     */

    private int id;
    private String name;
    private String sn;
    private String description;
    private String characteristic;
    private String application_scene;
    private String supplier;
    private boolean is_favorite;
    private List<String> specification;
    private List<ImagesBean> images;

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

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic;
    }

    public String getApplication_scene() {
        return application_scene;
    }

    public void setApplication_scene(String application_scene) {
        this.application_scene = application_scene;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public boolean isIs_favorite() {
        return is_favorite;
    }

    public void setIs_favorite(boolean is_favorite) {
        this.is_favorite = is_favorite;
    }

    public List<String> getSpecification() {
        return specification;
    }

    public void setSpecification(List<String> specification) {
        this.specification = specification;
    }

    public List<ImagesBean> getImages() {
        return images;
    }

    public void setImages(List<ImagesBean> images) {
        this.images = images;
    }

    public static class ImagesBean {
        /**
         * id : 2
         * image : http://lvzhihui.dev.com/storage/upload/material/201901/1cfa0ccf6de87001e11f1c51523b467a.jpg
         */

        private int id;
        private String image;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
