package rest.client;

import org.example.lab03.domain.Show;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchProperties;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.*;
import services.rest.ServiceException;

import java.io.IOException;
import java.util.concurrent.Callable;

import static org.springframework.http.MediaType.APPLICATION_JSON;

public class ShowRestClient {

    RestClient restClient = RestClient.builder().
            requestInterceptor(new CustomRestClientInterceptor()).
            build();

    public static final String URL = "http://localhost:8080/shows";

    private <T> T execute(Callable<T> callable) {
        try{
            return callable.call();
        }catch(ResourceAccessException | HttpClientErrorException e){
            throw new ServiceException(e);
        }catch(Exception e){
            throw new ServiceException(e);
        }
    }

    public Show[] getAll(){
        return execute(() ->restClient.get()
                .uri(URL)
                .retrieve()
                .body(Show[].class));
    }

    public Show getShowById(long id){
        return execute(() ->restClient.get()
                .uri(String.format("%s/%s", URL, id))
                .retrieve()
                .body(Show.class));
    }

    public Show[] getShowByArtist(String name){
        return execute(() -> restClient.get())
                .uri(String.format("%s/artist/%s",URL,name))
                .retrieve()
                .body(Show[].class);
    }

    public Show create(Show show){
        return execute(() -> restClient.post()
                .uri(URL)
                .contentType(APPLICATION_JSON)
                .body(show)
                .retrieve()
                .body(Show.class));
    }

    public void delete(long id){
        execute(() -> restClient.delete()
                .uri(String.format("%s/%s", URL, id))
                .retrieve()
                .toBodilessEntity());
    }

    public void update(Show show){
        execute(() -> restClient.put()
                        .uri(String.format("%s/%s", URL, show.getId()))
                        .contentType(APPLICATION_JSON)
                        .body(show)
                        .retrieve()
                        .toBodilessEntity()
        );
    }

    public class CustomRestClientInterceptor implements ClientHttpRequestInterceptor {

        @Override
        public ClientHttpResponse intercept(HttpRequest request,
                                            byte[] body,
                                            ClientHttpRequestExecution execution) throws IOException{
            System.out.println("Sending a "+request.getMethod()+ " request to "+request.getURI()+ " and body ["+new String(body)+"]");
            ClientHttpResponse response = null;
            try{
                response = execution.execute(request, body);
                System.out.println("Got response code " + response.getStatusCode());
            }catch(IOException ex){
                System.out.println("Eroare executie " + ex);
            }
            return response;
        }
    }
}
