package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.SwingConstants;
import java.awt.Font;

public class VentanaTareasDiarias extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTextField txtFecha;
    private JTextField txtHoras;
    private JTextArea areaDescripcion;
    private JTable tablaHistorialTareas;
    private JButton btnRegistrar;
    private DefaultTableModel modeloTabla;

  
    public static void main(String[] args) {
        try {
            VentanaTareasDiarias dialog = new VentanaTareasDiarias();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public VentanaTareasDiarias() {
        setTitle("Registro Diario de Tareas - Técnico");
        setBounds(100, 100, 550, 500);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 10));

        // panel superior del formulario de entrada
        JPanel panelFormulario = new JPanel();
        contentPanel.add(panelFormulario, BorderLayout.NORTH);
        panelFormulario.setLayout(new GridLayout(4, 2, 5, 10));

        panelFormulario.add(new JLabel("Fecha (AAAA-MM-DD):"));
        txtFecha = new JTextField();
        // Por defecto ponemos la fecha de hoy
        txtFecha.setText(LocalDate.now().toString());
        panelFormulario.add(txtFecha);

        panelFormulario.add(new JLabel("Horas dedicadas:"));
        txtHoras = new JTextField();
        panelFormulario.add(txtHoras);

        panelFormulario.add(new JLabel("Descripción de la tarea:"));
        areaDescripcion = new JTextArea();
        areaDescripcion.setLineWrap(true);
        areaDescripcion.setWrapStyleWord(true);
        JScrollPane scrollArea = new JScrollPane(areaDescripcion);
        panelFormulario.add(scrollArea);

        panelFormulario.add(new JLabel("")); // Espacio vacío para alineación
        btnRegistrar = new JButton("Registrar Tarea y Actualizar Historial");
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 11));
        panelFormulario.add(btnRegistrar);

        // Panel central
        JPanel panelHistorial = new JPanel();
        contentPanel.add(panelHistorial, BorderLayout.CENTER);
        panelHistorial.setLayout(new BorderLayout(0, 5));

        JLabel lblTituloHistorial = new JLabel("Historial de tareas registradas en esta incidencia:");
        lblTituloHistorial.setHorizontalAlignment(SwingConstants.LEFT);
        lblTituloHistorial.setFont(new Font("Tahoma", Font.ITALIC, 11));
        panelHistorial.add(lblTituloHistorial, BorderLayout.NORTH);

        // Configuración de la Tabla
        String[] columnas = {"Fecha", "Descripción", "Horas"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Evita que el usuario edite la tabla directamente
            }
        };
        
        tablaHistorialTareas = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaHistorialTareas);
        panelHistorial.add(scrollTabla, BorderLayout.CENTER);

        // Botón cerrar abajo
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        
        JButton okButton = new JButton("Cerrar");
        okButton.addActionListener(e -> dispose());
        buttonPane.add(okButton);
    }

 
    public JTextField getTxtFecha() {
    	return txtFecha;
    	}
    public JTextField getTxtHoras() {
    	return txtHoras;
    	}
    public JTextArea getAreaDescripcion() {
    	return areaDescripcion;
    	}
    public JButton getBtnRegistrar() {
    	return btnRegistrar; 
    	}
    public JTable getTablaHistorialTareas() { 
    	return tablaHistorialTareas; 
    	}
    public DefaultTableModel getModeloTabla() { 
    	return modeloTabla; 
    	}
}
