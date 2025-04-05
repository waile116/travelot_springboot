package com.web.travelot.po;

public class State{
    private Integer id;
    private String name;
    private String description;
    private String state_img;
    private String region;
    private String map_img;
    private String map_link;

        //backend parse stateId as a JSON file to frontend
    //frontend use stateId as a parameter to id in this case
    public Integer getStateId() {
        return id;
    }

    public void setStateId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return description;
    }

    public void setDesc(String description) {
        this.description = description;
    }
    public String getStateImg() {
        return state_img;
    }

    public void setStateImg(String state_img) {
        this.state_img = state_img;
    }

    public String getRegion() {
        return region;
    }
    public void setRegion() {
        this.region = region;
    }

    public String getMapImg() {
        return map_img;
    }

    public void setMapImg(String map_img) {
        this.map_img = map_img;
    }

    public String getMapLink() {
        return map_link;
    }

    public void setMapLink(String map_link) {
        this.map_link = map_link;
    }
}
