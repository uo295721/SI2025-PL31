package vista;

import javax.swing.*;
import java.awt.*;

public class VentanaExportarHistorial extends JDialog {
    private static final long serialVersionUID = 1L;
	public JTextField txtFechaInicio, txtFechaFin;
    public JComboBox<String> cBTipos, cBZonas;
    public JButton btnExportarJSON, btnCancelar;

    public VentanaExportarHistorial(Frame padre) {
        super(padre, "Configurar Exportación de Historial", true);
        setLayout(new BorderLayout(10, 10));
        setSize(400, 300);
        setLocationRelativeTo(padre);

        JPanel panelFiltros = new JPanel(new GridLayout(4, 2, 10, 10));
        panelFiltros.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panelFiltros.add(new JLabel("Fecha Inicio (AAAA-MM-DD):"));
        txtFechaInicio = new JTextField();
        panelFiltros.add(txtFechaInicio);

        panelFiltros.add(new JLabel("Fecha Fin (AAAA-MM-DD):"));
        txtFechaFin = new JTextField();
        panelFiltros.add(txtFechaFin);

        panelFiltros.add(new JLabel("Tipo de Incidencia:"));
        cBTipos = new JComboBox<>();
        panelFiltros.add(cBTipos);

        panelFiltros.add(new JLabel("Zona:"));
        cBZonas = new JComboBox<>();
        panelFiltros.add(cBZonas);

        JPanel panelBotones = new JPanel();
        btnExportarJSON = new JButton("Guardar JSON...");
        btnCancelar = new JButton("Cancelar");
        panelBotones.add(btnExportarJSON);
        panelBotones.add(btnCancelar);

        add(panelFiltros, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }
}