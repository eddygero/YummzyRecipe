package com.gero.yummzyrecipe.models;

import org.parceler.Parcel;


@Parcel
public class Recipe {
    public String name;
    public String image;
    public String source;

    public Recipe() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

}
