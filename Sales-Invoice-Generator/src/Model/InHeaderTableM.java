package Model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;


public class InHeaderTableM extends AbstractTableModel{
    
      private ArrayList<InvoiceHeader> inHeader; 
      private String[] columns = {"No.","Date","Customer","Total"};
   


    public InHeaderTableM(ArrayList<InvoiceHeader> inHeader) {
        this.inHeader = inHeader;
    }
    
 
    @Override
    public int getRowCount() {
        return inHeader.size();
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
        InvoiceHeader invoice = inHeader.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return invoice.getInNum();
                case 1:
                    return invoice.getInDate();
                case 2:
                    return invoice.getCustomerName();
                case 3 :
                    return  invoice.getInTotal();
                default:
                        return "   ";
        }

    }
}
