package org.example.ModuloCliente.config;

import lombok.Data;

import javax.inject.Singleton;

@Data
@Singleton
public class CacheAuthorization {

    private String user;
    private String pass;
    private String jwt;
}
