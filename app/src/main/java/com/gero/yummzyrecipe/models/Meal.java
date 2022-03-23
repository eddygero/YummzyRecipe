package com.gero.yummzyrecipe.models;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class Meal {
    public String id,meal,name,category,area,instructions,tags,youtube,source,image;
    public List<String> ingredients;
    public List<String> measure;
    public String mealUid;
    String index;

    public Meal() {
    }

    public Meal(String id,String name, String category, String area, String instructions, String tags, String youtube, String source, String image, List<String> ingredients, List<String> measure) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.area = area;
        this.instructions = instructions;
        this.tags = tags;
        this.youtube = youtube;
        this.source = source;
        this.image = image;
        this.ingredients = ingredients;
        this.measure = measure;

        this.index = "not_specified";
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getArea() {
        return area;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getYoutube() {
        return youtube;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public List<String> getMeasure() {
        return measure;
    }

    public void setMealUid(String mealUid) {
        this.mealUid = mealUid;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
