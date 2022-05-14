package notas.Cliente.dao.utils;

import com.google.gson.*;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import notas.Cliente.config.ConfigurationSingleton_Client;
import notas.Cliente.dao.retrofit.*;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class NetworkProducers {

    private CacheAuthorization cacheAuthorization;

    @Inject
    public NetworkProducers(CacheAuthorization cacheAuthorization) {
        this.cacheAuthorization = cacheAuthorization;
    }

    @Produces
    @Singleton
    public OkHttpClient getOK() {
        return new OkHttpClient.Builder()
                .protocols(List.of(Protocol.HTTP_1_1))
                .readTimeout(Duration.of(10, ChronoUnit.MINUTES))
                .callTimeout(Duration.of(10, ChronoUnit.MINUTES))
                .connectTimeout(Duration.of(10, ChronoUnit.MINUTES))
                .addInterceptor(new AuthorizationInterceptor(cacheAuthorization))
                .build();
    }

    @Produces
    @Singleton
    @Named(ConstantesParametros.PATH_BASE)
    public String getPathBase(ConfigurationSingleton_Client configurationSingletonClient) {
        return configurationSingletonClient.getPathBase();
    }


    @Produces
    @Singleton
    public Gson getGson() {
        return new GsonBuilder().registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
            @Override
            public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                return LocalDate.parse(json.getAsJsonPrimitive().getAsString());
            }
        }).registerTypeAdapter(LocalDate.class, new JsonSerializer<LocalDate>() {
                    @Override
                    public JsonElement serialize(LocalDate localDate, Type type, JsonSerializationContext jsonSerializationContext) {
                        return new JsonPrimitive(localDate.toString());
                    }
                }
        ).create();
    }


    @Produces
    @Singleton
    public Retrofit createRetrofit(Gson gson, @Named(ConstantesParametros.PATH_BASE) String pathBase, OkHttpClient ok) {

        return new Retrofit.Builder()
                .baseUrl(pathBase)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create()) //para String
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(ok)
                .build();
    }


    @Produces
    public ApiParte createRegistroApi(Retrofit retrofit) {
        return retrofit.create(ApiParte.class);
    }
}
