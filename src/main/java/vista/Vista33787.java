package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Vista33787 extends JFrame {
    private JTable tablaIncidencias;
    private DefaultTableModel modeloTabla;
    private JComboBox<String> cbTipos;
    private JButton btnValidar;
    private JLabel lblSesion;

    public Vista33787(String emailOperador) {
        setTitle("Clasificación de incidencias (Nueva)");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Cabecera: Sesión
        lblSesion = new JLabel(" Sesión iniciada como: " + emailOperador);
        lblSesion.setFont(new Font("Arial", Font.ITALIC, 12));
        add(lblSesion, BorderLayout.NORTH);

        // Centro: Tabla de incidencias nuevas
        String[] columnas = {"ID", "Fecha registro", "Ciudadano", "Tipo Propuesto"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaIncidencias = new JTable(modeloTabla);
        add(new JScrollPane(tablaIncidencias), BorderLayout.CENTER);

        // Inferior: Panel de clasificación
        JPanel pnlInferior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlInferior.setBorder(BorderFactory.createTitledBorder("Detalles incidencia"));

        pnlInferior.add(new JLabel("Tipo de incidencia:"));
        String[] opciones = {"Alumbrado", "Carretera", "Pelea", "Electricidad", "Otro"};
        cbTipos = new JComboBox<>(opciones);
        pnlInferior.add(cbTipos);

        btnValidar = new JButton("Validar Clasificación");
        pnlInferior.add(btnValidar);

        add(pnlInferior, BorderLayout.SOUTH);
        setLocationRelativeTo(null);
    }

    // Getters
    public JTable getTablaIncidencias() { return tablaIncidencias; }
    public JComboBox<String> getCbTipos() { return cbTipos; }
    public JButton getBtnValidar() { return btnValidar; }
}