package org.arzzcorp.barisystem.services;

// MONGODB_URI=mongodb+srv://arzz:ArZzDev229@clusterbasic.tv3xx4y.mongodb.net/bari-system?retryWrites=true&w=majority&appName=ClusterBasic
// PASSWORD_PEPPER=miKarlitaHermosaaMiFrootloops<3

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class ApiFunctions {

    private static final String API_BASE_URL = "http://bariparques.local:3000";  // Dirección base de tu API
    private static final String PASSWORD_PEPPER = "miKarlitaHermosaaMiFrootloops<3";  // La palabra secreta para hashear

    public static String makePostRequestSync(String url, String requestBody) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new Exception("HTTP error: " + response.statusCode() + " - " + response.body());
        }
    }

    // Método para hacer una solicitud Post con token
    public static String makePutRequestSync(String url, String body, String token) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        // Construye la solicitud PUT con el token y cuerpo
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token) // 👈 ¡Header de autorización!
                .PUT(HttpRequest.BodyPublishers.ofString(body)) // 👈 Método PUT
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Maneja códigos de error
        if (response.statusCode() >= 400) {
            throw new Exception("HTTP error: " + response.statusCode() + " - " + response.body());
        }
        return response.body();
    }

    // Método para hacer una solicitud Get sin token
    public static String makeGetRequestSync(String url) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new Exception("HTTP error: " + response.statusCode() + " - " + response.body());
        }
    }

    public static String makeGetRequestSync(String url, String token) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)  // Aquí se incluye el token en el header
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new Exception("HTTP error: " + response.statusCode() + " - " + response.body());  // Lanza un error si la respuesta no es exitosa
        }
    }
}
