package org.example.ModuloCliente.gui;

import com.google.gson.*;
import jakarta.validation.constraints.NotNull;
import org.example.ModuloCliente.config.ConfigurationSingletonClient;
import org.example.ModuloCliente.dao.retrofit.BaloncestoApi;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Producers {

    private final ConfigurationSingletonClient configurationSingletonClient;

    @Inject
    public Producers(ConfigurationSingletonClient configurationSingletonClient) {
        this.configurationSingletonClient = configurationSingletonClient;
    }

    @Produces
    @Singleton
    public OkHttpClient getOKHttpClient() {
        return new OkHttpClient.Builder()
                .protocols(List.of(Protocol.HTTP_1_1))
                .readTimeout(Duration.of(10, ChronoUnit.MINUTES))
                .callTimeout(Duration.of(10, ChronoUnit.MINUTES))
                .connectTimeout(Duration.of(10, ChronoUnit.MINUTES))
                .build();
    }

    @Produces
    @Singleton
    public Gson getGson() {
        return new GsonBuilder().registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (jsonElement, type, jsonDeserializationContext) ->
                        LocalDateTime.parse(jsonElement.getAsJsonPrimitive().getAsString()))
                .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>)
                        (LocalDateTime, type, jsonSerializationContext) -> new JsonPrimitive(LocalDateTime.toString())).create();
    }

    @Produces
    @Singleton
    public Retrofit createRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(configurationSingletonClient.getPathbase())
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .client(getOKHttpClient())
                .build();
    }


    @Produces
    public BaloncestoApi createApi(@NotNull Retrofit retrofit) {
        return retrofit.create(BaloncestoApi.class);
    }




}
