package br.ucb.climate.controller;

import br.ucb.climate.model.City;
import br.ucb.climate.model.WeatherData;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.*;
import java.util.Locale;
import java.util.Scanner;

public class WeatherFetcher {

    public static WeatherData fetch(City city) {
        String fileName = "cache/" + city.getNome().replaceAll("\\s+", "_") + ".json";
        Path cachePath = Paths.get(fileName);

        try {
            if (Files.exists(cachePath)) {
                String cached = Files.readString(cachePath);
                return processJson(cached, city.getNome());
            }
        } catch (Exception e) {
            System.err.println("Erro lendo cache para " + city.getNome() + ": " + e.getMessage());
            // continua para tentar buscar da API
        }

        int maxTentativas = 3;
        int tentativa = 0;
        long espera = 2000; // 2 segundos

        while (tentativa < maxTentativas) {
            tentativa++;
            try {
                String baseURL = "https://archive-api.open-meteo.com/v1/archive";
                String urlString = String.format(Locale.US,
                        "%s?latitude=%.6f&longitude=%.6f&start_date=2024-01-01&end_date=2024-01-31&hourly=temperature_2m",
                        baseURL, city.getLatitude(), city.getLongitude());

                HttpURLConnection conn = (HttpURLConnection) new URL(urlString).openConnection();
                conn.setRequestMethod("GET");

                int status = conn.getResponseCode();

                if (status == 429) {
                    System.err.println("⚠️  Erro 429: limite de requisições excedido para " + city.getNome() +
                            " (tentativa " + tentativa + "/" + maxTentativas + ")");
                    if (tentativa == maxTentativas) {
                        System.err.println("Máximo de tentativas atingido para " + city.getNome());
                        return null;
                    }
                    Thread.sleep(espera);
                    espera *= 2; // backoff exponencial
                    continue;
                } else if (status != 200) {
                    System.err.println("Erro HTTP " + status + " para " + city.getNome());
                    return null;
                }

                InputStream is = conn.getInputStream();
                Scanner sc = new Scanner(is);
                StringBuilder response = new StringBuilder();
                while (sc.hasNext()) response.append(sc.nextLine());
                sc.close();

                String responseString = response.toString();
                if (!responseString.trim().startsWith("{")) {
                    System.err.println("Resposta inválida da API para " + city.getNome() + ": " + responseString);
                    return null;
                }

                // Salva no cache
                Files.createDirectories(cachePath.getParent());
                Files.writeString(cachePath, responseString);

                return processJson(responseString, city.getNome());

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            } catch (Exception e) {
                System.err.println("Erro ao buscar dados de " + city.getNome() + ": " + e.getMessage());
                return null;
            }
        }

        return null; // caso não entre no loop, mas aqui fica seguro
    }

    private static WeatherData processJson(String responseString, String cidadeNome) {
        JSONObject json = new JSONObject(responseString);
        JSONArray temperatures = json.getJSONObject("hourly").getJSONArray("temperature_2m");
        JSONArray timestamps = json.getJSONObject("hourly").getJSONArray("time");

        WeatherData dados = new WeatherData(cidadeNome);

        for (int i = 0; i < timestamps.length(); i++) {
            String data = timestamps.getString(i).substring(0, 10); // "YYYY-MM-DD"
            double temp = temperatures.getDouble(i);
            dados.adicionarTemperatura(data, temp);
        }

        return dados;
    }
}