package Tabla;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.NumberFormatter;

public class CantidadCellEditor extends DefaultCellEditor {

    private EventCellInputChange event;
    private JSpinner input;
    private JTable table;
    private int row;
    private ModelItemSell item;
    private SpinnerNumberModel numberModel;

    public CantidadCellEditor(EventCellInputChange event) {
        super(new JCheckBox());
        input = new JSpinner();
        this.event = event;

        numberModel = (SpinnerNumberModel) input.getModel();
        numberModel.setMinimum(1);
        numberModel.setMaximum(10);
        JFormattedTextField txt = ((JSpinner.DefaultEditor) input.getEditor()).getTextField();
        ((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);
        txt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {
                    e.consume();
                }
            }
        });
        JSpinner.NumberEditor editor = (JSpinner.NumberEditor) input.getEditor();
        DefaultFormatter formatter = (DefaultFormatter) editor.getTextField().getFormatter();
        formatter.setCommitsOnValidEdit(true);
        editor.getTextField().setHorizontalAlignment(SwingConstants.CENTER);
        input.addChangeListener((ChangeEvent e) -> {
            inputChange();
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        super.getTableCellEditorComponent(table, value, isSelected, row, column);
        this.table = table;
        this.row = row;
        this.item = new ModelItemSell(table.getValueAt(row, 0).toString(),
                Integer.parseInt(table.getValueAt(row, 2).toString()),
                Double.parseDouble(table.getValueAt(row, 1).toString().substring(1)),
                Double.parseDouble(table.getValueAt(row, 3).toString().substring(1)));
        int cantidad = Integer.parseInt(value.toString());
        input.setValue(cantidad);
        input.setEnabled(false);
        enable();
        return input;
    }

    private void enable() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                    input.setEnabled(true);
                } catch (InterruptedException ex) {
                }
            }
        }).start();
    }

    @Override
    public Object getCellEditorValue() {
        return input.getValue();
    }

    private void inputChange() {
        int cant = Integer.parseInt(input.getValue().toString());
        DecimalFormat df = new DecimalFormat("##0.##");
        int max = getMaximumInput();
        if (cant > max) {
            input.setValue(max);
            cant = max;
        }
        if (cant != item.getQty()) {
            item.setQty(cant);
            item.setTotal(item.getPrice() * cant);
            table.setValueAt("$ " + df.format(item.getTotal()), row, 3);
            event.inputChanged();
        }
    }

    public void setMaximumInput(int max) {
        numberModel.setMaximum(max);
    }

    public int getMaximumInput() {
        return Integer.parseInt(numberModel.getMaximum().toString());
    }
}
