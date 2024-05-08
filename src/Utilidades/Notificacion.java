package Utilidades;

import static Principal.Ventana.cp;
import static Principal.Ventana.vta;
import SwingModificado.JButtonRounded;
import SwingModificado.JLabelImage;
import SwingModificado.JPanelRounded;
import static Utilidades.Recursos.getFuente;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.Timer;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class Notificacion extends JDialog {

    int tipo;
    String mensaje;
    JPanelRounded pnl;
    private boolean accion = false;

    public Notificacion(int tipo, String mensaje,boolean modal) {
        super(vta, modal);
        pnl = new JPanelRounded(0);
        this.tipo = tipo;
        this.mensaje = mensaje;
        this.setUndecorated(true);
        initComponents();
        pnl.setLayout(new BorderLayout());
        setContentPane(pnl);
        setVisible(true);
    }

    public void initComponents() {
        if (tipo < 2) {
            setSize(vta.getWidth(), 60);
            JLabelImage lblI = new JLabelImage("/res/imagenes/notificaciones/" + ((tipo == 0) ? "exito.png" : "error.png"));
            lblI.setSize(40, 40);
            lblI.setLocation(10, 10);
            pnl.add(lblI);
            JLabel lblMensaje = new JLabel(mensaje, JLabel.LEFT);
            lblMensaje.setLocation(60, 10);
            lblMensaje.setFont(getFuente(1, 0, 13));
            lblMensaje.setSize(860, 40);
            lblMensaje.setForeground(new Color(255, 255, 255));
            pnl.add(lblMensaje);
            timer();
            close();
        }
        switch (tipo) {
            case 0:
                notificacionDeExito();
                break;
            case 1:
                notificacionDeError();
                break;
            case 2:
                notificacionDeAdvertencia();
                break;
            case 3:
                notificacionDeEleccion();
                break;
            case 4:
                notificacionErrorAlAccionar();
                break;
        }
        setLayout(new BorderLayout());
    }

    public void notificacionDeExito() {
        movimientoDeVentana();
        pnl.setBackground(new Color(126, 217, 87));
    }

    public void notificacionDeError() {
        movimientoDeVentana();
        pnl.setBackground(new Color(255, 87, 87));
    }
    
    public void notificacionDeAdvertencia() {
        setSize(300, 250);
        setLocationRelativeTo(null);
        pnl.setOpaque(false);
        JLabelImage lblI = new JLabelImage("/res/imagenes/notificaciones/advertencia.png");
        lblI.setSize(50, 50);
        lblI.setLocation(getWidth() / 2 - 25, 15);
        pnl.add(lblI);

        JTextPane textpane = new JTextPane();
        textpane.setFont(getFuente(0, 1, 14));
        textpane.setLocation(30, 80);
        textpane.setSize(getWidth() - 60, 100);
        SimpleAttributeSet attribs = new SimpleAttributeSet();
        StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_JUSTIFIED);
        textpane.setParagraphAttributes(attribs, false);
        textpane.setText(mensaje);
        textpane.setEditable(false);
        StyledDocument document = textpane.getStyledDocument();
        MutableAttributeSet set = new SimpleAttributeSet();
        StyleConstants.setLineSpacing(set, 0.3f);
        document.setParagraphAttributes(0, document.getLength(), set, false);
        pnl.add(textpane);

        JButtonRounded btnCancelar = new JButtonRounded("CANCELAR", 20, false);
        btnCancelar.setLocation(20, 190);
        btnCancelar.setSize(120, 40);
        btnCancelar.setFont(getFuente(1, 0, 14));
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accion = false;
                dispose();
            }
        });
        pnl.add(btnCancelar);
        JButtonRounded btnConfirmar = new JButtonRounded("CONFIRMAR", 20);
        btnConfirmar.setLocation(160, 190);
        btnConfirmar.setSize(120, 40);
        btnConfirmar.setFont(getFuente(1, 0, 14));
        btnConfirmar.setBackground(new Color(126, 217, 87));
        btnConfirmar.setForeground(new Color(255, 255, 255));
        btnConfirmar.setColor(new Color(126, 217, 87));
        btnConfirmar.setColorOver(new Color(106, 197, 67));
        btnConfirmar.setColorClick(new Color(86, 177, 47));
        btnConfirmar.setBorderPainted(false);
        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accion = true;
                dispose();
                
            }
        });
        pnl.add(btnConfirmar);
        pnl.setBackground(Color.white);
    }
   
    public void notificacionDeEleccion() {
        setSize(300, 250);
        setLocationRelativeTo(null);
        JLabelImage lblI = new JLabelImage("/res/imagenes/notificaciones/eleccion.png");
        lblI.setSize(50, 50);
        lblI.setLocation(getWidth() / 2 - 25, 35);
        pnl.add(lblI);
        JLabel lblMensaje = new JLabel(mensaje, JLabel.CENTER);
        lblMensaje.setLocation(20, 110);
        lblMensaje.setFont(getFuente(1, 1, 14));
        lblMensaje.setSize(getWidth() - 40, 40);
        pnl.add(lblMensaje);

        JButtonRounded btnCancelar = new JButtonRounded("AHORA NO", 20, false);
        btnCancelar.setLocation(20, 190);
        btnCancelar.setSize(120, 40);
        btnCancelar.setFont(getFuente(1, 0, 14));
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        pnl.add(btnCancelar);
        JButtonRounded btnConfirmar = new JButtonRounded("ACEPTAR", 20);
        btnConfirmar.setLocation(160, 190);
        btnConfirmar.setSize(120, 40);
        btnConfirmar.setFont(getFuente(1, 0, 14));
        btnConfirmar.setBackground(new Color(126, 217, 87));
        btnConfirmar.setForeground(new Color(255, 255, 255));
        btnConfirmar.setColor(new Color(126, 217, 87));
        btnConfirmar.setColorOver(new Color(106, 197, 67));
        btnConfirmar.setColorClick(new Color(86, 177, 47));
        btnConfirmar.setBorderPainted(false);
        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accion=true;
                new Notificacion(0, "Venta realizada con exito",false);
                dispose();
            }
        });
        pnl.add(btnConfirmar);
        pnl.setBackground(Color.white);
    }

    public void notificacionErrorAlAccionar() {
        setSize(300, 200);
        setLocationRelativeTo(null);
        pnl.setOpaque(false);
        JLabelImage lblI = new JLabelImage("/res/imagenes/notificaciones/error.png");
        lblI.setSize(50, 50);
        lblI.setLocation(getWidth() / 2 - 25, 15);
        pnl.add(lblI);

        JTextPane textpane = new JTextPane();
        textpane.setFont(getFuente(0, 1, 14));
        textpane.setLocation(30, 80);
        textpane.setSize(getWidth() - 60, 40);
        SimpleAttributeSet attribs = new SimpleAttributeSet();
        StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_JUSTIFIED);
        textpane.setParagraphAttributes(attribs, false);
        textpane.setText(mensaje);
        textpane.setEditable(false);
        textpane.setFocusable(false);
        StyledDocument document = textpane.getStyledDocument();
        MutableAttributeSet set = new SimpleAttributeSet();
        StyleConstants.setLineSpacing(set, 0.3f);
        document.setParagraphAttributes(0, document.getLength(), set, false);
        pnl.add(textpane);
        
        JButtonRounded btnConfirmar = new JButtonRounded("ACEPTAR", 20);
        btnConfirmar.setLocation(90, 140);
        btnConfirmar.setSize(120, 40);
        btnConfirmar.setFont(getFuente(1, 0, 14));
        btnConfirmar.setBackground(new Color(126, 217, 87));
        btnConfirmar.setForeground(new Color(255, 255, 255));
        btnConfirmar.setColor(new Color(126, 217, 87));
        btnConfirmar.setColorOver(new Color(106, 197, 67));
        btnConfirmar.setColorClick(new Color(86, 177, 47));
        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        pnl.add(btnConfirmar);
        pnl.setCornerRadius(40);
        pnl.setPintarBorde(true);
        pnl.setColorBorde(Color.black);
        pnl.setBackground(Color.white);
        setBackground(new Color(0, 0, 0, 0));
    }
    
    public void close() {
        JButtonRounded btnClose = new JButtonRounded("X", 0);
        btnClose.setHorizontalAlignment(JButton.CENTER);
        btnClose.setFont(Recursos.FUENTE_BOTON);
        btnClose.setForeground(Color.white);
        btnClose.setSize(60, 40);
        btnClose.setBorderPainted(false);
        btnClose.setDibujarFondo(false);
        btnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnClose.setLocation(getWidth() - 80, 10);
        pnl.add(btnClose);
    }

    public void timer() {
        Timer timer = new Timer(10000, (ActionEvent e) -> {
            dispose();
        });
        timer.setRepeats(false);
        timer.start();
    }

    public boolean getRespuesta() {
        return accion;
    }
    
    public void movimientoDeVentana() {
        setLocation(cp.getPanelActual(0).getLocationOnScreen().x, cp.getPanelActual(0).getLocationOnScreen().y);
        vta.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setSize(cp.getPanelActual(0).getWidth(), 60);
                setLocation(cp.getPanelActual(0).getLocationOnScreen().x, cp.getPanelActual(0).getLocationOnScreen().y);
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                setSize(cp.getPanelActual(0).getWidth(), 60);
                setLocation(cp.getPanelActual(0).getLocationOnScreen().x, cp.getPanelActual(0).getLocationOnScreen().y);
            }
        });
    }
}
