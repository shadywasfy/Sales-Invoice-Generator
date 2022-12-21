package Model;

/**
 invoice lines as follows: Invoice Number, Item Name, Item Price, and Count, i.e. number of items purchased,
 */
public class InvoiceLines {
   
    private String itName;
    private double itPrice;
    private int count;
    private  InvoiceHeader inHeader;


    public InvoiceLines( String itName, double itPrice, int count,
            InvoiceHeader inHeader) {
         
        this.itName = itName;
        this.itPrice = itPrice;
        this.count = count;
        this.inHeader = inHeader;
    }

    public double getLineTotal(){
        return itPrice * count;
    }
            
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

   

    public String getItName() {
        return itName;
    }

    public void setItName(String itName) {
        this.itName = itName;
    }

    public double getItPrice() {
        return itPrice;
    }

    public void setItPrice(double itPrice) {
        this.itPrice = itPrice;
    }

        public InvoiceHeader getInHeader() {
        return inHeader;
    }

    public void setInHeader(InvoiceHeader inHeader) {
        this.inHeader = inHeader;
    }
    
    public String getAsCsvFiles(){
        return inHeader.getInNum() +  "," + itName + "," + itPrice+  "," + count ;
    }
    @Override
    public String toString() {
        return "InvoiceLines{" + "inNum=" + inHeader.getInNum() + ", itName=" + 
                itName + ", itPrice=" + itPrice + ", count=" + count +  '}';
    }
    
    
}

