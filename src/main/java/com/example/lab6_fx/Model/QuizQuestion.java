package com.example.lab6_fx.Model;

import com.example.lab6_fx.Model.Country;

public class QuizQuestion {
    private Country correctCountry;

    public QuizQuestion(Country correctCountry) {
        this.correctCountry = correctCountry;
    }

    public boolean isCorrect(String userAnswer) {
        return userAnswer.equalsIgnoreCase(correctCountry.getName());
    }

    public String getQuestion() {
        return "Где находится: " + correctCountry.getLandmark() + "?";
    }

    public Country getCorrectCountry() {
        return correctCountry;
    }
}