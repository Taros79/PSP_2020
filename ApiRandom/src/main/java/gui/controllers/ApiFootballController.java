package gui.controllers;

//import dao.DaoAreas;

import com.google.gson.Gson;
import dao.DaoMarvel;
import dao.DaoUsuarios;
import dao.modelo.ApiError;
import dao.modelo.Area;
import dao.modelo.Competition;
import dao.modelo.Usuario;
import io.reactivex.Single;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import io.vavr.control.Either;
import io.vavr.control.Try;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import retrofit2.HttpException;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

public class ApiFootballController implements Initializable {
    public ListView<Area> listAreas;
    public ListView<Competition> listCompetitions;
    public ListView listTeams;
    public TextField texto;
    private Alert alert;


    private PrincipalController principalController;


    public void setPrincipalController(PrincipalController principalController) {
        this.principalController = principalController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        alert = new Alert(Alert.AlertType.INFORMATION);

    }

    @FXML
    private void clickEntrar(ActionEvent actionEvent) {

        DaoMarvel dao = new DaoMarvel();


        dao.getCharacters(texto.getText())
                .subscribeOn(Schedulers.io())
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> this.principalController
                        .getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                .subscribe(areas -> System.out.println(areas)
                        , throwable -> {

                    if (throwable instanceof HttpException)
                    {
                        Gson g = new Gson();
                        dao.modelo.marvel.ApiError apierror = g.fromJson(((HttpException)throwable).response().errorBody().string(), dao.modelo.marvel.ApiError.class);

                        System.out.println("error Code"+ ((HttpException)throwable).response().code());
                        System.out.println("error Code"+ ((HttpException)throwable).response().message());
                        System.out.println("error " +apierror.getMessage());
                    }

                    alert.setContentText(throwable.getMessage());
                            alert.showAndWait();
                        }

                );

//        ServiciosAreas dao = new ServiciosAreas();
//        dao.getAreas(Integer.parseInt(texto.getText()))
//                .subscribeOn(Schedulers.io())
//                .observeOn(JavaFxScheduler.platform())
//                .doFinally(() -> this.principalController
//                        .getPantallaPrincipal().setCursor(Cursor.DEFAULT))
//                .map(areasRequest ->
//                        areasRequest.getAreas())
//                .subscribe(areas -> listAreas.getItems().addAll(areas)
//                        , throwable -> {
//                            alert.setContentText(throwable.getMessage());
//                            alert.showAndWait();
//                        }
//
//                );


//        var tarea = new Task<Either<String, List<Area>>>() {
//            //public StringProperty test;
//            @Override
//            protected Either<String,List<Area>> call() throws Exception {
//
//                DaoRxAreas dao = new DaoRxAreas();
//                dao.getAreas().map(areasRequest ->
//                        areasRequest.getAreas());
////                DaoAreas dao = new DaoAreas();
////
//////                Thread.sleep(5000);
////                return Either.right(dao.getAreas().map(
////                        areasRequest -> areasRequest.getAreas()
////                ).blockingGet());
//                return null;
//            }
//        };


//        tarea.setOnSucceeded(workerStateEvent -> {
//            Try.of(() -> tarea.get()
//                    .peek(areas -> listAreas.getItems().addAll(areas))
//                    .peekLeft(s -> {
//                        alert.setContentText(s);
//                        alert.showAndWait();
//                    }))
//                    .onFailure(throwable -> {
//                        alert.setContentText(throwable.getMessage());
//                        alert.showAndWait();
//                    });
//            this.principalController.getPantallaPrincipal().setCursor(Cursor.DEFAULT);
//        });
//        tarea.setOnFailed(workerStateEvent -> {
//            alert.setContentText(workerStateEvent.getSource().getException().getMessage());
//            alert.showAndWait();
//            this.principalController.getPantallaPrincipal().setCursor(Cursor.DEFAULT);
//        });
//
//
//        new Thread(tarea).start();
////        ExecutorService executorService = Executors.newFixedThreadPool(1);
////        executorService.submit(tarea);
        this.principalController.getPantallaPrincipal().setCursor(Cursor.WAIT);


    }


    public void cargarCompeticiones(ActionEvent actionEvent) {
        var tarea = new Task<Either<String, List<Competition>>>() {
            //public StringProperty test;
            @Override
            protected Either<String, List<Competition>> call() throws Exception {

//                DaoAreas dao = new DaoAreas();

//                Thread.sleep(5000);
                return null; //dao.getCompetitions(listAreas.getSelectionModel().getSelectedItem());
            }
        };


//        fxText.textProperty().bind(tarea.valueProperty());
        tarea.setOnSucceeded(workerStateEvent -> {
            Try.of(() -> tarea.get().peek(competitions -> listCompetitions.getItems().addAll(competitions))
                    .peekLeft(s -> {
                        alert.setContentText(s);
                        alert.showAndWait();
                    }))
                    .onFailure(throwable -> {
                        alert.setContentText(throwable.getMessage());
                        alert.showAndWait();
                    });
            this.principalController.getPantallaPrincipal().setCursor(Cursor.DEFAULT);
        });
        tarea.setOnFailed(workerStateEvent -> {
            alert.setContentText(workerStateEvent.getSource().getException().getMessage());
            alert.showAndWait();
            this.principalController.getPantallaPrincipal().setCursor(Cursor.DEFAULT);
        });
        new Thread(tarea).start();
//        ExecutorService executorService = Executors.newFixedThreadPool(1);
//        executorService.submit(tarea);
        this.principalController.getPantallaPrincipal().setCursor(Cursor.WAIT);


    }

