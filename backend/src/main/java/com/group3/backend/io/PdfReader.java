package com.group3.backend.io;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group3.backend.model.Course;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class PdfReader implements AdapterFileReader {

    // URL of your FastAPI endpoint (adjust as needed)
    private static final String FASTAPI_ENDPOINT = "http://localhost:8000/process-static-pdf/";

    @Override
    public List<Course> readSchedule() {
        List<Course> entries = new ArrayList<>();
        try {
            // Create an HttpClient
            HttpClient client = HttpClient.newHttpClient();

            // Build the GET request to your FastAPI endpoint
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(FASTAPI_ENDPOINT))
                    .GET()
                    .build();

            // Send the request and retrieve the response body as a String
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            // For debugging, print out the raw response
            System.out.println("Response from FastAPI: " + responseBody);

            // Use Jackson to parse the JSON response
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(responseBody);

            // Extract the "course_schedule" array from the JSON response
            JsonNode courseScheduleNode = root.get("course_schedule");

            // Convert the JSON array into a List<Course>
            entries = mapper.readValue(courseScheduleNode.toString(), new TypeReference<List<Course>>() {});
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return entries;
    }
}
