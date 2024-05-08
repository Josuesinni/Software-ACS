package Principal;

import InicioSesion.IniciarSesion;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JPanel;

public class CambiarPaneles {

    ArrayList<JPanel> paneles = new ArrayList<JPanel>();

    public CambiarPaneles(JPanel pnlPrincipal) {
        paneles.add(pnlPrincipal);
    }

    public void cargarPanel(JPanel panel) {
        paneles.add(panel);
    }

    public void cargarPaneles(JPanel[] panels) {
        paneles.addAll(Arrays.asList(panels));
    }

    public void siguientePanel(int index) {
        if (index > 0) {
            paneles.get(0).removeAll();
            paneles.get(0).add(paneles.get(index), BorderLayout.CENTER);
            paneles.get(0).revalidate();
            paneles.get(0).repaint();
        }
    }

    public void panelAnterior(int index) {
        if (index > 0) {
            paneles.get(0).removeAll();
            paneles.get(0).add(paneles.get(index - 1), BorderLayout.CENTER);
            paneles.get(0).revalidate();
            paneles.get(0).repaint();
            paneles.remove(index);
        }
    }

    public JPanel getPanelActual(int index) {
        return paneles.get(index);
    }

    public int panelActual() {
        return paneles.size() - 1;
    }

    public void inicio(int index) {
        paneles.remove(1);
        paneles.get(0).removeAll();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        IniciarSesion is=new IniciarSesion(screenSize);
        paneles.add(is);
        paneles.get(0).add(is, BorderLayout.CENTER);
        paneles.get(0).revalidate();
        paneles.get(0).repaint();
        paneles.remove(index);
    }
}
