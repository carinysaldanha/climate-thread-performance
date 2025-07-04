package br.ucb.climate.threads;

import br.ucb.climate.Util;
import br.ucb.climate.controller.WeatherFetcher;
import br.ucb.climate.model.City;
import br.ucb.climate.model.WeatherData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main27Threads {

    public static void main(String[] args) {
        List<City> cidades = Util.carregarCapitais();
        double total = 0;

        for (int rodada = 1; rodada <= 10; rodada++) {
            System.out.println("Rodada " + rodada);
            long inicio = System.currentTimeMillis();

            ExecutorService executor = Executors.newFixedThreadPool(27);

            for (City cidade : cidades) {
                executor.execute(new Worker(cidade));
            }

            executor.shutdown();
            while (!executor.isTerminated()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            long fim = System.currentTimeMillis();
            double tempo = (fim - inicio) / 1000.0;
            System.out.printf("Tempo da rodada: %.2f segundos\n", tempo);
            total += tempo;
        }

        System.out.printf("\nTempo médio: %.2f segundos\n", total / 10);
    }

    public static class Worker implements Runnable {
        private final City cidade;

        public Worker(City cidade) {
            this.cidade = cidade;
        }

        @Override
        public void run() {
            WeatherData dados = WeatherFetcher.fetch(cidade);
            if (dados != null) dados.imprimirResumo();
        }
    }
}