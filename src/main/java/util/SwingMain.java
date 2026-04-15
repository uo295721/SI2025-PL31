package util;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controlador.*;
import modelo.*;
import vista.*;

public class SwingMain {

    private JFrame frame;
    private JPanel panelContenedor; 
    private CardLayout cardLayout;
    private UsuarioModelo usuario = new UsuarioModelo();

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                SwingMain window = new SwingMain();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public SwingMain() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Sistema de Gestión de Incidencias - Menú Principal");
        frame.setBounds(100, 100, 450, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);
        frame.getContentPane().add(panelContenedor, BorderLayout.CENTER);

        // Pantalla de inicio con 4 botones separados por funcionalidad
        JPanel menuInicio = new JPanel(new GridLayout(4, 1, 15, 15));
        menuInicio.setBorder(new EmptyBorder(40, 40, 40, 40));

        JButton btnIrSistema = new JButton("SISTEMA Y MANTENIMIENTO");
        JButton btnIrCiudadano = new JButton("CIUDADANÍA Y REGISTRO");
        JButton btnIrOperador = new JButton("GESTIÓN DE OPERADOR");
        JButton btnIrTecnicoRespon = new JButton("TÉCNICOS Y RESPONSABLES");

        Font fuenteBotones = new Font("Arial", Font.BOLD, 13);
        btnIrSistema.setFont(fuenteBotones);
        btnIrCiudadano.setFont(fuenteBotones);
        btnIrOperador.setFont(fuenteBotones);
        btnIrTecnicoRespon.setFont(fuenteBotones);

        menuInicio.add(btnIrSistema);
        menuInicio.add(btnIrCiudadano);
        menuInicio.add(btnIrOperador);
        menuInicio.add(btnIrTecnicoRespon);

        panelContenedor.add(menuInicio, "MENU_INICIAL");

        panelContenedor.add(crearSeccionSistema(), "SECCION_SISTEMA");
        panelContenedor.add(crearSeccionCiudadano(), "SECCION_CIUDADANO");
        panelContenedor.add(crearSeccionOperador(), "SECCION_OPERADOR");
        panelContenedor.add(crearSeccionTecnicoRespon(), "SECCION_TECNICO_RESPON");

