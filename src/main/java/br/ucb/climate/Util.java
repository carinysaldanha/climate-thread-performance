package br.ucb.climate;

import br.ucb.climate.model.City;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Util {

    public static List<City> carregarCapitais() {
        try {
            InputStream is = Util.class.getResourceAsStream("/cities.json");
            String json = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            JSONArray array = new JSONArray(json);
            List<City> cidades = new ArrayList<>();

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String nome = obj.getString("nome");
                double lat = obj.getDouble("latitude");
                double lon = obj.getDouble("longitude");
                cidades.add(new City(nome, lat, lon));
            }

            return cidades;

        } catch (Exception e) {
            System.err.println("Erro ao carregar cidades: " + e.getMessage());
            return List.of();
        }
    }
}