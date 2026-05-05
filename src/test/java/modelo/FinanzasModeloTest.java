package modelo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import util.Database;

class FinanzasModeloTest {

    private IncidenciaModelo modelo;
    private Database db = new Database();
    private final String DNI_TEST = "71000000X";
    private final String FECHA_TEST = "2026-05-05";

    @BeforeEach
    void setUp() throws Exception {
        modelo = new IncidenciaModelo();
        
        db.executeUpdate("DELETE FROM TareaDiaria");
        db.executeUpdate("DELETE FROM Historial");
        db.executeUpdate("DELETE FROM Asignacion_Incidencia");
        db.executeUpdate("DELETE FROM Incidencia");
        db.executeUpdate("DELETE FROM Presupuesto");
        db.executeUpdate("DELETE FROM Usuario");
        db.executeUpdate("DELETE FROM TipoIncidencia");

        db.executeUpdate("INSERT INTO TipoIncidencia (id_tipo, nombre) VALUES (1, 'Electricidad')");
        db.executeUpdate("INSERT INTO Presupuesto (id_tipo, importe_total, importe_consumido, fecha_inicio, fecha_fin) " +
                         "VALUES (1, 5000.0, 0.0, '2026-01-01', '2026-12-31')");
    }

    // --- PROCESO: marcarComoResueltaConCoste ---

    @Test
    void testMarcarResueltaConSaldoYFechaCorrecta() {
        db.executeUpdate("INSERT INTO Incidencia (id_incidencia, id_tipo, id_ciudadano, estado, fecha) " +
                         "VALUES (100, 1, '" + DNI_TEST + "', 'Proceso', '" + FECHA_TEST + "')");
        
        boolean resultado = modelo.marcarComoResueltaConCoste(100, "TEC01", 2.0, 100.0, "Reparación de iluminación");
        assertTrue(resultado, "Debe permitir resolver con presupuesto vigente y saldo suficiente");
    }

    @Test
    void testMarcarResueltaPresupuestoExpirado() {
        db.executeUpdate("UPDATE Presupuesto SET fecha_inicio = '2025-01-01', fecha_fin = '2025-12-31' WHERE id_tipo = 1");
        db.executeUpdate("INSERT INTO Incidencia (id_incidencia, id_tipo, id_ciudadano, estado, fecha) " +
                         "VALUES (101, 1, '" + DNI_TEST + "', 'Proceso', '" + FECHA_TEST + "')");
        
        boolean resultado = modelo.marcarComoResueltaConCoste(101, "TEC01", 1.5, 50.0, "Mantenimiento preventivo");
        assertFalse(resultado, "Debe fallar si el presupuesto no está vigente");
    }

    @Test
    void testMarcarResueltaSaldoInsuficiente() {
        db.executeUpdate("INSERT INTO Incidencia (id_incidencia, id_tipo, id_ciudadano, estado, fecha) " +
                         "VALUES (102, 1, '" + DNI_TEST + "', 'Proceso', '" + FECHA_TEST + "')");
        
        boolean resultado = modelo.marcarComoResueltaConCoste(102, "TEC01", 8.0, 6000.0, "Reconstrucción total");
        assertFalse(resultado, "Debe fallar si el coste supera el presupuesto");
    }

    // --- PROCESO: archivarIncidencias ---

    @Test
    void testArchivarExito() {
        db.executeUpdate("INSERT INTO Incidencia (id_incidencia, id_tipo, id_ciudadano, estado, coste, fecha) " +
                         "VALUES (10, 1, '" + DNI_TEST + "', 'Resuelta', 50.0, '" + FECHA_TEST + "')");
        
        String resultado = modelo.archivarIncidencias(Arrays.asList(10), "responsable@oviedo.es");
        assertEquals("OK", resultado);
    }

    @Test
    void testArchivarSaldoInsuficiente() {
        db.executeUpdate("UPDATE Presupuesto SET importe_total = 10.0 WHERE id_tipo = 1");
        db.executeUpdate("INSERT INTO Incidencia (id_incidencia, id_tipo, id_ciudadano, estado, coste, fecha) " +
                         "VALUES (11, 1, '" + DNI_TEST + "', 'Resuelta', 100.0, '" + FECHA_TEST + "')");
        
        String resultado = modelo.archivarIncidencias(Arrays.asList(11), "responsable@oviedo.es");
        assertEquals("Saldo insuficiente", resultado);
    }

    @Test
    void testArchivarSinPresupuestoActivo() {
        db.executeUpdate("DELETE FROM Presupuesto");
        db.executeUpdate("INSERT INTO Incidencia (id_incidencia, id_tipo, id_ciudadano, estado, coste, fecha) " +
                         "VALUES (12, 1, '" + DNI_TEST + "', 'Resuelta', 20.0, '" + FECHA_TEST + "')");
        
        String resultado = modelo.archivarIncidencias(Arrays.asList(12), "admin@oviedo.es");
        assertEquals("No hay presupuesto activo", resultado);
    }

    // --- PROCESO: obtenerInformeEconomico ---

    @Test
    void testInformeConDatos() {
        db.executeUpdate("INSERT INTO Incidencia (id_incidencia, id_tipo, id_ciudadano, estado, fecha, coste) " +
                         "VALUES (200, 1, '" + DNI_TEST + "', 'Resuelta', '" + FECHA_TEST + "', 150.0)");
                db.executeUpdate("INSERT INTO TareaDiaria (id_incidencia, id_tecnico, fecha, descripcion_tarea, horas_dedicadas) " +
                         "VALUES (200, 'T1', '" + FECHA_TEST + "', 'Tarea test', 2.0)");
        
        List<InformeEconomicoDTO> informe = modelo.obtenerInformeEconomico();
        
        assertFalse(informe.isEmpty(), "El informe no debe estar vacío si hay datos en la BD");
        assertTrue(informe.get(0).getTotalIncidencias() > 0, "El volumen de incidencias debe ser > 0");
        assertTrue(informe.get(0).getCosteTotal() > 0, "El coste total debe ser mayor a 0.0");
    }
    
    @Test
    void testInformeSinTareas() {
        db.executeUpdate("INSERT INTO Incidencia (id_incidencia, id_tipo, id_ciudadano, estado, fecha) " +
                         "VALUES (201, 1, '" + DNI_TEST + "', 'Abierta', '" + FECHA_TEST + "')");
        
        List<InformeEconomicoDTO> informe = modelo.obtenerInformeEconomico();
        assertEquals(0.0, informe.get(0).getCosteTotal(), 0.001);
    }

    @Test
    void testInformeSinIncidencias() {
        List<InformeEconomicoDTO> informe = modelo.obtenerInformeEconomico();
        boolean vacio = informe.isEmpty() || informe.get(0).getTotalIncidencias() == 0;
        assertTrue(vacio);
    }
}