package com.nutmeg.config;


import com.nutmeg.Constants;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.util.Timeout;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Value("${contact.httpConnectionMaxPerRoute:50}")
    int retrieveDataHttpConnectionMaxPerRoute;
    @Value("${contact.httpConnectionMaxSize:50}")
    int retrieveDataHttpConnectionMaxSize;
    @Value("${contact.httpConnectionTimeout:500}")
    int retrieveDataHttpConnectionTimeout;
    @Value("${contact.httpReadTimeout:2000}")
    int retrieveDataHttpReadTimeout;
    @Value("${contact.httpRequestCloseIdleConnectionsInSecond:5}")
    int httpRequestCloseIdleConnectionsInSecond;

    @Bean(name = Constants.REST_TEMPLATE)
    public RestTemplate restTemplate() {

        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
        poolingHttpClientConnectionManager.setMaxTotal(retrieveDataHttpConnectionMaxSize);
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(retrieveDataHttpConnectionMaxPerRoute);

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(Timeout.ofSeconds(retrieveDataHttpConnectionTimeout))
                .setResponseTimeout(Timeout.ofSeconds(retrieveDataHttpReadTimeout))
                .build();

        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(poolingHttpClientConnectionManager)
                .evictExpiredConnections()
                .evictIdleConnections(Timeout.ofSeconds(httpRequestCloseIdleConnectionsInSecond))
                .build();

        HttpComponentsClientHttpRequestFactory httpRequestFactory =
                new HttpComponentsClientHttpRequestFactory(httpClient);

        return new RestTemplate(httpRequestFactory);
    }
}

