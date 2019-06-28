package com.example.mosaab.googlemapsgoogleplaces;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;


class ClusterMarker implements ClusterItem {


    private LatLng postition;
    private String title;
    private String snippet;
    private int iconPiture;
    private Object usermodel;

    public ClusterMarker(LatLng postition, String title, String snippet, int iconPiture, Object usermodel) {
        this.postition = postition;
        this.title = title;
        this.snippet = snippet;
        this.iconPiture = iconPiture;
        this.usermodel = usermodel;
    }

    public ClusterMarker() {
    }

    public LatLng getPostition() {
        return postition;
    }

    public void setPostition(LatLng postition) {
        this.postition = postition;
    }


    @Override
    public LatLng getPosition() {
        return postition;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public int getIconPiture() {
        return iconPiture;
    }

    public void setIconPiture(int iconPiture) {
        this.iconPiture = iconPiture;
    }

    public Object getUsermodel() {
        return usermodel;
    }

    public void setUsermodel(Object usermodel) {
        this.usermodel = usermodel;
    }
}
