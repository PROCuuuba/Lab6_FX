package com.example.lab6_fx.Service;

import com.example.lab6_fx.Model.QuizQuestion;
import com.example.lab6_fx.Model.Country;

import java.util.*;

public class QuizGame {

    private List<Country> countries;
    private Set<Country> used = new HashSet<>();
    private Random random = new Random();

    public QuizGame(List<Country> countries) {
        this.countries = countries;
    }

    public QuizQuestion getNextQuestion() {
        if (used.size() == countries.size()) {
            return null;
        }

        Country c;
        do {
            c = countries.get(random.nextInt(countries.size()));
        } while (used.contains(c));

        used.add(c);
        return new QuizQuestion(c);
    }

    public boolean isFinished() {
        return used.size() == countries.size();
    }
}