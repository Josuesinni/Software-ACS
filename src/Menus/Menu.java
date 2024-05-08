package Menus;


import Apartados.CatalogoApartados;
import Clientes.CatalogoClientes;
import Contratos.CatalogoContratos;
import Historicos.HistorialVentas;
import static Principal.Ventana.cp;
import Productos.CatalogoProductos;
import SwingModificado.JButtonRounded;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static Utilidades.Recursos.getFuente;
import Ventas.Venta;

public class Menu extends JPanel {

    public Menu(Dimension d) {
        setSize(d);
        initComponents();
        
        setLayout(new BorderLayout());
        setBackground(Color.white);
    }

    public void initComponents() {
        panelCentro();
        panelInferior();

    }

    public void panelCentro() {
        add(contratos());
        add(productos());
        add(clientes());
        add(ventas());
        add(apartados());
        add(historicos());
    }

    public JPanel contratos() {
        JPanel pnlContratos = new JPanel();
        pnlContratos.setSize(getWidth() / 3 - 125, 300);
        pnlContratos.setLocation(100, 25);
        pnlContratos.setBackground(Color.white);

        JButtonRounded btnContratos = new JButtonRounded(0);
        btnContratos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnContratos.setSize(200, 200);
        btnContratos.setBackground(Color.white);
        btnContratos.setIcon(new ImageIcon(getClass().getResource("/res/imagenes/menu/contratos.png")));
        btnContratos.setLocation(pnlContratos.getWidth() / 2 - 100, 50);
        btnContratos.setBorderPainted(false);
        pnlContratos.add(btnContratos);

        JLabel lblContratos = new JLabel("CONTRATOS", JLabel.CENTER);
        lblContratos.setSize(200, 30);
        lblContratos.setFont(getFuente(0, 1, 24));
        lblContratos.setLocation(pnlContratos.getWidth() / 2 - 100, 270);
        pnlContratos.add(lblContratos);
        btnContratos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cp.cargarPanel(new CatalogoContratos(getSize()));
                cp.siguientePanel(cp.panelActual());
            }
        });

        pnlContratos.setLayout(new BorderLayout());
        return pnlContratos;

    }

    public JPanel productos() {
        JPanel pnlProductos = new JPanel();
        pnlProductos.setSize(getWidth() / 3, 300);
        pnlProductos.setLocation((getWidth() / 3), 25);
        pnlProductos.setBackground(Color.white);

        JButtonRounded btnProductos = new JButtonRounded(0);
        btnProductos.setSize(200, 200);
        btnProductos.setBackground(Color.white);
        btnProductos.setLocation(pnlProductos.getWidth() / 2 - 100, 50);
        btnProductos.setIcon(new ImageIcon(getClass().getResource("/res/imagenes/menu/productos.png")));
        btnProductos.setBorderPainted(false);
        btnProductos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pnlProductos.add(btnProductos);

        JLabel lblProductos = new JLabel("PRODUCTOS", JLabel.CENTER);
        lblProductos.setSize(200, 30);
        lblProductos.setFont(getFuente(0, 1, 24));
        lblProductos.setLocation(pnlProductos.getWidth() / 2 - 100, 270);
        pnlProductos.add(lblProductos);

        pnlProductos.setLayout(new BorderLayout());
        btnProductos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cp.cargarPanel(new CatalogoProductos(getSize()));
                cp.siguientePanel(cp.panelActual());
            }
        });
        return (pnlProductos);
    }

    public JPanel clientes() {
        JPanel pnlClientes = new JPanel();
        pnlClientes.setSize(getWidth() / 3 - 125, 300);
        pnlClientes.setLocation((getWidth() / 3) * 2 + 25, 25);
        pnlClientes.setBackground(Color.white);
        JButtonRounded btnClientes = new JButtonRounded(0);
        btnClientes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClientes.setSize(200, 200);
        btnClientes.setIcon(new ImageIcon(getClass().getResource("/res/imagenes/menu/clientes.png")));
        btnClientes.setLocation(pnlClientes.getWidth() / 2 - 100, 20);
        btnClientes.setBorderPainted(false);
        btnClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cp.cargarPanel(new CatalogoClientes(getSize()));
                cp.siguientePanel(cp.panelActual());
            }
        });
        pnlClientes.add(btnClientes);

        JLabel lblClientes = new JLabel("CLIENTES", JLabel.CENTER);
        lblClientes.setSize(200, 30);
        lblClientes.setFont(getFuente(0, 1, 24));
        lblClientes.setLocation(pnlClientes.getWidth() / 2 - 100, 270);
        pnlClientes.add(lblClientes);

        pnlClientes.setLayout(new BorderLayout());
        return pnlClientes;
    }

    public JPanel ventas() {
        JPanel pnlVentas = new JPanel();
        pnlVentas.setSize(getWidth() / 3-125, 300);
        pnlVentas.setLocation(100, 350);
        pnlVentas.setBackground(Color.white);
        JButtonRounded btnVentas = new JButtonRounded(0);
        btnVentas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVentas.setSize(200, 200);
        btnVentas.setBackground(Color.white);
        btnVentas.setLocation(pnlVentas.getWidth() / 2 - 100, 50);
        btnVentas.setBorderPainted(false);
        btnVentas.setIcon(new ImageIcon(getClass().getResource("/res/imagenes/menu/ventas.png")));
        btnVentas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cp.cargarPanel(new Venta(getSize()));
                cp.siguientePanel(cp.panelActual());
            }
        });
        pnlVentas.add(btnVentas);

        JLabel lblVentas = new JLabel("VENTAS", JLabel.CENTER);
        lblVentas.setSize(200, 30);
        lblVentas.setFont(getFuente(0, 1, 24));
        lblVentas.setLocation(pnlVentas.getWidth() / 2 - 100, 270);
        pnlVentas.add(lblVentas);

        pnlVentas.setLayout(new BorderLayout());
        return (pnlVentas);
    }

    public JPanel apartados() {
        JPanel pnlApartados = new JPanel();
        pnlApartados.setSize(getWidth() / 3, 300);
        pnlApartados.setLocation((getWidth() / 3), 350);
        pnlApartados.setBackground(Color.white);
        
        JButtonRounded btnApartados = new JButtonRounded(0);
        btnApartados.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnApartados.setSize(200, 200);
        btnApartados.setLocation(pnlApartados.getWidth() / 2 - 100, 50);
        btnApartados.setIcon(new ImageIcon(getClass().getResource("/res/imagenes/menu/apartados.png")));
        btnApartados.setBackground(Color.white);
        btnApartados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cp.cargarPanel(new CatalogoApartados(getSize()));
                cp.siguientePanel(cp.panelActual());
            }
        });
        pnlApartados.add(btnApartados);
        
        btnApartados.setBorderPainted(false);

        JLabel lblApartados = new JLabel("APARTADOS", JLabel.CENTER);
        lblApartados.setSize(200, 30);
        lblApartados.setFont(getFuente(0, 1, 24));
        lblApartados.setLocation(pnlApartados.getWidth() / 2 - 100, 270);
        pnlApartados.add(lblApartados);

        pnlApartados.setLayout(new BorderLayout());
        return pnlApartados;
    }

    public JPanel historicos() {
        JPanel pnlHistoricos = new JPanel();
        pnlHistoricos.setSize(getWidth() / 3-125, 300);
        pnlHistoricos.setLocation((getWidth() / 3) * 2+25, 350);
        pnlHistoricos.setBackground(Color.white);
        
        JButtonRounded btnHistoricos = new JButtonRounded(0);
        btnHistoricos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHistoricos.setSize(200, 200);
        btnHistoricos.setLocation(pnlHistoricos.getWidth() / 2 - 100, 50);
        btnHistoricos.setBorderPainted(false);
        btnHistoricos.setIcon(new ImageIcon(getClass().getResource("/res/imagenes/menu/historicos.png")));
        btnHistoricos.setBackground(Color.white);
        btnHistoricos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cp.cargarPanel(new HistorialVentas(getSize()));
                cp.siguientePanel(cp.panelActual());
            }
        });
        pnlHistoricos.add(btnHistoricos);

        JLabel lblHistoricos = new JLabel("HISTORICOS", JLabel.CENTER);
        lblHistoricos.setSize(200, 30);
        lblHistoricos.setFont(getFuente(0, 1, 24));
        lblHistoricos.setLocation(pnlHistoricos.getWidth() / 2 - 100, 270);
        pnlHistoricos.add(lblHistoricos);

        pnlHistoricos.setLayout(new BorderLayout());
        
        return pnlHistoricos;
    }

    public void panelInferior() {
        JButtonRounded btnAtras = new JButtonRounded(20,true);
        btnAtras.setLocation(100, 680);
        btnAtras.setSize(120, 60);
        btnAtras.setIcon(new ImageIcon(getClass().getResource("/res/imagenes/iu/atras.png")));
        btnAtras.setIconPosition(0);
        btnAtras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cp.inicio(cp.panelActual());
            }
        });
        add(btnAtras);
    }
}
