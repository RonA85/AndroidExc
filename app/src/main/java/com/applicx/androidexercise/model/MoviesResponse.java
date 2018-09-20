package com.applicx.androidexercise.model;

import java.io.Serializable;
import java.util.List;

public class MoviesResponse implements Serializable{

    private int page;
    private List<Movie> results;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}
