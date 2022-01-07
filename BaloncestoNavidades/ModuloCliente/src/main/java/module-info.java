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
    requires okhttp3;
    requires retrofit2.adapter.rxjava2;
    requires retrofit2.converter.gson;
    requires retrofit2.converter.scalars;
    requires gson;
    requires jakarta.inject.api;
    requires jakarta.enterprise.cdi.api;

    requires org.pdfsam.rxjavafx;
    requires Common;
    requires org.jetbrains.annotations;

    //requires java.validation;          --module-path  ${PATH_TO_FX} --add-modules javafx.controls,javafx.fxml

    opens org.example.ModuloCliente.gui;
    opens org.example.ModuloCliente.gui.utils;
    opens org.example.ModuloCliente.gui.controllers to javafx.fxml;
    opens org.example.ModuloCliente.dao.utils;
    opens org.example.ModuloCliente.dao.retrofit;
    opens org.example.ModuloCliente.config;
    opens org.example.ModuloCliente.servicios;

    exports org.example.ModuloCliente.gui;
    exports org.example.ModuloCliente.gui.utils;
    exports org.example.ModuloCliente.gui.controllers;
    exports org.example.ModuloCliente.dao;
    exports org.example.ModuloCliente.dao.utils;
    exports org.example.ModuloCliente.dao.retrofit;
    exports org.example.ModuloCliente.config;
    exports org.example.ModuloCliente.servicios;
}
