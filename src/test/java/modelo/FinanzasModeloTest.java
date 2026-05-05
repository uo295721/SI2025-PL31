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

    @BeforeEach
    void setUp() throws Exception {
        modelo = new IncidenciaModelo();
        
        db.executeUpdate("DELETE FROM TareaDiaria");
        db.executeUpdate("DELETE FROM Historial");
        db.executeUpdate("DELETE FROM Incidencia");
        db.executeUpdate("DELETE FROM Presupuesto");
        db.executeUpdate("DELETE FROM TipoIncidencia");

        db.executeUpdate("INSERT INTO TipoIncidencia (id_tipo, nombre) VALUES (1, 'Vialidad')");
        db.executeUpdate("INSERT INTO Presupuesto (id_tipo, importe_total, importe_consumido, fecha_inicio, fecha_fin) " +
                         "VALUES (1, 1000.0, 0.0, '2026-01-01', '2026-12-31')");
    }


 // --- PROCESO: marcarComoResueltaConCoste ---

    @Test
    void testMarcarResueltaConSaldoYFechaCorrecta() {
        db.executeUpdate("INSERT INTO Incidencia (id_incidencia, id_tipo, estado, fecha) VALUES (100, 1, 'Proceso', '2026-05-01')");
        
        boolean resultado = modelo.marcarComoResueltaConCoste(100, "TEC01", 2.0, 100.0, "Reparación de iluminación");
        assertTrue(resultado, "Debe permitir resolver con presupuesto vigente y saldo suficiente");
    }

    @Test
    void testMarcarResueltaPresupuestoExpirado() {
        db.executeUpdate("UPDATE Presupuesto SET fecha_inicio = '2025-01-01', fecha_fin = '2025-12-31' WHERE id_tipo = 1");
        db.executeUpdate("INSERT INTO Incidencia (id_incidencia, id_tipo, estado, fecha) VALUES (101, 1, 'Proceso', '2026-05-01')");
        
        boolean resultado = modelo.marcarComoResueltaConCoste(101, "TEC01", 1.5, 50.0, "Mantenimiento preventivo");
        assertFalse(resultado, "Debe fallar si el presupuesto no está vigente para la fecha actual[cite: 2]");
    }

    @Test
    void testMarcarResueltaSaldoInsuficiente() {
        db.executeUpdate("INSERT INTO Incidencia (id_incidencia, id_tipo, estado, fecha) VALUES (102, 1, 'Proceso', '2026-05-01')");
        
        boolean resultado = modelo.marcarComoResueltaConCoste(102, "TEC01", 8.0, 5000.0, "Reconstrucción total");
        assertFalse(resultado, "Debe fallar si el importe supera el saldo disponible del presupuesto[cite: 2]");
    }

 // --- PROCESO: archivarIncidencias ---

    @Test
    void testArchivarExito() {
        db.executeUpdate("INSERT INTO Incidencia (id_incidencia, id_tipo, estado, coste) VALUES (10, 1, 'Resuelta', 50.0)");
        
        String resultado = modelo.archivarIncidencias(Arrays.asList(10), "responsable@oviedo.es");
        
        assertEquals("OK", resultado, "Debe archivar correctamente con solvencia económica[cite: 1]");
    }

    @Test
    void testArchivarSaldoInsuficiente() {
        db.executeUpdate("UPDATE Presupuesto SET importe_total = 10.0 WHERE id_tipo = 1");
        db.executeUpdate("INSERT INTO Incidencia (id_incidencia, id_tipo, estado, coste) VALUES (11, 1, 'Resuelta', 100.0)");
        
        String resultado = modelo.archivarIncidencias(Arrays.asList(11), "responsable@oviedo.es");
        
        assertEquals("Saldo insuficiente", resultado, "Debe detectar la falta de fondos en la partida");
    }

    @Test
    void testArchivarSinPresupuestoActivo() {
        db.executeUpdate("DELETE FROM Presupuesto");
        db.executeUpdate("INSERT INTO Incidencia (id_incidencia, id_tipo, estado, coste) VALUES (12, 1, 'Resuelta', 20.0)");
        
        String resultado = modelo.archivarIncidencias(Arrays.asList(12), "admin@oviedo.es");
        
        assertEquals("No hay presupuesto activo", resultado, "Debe detectar la ausencia de presupuesto vigente[cite: 2]");
    }

 // --- PROCESO: obtenerInformeEconomico ---

    @Test
    void testInformeConDatos() {
        db.executeUpdate("INSERT INTO Incidencia (id_incidencia, id_tipo, estado) VALUES (200, 1, 'Resuelta')");
        db.executeUpdate("INSERT INTO TareaDiaria (id_incidencia, id_tecnico, fecha, coste_tarea) VALUES (200, 'T1', '2026-05-02', 150.0)");
        
        List<InformeEconomicoDTO> informe = modelo.obtenerInformeEconomico();
        
        assertFalse(informe.isEmpty(), "El informe no debe estar vacío si hay datos en la BD[cite: 1]");
        assertTrue(informe.get(0).getTotalIncidencias() > 0 && informe.get(0).getCosteTotal() > 0, 
                   "Debe reflejar el número de incidencias y costes positivos");
    }

    @Test
    void testInformeSinTareas() {
        db.executeUpdate("INSERT INTO Incidencia (id_incidencia, id_tipo, estado) VALUES (201, 1, 'Abierta')");
        
        List<InformeEconomicoDTO> informe = modelo.obtenerInformeEconomico();
        
        assertEquals(0.0, informe.get(0).getCosteTotal(), 0.001, "El coste total debe ser 0.0 si no hay tareas[cite: 1]");
        assertTrue(informe.get(0).getTotalIncidencias() > 0, "El total de incidencias debe ser mayor a 0");
    }

    @Test
    void testInformeSinIncidencias() {
        List<InformeEconomicoDTO> informe = modelo.obtenerInformeEconomico();
        
        boolean sinActividad = informe.isEmpty() || informe.stream().noneMatch(i -> i.getTotalIncidencias() > 0);
        assertTrue(sinActividad, "No deben aparecer incidencias contadas para categorías vacías[cite: 2]");
    }
}