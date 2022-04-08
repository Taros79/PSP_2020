package rol.Cliente.dao;

import com.google.gson.Gson;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import okhttp3.MediaType;
import retrofit2.Call;
import retrofit2.HttpException;
import retrofit2.Response;
import rol.Common.errores.ApiError;

import javax.inject.Inject;


@Log4j2
abstract class DaoGenerics {

    private final Gson gson;

    @Inject
    public DaoGenerics(Gson gson) {
        this.gson = gson;
    }

    public <T> Single<Either<String, T>> safeSingleApicall(Single<T> call) {
        return call.map(t -> Either.right(t).mapLeft(Object::toString))
                .subscribeOn(Schedulers.io())
                .onErrorReturn(throwable -> {
                    Either<String, T> error = Either.left(ConstantesDao.ERROR_DE_COMUNICACION);
                    if (throwable instanceof HttpException) {
                        if (((HttpException) throwable).response().errorBody().contentType().toString().equals(ConstantesDao.TEXT_JSON) ||
                                ((HttpException) throwable).response().errorBody().contentType().toString().equals(ConstantesDao.TEXT_ERROR_SERVLET)) {
                            ApiError apiError = gson.fromJson(((HttpException) throwable).response().errorBody().string(), ApiError.class);
                            error = Either.left(apiError.getMessage());
                        } else {
                            error = Either.left(ConstantesDao.FALLO_DEL_SERVIDOR);
                        }
                    }
                    return error;
                });
    }

    public <T> Either<String, T> construirEither(Call<T> call) {
        Either<String, T> resultado = null;
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                if (response.errorBody().contentType().equals(MediaType.get(ConstantesDao.APPLICATION_JSON))) {
                    resultado = Either.left(response.errorBody().string());
                } else {
                    resultado = Either.left(response.message());
                }
            }
        } catch (Exception e) {
            resultado = Either.left(ConstantesDao.FALLO_DEL_SERVIDOR);
            log.error(e.getMessage(), e);

        }
        return resultado;
    }
}
