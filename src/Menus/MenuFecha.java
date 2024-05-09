package Menus;

import BaseDeDatos.Control.GestionApartados;
import BaseDeDatos.Control.GestionContratos;
import BaseDeDatos.Control.GestionVentas;
import BaseDeDatos.Control.Miscelanea;
import Calendario.DateChooser;
import Calendario.EventDateChooser;
import Calendario.SelectedAction;
import Calendario.SelectedDate;
import SwingModificado.JCheckBoxCustom;
import SwingModificado.JTextFieldRounded;
import Utilidades.Recursos;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MenuFecha extends JDialog {

    //private JDialog dialog;
    private JLabel lblDe;
    private JLabel lblHasta;
    private JTextFieldRounded fechaIni;
    private JTextFieldRounded fechaFin;
    private JPanel pnlPersonalizado;
    JPanel pnlFondo;

    public MenuFecha() {
        initComponents();
        pnlFondo.setLayout(new BorderLayout());
        setUndecorated(true);
        setContentPane(pnlFondo);
        setBackground(new Color(0, 0, 0, 0));
    }
    JCheckBoxCustom hoy, semana, mes, personalizado, cualquierFecha;

    private void initComponents() {
        pnlFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                //g2.setColor(Color.white);
                //g2.setColor(new Color(255, 224, 163));
                g2.setColor(new Color(240, 240, 240));

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Area borde = new Area(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 40, 40));
                Area headear = new Area(new Rectangle2D.Double(0, 0, getWidth(), 40));
                borde.add(headear);
                g2.fill(borde);
            }
        };
        //AQUIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII
        pnlFondo.setBackground(new Color(240, 240, 240));
        //pnlFondo.setBackground(new Color(255, 224, 163));
        //pnlFondo.setBorder(new LineBorder(Color.gray, 2, true));
        //pnlFondo.setBackground(Color.white);

        cualquierFecha = new JCheckBoxCustom("De cualquier fecha", Recursos.FUENTE_GENERAL);
        cualquierFecha.setLocation(20, 10);
        cualquierFecha.setSize(180, 30);
        cualquierFecha.setBackground(pnlFondo.getBackground());
        cualquierFecha.setOver(Color.black);
        cualquierFecha.setSelected(true);
        cualquierFecha.addItemListener((e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                setDates(-1);
                establecerFechas();
            }
        });
        pnlFondo.add(cualquierFecha);

        hoy = new JCheckBoxCustom("De hoy", Recursos.FUENTE_GENERAL);
        hoy.setLocation(20, 40);
        hoy.setSize(150, 30);
        hoy.setBackground(pnlFondo.getBackground());
        hoy.setSelected(Color.black);
        hoy.addItemListener((e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                setDates(0);
                establecerFechas();
            }
        });
        pnlFondo.add(hoy);
        semana = new JCheckBoxCustom("De esta semana", Recursos.FUENTE_GENERAL);
        semana.setLocation(20, 70);
        semana.setSize(150, 30);
        semana.setBackground(pnlFondo.getBackground());
        semana.addItemListener((e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                setDates(1);
                establecerFechas();
            }
        });
        pnlFondo.add(semana);
        mes = new JCheckBoxCustom("De este mes", Recursos.FUENTE_GENERAL);
        mes.setLocation(20, 100);
        mes.setSize(150, 30);
        mes.setBackground(pnlFondo.getBackground());
        mes.addItemListener((e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                setDates(2);
                establecerFechas();
            }
        });
        pnlFondo.add(mes);
        personalizado = new JCheckBoxCustom("Personalizado", Recursos.FUENTE_GENERAL);
        personalizado.setLocation(20, 130);
        personalizado.setSize(150, 30);
        personalizado.setBackground(pnlFondo.getBackground());
        personalizado.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    fechaIni.setVisible(true);
                    fechaFin.setVisible(true);
                    lblDe.setVisible(true);
                    lblHasta.setVisible(true);
                    pnlPersonalizado.setVisible(true);
                    pnlPersonalizado.setSize(getWidth(), 80);
                    resizeJDialog(getWidth(), getHeight() + 100);
                    setDates(3);
                    establecerFechas();
                } else {
                    fechaIni.setVisible(false);
                    fechaFin.setVisible(false);
                    lblDe.setVisible(false);
                    lblHasta.setVisible(false);
                    pnlPersonalizado.setVisible(false);
                    resizeJDialog(getWidth(), getHeight() - 100);
                }
            }
        });
        pnlFondo.add(personalizado);
        ButtonGroup btg = new ButtonGroup();
        btg.add(cualquierFecha);
        btg.add(hoy);
        btg.add(semana);
        btg.add(mes);
        btg.add(personalizado);
        panelPersonalizado();
    }

    public void resizeJDialog(int w, int h) {
        setSize(w, h);
    }

    public void setDialogPosition(int x, int y) {
        setLocation(x, y);
    }

    public void showDialog() {
        setVisible(true);
    }

    public void hideDialog() {
        dispose();
    }

    public void panelPersonalizado() {
        pnlPersonalizado = new JPanel();
        pnlPersonalizado.setLocation(0, 170);
        pnlPersonalizado.setSize(getWidth(), 80);
        pnlPersonalizado.setBackground(pnlFondo.getBackground());
        pnlPersonalizado.setVisible(false);

        lblDe = new JLabel("De:");
        lblDe.setLocation(40, 0);
        lblDe.setSize(60, 30);
        lblDe.setVisible(false);
        lblDe.setFont(Recursos.FUENTE_GENERAL);
        pnlPersonalizado.add(lblDe);

        fechaIni = new JTextFieldRounded("", 20, Recursos.FUENTE_GENERAL_2);
        fechaIni.setLocation(100, 0);
        fechaIni.setSize(100, 30);
        fechaIni.setVisible(false);
        fechaIni.setHorizontalAlignment(JLabel.CENTER);
        fechaIni.setBackground(Color.white);
        pnlPersonalizado.add(fechaIni);

        lblHasta = new JLabel("Hasta:");
        lblHasta.setLocation(40, 40);
        lblHasta.setSize(60, 30);
        lblHasta.setVisible(false);
        lblHasta.setFont(Recursos.FUENTE_GENERAL);
        pnlPersonalizado.add(lblHasta);

        fechaFin = new JTextFieldRounded("", 20, Recursos.FUENTE_GENERAL_2);
        fechaFin.setLocation(100, 40);
        fechaFin.setSize(100, 30);
        fechaFin.setVisible(false);
        fechaFin.setHorizontalAlignment(JLabel.CENTER);
        fechaFin.setBackground(Color.white);
        pnlPersonalizado.add(fechaFin);

        DateChooser fFin = new DateChooser();
        fFin.setTextRefernce(fechaFin);
        DateChooser fIni = new DateChooser();
        fIni.setTextRefernce(fechaIni);
        fIni.addEventDateChooser(new EventDateChooser() {
            @Override
            public void dateSelected(SelectedAction action, SelectedDate sd) {
                try {
                    String dateIni = sd.getYear() + "-" + sd.getMonth() + "-" + sd.getDay();
                    String dateFin = fFin.getSelectedDate().getYear() + "-" + fFin.getSelectedDate().getMonth() + "-" + fFin.getSelectedDate().getDay();
                    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                    Date dIni = formato.parse(dateIni);
                    Date dFin = formato.parse(dateFin);
                    String act = sd.getDay() + "-" + sd.getMonth() + "-" + sd.getYear();
                    if (txtCiclo1 != null) {
                        if (dIni.after(dFin)) {
                            fFin.setSelectedDate(new SimpleDateFormat("dd-MM-yyyy").parse(act));
                            txtCiclo2.setText(act);
                        }
                        txtCiclo1.setText(act);
                    }
                    if (txtInicio != null) {
                        if (dIni.after(dFin)) {
                            fFin.setSelectedDate(new SimpleDateFormat("dd-MM-yyyy").parse(act));
                            txtFin.setText(act);
                        }
                        txtInicio.setText(act);
                    }
                    casos();
                } catch (ParseException ex) {
                }
            }
        });
        fFin.addEventDateChooser(new EventDateChooser() {
            @Override
            public void dateSelected(SelectedAction action, SelectedDate sd) {
                try {
                    String fechaFin = sd.getYear() + "-" + sd.getMonth() + "-" + sd.getDay();
                    String fechaIni = fIni.getSelectedDate().getYear() + "-" + fIni.getSelectedDate().getMonth() + "-" + fIni.getSelectedDate().getDay();
                    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                    Date dIni = formato.parse(fechaIni);
                    Date dFin = formato.parse(fechaFin);
                    String act = sd.getDay() + "-" + sd.getMonth() + "-" + sd.getYear();
                    if (txtCiclo1 != null) {
                        if (dFin.before(dIni)) {
                            fIni.setSelectedDate(new SimpleDateFormat("dd-MM-yyyy").parse(act));
                            txtCiclo1.setText(act);
                        }
                        txtCiclo2.setText(act);
                    }
                    if (txtInicio != null) {
                        if (dFin.before(dIni)) {
                            fIni.setSelectedDate(new SimpleDateFormat("dd-MM-yyyy").parse(act));
                            txtInicio.setText(act);
                        }
                        txtFin.setText(act);
                    }
                    casos();
                } catch (ParseException ex) {
                }
            }
        });

        pnlPersonalizado.setLayout(new BorderLayout());
        pnlFondo.add(pnlPersonalizado);
    }
    String fechas[] = new String[2];

    public void setDates(int tipo) {
        Calendar hoyFecha = Calendar.getInstance();
        switch (tipo) {
            case -1:
                fechas[0] = "";
                fechas[1] = "";
            case 0:
                fechas[0] = hoyFecha.get(Calendar.DATE) + "-" + (hoyFecha.get(Calendar.MONTH) + 1) + "-" + hoyFecha.get(Calendar.YEAR);
                fechas[1] = hoyFecha.get(Calendar.DATE) + "-" + (hoyFecha.get(Calendar.MONTH) + 1) + "-" + hoyFecha.get(Calendar.YEAR);
                break;
            case 1:
                Calendar lunes = Calendar.getInstance();
                Calendar domingo = Calendar.getInstance();
                hoyFecha.setFirstDayOfWeek(Calendar.MONDAY);
                int diaHoy = hoyFecha.get(Calendar.DAY_OF_WEEK);
                if (diaHoy < hoyFecha.getFirstDayOfWeek()) {
                    diaHoy += Calendar.SATURDAY;
                }
                lunes.add(Calendar.DATE, hoyFecha.getFirstDayOfWeek() - diaHoy);
                int ndias = hoyFecha.getFirstDayOfWeek() + Calendar.SATURDAY - diaHoy - 1;
                domingo.add(Calendar.DATE, ndias);
                fechas[0] = lunes.get(Calendar.DATE) + "-" + (lunes.get(Calendar.MONTH) + 1) + "-" + lunes.get(Calendar.YEAR);
                fechas[1] = domingo.get(Calendar.DATE) + "-" + (domingo.get(Calendar.MONTH) + 1) + "-" + domingo.get(Calendar.YEAR);
                break;
            case 2:
                fechas[0] = "1" + "-" + (hoyFecha.get(Calendar.MONTH) + 1) + "-" + hoyFecha.get(Calendar.YEAR);
                fechas[1] = hoyFecha.getActualMaximum(Calendar.DATE) + "-" + (hoyFecha.get(Calendar.MONTH) + 1) + "-" + hoyFecha.get(Calendar.YEAR);
                break;
            default:
                fechas[0] = fechaIni.getText();
                fechas[1] = fechaFin.getText();
                break;
        }
        casos();
    }

    private void casos() {
        if (tblLista != null) {
            switch (tipo) {
                case 0:
                    if (cualquierFecha.isSelected()) {
                        Miscelanea.CargarTabla(GestionApartados.buscarApartadoPorEstado(estado), tblLista, true);
                        fechas[0]=(Miscelanea.getFechaMin("fecha_inicio","apartado"));
                        txtCiclo1.setText(Miscelanea.getFechaMin("fecha_inicio","apartado"));
                    } else {
                        System.out.println(fechas[0] + " " + fechas[1]);
                        Miscelanea.CargarTabla(GestionApartados.vistaApartadosDe(fechas[0], fechas[1], estado), tblLista, true);
                    }
                    break;
                case 1:
                    if (cualquierFecha.isSelected()) {
                        Miscelanea.CargarTabla(GestionContratos.vistaContratos(), tblLista, true);
                    } else {
                        Miscelanea.CargarTabla(GestionContratos.vistaContratosDe(fechas[0], fechas[1], estado2), tblLista, true);
                    }
                    for (int i = 0; i < tblLista.getRowCount(); i++) {
                        tblLista.setValueAt("$" + tblLista.getValueAt(i, 4), i, 4);
                    }
                    break;
                case 2:
                    if (cualquierFecha.isSelected()) {
                        fechas[0] = Miscelanea.getFechaMin("fecha","venta");
                        Miscelanea.CargarTabla(GestionVentas.vistaVentas(), tblLista, true);
                        for (int i = 0; i < tblLista.getRowCount(); i++) {
                            tblLista.setValueAt("$" + tblLista.getValueAt(i, 3), i, 3);
                        }
                    }
                    break;
            }
        }
    }

    public String[] getDates() {
        return fechas;
    }
    JTextFieldRounded txtCiclo1, txtCiclo2;
    JLabel txtInicio, txtFin;

    public void setTextCiclos(JTextFieldRounded txtCiclo1, JTextFieldRounded txtCiclo2) {
        this.txtCiclo1 = txtCiclo1;
        this.txtCiclo2 = txtCiclo2;
    }

    public void setTextCiclos(JLabel txtInicio, JLabel txtFin) {
        this.txtInicio = txtInicio;
        this.txtFin = txtFin;
    }

    public void establecerFechas() {
        if (txtCiclo1 != null) {
            txtCiclo1.setText(fechas[0]);
            txtCiclo2.setText(fechas[1]);
        } else {
            if (txtInicio != null) {
                txtInicio.setText(fechas[0]);
                txtFin.setText(fechas[1]);
            }
        }
    }
    JTable tblLista;
    String estado;
    int tipo;
    int estado2;

    public void addTablaLista(JTable tblLista, String estado) {
        this.tblLista = tblLista;
        this.estado = estado;
    }

    public void setEstado2(int estado2) {
        this.estado2 = estado2;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
