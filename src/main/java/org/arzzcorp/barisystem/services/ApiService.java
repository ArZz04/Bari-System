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
    private String jwtToken;  // Token JWT

    // Constructor para inicializar ApiService con el JWT
    public ApiService(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    // Método para hacer una solicitud GET a la API
    public String makeGetRequest(String endpoint) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        // Crear la solicitud GET con el JWT en el header de autorización
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + endpoint))
                .header("Authorization", "Bearer " + jwtToken)
                .build();

        // Enviar la solicitud y obtener la respuesta
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return response.body();  // Retornar el cuerpo de la respuesta si es exitosa
        } else {
            throw new Exception("Error: " + response.statusCode() + " - " + response.body());
        }
    }

    // Método para hacer una solicitud POST a la API
    public String makePostRequest(String endpoint, String requestBody) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        // Crear la solicitud POST con el JWT en el header de autorización
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + endpoint))
                .header("Authorization", "Bearer " + jwtToken)
                .header("Content-Type", "application/json")  // Asegúrate de que sea un JSON
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))  // Cuerpo de la solicitud POST
                .build();

        // Enviar la solicitud y obtener la respuesta
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return response.body();  // Retornar el cuerpo de la respuesta si es exitosa
        } else {
            throw new Exception("Error: " + response.statusCode() + " - " + response.body());
        }
    }

    // Método para obtener las cabeceras de la respuesta, por ejemplo, para obtener el token refrescado
    public List<String> getResponseHeaders(String endpoint) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        // Crear la solicitud GET con el JWT en el header de autorización
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + endpoint))
                .header("Authorization", "Bearer " + jwtToken)
                .build();

        // Enviar la solicitud y obtener la respuesta
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Obtener las cabeceras de la respuesta
        HttpHeaders headers = response.headers();
        return headers.allValues("Authorization");  // Retornar el valor de la cabecera Authorization
    }

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

}
