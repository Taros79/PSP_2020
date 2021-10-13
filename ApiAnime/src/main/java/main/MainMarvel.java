package main;

import com.google.gson.*;
import dao.modelo.ModPokemon.Pokemon;
import dao.modelo.marvel.ApiError;
import dao.retrofit.PokemonApi;
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
                .baseUrl("https://pokeapi.co/api/v2/")
                //.addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(clientOK)
                .build();

        PokemonApi marvelAPI = retrofit.create(PokemonApi.class);

        Response<Pokemon> response = marvelAPI.getAnimes().execute();

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
