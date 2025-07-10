package com.equipo.validador;
public class App {
    public static void main(String[] args) {
        String usuario = System.getenv("APP_USER");
        if ("admin".equals(usuario)) {
            System.out.println("¡Bienvenido administrador!");
        }
    }
}
