module ModuloCliente {
    //needed for JavaFX
    requires javafx.controls;
    requires javafx.fxml;
    requires org.yaml.snakeyaml;
    requires transitive lombok;
    requires java.logging;

    requires org.apache.logging.log4j;
    requires io.vavr;

    //requires java.validation;          --module-path  ${PATH_TO_FX} --add-modules javafx.controls,javafx.fxml


    //requires javafx.swing;

    //needed for HTTP
    // requires unirest.java;

    //needed for JSON
    requires java.sql;

    //needed for JavaFX
}