        btnIrSistema.addActionListener(e -> cardLayout.show(panelContenedor, "SECCION_SISTEMA"));
        btnIrCiudadano.addActionListener(e -> cardLayout.show(panelContenedor, "SECCION_CIUDADANO"));
        btnIrOperador.addActionListener(e -> cardLayout.show(panelContenedor, "SECCION_OPERADOR"));
        btnIrTecnicoRespon.addActionListener(e -> cardLayout.show(panelContenedor, "SECCION_TECNICO_RESPON"));
    }

    private JPanel crearSeccionSistema() {
        JPanel p = prepararSubmenu();
        
        JButton b1 = new JButton("Inicializar Base de Datos");
        b1.addActionListener(e -> { new Database().createDatabase(false); JOptionPane.showMessageDialog(frame, "DB inicializada."); });
        
        JButton b2 = new JButton("Cargar Datos Iniciales");
        b2.addActionListener(e -> { Database db = new Database(); db.createDatabase(false); db.loadDatabase(); JOptionPane.showMessageDialog(frame, "Datos cargados."); });
        
        JButton b3 = new JButton("Exportar Historial Completo (JSON)");
        b3.addActionListener(e -> {
            vista.VentanaExportarHistorial ventanaHistorial = new vista.VentanaExportarHistorial(frame);
            new controlador.ExportarHistorialControlador(ventanaHistorial);
            ventanaHistorial.setVisible(true);
        });

        p.add(b1); p.add(b2); p.add(b3); p.add(botonVolver());
        return p;
    }

    private JPanel crearSeccionCiudadano() {
        JPanel p = prepararSubmenu();
        
        JButton b1 = new JButton("Registrar Nueva Incidencia");
        b1.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(frame, "DNI o Email:");
            if (id != null && usuario.esUsuario(id)) {
                RegistrarIncidencia v = new RegistrarIncidencia();
                new RegistrarIncidenciasControlador(v, new IncidenciaModelo(), new ZonaModelo(), id);
                v.setVisible(true); 
            } else {
            	JOptionPane.showMessageDialog(frame, 
                        "Usuario con los permisos necesarios no encontrado en la base de datos. Por favor, verifique el DNI o Email introducido.", 
                        "Error de Acceso", 
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        JButton b2 = new JButton("Consultar Mis Incidencias");
        b2.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(frame, "DNI o Email:");
            if (id != null && usuario.esUsuario(id)) {
                VentanaMisIncidencias v = new VentanaMisIncidencias();
                new ConsultaIncidenciasControlador(v, new IncidenciaModelo(), new ZonaModelo(), usuario.asegurarID(id));
                v.setVisible(true);
            } else {
            	JOptionPane.showMessageDialog(frame, 
                        "Usuario con los permisos necesarios no encontrado en la base de datos. Por favor, verifique el DNI o Email introducido.", 
                        "Error de Acceso", 
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        p.add(b1); p.add(b2); p.add(botonVolver());
        return p;
    }

    private JPanel crearSeccionOperador() {
        JPanel p = prepararSubmenu();
        JButton b1 = new JButton("Asignar Incidencias (Operador)");
        b1.addActionListener(e -> {
            VentanaAsignarTecnico v = new VentanaAsignarTecnico();
            new AsignarTecnicoControlador(v, new IncidenciaModelo());
            v.setVisible(true); 
        });
        JButton b2 = new JButton("Rechazar Incidencias (Operador)");
        b2.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(frame, "Email Operador:");
            if (id != null && usuario.esUsuarioConRol(id, "OPERADOR")) {
                VistaRechazoOperador v = new VistaRechazoOperador(id);
                new RechazoIncidenciaControlador(v, new IncidenciaModelo(), id);
                v.setVisible(true);
            }  else {
            	JOptionPane.showMessageDialog(frame, 
                        "Usuario con los permisos necesarios no encontrado en la base de datos. Por favor, verifique el DNI o Email introducido.", 
                        "Error de Acceso", 
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        JButton b3 = new JButton("Ver Historial de Incidencia");
        b3.addActionListener(e -> abrirHistorial());
        p.add(b1); p.add(b2); p.add(b3); p.add(botonVolver());
        return p;
    }

    private JPanel crearSeccionTecnicoRespon() {
        JPanel p = prepararSubmenu();
        
        JButton b1 = new JButton("Planificar Resolución (Técnico)");
        b1.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(frame, "Email Técnico:");
            if (id != null && usuario.esUsuarioConRol(id, "TÉCNICO")) {
                VentanaTecnico v = new VentanaTecnico();
                new TecnicoControlador(new IncidenciaModelo(), v, id);
                v.setVisible(true);
            }  else {
            	JOptionPane.showMessageDialog(frame, 
                        "Usuario con los permisos necesarios no encontrado en la base de datos. Por favor, verifique el DNI o Email introducido.", 
                        "Error de Acceso", 
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        
        JButton b2 = new JButton("Resolver en Proceso (Técnico)");
        b2.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(frame, "ID/Email Técnico:");
            if (id != null && usuario.esUsuarioConRol(id, "TÉCNICO")) {
                IncidenciasTecnicoProceso v = new IncidenciasTecnicoProceso(id);
                new IncidenciaControlador(new IncidenciaModelo(), v, id);
                v.setVisible(true);
            } else {
            	JOptionPane.showMessageDialog(frame, 
                        "Usuario con los permisos necesarios no encontrado en la base de datos. Por favor, verifique el DNI o Email introducido.", 
                        "Error de Acceso", 
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        
        JButton b3 = new JButton("Control de Calidad (Responsable)");
        b3.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(frame, "Email Responsable:");
            if (id != null && usuario.esUsuarioConRol(id, "RESPONSABLE")) {
                VentanaResponsable v = new VentanaResponsable();
                new ResponsableControlador(v, id);
                v.setVisible(true);
            } else {
            	JOptionPane.showMessageDialog(frame, 
                        "Usuario con los permisos necesarios no encontrado en la base de datos. Por favor, verifique el DNI o Email introducido.", 
                        "Error de Acceso", 
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        JButton b4 = new JButton("Generar Informe Responsable");
        b4.addActionListener(e -> {
            String email = JOptionPane.showInputDialog(frame, "Email Responsable:");
            if (email != null && usuario.esUsuarioPorEmail(email, "RESPONSABLE")) {
                VentanaInformeResponsable v = new VentanaInformeResponsable(email);
                new InformeResponsableControlador(new IncidenciaModelo(), new UsuarioModelo(), v, email);
                v.setVisible(true);
            } else {
            	JOptionPane.showMessageDialog(frame, 
                        "Usuario con los permisos necesarios no encontrado en la base de datos. Por favor, verifique el DNI o Email introducido.", 
                        "Error de Acceso", 
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        
        JButton b5 = new JButton("Estadísticas (Público)");
        b5.addActionListener(e -> {
            VentanaInformes v = new VentanaInformes();
            new InformesControlador(v);
            v.setVisible(true);
        });
        
        JButton b6 = new JButton("Informe Económico por Categoría");
        b6.addActionListener(e -> {
            String email = JOptionPane.showInputDialog(frame, "Email Responsable:");

            if (email != null && usuario.esUsuarioConRol(email, "RESPONSABLE")) {
                VentanaInformeEconomico v = new VentanaInformeEconomico();
                new InformeEconomicoControlador(v, new IncidenciaModelo());
                v.setVisible(true);
            } 
            else if (email != null) {
                JOptionPane.showMessageDialog(frame, "Acceso denegado: El email no corresponde a un Responsable.",
                        "Error de Permisos", JOptionPane.ERROR_MESSAGE);
            }
        });
        p.add(b1); p.add(b2); p.add(b3); p.add(b4); p.add(b5);p.add(b6); p.add(botonVolver());
        
        JButton b7 = new JButton("Gestionar Presupuestos (Económico)");
        b7.addActionListener(e -> {
            VentanaPresupuestos v = new VentanaPresupuestos();
            new PresupuestoControlador(v, new PresupuestoModelo());
            v.setVisible(true);
        });
        
        p.add(b7);
        return p;
    }

    private void abrirHistorial() {
        String email = JOptionPane.showInputDialog(frame, "Email:");
        if (email != null && (usuario.esUsuarioConRol(email, "OPERADOR") || usuario.esUsuarioConRol(email, "TÉCNICO"))) {
            IncidenciaModelo mI = new IncidenciaModelo();
            List<IncidenciaDTO> lista = mI.getTodasLasIncidencias();
            if (lista.isEmpty()) return;
            Object sel = JOptionPane.showInputDialog(frame, "Seleccione:", "Historial", 
                         JOptionPane.QUESTION_MESSAGE, null, lista.toArray(), lista.get(0));
            if (sel != null) {
                int id = ((IncidenciaDTO) sel).getIdIncidencia();
                VentanaHistorial v = new VentanaHistorial(frame, id);
                new HistorialControlador(v, mI, id);
                v.setVisible(true); 
            }
        }
    }

    private JPanel prepararSubmenu() {
        JPanel p = new JPanel(new GridLayout(0, 1, 10, 10));
        p.setBorder(new EmptyBorder(30, 30, 30, 30));
        return p;
    }

    private JButton botonVolver() {
        JButton btn = new JButton("<< VOLVER");
        btn.addActionListener(e -> cardLayout.show(panelContenedor, "MENU_INICIAL"));
        return btn;
    }

    public JFrame getFrame() {
        return this.frame;
    }
}