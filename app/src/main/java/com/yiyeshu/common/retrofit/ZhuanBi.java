package com.yiyeshu.common.retrofit;

/**
 * Created by YSL on 2016/8/3 0003.
 */
public class ZhuanBi {

    private String id;
    private String image_url;
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "id: " + id + " image_url:" + image_url + " description:" + description;
    }
}
