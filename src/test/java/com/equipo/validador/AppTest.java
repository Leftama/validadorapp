package com.equipo.validador;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import static org.junit.Assert.*;

import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.LogRecord;
import java.util.logging.Level;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class AppTest {
    
    private App app;
    private TestLogHandler testLogHandler;
    private Logger logger;
    
    @Before
    public void setUp() {
        app = new App();
        logger = app.getLogger();
        
        // Configurar handler de test para capturar logs
        testLogHandler = new TestLogHandler();
        logger.addHandler(testLogHandler);
        logger.setLevel(Level.ALL);
        
        // Remover otros handlers para evitar ruido en consola durante tests
        Handler[] handlers = logger.getHandlers();
        for (Handler handler : handlers) {
            if (handler != testLogHandler) {
                logger.removeHandler(handler);
            }
        }
    }
    
    @After
    public void tearDown() {
        if (testLogHandler != null) {
            logger.removeHandler(testLogHandler);
        }
    }
    
    /**
     * Test: Usuario admin debe generar log INFO con mensaje de bienvenida
     */
    @Test
    public void testValidarUsuarioAdmin() {
        // Ejecutar
        app.validarYLogearUsuario("admin");
        
        // Verificar
        List<LogRecord> logs = testLogHandler.getLogRecords();
        assertEquals("Debería haber exactamente 1 log", 1, logs.size());
        
        LogRecord logRecord = logs.get(0);
        assertEquals("El nivel debería ser INFO", Level.INFO, logRecord.getLevel());
        assertTrue("El mensaje debería contener 'Bienvenido administrador'", 
                   logRecord.getMessage().contains("Bienvenido administrador"));
    }
    
    /**
     * Test: Verificar que se pueden procesar múltiples usuarios
     */
    @Test
    public void testMultiplesValidaciones() {
        // Ejecutar múltiples validaciones
        app.validarYLogearUsuario("admin");
        app.validarYLogearUsuario("usuario1");
        app.validarYLogearUsuario("admin");
        
        // Verificar
        List<LogRecord> logs = testLogHandler.getLogRecords();
        assertEquals("Debería haber exactamente 3 logs", 3, logs.size());
        
        // Verificar primer log (admin)
        assertEquals("Primer log debería ser INFO", Level.INFO, logs.get(0).getLevel());
        
        // Verificar segundo log (usuario1)
        assertEquals("Segundo log debería ser WARNING", Level.WARNING, logs.get(1).getLevel());
        
        // Verificar tercer log (admin nuevamente)
        assertEquals("Tercer log debería ser INFO", Level.INFO, logs.get(2).getLevel());
    }
    
    /**
     * Test: Verificar que el logger se inicializa correctamente
     */
    @Test
    public void testLoggerInicializacion() {
        Logger appLogger = app.getLogger();
        assertNotNull("El logger no debería ser null", appLogger);
        assertEquals("El nombre del logger debería ser correcto", 
                     "com.equipo.validador.App", appLogger.getName());
    }
    
    /**
     * Test: Verificar el método procesarUsuario (integración)
     */
    @Test
    public void testProcesarUsuario() {
        // Crear una versión extendida para controlar el usuario
        App testApp = new App() {
            @Override
            protected String obtenerUsuario() {
                return "admin"; // Simular que la variable de entorno es "admin"
            }
        };
        
        // Configurar logger para esta instancia
        Logger testLogger = testApp.getLogger();
        TestLogHandler testHandler = new TestLogHandler();
        testLogger.addHandler(testHandler);
        
        // Ejecutar
        testApp.procesarUsuario();
        
        // Verificar
        List<LogRecord> logs = testHandler.getLogRecords();
        assertEquals("Debería haber exactamente 1 log", 1, logs.size());
        assertEquals("El nivel debería ser INFO", Level.INFO, logs.get(0).getLevel());
        
        // Limpiar
        testLogger.removeHandler(testHandler);
    }
    
    /**
     * Handler personalizado para capturar logs durante las pruebas
     */
    private static class TestLogHandler extends Handler {
        private final List<LogRecord> logRecords = new ArrayList<>();
        
        @Override
        public void publish(LogRecord logRecord) {
            logRecords.add(logRecord);
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
    
    /**
     * Clase de test parametrizado para usuarios no autorizados
     * Reemplaza los 3 tests individuales: testValidarUsuarioNoAdmin, testValidarUsuarioNull, testValidarUsuarioVacio
     */
    @RunWith(Parameterized.class)
    public static class UsuarioNoAutorizadoTest {
        
        private String usuario;
        private App app;
        private TestLogHandler testLogHandler;
        private Logger logger;
        
        public UsuarioNoAutorizadoTest(String usuario) {
            this.usuario = usuario;
        }
        
        @Parameters(name = "Usuario no autorizado: {0}")
        public static Iterable<Object[]> datos() {
            return Arrays.asList(new Object[][]{
                {"usuario_normal"},
                {null},
                {""}
            });
        }
        
        @Before
        public void setUp() {
            app = new App();
            logger = app.getLogger();
            
            // Configurar handler de test para capturar logs
            testLogHandler = new TestLogHandler();
            logger.addHandler(testLogHandler);
            logger.setLevel(Level.ALL);
            
            // Remover otros handlers para evitar ruido en consola durante tests
            Handler[] handlers = logger.getHandlers();
            for (Handler handler : handlers) {
                if (handler != testLogHandler) {
                    logger.removeHandler(handler);
                }
            }
        }
        
        @After
        public void tearDown() {
            if (testLogHandler != null) {
                logger.removeHandler(testLogHandler);
            }
        }
        
        @Test
        public void testValidarUsuarioNoAutorizado() {
            // Ejecutar
            app.validarYLogearUsuario(usuario);
            
            // Verificar
            List<LogRecord> logs = testLogHandler.getLogRecords();
            assertEquals("Debería haber exactamente 1 log", 1, logs.size());
            
            LogRecord logRecord = logs.get(0);
            assertEquals("El nivel debería ser WARNING", Level.WARNING, logRecord.getLevel());
            assertTrue("El mensaje debería contener 'Usuario no autorizado'", 
                       logRecord.getMessage().contains("Usuario no autorizado"));
        }
    }
}