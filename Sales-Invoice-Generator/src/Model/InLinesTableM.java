package Model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;


public class InLinesTableM extends AbstractTableModel{
    
      private ArrayList<InvoiceLines> inLines; 
      private String[] columns = {"No.","Item Name","Item Price"," Count","Item Total"};
      
      public InLinesTableM(ArrayList<InvoiceLines> inLines) {
        this.inLines = inLines;
      }
      public ArrayList<InvoiceLines> getLines() {
        return inLines;
    }
      
    @Override
    public int getRowCount() {
            return inLines.size();
      }

    @Override
    public int getColumnCount() {
    return columns.length;

            }
@Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
          InvoiceLines inLine = inLines.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return inLine.getInHeader().getInNum();
                case 1:
                    return inLine.getItName();
                case 2:
                    return inLine.getItPrice();
                case 3 :
                    return  inLine.getCount();
                case 4 :
                    return  inLine.getLineTotal();
                default:
                        return "   ";
        }
    }
}
    

