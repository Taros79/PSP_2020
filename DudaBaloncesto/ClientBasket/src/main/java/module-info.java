module ClientBasket {
    //    JAVA FX
    requires javafx.controls;
    requires javafx.fxml;
//    LOMBOK
    requires lombok;
//    YAML
    requires org.yaml.snakeyaml;
//    LOG4J
    requires org.apache.logging.log4j;

//    GSON CONVERTER
    requires retrofit2.converter.gson;
    requires com.google.gson;
//    RETROFIT
    requires retrofit2;
    requires okhttp3;
//    VAVR
    requires io.vavr;

//    DI
    requires jakarta.enterprise.cdi.api;
    requires jakarta.inject.api;

//    Propios
    requires Common;
    requires io.reactivex.rxjava3;
    requires org.pdfsam.rxjavafx;
    requires retrofit2.adapter.rxjava3;


    exports quevedo.ClientBasket.ui;
    exports quevedo.ClientBasket.ui.controllers;
    exports quevedo.ClientBasket.ui.controllers.entrar;
    exports quevedo.ClientBasket.utils;
    exports quevedo.ClientBasket.config;
    exports quevedo.ClientBasket.dao.network.di;
    exports quevedo.ClientBasket.dao;
    exports quevedo.ClientBasket.dao.retrofit;
    exports quevedo.ClientBasket.services;
    exports quevedo.ClientBasket.ui.controllers.gestionar;
    exports quevedo.ClientBasket.ui.controllers.visualizar;
    exports quevedo.ClientBasket.dao.modelo;
    exports quevedo.ClientBasket.dao.network;


    opens quevedo.ClientBasket.ui.controllers.visualizar;
    opens quevedo.ClientBasket.ui.controllers.gestionar;
    opens quevedo.ClientBasket.ui;
    opens quevedo.ClientBasket.ui.controllers;
    opens quevedo.ClientBasket.ui.controllers.entrar;
    opens quevedo.ClientBasket.utils;

}