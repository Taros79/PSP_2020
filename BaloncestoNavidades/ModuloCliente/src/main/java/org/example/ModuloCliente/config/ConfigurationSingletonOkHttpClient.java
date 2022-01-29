package org.example.ModuloCliente.config;

import com.google.gson.*;
import lombok.Getter;
import lombok.Setter;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
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
    private final CacheAuthorization cache;

    private final ConfigurationSingletonClient configurationSingletonClient;

    @Inject
    private ConfigurationSingletonOkHttpClient(ConfigurationSingletonClient configurationSingletonClient, CacheAuthorization cache) {
        this.configurationSingletonClient = configurationSingletonClient;
        this.cache = cache;

        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        client = new OkHttpClient.Builder()
                .readTimeout(Duration.of(10, ChronoUnit.MINUTES))
                .callTimeout(Duration.of(10, ChronoUnit.MINUTES))
                .connectTimeout(Duration.of(2, ChronoUnit.MINUTES))
                .addInterceptor(new AuthorizationInterceptor(cache)).build();

        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>)
                        (json, type, jsonDeserializationContext) -> LocalDateTime.parse(json.getAsJsonPrimitive().getAsString()))
                .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>)
                        (localDateTime, type, jsonSerializationContext) -> new JsonPrimitive(localDateTime.toString())
                ).create();

        retrofit = new Retrofit.Builder()
                .baseUrl(configurationSingletonClient.getPathbase())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(client)
                .build();
    }

}