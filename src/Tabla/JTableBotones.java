package Tabla;

import SwingModificado.JButtonRounded;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class JTableBotones extends JPanel {

    JButtonRounded btnVer, btnEditar, btnEliminar, btnHistorial, btnAjuste;
    boolean botonesActivos[] = new boolean[]{false, false, false, false, false};

    public JTableBotones(int tipo, Color bg) {
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 4));
        setBackground(bg);
        setTipo(tipo);
        iniciar();
    }

    public void setTipo(int tipo) {
        //ajuste,ver,hitorial,editar,eliminar
        switch (tipo) {
            case 0 -> //Ver, editar, eliminar
                botonesActivos = new boolean[]{false, true, false, true, true};
            case 1 -> //Ver, historial, editar, eliminar
                botonesActivos = new boolean[]{false, true, true, true, true};
            case 2 -> //Ajuste, editar, eliminar
                botonesActivos = new boolean[]{true, false, false, true, true};
            case 3 -> //Editar, eliminar
                botonesActivos = new boolean[]{false, false, false, true, true};
            case 4 -> //Eliminar
                botonesActivos = new boolean[]{false, false, false, false, true};
            case -1 -> //Ver, eliminar
                 botonesActivos = new boolean[]{false, true, false, false, true};
            case -2 -> //ver, historial, eliminar
                botonesActivos = new boolean[]{false, true, true, false, true};
        }
    }

    public void iniciar() {
        if (botonesActivos[0]) {
            btnAjuste = new JButtonRounded(0);
            btnAjuste.setIcon(new ImageIcon(getClass().getResource("/res/imagenes/crud/ajuste.png")));
            btnAjuste.setIconoPresionado(new ImageIcon(getClass().getResource("/res/imagenes/crud/ajuste_oscurecido.png")));
            btnAjuste.setPreferredSize(new Dimension(28, 28));
            btnAjuste.addMouseListener(new retroAlimentacion(btnAjuste));
            btnAjuste.setBackground(getBackground());
            btnAjuste.setDibujarFondo(false);
            btnAjuste.setBorderPainted(false);
            add(btnAjuste);
        }

        if (botonesActivos[1]) {
            btnVer = new JButtonRounded(0);
            btnVer.setIcon(new ImageIcon(getClass().getResource("/res/imagenes/crud/ver.png")));
            btnVer.setIconoPresionado(new ImageIcon(getClass().getResource("/res/imagenes/crud/ver_oscurecido.png")));
            btnVer.setPreferredSize(new Dimension(28, 28));
            btnVer.addMouseListener(new retroAlimentacion(btnVer));
            btnVer.setDibujarFondo(false);
            btnVer.setBorderPainted(false);
            add(btnVer);
        }

        if (botonesActivos[2]) {
            btnHistorial = new JButtonRounded(0);
            btnHistorial.setIcon(new ImageIcon(getClass().getResource("/res/imagenes/crud/historial.png")));
            btnHistorial.setIconoPresionado(new ImageIcon(getClass().getResource("/res/imagenes/crud/historial_oscurecido.png")));
            btnHistorial.setPreferredSize(new Dimension(28, 28));
            btnHistorial.addMouseListener(new retroAlimentacion(btnHistorial));
            btnHistorial.setDibujarFondo(false);
            btnHistorial.setBorderPainted(false);
            add(btnHistorial);
        }

        if (botonesActivos[3]) {
            btnEditar = new JButtonRounded(0);
            btnEditar.setIcon(new ImageIcon(getClass().getResource("/res/imagenes/crud/editar.png")));
            btnEditar.setIconoPresionado(new ImageIcon(getClass().getResource("/res/imagenes/crud/editar_oscurecido.png")));
            btnEditar.setPreferredSize(new Dimension(28, 28));
            btnEditar.addMouseListener(new retroAlimentacion(btnEditar));
            btnEditar.setDibujarFondo(false);
            btnEditar.setBorderPainted(false);
            add(btnEditar);
        }

        if (botonesActivos[4]) {
            btnEliminar = new JButtonRounded(0);
            btnEliminar.setIcon(new ImageIcon(getClass().getResource("/res/imagenes/crud/eliminar.png")));
            btnEliminar.setIconoPresionado(new ImageIcon(getClass().getResource("/res/imagenes/crud/eliminar_oscurecido.png")));
            btnEliminar.setPreferredSize(new Dimension(28, 28));
            btnEliminar.addMouseListener(new retroAlimentacion(btnEliminar));
            btnEliminar.setDibujarFondo(false);
            btnEliminar.setBorderPainted(false);
            add(btnEliminar);
        }

    }

    public void initEvent(AccionEnJTable event, int row) {
        if (botonesActivos[0]) {
            btnAjuste.addActionListener((ActionEvent ae) -> {
                event.ajustar(row);
            });
        }
        if (botonesActivos[1]) {
            btnVer.addActionListener((ActionEvent ae) -> {
                event.visualizar(row);
            });
        }
        if (botonesActivos[2]) {
            btnHistorial.addActionListener((ActionEvent ae) -> {
                event.verHistorial(row);
            });
        }
        if (botonesActivos[3]) {
            btnEditar.addActionListener((ActionEvent ae) -> {
                event.editar(row);
            });
        }
        if (botonesActivos[4]) {
            btnEliminar.addActionListener((ActionEvent ae) -> {
                event.eliminar(row);
            });
        }
    }

    class retroAlimentacion extends MouseAdapter {

        JButtonRounded btn;

        public retroAlimentacion(JButtonRounded btn) {
            this.btn = btn;
        }

        @Override
        public void mouseEntered(MouseEvent me) {
            btn.setSize(btn.getWidth() + 4, btn.getHeight() + 4);
            btn.setLocation(btn.getLocation().x-2, btn.getLocation().y-2);
        }

        @Override
        public void mouseExited(MouseEvent me) {
            btn.setSize(btn.getWidth() - 4, btn.getHeight() - 4);
            btn.setLocation(btn.getLocation().x+2, btn.getLocation().y+2);
        }

        @Override
        public void mousePressed(MouseEvent me) {
            btn.setPresionado(true);
        }

        @Override
        public void mouseReleased(MouseEvent me) {
            btn.setPresionado(false);
        }
    };
}
