package com.example.simpleenglish;

public class Grammar {
    private String name;
    private String inRussian;
    private String text;
    private String image;
    private String example;
    private String example_in_russian;
    private String example2;
    private String example_in_russian2;

    public Grammar(String name, String inRussian, String text, String image, String example, String example_in_russian, String example2, String example_in_russian2) {
        this.name = name;
        this.inRussian = inRussian;
        this.text = text;
        this.image = image;
        this.example=example;
        this.example_in_russian=example_in_russian;
        this.example2=example2;
        this.example_in_russian2=example_in_russian2;
    }

    public String getExample2() {
        return example2;
    }

    public void setExample2(String example2) {
        this.example2 = example2;
    }

    public String getExample_in_russian2() {
        return example_in_russian2;
    }

    public void setExample_in_russian2(String example_in_russian2) {
        this.example_in_russian2 = example_in_russian2;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getExample_in_russian() {
        return example_in_russian;
    }

    public void setExample_in_russian(String example_in_russian) {
        this.example_in_russian = example_in_russian;
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
