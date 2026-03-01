package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import modelo.HistorialDTO;

public class VentanaHistorial extends JDialog {

    private static final long serialVersionUID = 1L;
	private JTable tablaHistorial;
    private DefaultTableModel modeloTabla;

    public VentanaHistorial(JFrame padre, int idIncidencia) {
        super(padre, true);
        setTitle("Historial Detallado - Incidencia #" + idIncidencia);
        setBounds(100, 100, 700, 400);
        getContentPane().setLayout(new BorderLayout());
        
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        String[] columnas = {"Fecha/Hora", "Usuario", "Descripción del Cambio", "Estado Nuevo"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };

        tablaHistorial = new JTable(modeloTabla);
        contentPanel.add(new JScrollPane(tablaHistorial), BorderLayout.CENTER);
        tablaHistorial.getTableHeader().setReorderingAllowed(false); // Bloqueamos el movimiento de las columnas

        // Botón cerrar
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlBotones.add(btnCerrar);
        getContentPane().add(pnlBotones, BorderLayout.SOUTH);
        
        setLocationRelativeTo(padre);
    }

    public void rellenarTablaHistorial(List<HistorialDTO> historial) {
        modeloTabla.setRowCount(0);
        if (historial != null) {
            for (HistorialDTO h : historial) {
                modeloTabla.addRow(new Object[] {
                    h.getFecha_modificacion(),
                    h.getId_usuario(),
                    h.getComentario(),
                    h.getEstado_nuevo()
                });
            }
        }
    }
}