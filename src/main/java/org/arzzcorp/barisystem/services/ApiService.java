package org.arzzcorp.barisystem.services;

// MONGODB_URI=mongodb+srv://arzz:ArZzDev229@clusterbasic.tv3xx4y.mongodb.net/bari-system?retryWrites=true&w=majority&appName=ClusterBasic
// PASSWORD_PEPPER=miKarlitaHermosaaMiFrootloops<3

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpHeaders;
import java.util.List;

public class ApiService {

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
