package br.ucb.climate.controller;

import br.ucb.climate.model.City;
import br.ucb.climate.model.WeatherData;

import java.util.ArrayList;
import java.util.List;

public class ThreadWorker extends Thread {
    private List<City> cities;
    private List<WeatherData> results;

    public ThreadWorker(List<City> cities) {
        this.cities = cities;
        this.results = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            for (City city : cities) {
                WeatherData dados = WeatherFetcher.fetch(city); // variavel city corrigida
                synchronized (results) {
                    results.add(dados); // variável dados corrigida
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<WeatherData> getResults() {
        return results;
    }
}
