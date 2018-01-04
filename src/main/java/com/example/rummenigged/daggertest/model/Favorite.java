package com.example.rummenigged.daggertest.model;

/**
 * Created by rummenigged on 04/01/18.
 */

public class Favorite {
    private long timeAdded;
    private String url;

    public Favorite(long timeAdded, String url) {
        this.timeAdded = timeAdded;
        this.url = url;
    }

    public long getTimeAdded() {
        return timeAdded;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Favorite) {
            Favorite f = (Favorite) obj;
            return url.equals(f.getUrl()) && timeAdded == f.timeAdded;
        }
        return false;
    }
}
