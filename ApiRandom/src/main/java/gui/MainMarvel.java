package gui;

import com.google.gson.*;
import dao.modelo.marvel.ApiError;
import dao.modelo.marvel.MarvelCharacters;
import dao.retrofit.MarvelApi;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class MainMarvel {

    public static void main(String[] args) throws IOException {
        OkHttpClient clientOK;

        Retrofit retrofit;


        clientOK = new OkHttpClient.Builder()
                .readTimeout(Duration.of(10, ChronoUnit.MINUTES))
                .callTimeout(Duration.of(10, ChronoUnit.MINUTES))
                .connectTimeout(Duration.of(10, ChronoUnit.MINUTES))
                .addInterceptor(chain -> {
                    Request original = chain.request();

                    Request.Builder builder1 = original.newBuilder()
                            .url(original.url().newBuilder()
                                    .addQueryParameter("ts","1")
                                    .addQueryParameter("apikey","a26d34b6ea64ce618360835be5888f91")
                                    .addQueryParameter("hash","073e520a55d710ef1b77df866349e689")
                                    .build());
                    Request request = builder1.build();
                    return chain.proceed(request);}
                )

                .build();
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
            @Override
            public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                return LocalDateTime.parse(json.getAsJsonPrimitive().getAsString());
            }
        }).registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>(){
                    @Override
                    public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
                        return new JsonPrimitive(localDateTime.toString());
                    }
                }
        ).create();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://gateway.marvel.com/v1/public/")
                //.addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(clientOK)
                .build();

        MarvelApi marvelAPI = retrofit.create(MarvelApi.class);

        Response<MarvelCharacters> response = marvelAPI.getCharacters().execute();

        if (response.isSuccessful())
        {
            System.out.println(response.body());
        }
        else
        {
            Gson g = new Gson();
            ApiError apierror = g.fromJson(response.errorBody().string(), ApiError.class);

            System.out.println("error Code"+ response.code());
            System.out.println("error Code"+ response.message());
            System.out.println("error " +apierror.getMessage());
        }


    }
}
