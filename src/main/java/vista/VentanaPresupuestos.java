package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import modelo.TipoIncidenciaDTO;

public class VentanaPresupuestos extends JFrame {
    public JTable tabla;
    public DefaultTableModel modeloTabla;
    public JComboBox<TipoIncidenciaDTO> cbTipos;
    public JTextField txtImporte, txtInicio, txtFin;
    public JButton btnGuardar;

    public VentanaPresupuestos() {
        setTitle("Control Económico: Gestión de Presupuestos");
        setSize(700, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Arriba: Tabla
        modeloTabla = new DefaultTableModel(new String[]{"Tipo", "Total", "Consumido", "Inicio", "Fin"}, 0);
        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Abajo: Formulario
        JPanel pnlForm = new JPanel(new GridLayout(5, 2, 5, 5));
        pnlForm.setBorder(BorderFactory.createTitledBorder("Nuevo Presupuesto"));

        pnlForm.add(new JLabel("Tipo de Incidencia:"));
        cbTipos = new JComboBox<>();
        pnlForm.add(cbTipos);

        pnlForm.add(new JLabel("Importe Total (€):"));
        txtImporte = new JTextField();
        pnlForm.add(txtImporte);

        pnlForm.add(new JLabel("Fecha Inicio (YYYY-MM-DD):"));
        txtInicio = new JTextField("2026-01-01");
        pnlForm.add(txtInicio);

        pnlForm.add(new JLabel("Fecha Fin (YYYY-MM-DD):"));
        txtFin = new JTextField("2026-12-31");
        pnlForm.add(txtFin);

        btnGuardar = new JButton("Registrar Presupuesto");
        pnlForm.add(btnGuardar);

        add(pnlForm, BorderLayout.SOUTH);
        setLocationRelativeTo(null);
    }
}
