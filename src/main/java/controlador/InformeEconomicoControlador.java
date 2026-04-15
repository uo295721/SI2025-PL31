package controlador;

import java.util.List;
import modelo.IncidenciaModelo;
import modelo.InformeEconomicoDTO;
import vista.VentanaInformeEconomico;

public class InformeEconomicoControlador {

    private VentanaInformeEconomico vista;
    private IncidenciaModelo modelo;

    public InformeEconomicoControlador(VentanaInformeEconomico vista, IncidenciaModelo modelo) {
        this.vista = vista;
        this.modelo = modelo;
        
        ejecutarInforme();	
    }

    private void ejecutarInforme() {
        List<InformeEconomicoDTO> datos = modelo.obtenerInformeEconomico();
        vista.cargarDatos(datos);
    }
}