module Servidor {
    requires io.vavr;
    requires jakarta.jakartaee.api;
    requires lombok;
    requires org.yaml.snakeyaml;
    requires org.apache.logging.log4j;
    requires java.sql;
    requires java.naming;
    requires com.zaxxer.hikari;
    requires spring.jdbc;
    requires spring.tx;
    requires Common;
    requires log4j;
    requires jjwt.api;


    opens org.example.ModuloServidor.config;
    opens org.example.ModuloServidor.dao;
    opens org.example.ModuloServidor.EE.rest;
    opens org.example.ModuloServidor.EE.servlet;
    opens org.example.ModuloServidor.EE.servlet.mail;
    opens org.example.ModuloServidor.servicios;
    opens org.example.ModuloServidor.utils;

    exports org.example.ModuloServidor.config;
    exports org.example.ModuloServidor.dao;
    exports org.example.ModuloServidor.EE.rest;
    exports org.example.ModuloServidor.EE.servlet;
    exports org.example.ModuloServidor.EE.servlet.mail;
    exports org.example.ModuloServidor.servicios;
    exports org.example.ModuloServidor.utils;
    exports org.example.ModuloServidor.dao.jdbc;
    opens org.example.ModuloServidor.dao.jdbc;
}
