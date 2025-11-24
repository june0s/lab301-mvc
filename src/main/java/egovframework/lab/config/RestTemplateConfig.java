package egovframework.lab.config;

import egovframework.lab.exception.ApiClientException;
import egovframework.lab.exception.ApiServerException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.setRequestFactory(clientHttpRequestFactory());
        return restTemplate;
    }

    private BufferingClientHttpRequestFactory clientHttpRequestFactory() {
        SimpleClientHttpRequestFactory simple = new SimpleClientHttpRequestFactory();
        simple.setConnectTimeout(5000);
        simple.setReadTimeout(15000);
        BufferingClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(simple);

        return factory;
    }

    static class CustomResponseErrorHandler implements ResponseErrorHandler {
        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            int status = response.getRawStatusCode();
            return status >= 400 && status < 600;
        }
        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            String body = readBody(response);
            int status = response.getRawStatusCode();

            if (status >= 400 && status < 500) {
                throw new ApiClientException(status, body);
            } else if (status >= 500) {
                throw new ApiServerException(status, body);
            }
        }
    }

    private static String readBody(ClientHttpResponse response) throws IOException {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(response.getBody(), StandardCharsets.UTF_8)
        )) {
            return br.lines().collect(Collectors.joining());
        } catch (Exception e) {
            return "Failed to read body.";
        }
    }
}
