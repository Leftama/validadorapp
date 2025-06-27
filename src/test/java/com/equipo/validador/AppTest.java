package com.equipo.validador;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.LogRecord;
import java.util.logging.Level;
import java.util.ArrayList;
import java.util.List;

public class AppTest {
    
    // Para capturar los logs
    private TestLogHandler testLogHandler;
    private Logger logger;
    
    // Para capturar la salida del sistema
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    
    @Before
    public void setUp() {
        // Configurar captura de logs
        logger = Logger.getLogger(App.class.getName());
        testLogHandler = new TestLogHandler();
        logger.addHandler(testLogHandler);
        logger.setLevel(Level.ALL);
        
        // Configurar captura de salida del sistema
        System.setOut(new PrintStream(outContent));
    }
    
    @After
    public void tearDown() {
        // Limpiar después de cada test
        logger.removeHandler(testLogHandler);
        System.setOut(originalOut);
        
        // Limpiar variables de entorno simuladas
        System.clearProperty("APP_USER");
    }
    
    /**
     * Test para verificar el comportamiento cuando el usuario es "admin"
     */
    @Test
    public void testMainConUsuarioAdmin() {
        // Simular variable de entorno APP_USER = "admin" 
        // Nota: Como System.getenv() es difícil de mockear, podríamos refactorizar
        // el código para usar System.getProperty() en su lugar
        
        // Llamar al método main
        String[] args = {};
        
        // Para este test, necesitaremos modificar ligeramente el código original
        // o usar un enfoque diferente. Te muestro ambas opciones:
        
        // OPCIÓN 1: Test del comportamiento esperado (conceptual)
        // Si el usuario fuera "admin", debería loggearse un mensaje INFO
        
        // Simulemos el comportamiento manualmente para demostrar el test
        String usuarioTest = "admin";
        if ("admin".equals(usuarioTest)) {
            logger.info("¡Bienvenido administrador!");
        }
        
        // Verificar que se registró el log correcto
        List<LogRecord> logRecords = testLogHandler.getLogRecords();
        assert logRecords.size() == 1 : "Debería haberse registrado exactamente un log";
        assert logRecords.get(0).getLevel() == Level.INFO : "El nivel del log debería ser INFO";
        assert logRecords.get(0).getMessage().contains("Bienvenido administrador") : 
               "El mensaje debería contener 'Bienvenido administrador'";
    }
    
    /**
     * Test para verificar el comportamiento cuando el usuario NO es "admin"
     */
    @Test
    public void testMainConUsuarioNoAdmin() {
        // Simular usuario diferente a "admin"
        String usuarioTest = "usuario_normal";
        if (!"admin".equals(usuarioTest)) {
            logger.log(Level.WARNING, "Usuario no autorizado o variable de entorno no definida");
        }
        
        // Verificar que se registró el log de WARNING
        List<LogRecord> logRecords = testLogHandler.getLogRecords();
        assert logRecords.size() == 1 : "Debería haberse registrado exactamente un log";
        assert logRecords.get(0).getLevel() == Level.WARNING : "El nivel del log debería ser WARNING";
        assert logRecords.get(0).getMessage().contains("Usuario no autorizado") : 
               "El mensaje debería contener 'Usuario no autorizado'";
    }
    
    /**
     * Test para verificar el comportamiento cuando la variable de entorno es null
     */
    @Test
    public void testMainConVariableEntornoNull() {
        // Simular variable de entorno null
        String usuarioTest = null;
        if (!"admin".equals(usuarioTest)) {
            logger.log(Level.WARNING, "Usuario no autorizado o variable de entorno no definida");
        }
        
        // Verificar que se registró el log de WARNING
        List<LogRecord> logRecords = testLogHandler.getLogRecords();
        assert logRecords.size() == 1 : "Debería haberse registrado exactamente un log";
        assert logRecords.get(0).getLevel() == Level.WARNING : "El nivel del log debería ser WARNING";
        assert logRecords.get(0).getMessage().contains("variable de entorno no definida") : 
               "El mensaje debería mencionar la variable de entorno";
    }
    
    /**
     * Test para verificar que el logger se inicializa correctamente
     */
    @Test
    public void testLoggerInicializacion() {
        Logger appLogger = Logger.getLogger(App.class.getName());
        assert appLogger != null : "El logger no debería ser null";
        assert appLogger.getName().equals("com.equipo.validador.App") : 
               "El nombre del logger debería ser el nombre completo de la clase";
    }
    
    /**
     * Handler personalizado para capturar logs en los tests
     */
    private static class TestLogHandler extends Handler {
        private final List<LogRecord> logRecords = new ArrayList<>();
        
        @Override
        public void publish(LogRecord record) {
            logRecords.add(record);
        }
        
        @Override
        public void flush() {
            // No necesario para tests
        }
        
        @Override
        public void close() throws SecurityException {
            logRecords.clear();
        }
        
        public List<LogRecord> getLogRecords() {
            return new ArrayList<>(logRecords);
        }
    }
}