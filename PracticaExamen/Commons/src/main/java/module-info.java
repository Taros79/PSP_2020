module Commons {
    //needed for JavaFX
    requires transitive lombok;
    requires java.logging;
    requires org.apache.logging.log4j;
    requires io.vavr;

    //requires java.validation;          --module-path  ${PATH_TO_FX} --add-modules javafx.controls,javafx.fxml

    requires java.sql;

    exports GID.Commons.dao.modelo;
    exports GID.Commons.EE.errores;
    exports GID.Commons.EE.utils;
}
