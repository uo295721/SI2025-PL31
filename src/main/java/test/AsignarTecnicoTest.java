package test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import modelo.IncidenciaModelo;
import java.util.ArrayList;
import java.util.List;

public class AsignarTecnicoTest {
    private IncidenciaModelo modelo;

    @Before
    public void setUp() {
        modelo = new IncidenciaModelo();
    }

    @Test
    //Test T1
    public void testAsignacionSimpleValida() {
        List<String> tecnicos = new ArrayList<>();
        tecnicos.add("T5");
        boolean exito = modelo.asignarVariosTecnicos(108, tecnicos, "omar.operador@oviedo.es");
        assertTrue("Debería permitir asignar un técnico si cumplen requisitos", exito);
    }
    
    @Test
    //Test T2
    public void testAsignacionMultipleValida() {
        List<String> tecnicos = new ArrayList<>();
        tecnicos.add("T2");
        tecnicos.add("T4");
        boolean exito = modelo.asignarVariosTecnicos(107, tecnicos, "omar.operador@oviedo.es");
        assertTrue("Debería permitir asignar varios técnicos si cumplen requisitos", exito);
    }

    @Test
    //Test T3
    public void testFalloPorCargaExcedida() {
        List<String> tecnicos = new ArrayList<>();
        tecnicos.add("T1");
        
        boolean exito = modelo.asignarVariosTecnicos(101, tecnicos, "omar.operador@oviedo.es");
        assertFalse("No debe permitir la asignación si el técnico está saturado", exito);
    }

    @Test
    //Test T4
    public void testFalloPorEstadoIncorrecto() {
        List<String> tecnicos = new ArrayList<>();
        tecnicos.add("T5");
        boolean exito = modelo.asignarVariosTecnicos(105, tecnicos, "omar.operador@oviedo.es");
        assertFalse("Solo se pueden asignar técnicos a incidencias validadas", exito);
    }
    
    @Test
    //Test T5
    public void testFalloPorTipoIncorrecto() {
        List<String> tecnicos = new ArrayList<>();
        tecnicos.add("T5");
        boolean exito = modelo.asignarVariosTecnicos(109, tecnicos, "omar.operador@oviedo.es");
        assertFalse("Solo pueden asignar técnicos a incidencias que compartan especialidad", exito);
    }
}