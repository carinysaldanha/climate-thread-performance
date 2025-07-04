package br.ucb.climate.view;

import br.ucb.climate.model.WeatherData;

public class ConsolePrinter {
    public static void imprimir(WeatherData dados) {
        if (dados != null) {
            dados.imprimirResumo();
            System.out.println("------------------------------------------------");
        }
    }
}