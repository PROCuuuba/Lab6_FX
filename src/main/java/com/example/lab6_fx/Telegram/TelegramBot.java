package com.example.lab6_fx.Telegram;

import com.example.lab6_fx.BotConfig;
import com.example.lab6_fx.Model.*;
import com.example.lab6_fx.Service.*;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

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

        if (text.equals("/start") || text.equals("▶️ Старт") || text.equals("🔄 Играть снова")) {

            session = new UserSession(new QuizGame(countries));
            sessionManager.getSession(userId, session);

            send(userId, "🎮 Викторина началась!");
            sendNextQuestion(userId, session);
            return;
        }

        if (session.getGame().isFinished()) {
            sendWithRestart(userId, session.getFinalStats());
            return;
        }

        if (text.equals("⏭ Пропустить")) {
            session.skip();
            send(userId, "⏭ Вопрос пропущен!");
            sendNextQuestion(userId, session);
            return;
        }

        boolean correct = session.answer(text);

        if (correct) {
            send(userId, "✅ Верно!");
        } else {
            send(userId, "❌ Неверно!");
        }

        // конец игры
        if (session.getGame().isFinished()) {
            sendWithRestart(userId, session.getFinalStats());
        } else {
            sendNextQuestion(userId, session);
        }
    }

    private void sendNextQuestion(String userId, UserSession session) {
        QuizQuestion q = session.nextQuestion();

        if (q == null) {
            sendWithRestart(userId, session.getFinalStats());
            return;
        }

        send(userId, q.getQuestion());
    }

    private ReplyKeyboardMarkup getGameKeyboard() {
        KeyboardButton skipBtn = new KeyboardButton("⏭ Пропустить");

        KeyboardRow row = new KeyboardRow();
        row.add(skipBtn);

        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(row);

        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setKeyboard(keyboard);
        markup.setResizeKeyboard(true);

        return markup;
    }

    private ReplyKeyboardMarkup getStartKeyboard() {
        KeyboardButton startBtn = new KeyboardButton("▶️ Старт");

        KeyboardRow row = new KeyboardRow();
        row.add(startBtn);

        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(row);

        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setKeyboard(keyboard);
        markup.setResizeKeyboard(true);

        return markup;
    }

    private ReplyKeyboardMarkup getRestartKeyboard() {
        KeyboardButton restartBtn = new KeyboardButton("🔄 Играть снова");

        KeyboardRow row = new KeyboardRow();
        row.add(restartBtn);

        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(row);

        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setKeyboard(keyboard);
        markup.setResizeKeyboard(true);

        return markup;
    }

    private void send(String chatId, String text) {
        SendMessage msg = new SendMessage(chatId, text);
        msg.setReplyMarkup(getGameKeyboard());

        try {
            execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendWithRestart(String chatId, String text) {
        SendMessage msg = new SendMessage(chatId, text);
        msg.setReplyMarkup(getRestartKeyboard());

        try {
            execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendStart(String chatId) {
        SendMessage msg = new SendMessage(chatId, "Нажми кнопку, чтобы начать 👇");
        msg.setReplyMarkup(getStartKeyboard());

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