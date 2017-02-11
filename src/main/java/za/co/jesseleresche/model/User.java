package za.co.jesseleresche.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by jesse on 2016/11/13.
 */
public class User {

    private Long id;

    private String name;

    @JsonProperty("picture_medium")
    private String pictureMedium;

    public User() {
    }

    public User(Long id, String name, String pictureMedium) {
        this.id = id;
        this.name = name;
        this.pictureMedium = pictureMedium;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPictureMedium() {
        return pictureMedium;
    }

    public void setPictureMedium(String pictureMedium) {
        this.pictureMedium = pictureMedium;
    }
}
