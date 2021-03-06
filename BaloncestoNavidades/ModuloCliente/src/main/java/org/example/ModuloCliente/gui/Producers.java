package org.example.ModuloCliente.gui;

import com.google.gson.*;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import org.example.ModuloCliente.config.CacheAuthorization;
import org.example.ModuloCliente.config.ConfigurationSingletonClient;
import org.example.ModuloCliente.config.ConfigurationSingletonOkHttpClient;
import org.example.ModuloCliente.dao.retrofit.EquiposApi;
import org.example.ModuloCliente.dao.retrofit.JornadasApi;
import org.example.ModuloCliente.dao.retrofit.PartidosApi;
import org.example.ModuloCliente.dao.retrofit.UsuariosApi;
import org.jetbrains.annotations.NotNull;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Producers {

    private final ConfigurationSingletonOkHttpClient configSingleton;

    @Inject
    public Producers(ConfigurationSingletonOkHttpClient configSingleton) {
        this.configSingleton = configSingleton;
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
                        (LocalDateTime, type, jsonSerializationContext) -> new JsonPrimitive(LocalDateTime.toString())).setLenient().create();
    }


    @Produces
    public UsuariosApi createApi(CacheAuthorization ca) {
       /* ca.setUser("grinch");
        ca.setPass("531d5ddd52f607ebf9b94bbe30d8ed03d5d76dc5d8a4bc24e3995e537517f1b0d77ce65fc38a7f98d1321de859e28e6caf84e1fe42eeba91ecf4d8b86b6b84a6");
*/
        configSingleton.getCache().setUser(ca.getUser());
        configSingleton.getCache().setPass(ca.getPass());
        return configSingleton.getRetrofit().create(UsuariosApi.class);
    }


    @Produces
    public PartidosApi createApiPartidos() {
        return configSingleton.getRetrofit().create(PartidosApi.class);
    }

    @Produces
    public EquiposApi createApiEquipos() {
        return configSingleton.getRetrofit().create(EquiposApi.class);
    }

    @Produces
    public JornadasApi createApiJornadas() {
        return configSingleton.getRetrofit().create(JornadasApi.class);
    }

}
