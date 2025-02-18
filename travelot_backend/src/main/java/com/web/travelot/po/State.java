package com.web.travelot.po;

public class State{
		private Integer id;
        private String name;
        private String description;
        private String state_img;
        private String region;

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


}
