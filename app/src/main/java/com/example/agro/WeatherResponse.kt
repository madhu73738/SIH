package com.example.agro



data class WeatherResponse(
    val weather: List<Weather>,  // A list of weather descriptions
    val main: Main,              // Main weather data such as temperature and humidity
    val rain: Rain? = null              // Rain data (if available)
)

data class Weather(
    val description: String  // Description of the weather, e.g., "clear sky"
)

data class Main(
    val temp: Double,         // Temperature in Celsius
    val humidity: Int         // Humidity percentage
)

data class Rain(
    val `1h`: Double?         // Rainfall in the last hour (in mm)
)