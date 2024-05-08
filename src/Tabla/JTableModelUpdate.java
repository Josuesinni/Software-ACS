/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tabla;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class JTableModelUpdate extends DefaultTableModel{

    public JTableModelUpdate() {
    }
    
    public void removeColumn(int column) {
        columnIdentifiers.remove(column);
        for (Object row : dataVector) {
            ((Vector) row).remove(column);
        }
        fireTableStructureChanged();
    }

    public void addColumn(int column,String element) {
        columnIdentifiers.add(column, element);
        for (Object row : dataVector) {
            ((Vector) row).add(column);
        }
        fireTableStructureChanged();
    }
}
