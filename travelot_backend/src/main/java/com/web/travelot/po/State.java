package com.web.travelot.po;

public class State{
		private Integer id;
        private String name;
        private String description;
        private String stateimg;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
        return stateimg;
    }

    public void setStateImg(String stateimg) {
        this.stateimg = stateimg;
    }
}
