package com.afeibaili.ioc_2;

public class SimpleMovieLister {
    private MovieFinder movieFinder;
    private String name;

    public void setMovieFinder(MovieFinder movieFinder) {
        this.movieFinder = movieFinder;
    }

    public void setName(String name) {
        this.name = name;
    }
}
