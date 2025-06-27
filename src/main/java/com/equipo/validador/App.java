package com.equipo.validador;

import java.util.logging.Logger;
import java.util.logging.Level;

public class App {
    private static final Logger logger = Logger.getLogger(App.class.getName());
    
    public static void main(String[] args) {
        String usuario = System.getenv("APP_USER");
        if ("admin".equals(usuario)) {
            logger.info("¡Bienvenido administrador!");
        } else {
            logger.log(Level.WARNING, "Usuario no autorizado o variable de entorno no definida");
        }
    }
}