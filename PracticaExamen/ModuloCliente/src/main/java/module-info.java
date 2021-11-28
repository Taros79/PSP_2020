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
    requires annotations;
    requires Commons;




    //requires java.validation;          --module-path  ${PATH_TO_FX} --add-modules javafx.controls,javafx.fxml

    opens GID.ModuloCliente.gui;
    opens GID.ModuloCliente.gui.main;
    opens GID.ModuloCliente.gui.controllers to javafx.fxml;
    opens GID.ModuloCliente.dao.utils;
    opens GID.ModuloCliente.config;

    exports GID.ModuloCliente.gui;
    exports GID.ModuloCliente.gui.main;
    exports GID.ModuloCliente.gui.controllers;
    exports GID.ModuloCliente.gui.utils;
    exports GID.ModuloCliente.dao.daoImplementacion;
    exports GID.ModuloCliente.config;
    exports GID.ModuloCliente.servicios;
    exports GID.ModuloCliente.dao.utils;
    exports GID.ModuloCliente.dao.retrofit;
    exports GID.ModuloCliente.servicios.serviciosImplementacion;
}
