package com.example.simpleenglish;

public class Sentence {
   private String original;
   private String inRussian;
   private String wrongWord1;
   private String wrongWord2;

    public Sentence(String original, String inRussian, String wrongWord1, String wrongWord2) {
        this.original = original;
        this.inRussian = inRussian;
        this.wrongWord1 = wrongWord1;
        this.wrongWord2 = wrongWord2;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getInRussian() {
        return inRussian;
    }

    public void setInRussian(String inRussian) {
        this.inRussian = inRussian;
    }

    public String getWrongWord1() {
        return wrongWord1;
    }

    public void setWrongWord1(String wrongWord1) {
        this.wrongWord1 = wrongWord1;
    }

    public String getWrongWord2() {
        return wrongWord2;
    }

    public void setWrongWord2(String wrongWord2) {
        this.wrongWord2 = wrongWord2;
    }
}
