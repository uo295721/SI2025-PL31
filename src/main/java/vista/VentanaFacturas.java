package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaFacturas extends JFrame {
	
    private static final long serialVersionUID = 1L;
	public JTable tablaPendientes, tablaFacturas;
    public DefaultTableModel modeloPendientes, modeloFacturas;
    public JButton btnGenerarFactura, btnAnularFactura, btnRefrescar;

    public VentanaFacturas() {
        setTitle("Gestión de Facturación Simplificada");
        setSize(800, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTabbedPane pestañas = new JTabbedPane();

        JPanel panelPendientes = new JPanel(new BorderLayout());
        modeloPendientes = new DefaultTableModel(new Object[]{"ID", "Fecha", "Detalle Técnico", "Total Horas", "Coste (€)"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Esto impide que se pueda escribir en ninguna celda
            }
        };
        tablaPendientes = new JTable(modeloPendientes);
        modeloFacturas = new DefaultTableModel(new Object[]{"ID", "Nº Factura", "ID Incid.", "Fecha", "Total Horas", "Importe (€)", "Estado"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        tablaFacturas = new JTable(modeloFacturas);
        panelPendientes.add(new JScrollPane(tablaPendientes), BorderLayout.CENTER);
        
        btnGenerarFactura = new JButton("Generar Factura de Incidencia Seleccionada");
        panelPendientes.add(btnGenerarFactura, BorderLayout.SOUTH);

        JPanel panelHistorial = new JPanel(new BorderLayout());
        modeloFacturas = new DefaultTableModel(new Object[]{"ID", "Nº Factura", "ID Incid.", "Fecha", "Total Horas","Importe (€) ", "Estado"}, 0);
        tablaFacturas = new JTable(modeloFacturas);
        panelHistorial.add(new JScrollPane(tablaFacturas), BorderLayout.CENTER);
        
        btnAnularFactura = new JButton("Anular Factura Seleccionada");
        btnAnularFactura.setBackground(new Color(255, 200, 200));
        panelHistorial.add(btnAnularFactura, BorderLayout.SOUTH);

        pestañas.addTab("Pendientes de Factura", panelPendientes);
        pestañas.addTab("Registro de Facturas", panelHistorial);

        add(pestañas, BorderLayout.CENTER);
        
        btnRefrescar = new JButton("Actualizar Datos");
        add(btnRefrescar, BorderLayout.NORTH);
    }
}
