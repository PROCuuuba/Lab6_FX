package com.example.lab6_fx.Service;

import com.example.lab6_fx.Model.Country;
import com.example.lab6_fx.BotConfig;

import java.io.*;
import java.util.*;

public class DataLoader {
    public static List<Country> loadCountries() {
        List<Country> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(BotConfig.RESOURCES_PATH + "countries.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if(parts.length >= 2)
                    list.add(new Country(parts[0], parts[1]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}