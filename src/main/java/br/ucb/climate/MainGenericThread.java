package br.ucb.climate;

import br.ucb.climate.controller.ThreadManager;
import br.ucb.climate.model.City;

import java.util.List;

public class MainGenericThread {
    public static void main(String[] args) {
        List<City> cidades = Util.carregarCapitais();
        double total = 0;

        int numThreads = 9; // ou 3, ou 27

        for (int i = 1; i <= 10; i++) {
            System.out.println("Rodada " + i);
            long ini = System.currentTimeMillis();
            ThreadManager.executar(cidades, numThreads);
            long fim = System.currentTimeMillis();
            double tempo = (fim - ini) / 1000.0;
            System.out.printf("Tempo da rodada: %.2f segundos\n", tempo);
            total += tempo;
        }

        System.out.printf("\nTempo médio: %.2f segundos\n", total / 10);
    }
}