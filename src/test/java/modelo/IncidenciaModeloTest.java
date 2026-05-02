package modelo;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

public class IncidenciaModeloTest {
    
    private IncidenciaModelo modelo;

    @Before
    public void setUp() {
        modelo = new IncidenciaModelo();
    }


    @Test
    public void testRegistrarTareaValida() {
        int idInci = 101; 
        String desc = "Tarea Unica " + System.currentTimeMillis(); 
        modelo.registrarTareaDiaria(idInci, "T1", "2023-01-01", desc, 2.5);
        
        List<Object[]> tareas = modelo.getTareasPorIncidencia(idInci);
        
        boolean encontrada = false;
        for(Object[] t : tareas) {
            if(t[1].toString().equals(desc)) {
                encontrada = true;
                break;
            }
        }
        assertTrue("La tarea recién insertada debería aparecer en la lista", encontrada);
    }

    @Test
    public void testRegistrarTareaIdInexistente() {
        // Usamos un ID que no existe en la base de datos
        int idFalso = 999999; 
        modelo.registrarTareaDiaria(idFalso, "T1", "2024-01-01", "No debe guardarse", 1.0);
        
        List<Object[]> tareas = modelo.getTareasPorIncidencia(idFalso);
        assertTrue("La lista debe estar vacía porque el ID no existe", tareas.isEmpty());
    }

    @Test
    public void testRegistrarTareaHorasNegativas() {
        // Inserto un valor de horas negativo
        int idInci = 101;
        modelo.registrarTareaDiaria(idInci, "T1", "2026-02-25", "Prueba horas negativas", -5.0);
        
        List<Object[]> tareas = modelo.getTareasPorIncidencia(idInci);
        for(Object[] tarea : tareas) {
            double h = Double.parseDouble(tarea[2].toString());
            assertTrue("Ninguna tarea debería tener horas negativas en la BD", h >= 0);
        }
    }

    @Test
    public void testRegistrarTareaFechaFutura() {
        int idInci = 101;
        String fechaFutura = "2099-12-31";
        modelo.registrarTareaDiaria(idInci, "T1", fechaFutura, "Tarea fantasma", 1.0);
        
        List<Object[]> tareas = modelo.getTareasPorIncidencia(idInci);
        
        // Verificamos que ninguna de las tareas devueltas tenga la fecha futura
        for(Object[] t : tareas) {
            assertNotEquals("No debería existir la fecha futura en la base de datos", 
                            fechaFutura, t[0].toString());
        }
    }

    @Test
    public void testGetTareasExistentes() {
        // Incidencia con múltiples tareas , es este caso escogemos la 107
        List<Object[]> tareas = modelo.getTareasPorIncidencia(107);
        assertFalse("Debería recuperar las tareas del historial", tareas.isEmpty());
        assertEquals("Barnizar banco", tareas.get(0)[1].toString());
    }

    @Test
    public void testGetTareasInexistente() {
        // Tarea con un ID que no existe
        List<Object[]> tareas = modelo.getTareasPorIncidencia(8888);
        assertTrue("Debería devolver lista vacía", tareas.isEmpty());
    }


    @Test
    public void testGetPrecioTecnicoExistente() {
        // Introduzco un técnico que si existe, por ejemplo: Ana
        double precio = modelo.getPrecioHoraTecnico("ana.tecnico@oviedo.es");
        assertEquals("El precio por defecto es 9.0", 9.0, precio, 0.01);
    }

    @Test
    public void testGetPrecioTecnicoInexistente() {
        // Introduzco un técnico que no existe, por ejemplo: juanjo
        double precio = modelo.getPrecioHoraTecnico("juanjo@oviedo.es");
        assertEquals(0.0, precio, 0.01);
    }
}