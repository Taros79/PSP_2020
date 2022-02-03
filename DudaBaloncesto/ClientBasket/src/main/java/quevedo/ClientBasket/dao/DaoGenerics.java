package quevedo.ClientBasket.dao;

import com.google.gson.Gson;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import quevedo.ClientBasket.utils.ConstantesMensajes;
import quevedo.ClientBasket.utils.ConstantesParametros;
import quevedo.common.error.ApiError;
import retrofit2.HttpException;


@Log4j2
@NoArgsConstructor
abstract class DaoGenerics {


    public <T> Single<Either<String, T>> safeSingleApicall(Single<T> call, Gson gson) {
        return call.map(t -> Either.right(t).mapLeft(Object::toString))
                .subscribeOn(Schedulers.io())
                .onErrorReturn(throwable -> {
                    Either<String, T> error = Either.left(ConstantesMensajes.ERROR_DE_COMUNICACION);
                    if (throwable instanceof HttpException) {
                        if (((HttpException) throwable).response().errorBody().contentType().toString().equals(ConstantesParametros.TEXT_JSON) ||
                                ((HttpException) throwable).response().errorBody().contentType().toString().equals(ConstantesParametros.TEXT_ERROR_SERVLET)) {
                            ApiError apiError = gson.fromJson(((HttpException) throwable).response().errorBody().string(), ApiError.class);
                            error = Either.left(apiError.getMensaje());
                        } else {
                            error = Either.left(ConstantesMensajes.FALLO_DEL_SERVIDOR);
                        }
                    }
                    return error;
                });
    }

}
