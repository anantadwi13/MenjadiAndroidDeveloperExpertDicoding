package com.anantadwi13.asynctaskloader;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class WeatherItems {
    private int id;
    private String nama, currentWeather, description, temperature;

    public WeatherItems(JSONObject jsonObject){
        try {
            int id = jsonObject.getInt("id");
            String name = jsonObject.getString("name");
            String currentWeather = jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");
            String description = jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");
            double temp = jsonObject.getJSONObject("main").getDouble("temp");
            String temperature = new DecimalFormat("##.##").format(temp);

            this.id = id;
            this.nama = name;
            this.currentWeather = currentWeather;
            this.description = description;
            this.temperature = temperature;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(String currentWeather) {
        this.currentWeather = currentWeather;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
