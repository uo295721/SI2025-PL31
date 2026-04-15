package vista;


import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import modelo.InformeEconomicoDTO;

public class VentanaInformeEconomico extends JDialog {

    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public VentanaInformeEconomico() {
        setTitle("Informe Resumido: Volumen y Costes por Categoría");
        setModal(true);
        setSize(700, 500);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        
        JLabel lblTitulo = new JLabel("Resumen Económico", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(lblTitulo, BorderLayout.NORTH);

        // Definición de la tabla
        String[] columnas = {"Categoría", "Nº Incidencias", "Coste Total (€)", "Coste Medio (€)"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(25);
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(scrollPane, BorderLayout.CENTER);

        JButton btnCerrar = new JButton("Cerrar Informe");
        btnCerrar.addActionListener(e -> dispose());
        JPanel panelSur = new JPanel();
        panelSur.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        panelSur.add(btnCerrar);
        add(panelSur, BorderLayout.SOUTH);
    }

    public void cargarDatos(List<InformeEconomicoDTO> datos) {
        modeloTabla.setRowCount(0); // Limpiar tabla
        for (InformeEconomicoDTO d : datos) {
            Object[] fila = {
                d.getCategoria(),
                d.getTotalIncidencias(),
                String.format("%.2f €", d.getCosteTotal()),
                String.format("%.2f €", d.getCosteMedio())
            };
            modeloTabla.addRow(fila);
        }
    }
}
