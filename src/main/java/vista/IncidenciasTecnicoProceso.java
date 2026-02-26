package vista;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class IncidenciasTecnicoProceso extends JFrame {

	private static final long serialVersionUID = 1L;
	public JButton btnSalir = new JButton("X");
    public JTable tablaIncidencias;
    public DefaultTableModel modeloTabla;

    // Campos del formulario
    public JTextField txtIdIncidencia = new JTextField();
    public JTextField txtTituloIncidencia = new JTextField();
    public JTextField txtHorasEstimadas = new JTextField();
    public JTextField txtHorasReales = new JTextField();
    public JTextArea txtAreaTrabajos = new JTextArea(3, 20);
    public JButton btnMarcarResuelta = new JButton("Marcar como resuelta");
    public JButton btnCancelar = new JButton("Cancelar");

    public IncidenciasTecnicoProceso(String nombreTecnico) {
        // --- Nuevo Tamaño solicitado ---
        setSize(750, 600);
        setTitle("Gestión de Incidencias Urbanas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));

        // 1. CABECERA
        JPanel pnlCabecera = new JPanel(new BorderLayout());
        JLabel lblSesion = new JLabel(" Sesión iniciada (técnico): " + nombreTecnico);
        lblSesion.setFont(new Font("Arial", Font.BOLD, 13));
        pnlCabecera.add(lblSesion, BorderLayout.WEST);
        pnlCabecera.add(btnSalir, BorderLayout.EAST);
        add(pnlCabecera, BorderLayout.NORTH);

        // 2. CUERPO CENTRAL
        JPanel pnlCuerpo = new JPanel();
        pnlCuerpo.setLayout(new BoxLayout(pnlCuerpo, BoxLayout.Y_AXIS));
        pnlCuerpo.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        // Título del listado a la IZQUIERDA
        JPanel pnlTituloLista = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlTituloLista.add(new JLabel("Listado de incidencias en proceso:"));
        pnlCuerpo.add(pnlTituloLista);

        // Tabla
        String[] columnas = {"ID", "Título", "Fecha", "Localización", "Horas Est."};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaIncidencias = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaIncidencias);
        scroll.setPreferredSize(new Dimension(700, 120));
        pnlCuerpo.add(scroll);

        // Formulario (GridLayout 4x2)
        JPanel pnlForm = new JPanel(new GridLayout(4, 2, 8, 8));
        pnlForm.setBorder(new TitledBorder("Detalles de Resolución"));
        
        txtIdIncidencia.setEditable(false);
        txtTituloIncidencia.setEditable(false);
        txtHorasEstimadas.setEditable(false);

        pnlForm.add(new JLabel("ID Incidencia:")); pnlForm.add(txtIdIncidencia);
        pnlForm.add(new JLabel("Título:")); pnlForm.add(txtTituloIncidencia);
        pnlForm.add(new JLabel("Tiempo Estimado:")); pnlForm.add(txtHorasEstimadas);
        pnlForm.add(new JLabel("Tiempo Real (h):")); pnlForm.add(txtHorasReales);
        
        pnlCuerpo.add(Box.createVerticalStrut(10));
        pnlCuerpo.add(pnlForm);

        // Descripción
        JPanel pnlDesc = new JPanel(new BorderLayout());
        pnlDesc.add(new JLabel("Descripción de trabajos:"), BorderLayout.NORTH);
        pnlDesc.add(new JScrollPane(txtAreaTrabajos), BorderLayout.CENTER);
        pnlCuerpo.add(pnlDesc);

        add(pnlCuerpo, BorderLayout.CENTER);

        // 3. BOTONES INFERIORES
        JPanel pnlAcciones = new JPanel();
        pnlAcciones.add(btnCancelar);
        pnlAcciones.add(btnMarcarResuelta);
        add(pnlAcciones, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }
}