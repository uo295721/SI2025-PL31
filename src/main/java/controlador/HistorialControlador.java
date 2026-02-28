package controlador;

import java.util.List;
import modelo.IncidenciaModelo;
import modelo.OperadorModelo; 
import modelo.HistorialDTO;
import vista.VentanaHistorial;

public class HistorialControlador {

    private VentanaHistorial vista;
    private IncidenciaModelo modeloIncidencia;

    public HistorialControlador(VentanaHistorial vista, IncidenciaModelo modelo, int idIncidencia) {
        this.vista = vista;
        this.modeloIncidencia = modelo;

        this.cargarHistorialEspecifico(idIncidencia);
    }

    public void cargarHistorialEspecifico(int idIncidencia) {
        List<HistorialDTO> historial = modeloIncidencia.obtenerHistorialIncidencia(idIncidencia);
        vista.rellenarTablaHistorial(historial);
    }
}