package config;

import com.google.gson.*;
import lombok.extern.log4j.Log4j2;
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

@Log4j2
public class ConfSingleton_OkHttpClient {
    private static OkHttpClient clientOK;

    private static Retrofit retrofit;

    private ConfSingleton_OkHttpClient() {
    }

    public static Retrofit getInstance() {
        if (clientOK == null) {
            CookieManager cookieManager = new CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

//            CookieJar cookieJar =
//                    new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));


            clientOK = new OkHttpClient.Builder()
                    .readTimeout(Duration.of(10, ChronoUnit.MINUTES))
                    .callTimeout(Duration.of(10, ChronoUnit.MINUTES))
                    .connectTimeout(Duration.of(10, ChronoUnit.MINUTES))
                    .addInterceptor(chain -> {
                                Request original = chain.request();

                                Request.Builder builder1 = original.newBuilder()
                                        .header("X-Auth-Token", "2deee83e549c4a6e9709871d0fd58a0a")
                                        .url(original.url().newBuilder().addQueryParameter("headToken", "adfsdf")
                                                .addQueryParameter("ts", "1")

                                                .build());
                                Request request = builder1.build();
                                return chain.proceed(request);
                            }
                    )
//                    .cookieJar(new JavaNetCookieJar(cookieManager))
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
                    .baseUrl(ConfigurationSingleton_Client.getInstance().getPath_base())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(clientOK)
                    .build();
        }

        return retrofit;
    }

}
