package br.ucb.climate.controller;

import br.ucb.climate.model.City;
import br.ucb.climate.model.WeatherData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadManager {

    public static void executar(List<City> cidades, int numThreads) {
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        int tamanho = cidades.size() / numThreads;

        for (int i = 0; i < numThreads; i++) {
            int inicio = i * tamanho;
            int fim = (i == numThreads - 1) ? cidades.size() : inicio + tamanho;
            List<City> sublista = cidades.subList(inicio, fim);

            executor.execute(() -> {
                for (City cidade : sublista) {
                    WeatherData dados = WeatherFetcher.fetch(cidade);
                    if (dados != null) dados.imprimirResumo();
                }
            });
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}