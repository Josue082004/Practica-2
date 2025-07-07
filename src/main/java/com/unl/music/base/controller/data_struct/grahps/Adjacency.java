package com.unl.music.base.controller.data_struct.grahps;

public class Adjacency {
    private Float wieght;
    private Integer destiny;

    public Float getWieght() {
        return this.wieght;
    }

    public void setWieght(Float wieght) {
        this.wieght = wieght;
    }

    public Integer getDestiny() {
        return this.destiny;
    }

    public void setDestiny(Integer destiny) {
        this.destiny = destiny;
    }

    public Adjacency(Integer destiny, Float weight) {
        this.destiny = destiny;
        this.wieght = weight;
    }

}
