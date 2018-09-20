package com.applicx.androidexercise.model;

import java.io.Serializable;
import java.util.List;

public class CastResponse implements Serializable{

    private List<Cast> cast;

    public List<Cast> getCasts() {
        return cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }
}
