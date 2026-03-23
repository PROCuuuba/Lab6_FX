package com.example.lab6_fx.Telegram;

import com.example.lab6_fx.BotConfig;
import com.example.lab6_fx.Model.*;
import com.example.lab6_fx.Service.*;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class TelegramBot extends TelegramLongPollingBot {

    private SessionManager sessionManager = new SessionManager();
    private List<Country> countries;

    public TelegramBot(List<Country> countries) {
        this.countries = countries;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) return;

        String text = update.getMessage().getText();
        String userId = update.getMessage().getChatId().toString();

        UserSession session = sessionManager.getSession(userId, new UserSession(new QuizGame(countries)));

        if (text.equals("/start")) {
            send(userId, "Привет! Начинаем викторину!");
            sendNextQuestion(userId, session);
            return;
        }

        boolean correct = session.answer(text);
        if (correct) {
            send(userId, "✅ Верно! " + session.getStats());
        } else {
            send(userId, "❌ Неверно! " + session.getStats());
        }

        sendNextQuestion(userId, session);
    }

    private void sendNextQuestion(String userId, UserSession session) {
        QuizQuestion q = session.nextQuestion();
        send(userId, q.getQuestion());
    }

    private void send(String chatId, String text) {
        SendMessage msg = new SendMessage(chatId, text);
        try {
            execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return BotConfig.BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BotConfig.BOT_TOKEN;
    }
}