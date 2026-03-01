package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Vista33787 extends JFrame {
    
	private static final long serialVersionUID = 1L;
	private JTable tablaIncidencias;
    private DefaultTableModel modeloTabla;
    private JComboBox<String> cbTipos;
    private JButton btnValidar;
    private JLabel lblDetalleId;

    public Vista33787(String emailOperador) {
        setTitle("Clasificación de incidencias (Nueva)");
        setSize(750, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Cabecera: Sesión
        JPanel pnlNorte = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlNorte.add(new JLabel("Sesión iniciada como: " + emailOperador));
        add(pnlNorte, BorderLayout.NORTH);

        // Centro: Tabla de Incidencias
        String[] columnas = {"ID", "Fecha registro", "Ciudadano", "Tipo Propuesto"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaIncidencias = new JTable(modeloTabla);
        add(new JScrollPane(tablaIncidencias), BorderLayout.CENTER);

        // Sur: Panel de detalles y botón
        JPanel pnlSur = new JPanel();
        pnlSur.setLayout(new BoxLayout(pnlSur, BoxLayout.Y_AXIS));
        pnlSur.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        lblDetalleId = new JLabel("Detalles incidencia: # ---");
        lblDetalleId.setFont(new Font("Arial", Font.BOLD, 14));
        
        JPanel pnlAccion = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlAccion.add(new JLabel("Tipo de incidencia: "));
        cbTipos = new JComboBox<>(new String[]{"Alumbrado", "Carretera", "Pelea", "Electricidad", "Otro"});
        btnValidar = new JButton("Validar Clasificación");
        
        pnlAccion.add(cbTipos);
        pnlAccion.add(btnValidar);

        pnlSur.add(lblDetalleId);
        pnlSur.add(Box.createVerticalStrut(10));
        pnlSur.add(pnlAccion);
        add(pnlSur, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    // Getters para el controlador
    public JTable getTablaIncidencias() { return tablaIncidencias; }
    public JComboBox<String> getCbTipos() { return cbTipos; }
    public JButton getBtnValidar() { return btnValidar; }
    public void setLblDetalleId(String id) { lblDetalleId.setText("Detalles incidencia: #" + id); }
}