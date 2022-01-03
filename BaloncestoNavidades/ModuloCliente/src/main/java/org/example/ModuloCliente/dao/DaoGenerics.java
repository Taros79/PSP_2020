package org.example.ModuloCliente.dao;

import com.google.gson.Gson;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import okhttp3.MediaType;
import org.example.Common.EE.errores.ApiError;
import retrofit2.Call;
import retrofit2.HttpException;
import retrofit2.Response;

import java.time.LocalDateTime;
import java.util.Objects;

@Log4j2
abstract class DaoGenerics {

    public <T> Either<ApiError, T> safeApicall(Call<T> call) {
        Either<ApiError, T> resultado = null;

        try {
            Response <T> response = call.execute();
            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else if (response.code() == 404){
                resultado = Either.left(new ApiError("No encontrado", LocalDateTime.now()));
            }
        } catch (Exception e) {
            resultado = Either.left(new ApiError("No se que pasa", LocalDateTime.now()));
            log.error(e.getMessage(), e);        }

        return resultado;
    }


    public <T> Single<Either<String, T>> safeSingleApicall(Single<T> call) {
        return call.map(t -> Either.right(t).mapLeft(Object::toString))
                .subscribeOn(Schedulers.io())
                .onErrorReturn(throwable -> {
                    Either<String, T> error = Either.left("Error de comunicacion");
                    if (throwable instanceof HttpException) {
                        if (Objects.equals(((HttpException) throwable).response().errorBody().contentType(), MediaType.get("application/json"))) {
                            Gson g = new Gson();
                            ApiError apierror = g.fromJson(((HttpException) throwable).response().errorBody().string(),
                                    ApiError.class);
                            error = Either.left(apierror.getMessage());
                        } else {
                            error = Either.left(((HttpException) throwable).response().message());
                        }
                    }
                    return error;
                });


    }
}
