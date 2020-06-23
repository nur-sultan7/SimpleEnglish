package com.example.simpleenglish;

public class Word {
    private String image;
    private String in_english;
    private String in_russian;

    public Word(String image, String in_english, String in_russian) {
        this.image = image;
        this.in_english = in_english;
        this.in_russian = in_russian;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIn_english() {
        return in_english;
    }

    public void setIn_english(String in_english) {
        this.in_english = in_english;
    }

    public String getIn_russian() {
        return in_russian;
    }

    public void setIn_russian(String in_russian) {
        this.in_russian = in_russian;
    }
}
