package com.example.lab6_fx;

import com.example.lab6_fx.Telegram.TelegramBot;
import com.example.lab6_fx.Service.DataLoader;
import com.example.lab6_fx.Model.Country;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.List;

public class BotRunner {
    public static void main(String[] args) throws Exception {
        List<Country> countries = DataLoader.loadCountries();

        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(new TelegramBot(countries));

        System.out.println("Бот запущен!");
    }
}