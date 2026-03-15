package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VistaRechazoOperador extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTable tablaIncidencias;
    private DefaultTableModel modeloTabla;
    private JTextArea txtMotivo;
    private JButton btnConfirmar, btnCancelar;
    private JLabel lblSesion, lblAreaRechazo;
    private JPanel pnlRechazo;

    public VistaRechazoOperador(String nombreOperador) {
        setTitle("Gestión de Incidencias - Operador");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));

        // Cabecera
        JPanel pnlNorte = new JPanel(new GridLayout(2, 1));
        pnlNorte.setBorder(BorderFactory.createEmptyBorder(10, 20, 5, 20));
        lblSesion = new JLabel("Sesión iniciada como: " + nombreOperador);
        lblSesion.setFont(new Font("Arial", Font.BOLD, 14));
        pnlNorte.add(lblSesion);
        pnlNorte.add(new JLabel("Incidencias en estado: Nueva (Pendientes de validar o rechazar)"));
        add(pnlNorte, BorderLayout.NORTH);

        // Tabla Central
        String[] columnas = {"ID", "Título", "Responsable", "Categoría", "Acción"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            private static final long serialVersionUID = 1L;
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaIncidencias = new JTable(modeloTabla);
        tablaIncidencias.setRowHeight(30);
        tablaIncidencias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(tablaIncidencias), BorderLayout.CENTER);

        // Panel de Gestión de Rechazo (Sur)
        pnlRechazo = new JPanel(new BorderLayout(10, 10));
        pnlRechazo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 20, 20, 20),
                BorderFactory.createTitledBorder("Área de gestión de rechazo")
        ));
        pnlRechazo.setVisible(false); // Oculto inicialmente

        lblAreaRechazo = new JLabel("Motivo rechazo incidencia #: ");
        lblAreaRechazo.setFont(new Font("Arial", Font.BOLD, 13));
        
        txtMotivo = new JTextArea(4, 50);
        txtMotivo.setLineWrap(true);
        txtMotivo.setWrapStyleWord(true);
        
        JPanel pnlBotonesAccion = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnConfirmar = new JButton("Confirmar y notificar");
        btnConfirmar.setBackground(new Color(190, 230, 190));
        btnCancelar = new JButton("Cancelar");
        pnlBotonesAccion.add(btnConfirmar);
        pnlBotonesAccion.add(btnCancelar);

        pnlRechazo.add(lblAreaRechazo, BorderLayout.NORTH);
        pnlRechazo.add(new JScrollPane(txtMotivo), BorderLayout.CENTER);
        pnlRechazo.add(pnlBotonesAccion, BorderLayout.SOUTH);

        add(pnlRechazo, BorderLayout.SOUTH);
        setLocationRelativeTo(null);
    }

    public void mostrarPanelRechazo(int id) {
        lblAreaRechazo.setText("Área de gestión de rechazo - Incidencia: #" + id);
        pnlRechazo.setVisible(true);
        txtMotivo.setText("");
        txtMotivo.requestFocus();
        revalidate();
        repaint();
    }

    public void ocultarPanelRechazo() {
        pnlRechazo.setVisible(false);
        txtMotivo.setText("");
        revalidate();
        repaint();
    }

    // Getters
    public JTable getTablaIncidencias() { return tablaIncidencias; }
    public DefaultTableModel getModeloTabla() { return modeloTabla; }
    public JTextArea getTxtMotivo() { return txtMotivo; }
    public JButton getBtnConfirmar() { return btnConfirmar; }
    public JButton getBtnCancelar() { return btnCancelar; }
}