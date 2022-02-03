package quevedo.ClientBasket.dao.network.di;

import com.google.gson.*;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import quevedo.ClientBasket.config.ConfigSingletonClient;
import quevedo.ClientBasket.dao.network.AuthInterceptor;
import quevedo.ClientBasket.dao.retrofit.GestionApi;
import quevedo.ClientBasket.dao.retrofit.IdentificacionApi;
import quevedo.ClientBasket.dao.retrofit.VisualizarApi;
import quevedo.ClientBasket.utils.ConstantesParametros;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ClienProducers {


    private final ConfigSingletonClient configSingletonClient;
    private final AuthInterceptor interceptor;

    @Inject
    public ClienProducers(ConfigSingletonClient configSingletonClient, AuthInterceptor interceptor) {
        this.configSingletonClient = configSingletonClient;
        this.interceptor = interceptor;
    }


    @Produces
    @Singleton
    public OkHttpClient getOK() {
        return new OkHttpClient.Builder()
                .protocols(List.of(Protocol.HTTP_1_1))
                .readTimeout(Duration.of(10, ChronoUnit.MINUTES))
                .callTimeout(Duration.of(10, ChronoUnit.MINUTES))
                .connectTimeout(Duration.of(10, ChronoUnit.MINUTES))
                .addInterceptor(interceptor)
                .build();
    }

    @Produces
    @Singleton
    @Named(ConstantesParametros.PATH_BASE)
    public String getPathBase(ConfigSingletonClient configurationSingletonClient) {
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
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(ok)
                .build();
    }


    @Produces
    public IdentificacionApi createRegistroApi(Retrofit retrofit) {
        return retrofit.create(IdentificacionApi.class);
    }

    @Produces
    public VisualizarApi createVisualizarApi(Retrofit retrofit) {
        return retrofit.create(VisualizarApi.class);
    }

    @Produces
    public GestionApi createGestionarApi(Retrofit retrofit) {
        return retrofit.create(GestionApi.class);
    }


}
