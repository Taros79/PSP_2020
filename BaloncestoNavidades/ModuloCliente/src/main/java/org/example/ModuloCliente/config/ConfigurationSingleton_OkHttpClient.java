package org.example.ModuloCliente.config;

import com.google.gson.*;
import lombok.extern.log4j.Log4j2;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.lang.reflect.Type;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

@Log4j2
public class ConfigurationSingleton_OkHttpClient {
    private static OkHttpClient clientOK;

    private static Retrofit retrofit;

    private ConfigurationSingleton_OkHttpClient() {
    }

    public static synchronized Retrofit getInstance() {
        if (clientOK == null) {
            CookieManager cookieManager = new CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            ConnectionPool connectionPool = new ConnectionPool(10, 1, TimeUnit.SECONDS);

            clientOK = new OkHttpClient.Builder()
                    .connectionPool(connectionPool)
                    .readTimeout(Duration.of(10, ChronoUnit.MINUTES))
                    .callTimeout(Duration.of(10, ChronoUnit.MINUTES))
                    .connectTimeout(Duration.of(10, ChronoUnit.MINUTES))
                    .addInterceptor(chain -> {
                                Request original = chain.request();

                                Request.Builder builder1 = original.newBuilder()
                                        .url(original.url().newBuilder().build());
                                Request request = builder1.build();
                                return chain.proceed(request);
                            }
                    )
                    .build();
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
                @Override
                public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                    return LocalDateTime.parse(json.getAsJsonPrimitive().getAsString());
                }
            }).registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
                        @Override
                        public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
                            return new JsonPrimitive(localDateTime.toString());
                        }
                    }
            ).create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(ConfigurationSingletonClient.getInstance().getPath_base())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(clientOK)
                    .build();
        }

        return retrofit;
    }

}
