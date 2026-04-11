package com.example.lab6_fx.Service;

import com.example.lab6_fx.Model.QuizQuestion;

public class UserSession {
    private QuizGame game;
    private int correct = 0;
    private int total = 0;
    private int skipped = 0;
    private QuizQuestion currentQuestion;

    public UserSession(QuizGame game) {
        this.game = game;
    }

    public QuizQuestion nextQuestion() {
        currentQuestion = game.getNextQuestion();
        total++;
        return currentQuestion;
    }

    public boolean answer(String answer) {
        boolean result = currentQuestion.isCorrect(answer);
        if (result) correct++;
        return result;
    }

    public void skip() {
        skipped++;
    }

    public String getFinalStats() {
        return "🏁 Игра окончена!\n\n" +
                "✅ Правильных: " + correct + "\n" +
                "❌ Неправильных: " + (total - correct - skipped) + "\n" +
                "⏭ Пропущенных: " + skipped;
    }

    public QuizGame getGame() {
        return game;
    }
}