package br.ucb.climate;

import br.ucb.climate.controller.WeatherFetcher;
import br.ucb.climate.model.City;
import br.ucb.climate.model.WeatherData;

import java.util.List;

public class MainSingleThread {

    public static void main(String[] args) {
        List<City> cidades = Util.carregarCapitais();
        double total = 0;

        for (int rodada = 1; rodada <= 10; rodada++) {
            System.out.println("Rodada " + rodada);
            long inicio = System.currentTimeMillis();

            for (City cidade : cidades) {
                WeatherData dados = WeatherFetcher.fetch(cidade);
                if (dados != null) dados.imprimirResumo();
            }

            long fim = System.currentTimeMillis();
            double tempo = (fim - inicio) / 1000.0;
            System.out.printf("Tempo da rodada: %.2f segundos\n", tempo);
            total += tempo;
        }

        System.out.printf("\nTempo médio: %.2f segundos\n", total / 10);
    }
}