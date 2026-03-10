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
        // Configuración básica de la ventana
        setTitle("Gestión de Validación y Rechazo de Incidencias");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));

        // --- PANEL NORTE: Cabecera ---
        JPanel pnlNorte = new JPanel();
        pnlNorte.setLayout(new BoxLayout(pnlNorte, BoxLayout.Y_AXIS));
        pnlNorte.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        lblSesion = new JLabel("Sesion iniciada como: " + nombreOperador);
        lblSesion.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel lblSubtitulo = new JLabel("Incidencias en estado: Nueva");
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 13));

        pnlNorte.add(lblSesion);
        pnlNorte.add(Box.createVerticalStrut(5));
        pnlNorte.add(lblSubtitulo);
        pnlNorte.add(Box.createVerticalStrut(10));
        pnlNorte.add(new JSeparator());
        
        add(pnlNorte, BorderLayout.NORTH);

        // --- PANEL CENTRO: Tabla de Incidencias ---
        // Columnas según tu dibujo: ID, Titulo, Responsable, Categoria, Accion
        String[] columnas = {"ID", "Título", "Responsable", "Categoría", "Acción"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            private static final long serialVersionUID = 1L;
            @Override 
            public boolean isCellEditable(int r, int c) { return false; }
        };
        
        tablaIncidencias = new JTable(modeloTabla);
        tablaIncidencias.setRowHeight(30);
        tablaIncidencias.getTableHeader().setReorderingAllowed(false);
        
        JScrollPane scrollTabla = new JScrollPane(tablaIncidencias);
        scrollTabla.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        add(scrollTabla, BorderLayout.CENTER);

        // --- PANEL SUR: Área de Gestión de Rechazo (El recuadro de tu dibujo) ---
        pnlRechazo = new JPanel(new BorderLayout(10, 10));
        pnlRechazo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 20, 20, 20),
                BorderFactory.createTitledBorder("Gestión de Incidencia Seleccionada")
        ));
        pnlRechazo.setBackground(new Color(245, 245, 245));
        pnlRechazo.setVisible(false); // Oculto hasta que se pulse "Rechazar"

        // Texto informativo del área
        lblAreaRechazo = new JLabel("Área de gestión de rechazo : #");
        lblAreaRechazo.setFont(new Font("Arial", Font.BOLD, 13));
        
        JPanel pnlInputRechazo = new JPanel(new BorderLayout(5, 5));
        pnlInputRechazo.setOpaque(false);
        pnlInputRechazo.add(new JLabel("Motivo rechazo:"), BorderLayout.NORTH);
        
        txtMotivo = new JTextArea(4, 50);
        txtMotivo.setLineWrap(true);
        txtMotivo.setWrapStyleWord(true);
        JScrollPane scrollMotivo = new JScrollPane(txtMotivo);
        pnlInputRechazo.add(scrollMotivo, BorderLayout.CENTER);

        // Botones de acción del panel inferior
        JPanel pnlBotonesInferiores = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlBotonesInferiores.setOpaque(false);
        
        btnConfirmar = new JButton("Confirmar y notificar");
        btnConfirmar.setBackground(new Color(200, 230, 201)); // Verde suave
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(255, 205, 210)); // Rojo suave
        
        pnlBotonesInferiores.add(btnConfirmar);
        pnlBotonesInferiores.add(btnCancelar);

        pnlRechazo.add(lblAreaRechazo, BorderLayout.NORTH);
        pnlRechazo.add(pnlInputRechazo, BorderLayout.CENTER);
        pnlRechazo.add(pnlBotonesInferiores, BorderLayout.SOUTH);

        add(pnlRechazo, BorderLayout.SOUTH);

        // Centrar ventana
        setLocationRelativeTo(null);
    }

    // --- MÉTODOS DE UTILIDAD PARA EL CONTROLADOR ---
    
    public void mostrarPanelRechazo(String id) {
        lblAreaRechazo.setText("Área de gestión de rechazo : #" + id);
        pnlRechazo.setVisible(true);
        txtMotivo.requestFocus();
        this.revalidate();
        this.repaint();
    }

    public void ocultarPanelRechazo() {
        pnlRechazo.setVisible(false);
        txtMotivo.setText("");
        this.revalidate();
        this.repaint();
    }

    // --- GETTERS ---
    
    public JTable getTablaIncidencias() { return tablaIncidencias; }
    public DefaultTableModel getModeloTabla() { return modeloTabla; }
    public JTextArea getTxtMotivo() { return txtMotivo; }
    public JButton getBtnConfirmar() { return btnConfirmar; }
    public JButton getBtnCancelar() { return btnCancelar; }
    public JPanel getPnlRechazo() { return pnlRechazo; }
}