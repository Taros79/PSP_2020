module ModuloCliente {
    //needed for JavaFX
    requires javafx.controls;
    requires javafx.fxml;
    requires org.yaml.snakeyaml;
    requires transitive lombok;

    requires org.apache.logging.log4j;
    requires io.vavr;
    requires java.sql;
    requires spring.tx;
    requires spring.jdbc;
    requires jakarta.inject.api;
    requires com.zaxxer.hikari;
    requires java.annotation;
    requires jakarta.enterprise.cdi.api;
    requires com.google.common;
    requires org.bouncycastle.provider;


    //requires java.validation;          --module-path  ${PATH_TO_FX} --add-modules javafx.controls,javafx.fxml

    opens gui;
    opens gui.controllers to javafx.fxml;
    opens gui.utils;
    opens dao;
    opens dao.jdbc;
    opens dao.modelo;
    opens config;
    opens servicios;

    exports gui;
    exports gui.controllers;
    exports gui.utils;
    exports dao;
    exports dao.jdbc;
    exports dao.modelo;
    exports config;
    exports servicios;
}
