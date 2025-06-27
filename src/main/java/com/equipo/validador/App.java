package com.equipo.validador;

import java.util.logging.Logger;
import java.util.logging.Level;

public class App {
    private static final Logger logger = Logger.getLogger(App.class.getName());
    
    public static void main(String[] args) {
        App app = new App();
        app.procesarUsuario();
    }
    
    /**
     * Método público que procesa el usuario - más fácil de testear
     */
    public void procesarUsuario() {
        String usuario = obtenerUsuario();
        validarYLogearUsuario(usuario);
    }
    
    /**
     * Método que obtiene el usuario - se puede sobrescribir en tests
     */
    protected String obtenerUsuario() {
        return System.getenv("APP_USER");
    }
    
    /**
     * Lógica principal de validación - método público para testing directo
     */
    public void validarYLogearUsuario(String usuario) {
        if ("admin".equals(usuario)) {
            logger.info("¡Bienvenido administrador!");
        } else {
            logger.log(Level.WARNING, "Usuario no autorizado o variable de entorno no definida");
        }
    }
    
    /**
     * Getter para el logger - útil para tests
     */
    public Logger getLogger() {
        return logger;
    }
}