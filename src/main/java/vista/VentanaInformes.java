package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaInformes extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    
    // Componentes de Filtros (Entrada)
    public JTextField txtFechaInicio;
    public JTextField txtFechaFin;
    public JComboBox<String> cBTipos;
    public JComboBox<String> cBZonas;
    public JComboBox<String> cBEstados;
    public JButton btnGenerar;

    // Tabla de Resultados (Proceso/Salida)
    public JTable tablaResultados;
    public DefaultTableModel modeloTabla;

    // Estadísticas y Acciones (Salida)
    public JLabel lblMediaGlobal;
    public JButton btnCerrar;
    public JButton btnExportarCSV;

    public VentanaInformes() {
        setTitle("Informes y Estadísticas de Incidencias");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(850, 600);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new BorderLayout(0, 10));
        setContentPane(contentPane);

        JPanel pnlFiltros = new JPanel(new GridLayout(2, 4, 10, 10));
        pnlFiltros.setBorder(new TitledBorder("Filtros de Búsqueda"));

        pnlFiltros.add(new JLabel("Fecha Inicio (YYYY-MM-DD):"));
        txtFechaInicio = new JTextField("2026-01-01");
        pnlFiltros.add(txtFechaInicio);

        pnlFiltros.add(new JLabel("Tipo:"));
        cBTipos = new JComboBox<>();
        pnlFiltros.add(cBTipos);

        pnlFiltros.add(new JLabel("Fecha Fin (YYYY-MM-DD):"));
        txtFechaFin = new JTextField("2026-12-31");
        pnlFiltros.add(txtFechaFin);

        pnlFiltros.add(new JLabel("Zona:"));
        cBZonas = new JComboBox<>();
        pnlFiltros.add(cBZonas);

        JPanel pnlBotoneraFiltros = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlBotoneraFiltros.add(new JLabel("Estado:"));
        cBEstados = new JComboBox<>(new String[]{"Todos", "Nueva", "Validada", "Asignada", "Proceso", "Resuelta", "Cerrada"});
        pnlBotoneraFiltros.add(cBEstados);
        
        btnGenerar = new JButton("Generar Informe");
        btnGenerar.setBackground(new Color(50, 150, 50));
        pnlBotoneraFiltros.add(btnGenerar);

        JPanel pnlNorteUnificado = new JPanel(new BorderLayout());
        pnlNorteUnificado.add(pnlFiltros, BorderLayout.CENTER);
        pnlNorteUnificado.add(pnlBotoneraFiltros, BorderLayout.SOUTH);
        
        contentPane.add(pnlNorteUnificado, BorderLayout.NORTH);

        String[] columnas = {"ID", "Fecha Registro", "Estado", "Tipo", "Tiempo Resolución"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaResultados = new JTable(modeloTabla);
        contentPane.add(new JScrollPane(tablaResultados), BorderLayout.CENTER);

        JPanel pnlSur = new JPanel(new BorderLayout());
        
        JPanel pnlEstadistica = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlEstadistica.setBorder(new LineBorder(Color.GRAY));
        lblMediaGlobal = new JLabel("Tiempo Medio de Resolución (conjunto filtrado): --- ");
        pnlEstadistica.add(lblMediaGlobal);
        
        btnCerrar = new JButton("Cerrar");
        pnlSur.add(pnlEstadistica, BorderLayout.CENTER);
        pnlSur.add(btnCerrar, BorderLayout.EAST);
        
        btnExportarCSV = new JButton("Exportar a CSV");
        btnExportarCSV.setBackground(new Color(255, 153, 51)); // Color naranja para diferenciarlo
        pnlSur.add(btnExportarCSV, BorderLayout.WEST); // Lo ponemos a la izquierda en el panel sur
        
        contentPane.add(pnlSur, BorderLayout.SOUTH);
    }
}