package com.cons.man.domain;

import java.io.Serializable;

public class WeatherInfoVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private int site_id;
	private String result;
	private String weather_id;
	private String weather_main;
	private String weather_main_en;
	private String weather_desc;
	private String weather_icon;
	private double temp;
	private double humidity;
	private double wind_speed;
	private double wind_deg;
	private String update_time;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSite_id() {
		return site_id;
	}

	public void setSite_id(int site_id) {
		this.site_id = site_id;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getWeather_id() {
		return weather_id;
	}

	public void setWeather_id(String weather_id) {
		this.weather_id = weather_id;
	}

	public String getWeather_main() {
		return weather_main;
	}

	public void setWeather_main(String weather_main) {
		this.weather_main = weather_main;
	}

	public String getWeather_main_en() {
		return weather_main_en;
	}

	public void setWeather_main_en(String weather_main_en) {
		this.weather_main_en = weather_main_en;
	}

	public String getWeather_desc() {
		return weather_desc;
	}

	public void setWeather_desc(String weather_desc) {
		this.weather_desc = weather_desc;
	}

	public String getWeather_icon() {
		return weather_icon;
	}

	public void setWeather_icon(String weather_icon) {
		this.weather_icon = weather_icon;
	}

	public double getTemp() {
		return temp;
	}

	public void setTemp(double temp) {
		this.temp = temp;
	}

	public double getHumidity() {
		return humidity;
	}

	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}

	public double getWind_speed() {
		return wind_speed;
	}

	public void setWind_speed(double wind_speed) {
		this.wind_speed = wind_speed;
	}

	public double getWind_deg() {
		return wind_deg;
	}

	public void setWind_deg(double wind_deg) {
		this.wind_deg = wind_deg;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

}
