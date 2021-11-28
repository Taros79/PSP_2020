module ModuloServidor {
    requires io.vavr;
    requires jakarta.jakartaee.api;
    requires lombok;
    requires org.apache.logging.log4j;
    requires Commons;

    opens GID.ModuloServidor.dao;
    opens GID.ModuloServidor.EE.rest;
    opens GID.ModuloServidor.EE.errores;
    opens GID.ModuloServidor.servicios;

    exports GID.ModuloServidor.dao;
    exports GID.ModuloServidor.EE.rest;
    exports GID.ModuloServidor.EE.errores;
    exports GID.ModuloServidor.servicios;
}
