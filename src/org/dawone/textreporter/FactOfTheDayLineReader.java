package org.dawone.textreporter;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;

/**
 * Reads the fact of the day by fetching it from a remote JSON API.
 *
 * <p>Sends an HTTP GET request to the configured endpoint and extracts
 * the {@code text} field from the JSON response.</p>
 */
public class FactOfTheDayLineReader {

    private final String apiEndpoint;
    private final HttpClient httpClient;

    /**
     * Constructs a {@code FactOfTheDayLineReader} for the given API endpoint.
     *
     * @param apiEndpoint the URL of the API that returns the fact of the day
     */
    public FactOfTheDayLineReader(String apiEndpoint) {
        this.apiEndpoint = apiEndpoint;
        this.httpClient = HttpClient.newBuilder()
                .build();
    }

    /**
     * Fetches the fact of the day from the API and returns it as an array of lines.
     *
     * <p>Sends a GET request to the configured endpoint, parses the JSON response,
     * and returns the value of the {@code text} field as a single-element array.</p>
     *
     * @return an array containing the fact of the day as a single string
     * @throws RuntimeException if the HTTP request fails or the response cannot be parsed
     */
    public String[] readLines() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiEndpoint))
                .header("Accept", "text/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(
                    request, HttpResponse.BodyHandlers.ofString()
            );
            JSONObject responseJSON = new JSONObject(response.body());

            return new String[]{ responseJSON.getString("text") };
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch fact of the day", e);
        }
    }

    public static void main(String[] args) {
        String apiEndpoint = "https://uselessfacts.jsph.pl//api/v2/facts/today";
        FactOfTheDayLineReader reader = new FactOfTheDayLineReader(apiEndpoint);
        String[] lines = reader.readLines();
        for (String line : lines) {
            System.out.println(line);
        }
    }
}