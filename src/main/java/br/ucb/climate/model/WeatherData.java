package br.ucb.climate.model;

import java.util.HashMap;
import java.util.Map;

public class WeatherData {
    // Para cada dia (String yyyy-MM-dd), armazenamos um array de temperaturas daquele dia
    private Map<String, double[]> dailyTemperatures = new HashMap<>();

    // Você pode adaptar para armazenar média, mínima e máxima após cálculo
    private Map<String, TemperatureStats> dailyStats = new HashMap<>();

    public void addTemperatures(String date, double[] temps) {
        dailyTemperatures.put(date, temps);
    }

    public void calculateStats() {
        for (String date : dailyTemperatures.keySet()) {
            double[] temps = dailyTemperatures.get(date);
            double min = Double.MAX_VALUE;
            double max = Double.MIN_VALUE;
            double sum = 0.0;

            for (double t : temps) {
                if (t < min) min = t;
                if (t > max) max = t;
                sum += t;
            }

            double avg = sum / temps.length;
            dailyStats.put(date, new TemperatureStats(avg, min, max));
        }
    }

    public Map<String, TemperatureStats> getDailyStats() {
        return dailyStats;
    }

    public static class TemperatureStats {
        public final double avg;
        public final double min;
        public final double max;

        public TemperatureStats(double avg, double min, double max) {
            this.avg = avg;
            this.min = min;
            this.max = max;
        }
    }
}