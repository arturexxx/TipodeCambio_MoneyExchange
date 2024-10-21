import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultaTipoCambio {

    MoneyChange moneyChange(String tipoDeCambio) {
        URI urltipodeCambio = URI.create("https://v6.exchangerate-api.com/v6/35c1377acc4a08065d0df06c/latest/" + tipoDeCambio);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(urltipodeCambio)
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        if (response.statusCode() == 200) {
            return new Gson().fromJson(response.body(), MoneyChange.class);
        } else {
            System.out.println("Error en la solicitud: " + response.statusCode());
            return null;
        }
    }
}
