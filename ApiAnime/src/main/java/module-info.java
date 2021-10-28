module notoques {
    //needed for JavaFX
    requires javafx.controls;
    requires javafx.fxml;
    requires org.yaml.snakeyaml;
    requires transitive lombok;
    requires java.logging;

    requires javax.el;
    requires gson;
    requires retrofit2;
    requires org.apache.logging.log4j;
    requires io.reactivex.rxjava2;
    requires okhttp3;
    requires retrofit2.adapter.rxjava2;
    requires retrofit2.converter.gson;
    requires retrofit2.converter.scalars;
    requires io.vavr;

    //requires java.validation;          --module-path  ${PATH_TO_FX} --add-modules javafx.controls,javafx.fxml


    //requires javafx.swing;

    //needed for HTTP
    // requires unirest.java;

    //needed for JSON
    requires java.sql;
    requires jakarta.enterprise.cdi.api;
    requires jakarta.inject.api;
    requires java.validation;

    //needed for JavaFX
    opens gui;
    opens gui.controllers to javafx.fxml;
    opens dao.utils;
    opens config;

    exports gui;
    exports gui.main;
    exports gui.controllers;
    exports dao.modelo;
    exports dao.modelo.ModPokemon;
    exports dao.modelo.ModMovimientos;
    exports dao.modelo.ModObjetos;
    exports dao.implementacion;
    exports config;
    exports servicios;
    exports dao.utils;
    exports dao.retrofit;

    opens gui.main;
}