    public void cargarTeams(ActionEvent actionEvent) {

//        var tarea = new Task<Either<ApiError, Usuario>>() {
//            //public StringProperty test;
//            @Override
//            protected Either<ApiError, Usuario> call() throws Exception {
//                DaoUsuarios dao = new DaoUsuarios();
//                return dao.updateUsuario(new Usuario(null, "nombrecito"));
//            }
//        };
////        fxText.textProperty().bind(tarea.valueProperty());
//        tarea.setOnSucceeded(workerStateEvent -> {
//            tarea.getValue().peek(System.out::println)
//                    .peekLeft(s -> {
//                        alert.setContentText(s.getMessage());
//                        alert.showAndWait();
//                    });
//            this.principalController.getPantallaPrincipal().setCursor(Cursor.DEFAULT);
//        });
//        tarea.setOnFailed(workerStateEvent -> {
//            alert.setContentText(workerStateEvent.getSource().getException().getMessage());
//            alert.showAndWait();
//            this.principalController.getPantallaPrincipal().setCursor(Cursor.DEFAULT);
//        });
//        new Thread(tarea).start();
////        ExecutorService executorService = Executors.newFixedThreadPool(1);
////        executorService.submit(tarea);
//        this.principalController.getPantallaPrincipal().setCursor(Cursor.WAIT);

        DaoUsuarios dao = new DaoUsuarios();
        Single<Usuario> s = Single.fromObservable(dao.updateUsuario(new Usuario(null, "nombre", LocalDateTime.now())))
                .subscribeOn(Schedulers.io())
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> this.principalController
                        .getPantallaPrincipal().setCursor(Cursor.DEFAULT));

        s.subscribe(System.out::println,
                throwable -> {
                    alert.setContentText(throwable.getMessage());
                    alert.showAndWait();
                });
        this.principalController.getPantallaPrincipal().setCursor(Cursor.WAIT);

//        var tarea = new Task<Either<String, List<Team>>>() {
//            public StringProperty test = new SimpleStringProperty();
//            @Override
//            protected Either<String, List<Team>> call() throws Exception {
//
//                DaoAreas dao = new DaoAreas();
//                test.setValue(" cargo uno");
//                Thread.sleep(1000);
//                test.setValue(" cargo dos");
////                Thread.sleep(5000);
//                return dao.getTeams(listCompetitions.getSelectionModel().getSelectedItem(), "2020");
//
//
//            }
//        };
//
//
//        texto.textProperty().bind(tarea.test);
//
////        fxText.textProperty().bind(tarea.valueProperty());
//        tarea.setOnSucceeded(workerStateEvent -> {
//            Try.of(() -> tarea.get().peek(team -> listTeams.getItems().addAll(team))
//                    .peekLeft(s -> {
//                        alert.setContentText(s);
//                        alert.showAndWait();
//                    }))
//                    .onFailure(throwable -> {
//                        alert.setContentText(throwable.getMessage());
//                        alert.showAndWait();
//                    });
//            this.principalController.getPantallaPrincipal().setCursor(Cursor.DEFAULT);
//        });
//        tarea.setOnFailed(workerStateEvent -> {
//            alert.setContentText(workerStateEvent.getSource().getException().getMessage());
//            alert.showAndWait();
//            this.principalController.getPantallaPrincipal().setCursor(Cursor.DEFAULT);
//        });
//
//
//        new Thread(tarea).start();
////        ExecutorService executorService = Executors.newFixedThreadPool(1);
////        executorService.submit(tarea);
//        this.principalController.getPantallaPrincipal().setCursor(Cursor.WAIT);


    }


    public void delUsuario(ActionEvent actionEvent) {

        Single<Either<ApiError, Usuario>> s = Single.fromCallable(() ->
                {
                    DaoUsuarios dao = new DaoUsuarios();
                    return dao.delUsuario(new Usuario("0", "nombre", LocalDateTime.now()));
                }

        )
                .subscribeOn(Schedulers.io())
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> this.principalController
                        .getPantallaPrincipal().setCursor(Cursor.DEFAULT));
        s.subscribe(result ->
                        result.peek(System.out::println)
                        .peekLeft(error -> {
                            alert.setContentText(error.getMessage());
                            alert.showAndWait();
                        }),
                throwable -> {
                    alert.setContentText(throwable.getMessage());
                    alert.showAndWait();
                });

        this.principalController
                .getPantallaPrincipal().setCursor(Cursor.WAIT);
    }
}
