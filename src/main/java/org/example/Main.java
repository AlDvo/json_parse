package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.bank_info.TotalInfoExchanger;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;


// такса в самом конце статьи https://akupreychik.notion.site/10-HttpClient-d0f7f387bf084f2a9296f1b88d7f72a1

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpResponse<String> response = getHttpResponse("https://belarusbank.by/api/kursExchange");
        System.out.println(response.statusCode());
        Exchanger[] exch = getExchangers(response);
        List<TotalInfoExchanger> totalInfos = getTotalInfoExchangers(exch);

    }

    private static HttpResponse<String> getHttpResponse(String uri) throws IOException, InterruptedException {
        try (HttpClient client = HttpClient.newBuilder().build()) {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .GET()
                    .build();

            return client.send(request, HttpResponse.BodyHandlers.ofString());
        }
    }

    private static List<TotalInfoExchanger> getTotalInfoExchangers(Exchanger[] exch) {
        List<TotalInfoExchanger> totalInfos = new ArrayList<>();
        for (Exchanger exchanger : exch) {
            totalInfos.add(new TotalInfoExchanger(exchanger));
        }
        return totalInfos;
    }

    private static Exchanger[] getExchangers(HttpResponse<String> response) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        response.body();
        return om.readValue(response.body(), Exchanger[].class);
    }
}