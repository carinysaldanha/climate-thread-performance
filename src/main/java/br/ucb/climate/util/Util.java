package br.ucb.climate.utils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import br.ucb.climate.model.City;
import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.commons.io.IOUtils;

public class Util {

    public static List<City> loadCities() throws Exception {
        InputStream is = Util.class.getResourceAsStream("/cities.json");
        String jsonTxt = IOUtils.toString(is, StandardCharsets.UTF_8);

        JSONArray citiesArray = new JSONArray(jsonTxt);
        List<City> cities = new ArrayList<>();

        for (int i = 0; i < citiesArray.length(); i++) {
            JSONObject c = citiesArray.getJSONObject(i);
            String name = c.getString("name");
            double lat = c.getDouble("latitude");
            double lon = c.getDouble("longitude");
            cities.add(new City(name, lat, lon));
        }
        return cities;
    }
}