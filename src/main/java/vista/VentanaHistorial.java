package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import modelo.HistorialDTO;

public class VentanaHistorial extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTable tablaHistorial;
    private DefaultTableModel modeloTabla;

  
    public VentanaHistorial(JFrame padre, int idIncidencia, List<HistorialDTO> datos) {
        super(padre, true);
        setTitle("Historial de Incidencia #" + idIncidencia);
        setBounds(100, 100, 600, 400);
        
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0)); 

        JScrollPane scrollPane = new JScrollPane();
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        
        String[] columnas = {"Fecha/Hora", "Identificador", "Descripción", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        
        tablaHistorial = new JTable(modeloTabla);
        scrollPane.setViewportView(tablaHistorial);

      
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton("Cerrar");
        okButton.addActionListener(e -> dispose()); 
        buttonPane.add(okButton);

       
        if (datos != null) {
            for (HistorialDTO h : datos) {
                modeloTabla.addRow(new Object[] {
                    h.getFecha_modificacion(),
                    h.getId_usuario(),
                    h.getComentario(),
                    h.getEstado_nuevo()
                });
            }
        }
        
 
        setLocationRelativeTo(padre);
    }
}