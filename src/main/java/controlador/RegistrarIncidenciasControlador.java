package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import modelo.IncidenciaModelo;
import vista.RegistrarIncidencia;

public class RegistrarIncidenciasControlador {

    private RegistrarIncidencia vista;
    private IncidenciaModelo modelo;
    private String idCiudadano; // Guardamos el DNI del usuario logueado

    public RegistrarIncidenciasControlador(RegistrarIncidencia vista, IncidenciaModelo modelo, String idCiudadano) {
        this.vista = vista;
        this.modelo = modelo;
        this.idCiudadano = idCiudadano;

        // Configuramos el evento del botón
        this.vista.getBtnRegistrar().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registrar();
            }
        });
    }

    private void registrar() {
    	// Empiezo obteniendo lo que se ha introducido y hago la comprobacion pertinente
        String tipo = (String) vista.getCbTipo().getSelectedItem();
        String localizacion = vista.getTextLocalizacion().getText().trim();
        String descripcion = vista.getTextDescripcion().getText().trim();

        if (localizacion.isEmpty() || descripcion.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Por favor, complete todos los campos para crear la incidencia.");
            return;
        }

        // Inserto los valores dados usando la función creada en el modelo modelo
        boolean insertado = modelo.insertarIncidencia(tipo, descripcion, localizacion, idCiudadano);

        if (insertado) {
            JOptionPane.showMessageDialog(vista, "Incidencia registrada correctamente.");
            vista.dispose();
        } else {
            JOptionPane.showMessageDialog(vista, "Error técnico al guardar en la base de datos.");
        }
    }
}