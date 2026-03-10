package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;
import modelo.IncidenciaModelo;
import modelo.ZonaDTO;
import modelo.ZonaModelo;
import vista.RegistrarIncidencia;

public class RegistrarIncidenciasControlador {

    private RegistrarIncidencia vista;
    private IncidenciaModelo modeloI;
    private ZonaModelo modeloZ;
    private String idCiudadano; // Guardamos el DNI del usuario logueado

    public RegistrarIncidenciasControlador(RegistrarIncidencia vista, IncidenciaModelo modeloIncidencia, ZonaModelo modeloZona, String idCiudadano) {
        this.vista = vista;
        this.modeloI = modeloIncidencia;
        this.modeloZ = modeloZona;
        this.idCiudadano = idCiudadano;

        if (!cargarComboZonas()) {
            JOptionPane.showMessageDialog(vista, "Error: no se pudieron cargar las zonas.");
        }
        
        // Configuramos el evento del botón
        this.vista.getBtnRegistrar().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if(registrar()) {
                	vista.dispose();
                }
            }
        });
    }

    private boolean cargarComboZonas() {
        List<ZonaDTO> zonas = modeloZ.obtenerZonas();

        //Vacio la lista para eliminar posibles errores aunque no sea necesario
        vista.getcBLocalizacion().removeAllItems();
        
        if(zonas.isEmpty()) return false;
        
        //Meto el nombre de cada zona a la lista
        for (ZonaDTO z : zonas) {
        	String nombre = z.getNombre();
            vista.getcBLocalizacion().addItem(nombre);
        }
        
        return true;
    }
    
    private boolean registrar() {
    	// Empiezo obteniendo lo que se ha introducido y hago la comprobacion pertinente
        String tipo = (String) vista.getCbTipo().getSelectedItem();
        String localizacion = (String) vista.getcBLocalizacion().getSelectedItem();
        int zona = vista.getcBLocalizacion().getSelectedIndex();
        String descripcion = vista.getTextDescripcion().getText().trim();

        if (zona == 0 || descripcion.isEmpty() || tipo.trim() == "Sin tipo") {
            JOptionPane.showMessageDialog(vista, "Por favor, complete todos los campos para crear la incidencia.");
            return false;
        }

        // Inserto los valores dados usando la función creada en el modelo
        boolean insertado = modeloI.insertarIncidencia(tipo, descripcion, localizacion, idCiudadano);

        if (insertado) {
        	String resumen = "Incidencia registrada con éxito!\n"
					   + "--- Resumen de la Operación ---\n"
					   + "• Usuario asociado: " + idCiudadano + "\n"
		               + "• Tipo de incidencia: " + tipo + " \n"
		               + "• Localización: " + localizacion + "\n"
		               + "• Descripción: " + descripcion + "\n\n"
		               + "La incidencia ha pasado al estado 'Nueva'.";
		
        	JOptionPane.showMessageDialog(vista, resumen, 
				"Confirmación de Registro",JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(vista, "Error técnico al guardar en la base de datos.");
            return false;
        }
    }
}