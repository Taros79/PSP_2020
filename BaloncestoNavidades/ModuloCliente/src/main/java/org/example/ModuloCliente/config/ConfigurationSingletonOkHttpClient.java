package org.example.ModuloCliente.config;

import com.google.gson.*;
import lombok.Getter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Singleton
@Getter
public class ConfigurationSingletonOkHttpClient {
    private final OkHttpClient client;
    private final Retrofit retrofit;

    private final ConfigurationSingletonClient configurationSingletonClient;

    @Inject
    private ConfigurationSingletonOkHttpClient(ConfigurationSingletonClient configurationSingletonClient) {
        this.configurationSingletonClient = configurationSingletonClient;

        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        client = new OkHttpClient.Builder()
                .readTimeout(Duration.of(10, ChronoUnit.MINUTES))
                .callTimeout(Duration.of(10, ChronoUnit.MINUTES))
                .connectTimeout(Duration.of(2, ChronoUnit.MINUTES))
                .addInterceptor(chain -> {
                    Request original = chain.request();

                    Request.Builder builder = original.newBuilder()
                            .url(original.url().newBuilder().build());
                    Request request = builder.build();
                    return chain.proceed(request);
                }).build();

        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>)
                        (json, type, jsonDeserializationContext) -> LocalDateTime.parse(json.getAsJsonPrimitive().getAsString()))
                .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>)
                        (localDateTime, type, jsonSerializationContext) -> new JsonPrimitive(localDateTime.toString())
                ).create();

        retrofit = new Retrofit.Builder()
                .baseUrl(configurationSingletonClient.getPathbase())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

}

