module ModuloCliente {
    //needed for JavaFX
    requires javafx.controls;
    requires javafx.fxml;
    requires org.yaml.snakeyaml;
    requires transitive lombok;
    requires java.logging;

    requires org.apache.logging.log4j;
    requires io.vavr;
    requires java.sql;
    requires retrofit2;
    requires io.reactivex.rxjava2;
    requires okhttp3;
    requires retrofit2.adapter.rxjava2;
    requires retrofit2.converter.gson;
    requires retrofit2.converter.scalars;
    requires gson;
    requires jakarta.inject.api;
    requires jakarta.enterprise.cdi.api;
    requires java.validation;
    requires Commons;

    //requires java.validation;          --module-path  ${PATH_TO_FX} --add-modules javafx.controls,javafx.fxml


}
