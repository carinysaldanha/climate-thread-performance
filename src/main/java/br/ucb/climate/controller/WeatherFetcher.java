package br.ucb.climate.controller;

import br.ucb.climate.model.City;
import br.ucb.climate.model.WeatherData;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.Scanner;

public class WeatherFetcher {

    public static WeatherData fetch(City city) {
        try {
            String baseURL = "https://archive-api.open-meteo.com/v1/archive";
            String url = String.format(Locale.US, "%s?latitude=%.6f&longitude=%.6f&start_date=2024-01-01&end_date=2024-01-31&hourly=temperature_2m",
                    baseURL, city.getLatitude(), city.getLongitude());

            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");

            InputStream is = conn.getInputStream();
            Scanner sc = new Scanner(is);
            StringBuilder response = new StringBuilder();
            while (sc.hasNext()) response.append(sc.nextLine());
            sc.close();

            JSONObject json = new JSONObject(response.toString());
            JSONArray temperatures = json.getJSONObject("hourly").getJSONArray("temperature_2m");
            JSONArray timestamps = json.getJSONObject("hourly").getJSONArray("time");

            WeatherData dados = new WeatherData(city.getNome());

            for (int i = 0; i < timestamps.length(); i++) {
                String data = timestamps.getString(i).substring(0, 10); // pega "YYYY-MM-DD"
                double temp = temperatures.getDouble(i);
                dados.adicionarTemperatura(data, temp);
            }

            return dados;

        } catch (Exception e) {
            System.err.println("Erro ao buscar dados de " + city.getNome() + ": " + e.getMessage());
            return null;
        }
    }
}