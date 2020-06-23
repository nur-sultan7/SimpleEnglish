package com.example.simpleenglish;

public class Grammar {
    private String name;
    private String inRussian;
    private String text;
    private String image;

    public Grammar(String name, String inRussian, String text, String image) {
        this.name = name;
        this.inRussian = inRussian;
        this.text = text;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInRussian() {
        return inRussian;
    }

    public void setInRussian(String inRussian) {
        this.inRussian = inRussian;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
