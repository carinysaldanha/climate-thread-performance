package br.ucb.climate.model;

import java.util.HashMap;
import java.util.Map;

public class WeatherData {
    private String cidade;
    private Map<String, DiaClima> dias;

    public WeatherData(String cidade) {
        this.cidade = cidade;
        this.dias = new HashMap<>();
    }

    public void adicionarTemperatura(String data, double temperatura) {
        dias.computeIfAbsent(data, d -> new DiaClima()).adicionar(temperatura);
    }

    public void imprimirResumo() {
        System.out.println("Cidade: " + cidade);
        dias.forEach((data, clima) -> {
            System.out.printf("Data: %s | Média: %.2f | Mín: %.2f | Máx: %.2f\n",
                    data, clima.getMedia(), clima.getMin(), clima.getMax());
        });
    }

    private static class DiaClima {
        private double soma = 0;
        private int count = 0;
        private double min = Double.MAX_VALUE;
        private double max = Double.MIN_VALUE;

        public void adicionar(double temperatura) {
            soma += temperatura;
            count++;
            min = Math.min(min, temperatura);
            max = Math.max(max, temperatura);
        }

        public double getMedia() {
            return soma / count;
        }

        public double getMin() {
            return min;
        }

        public double getMax() {
            return max;
        }
    }
}