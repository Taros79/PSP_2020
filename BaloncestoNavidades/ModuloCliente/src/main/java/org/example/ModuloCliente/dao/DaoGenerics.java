package org.example.ModuloCliente.dao;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import okhttp3.MediaType;
import org.example.Common.EE.errores.ApiError;
import org.example.ModuloCliente.dao.utils.Constantes;
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
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else if (response.code() == 404) {
                resultado = Either.left(new ApiError(Constantes.OBJETO_NO_ENCONTRADO, LocalDateTime.now()));
            } else if (response.code() == 500) {
                resultado = Either.left(new ApiError(Constantes.PROBLEMA_EN_SERVIDOR, LocalDateTime.now()));
            }
        } catch (JsonSyntaxException jsonSyntaxException) {
            resultado = Either.left(new ApiError(Constantes.TODO_CORRECTO, LocalDateTime.now()));
        } catch (Exception e) {
            resultado = Either.left(new ApiError(Constantes.ERROR_DE_COMUNICACION, LocalDateTime.now()));
            log.error(e.getMessage(), e);
        }

        return resultado;
    }

    public String apiCallPersonalizado(Call<String> call) {
        String resultado = null;
        try {
            Response<String> response = call.execute();
            if (response.isSuccessful()) {
                resultado = response.body();
            } else if (response.code() == 404) {
                resultado = Constantes.OBJETO_NO_ENCONTRADO;
            } else if (response.code() == 500) {
                resultado = Constantes.PROBLEMA_EN_SERVIDOR;
            }
        } catch (Exception e) {
            resultado = Constantes.ERROR_DE_COMUNICACION;
            log.error(e.getMessage(), e);
        }
        return resultado;
    }
}
