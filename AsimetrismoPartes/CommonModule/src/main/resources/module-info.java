module ModuloCommon {
    requires lombok;

    //requires java.validation;          --module-path  ${PATH_TO_FX} --add-modules javafx.controls,javafx.fxml

    exports notas.CommonModule.modelo;
    exports notas.CommonModule.modeloDTO;
    exports notas.CommonModule.errores;
    exports notas.CommonModule.constantes;
}
