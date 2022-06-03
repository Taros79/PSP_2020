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
    requires retrofit2.adapter.rxjava3;
    requires okhttp3;
    requires retrofit2.converter.gson;
    requires retrofit2.converter.scalars;
    requires jakarta.inject.api;
    requires jakarta.enterprise.cdi.api;
    requires com.google.gson;
    requires io.reactivex.rxjava3;
    requires org.pdfsam.rxjavafx;
    requires ModuloCommon;



    //requires java.validation;          --module-path  ${PATH_TO_FX} --add-modules javafx.controls,javafx.fxml

    opens notas.ClienteModule.gui;
    opens notas.ClienteModule.gui.controllers to javafx.fxml;
    opens notas.ClienteModule.gui.utils;
    opens notas.ClienteModule.config;
    opens notas.ClienteModule.Servicios;

    exports notas.ClienteModule.gui.utils;
    exports notas.ClienteModule.gui.controllers.complements;
    exports notas.ClienteModule.gui.controllers;
    exports notas.ClienteModule.gui;
    exports notas.ClienteModule.config;
    exports notas.ClienteModule.Servicios;
    exports notas.ClienteModule.dao.utils;
    exports notas.ClienteModule.dao.retrofit;
    exports notas.ClienteModule.dao;


}
