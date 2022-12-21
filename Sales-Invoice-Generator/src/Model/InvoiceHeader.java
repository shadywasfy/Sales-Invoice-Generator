package Model;

//*invoice header data, invoiceNum, invoiceDate, customerName, and invoice total*/

import java.util.ArrayList;

public class InvoiceHeader {
    private int inNum;
    private String inDate;
    private String customerName;
    private ArrayList<InvoiceLines> inLines;
   
    
    public InvoiceHeader(){}

    public InvoiceHeader(int inNum, String inDate, String customerName) {
        this.inNum = inNum;
        this.inDate = inDate;
        this.customerName = customerName;
    }

    public double getInTotal(){
        double total =0.0;
        for( InvoiceLines line : getInLines() ){
            
            total += line.getLineTotal();
                }
    return total;
    }
   
    

    public int getInNum() {
        return inNum;
    }

    public ArrayList<InvoiceLines> getInLines() {
        if(inLines == null){
                inLines = new ArrayList<>();
        }
        return inLines;
    }

    public void setInNum(int inNum) {
        this.inNum = inNum;
    }

    public String getInDate() {
        return inDate;
    }

    public void setInDate(String inDate) {
        this.inDate = inDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAsCsvFiles(){
        return inNum +  "," + inDate + "," + customerName;
    }
    @Override
    public String toString() {
        return "InvoiceHeader{" + "inNum=" + inNum + ", inDate=" + inDate 
                + ", customerName=" + customerName +  '}';
    }
    
    
    
}
