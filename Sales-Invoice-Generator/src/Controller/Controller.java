package Controller;

import Model.InvoiceHeader;
import Model.InvoiceLines;
import Model.InHeaderTableM;
import Model.InLinesTableM;
import View.Frame;
import View.InHeaderDialog;
import View.InLineDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class Controller implements ActionListener ,ListSelectionListener {
    
    private Frame  frame;
    private InHeaderDialog invoiceDialog;
    private InLineDialog lineDialog;
    
    public Controller (Frame  frame){
        this.frame = frame;
    
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            String actionCommand = e.getActionCommand();
            System.out.println("Action "+ actionCommand );
            switch (actionCommand){
                case "Save File":
                    saveFile();
                    break;
                case "Load File":
                    loadFile() ;
                    break;
                case "Create New Invoice":
                    createNewInvoice();
                    break;
                case "Delet Invoice":
                    deletInvoice();
                    break;
                case "Create New Item":
                     createNewItem();
                    break;
                case "Delet Item":
                    deletItem();
                    break;
                case "createInvoiceOK":
                    createInvoiceOK();
                    break;
                case "createInvoiceCancel":
                    createInvoiceCancel();
                    break;
                case "createItemOK":
                    createItemOK();
                    break;
                case "createItemCancel":
                    createItemCancel();
                    break;
                    
            }   
    }
    
     @Override 
    public void valueChanged(ListSelectionEvent e) {
        int selectedIndex =frame.getInvoiceTable().getSelectedRow();
        if(selectedIndex != -1){
         InvoiceHeader currentInvoice = frame.getInHeader().get(selectedIndex);
         frame.getInvoiceNumberLabel().setText(""+currentInvoice.getInNum());
         frame.getInvoiceDateTF().setText(currentInvoice.getInDate());
         frame.getCustomerNameTF().setText(currentInvoice.getCustomerName());
         frame.getInvoiceTotalLabel().setText(""+currentInvoice.getInTotal());
         
         InLinesTableM lTableM = new InLinesTableM (currentInvoice.getInLines());
         frame.getLineTable().setModel(lTableM);
         lTableM.fireTableDataChanged();
                               


        }
    }   
    
     private void saveFile() {
          ArrayList<InvoiceHeader> inHeaders = frame.getInHeader();
          String headers = "";
          String lines = "";
          for (InvoiceHeader invoice : inHeaders) {
            String invCSV = invoice.getAsCsvFiles();
            headers += invCSV;
            headers += "\n";
         for (InvoiceLines line : invoice.getInLines()) {
                String lineCSV = line.getAsCsvFiles();
                lines += lineCSV;
                lines += "\n";
            }
        }
      
        try {
            JFileChooser fc = new JFileChooser();
            int result = fc.showSaveDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = fc.getSelectedFile();
                FileWriter headerFileWriter = new FileWriter(headerFile);
                headerFileWriter.write(headers);
                headerFileWriter.flush();
                headerFileWriter.close();
                result = fc.showSaveDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File lineFile = fc.getSelectedFile();
                    FileWriter lineFileWriter = new FileWriter(lineFile);
                    lineFileWriter.write(lines);
                    lineFileWriter.flush();
                    lineFileWriter.close();
                     JOptionPane.showMessageDialog(frame,
                            "save succesful", "INFORMATION",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (Exception ex) {

        }
     }
    
    private void loadFile() {
        JFileChooser fc = new JFileChooser();
        try{
        int result= fc.showOpenDialog(frame );
        if(result == JFileChooser.APPROVE_OPTION){
        File headerFile = fc.getSelectedFile();
        Path headerPath = Paths.get(headerFile.getAbsolutePath());
        List<String> headerLines = Files.readAllLines(headerPath);
       
        ArrayList<InvoiceHeader> invoicesArray = new ArrayList<>();
        for(String headerLine : headerLines ){
        try {    
         String[] headerParts = headerLine.split(",");
         int invoiceNum = Integer.parseInt(headerParts[0]);
         String invoiceDate = headerParts[1];
         String CustomerName = headerParts[2];
         
         InvoiceHeader inHeader = new InvoiceHeader(invoiceNum,invoiceDate,CustomerName);
         invoicesArray.add(inHeader);
         }catch(Exception ex){
        ex.printStackTrace();
         JOptionPane.showMessageDialog(frame, 
                 "Error in Header format", "Error",
                 JOptionPane.ERROR_MESSAGE);

         }       

        }
        result= fc.showOpenDialog(frame);
        if(result == JFileChooser.APPROVE_OPTION){
             File lineFile = fc.getSelectedFile();
             Path linePath = Paths.get(lineFile.getAbsolutePath());
             List<String> lineLines = Files.readAllLines(linePath);
             JOptionPane.showMessageDialog(frame, 
                    "Files Loded.\n Please select your Invoice.", "Information",
                    JOptionPane.INFORMATION_MESSAGE);
             
             
        for(String lineLine : lineLines ){
            try {
                String[] lineParts = lineLine.split(",");
                int invoiceNum = Integer.parseInt(lineParts[0]);
                String itemName = lineParts[1];
                double itemPrice = Double.parseDouble(lineParts[2]);
                int count =Integer.parseInt(lineParts[3]);
                InvoiceHeader inH = null;
                for(InvoiceHeader invoice :invoicesArray){
                    if(invoice.getInNum() == invoiceNum){
                       inH = invoice ;
                       break;
                  }
                  }
                    InvoiceLines inline = new InvoiceLines(itemName,itemPrice,count, inH);
                    inH.getInLines().add(inline);
                       
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, 
                        "Error in line format", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }          
        }
        
        }
        frame.setInHeader(invoicesArray);
        InHeaderTableM inTableM = new InHeaderTableM(invoicesArray);
        frame.setInvoicesTable(inTableM);
        frame.getInvoiceTable().setModel(inTableM);
        frame.getInHeaderTableM().fireTableDataChanged();
        }
        }catch(IOException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, 
                    "Cannot read file", "Error",
                    JOptionPane.ERROR_MESSAGE);
      
            }
       
    }

    
    private void createNewInvoice() {
      invoiceDialog = new InHeaderDialog(frame); 
      invoiceDialog.setVisible(true);
       

    }

    private void deletInvoice() {
        int selectedRow = frame.getInvoiceTable().getSelectedRow();
        if (selectedRow!= -1 );
        frame.getInHeader().remove(selectedRow);
        frame.getInHeaderTableM().fireTableDataChanged(); 
        
        JOptionPane.showMessageDialog(frame,
                            "Delet Invoice Successful", "INFORMATION",
                            JOptionPane.INFORMATION_MESSAGE);

    }

    private void createNewItem() {
        lineDialog = new InLineDialog(frame);
        lineDialog.setVisible(true);
    }

    private void deletItem() {
         int selectedRow = frame.getLineTable().getSelectedRow();

        if (selectedRow != -1) {
       InLinesTableM lTableM = (InLinesTableM) frame.getLineTable().getModel();
       lTableM.getLines().remove(selectedRow);
       lTableM.fireTableDataChanged();
       frame.getInHeaderTableM().fireTableDataChanged();
       JOptionPane.showMessageDialog(frame,
                            "Delet Item Successful", "INFORMATION",
                            JOptionPane.INFORMATION_MESSAGE);

       
        }
    }

    private void createInvoiceOK() {
         String date = invoiceDialog.getInvDateField().getText();
        String customer = invoiceDialog.getCustNameField().getText();
        int num = frame.getNextInNum();
        
        try {
            String[] dateParts = date.split("-");   
            if (dateParts.length < 3) {
                JOptionPane.showMessageDialog(frame, 
                        "Wrong date format", "Error", 
                        JOptionPane.ERROR_MESSAGE);
            } else {
                int day = Integer.parseInt(dateParts[0]);
                int month = Integer.parseInt(dateParts[1]);
                int year = Integer.parseInt(dateParts[2]);
                if (day > 31 || month > 12) {
                    JOptionPane.showMessageDialog(frame,
                            "Wrong date format", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } 
                else {
        
        InvoiceHeader inHeader = new InvoiceHeader(num, date,
                customer);
        frame.getInHeader().add(inHeader);
        frame.getInHeaderTableM().fireTableDataChanged();
        JOptionPane.showMessageDialog(frame, 
                    "Create Invoice Successfull", "Information",
                    JOptionPane.INFORMATION_MESSAGE);
        
        invoiceDialog.setVisible(false);
        invoiceDialog.dispose();
        invoiceDialog = null;
                }
            }
         } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, 
                    "Wrong date format",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
                
    }  
    
    private void createInvoiceCancel() {
        invoiceDialog.setVisible(false);
        invoiceDialog.dispose();
        invoiceDialog = null;
    }

   

    private void createItemOK() {
        String item = lineDialog.getItemNameField().getText();
        String priceStr = lineDialog.getItemPriceField().getText();
        String countStr = lineDialog.getItemCountField().getText();
        double price = Double.parseDouble(priceStr);
        int count = Integer.parseInt(countStr);
        
        int selectedInvoice = frame.getInvoiceTable().getSelectedRow();
        if (selectedInvoice != -1) {
            InvoiceHeader inHeader = frame.getInHeader().get(selectedInvoice);
            InvoiceLines lines = new InvoiceLines(item, price, count, inHeader);
            inHeader.getInLines().add(lines);
            InLinesTableM lTableM = (InLinesTableM) frame.getLineTable().getModel();
            lTableM.fireTableDataChanged();
            frame.getInHeaderTableM().fireTableDataChanged();
            frame.getInvoiceTotalLabel().setText(""+inHeader.getInTotal());
         }
        lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog = null;
        JOptionPane.showMessageDialog(frame, 
                    "Create Item Successfull", "Information",
                    JOptionPane.INFORMATION_MESSAGE);
    }

    private void createItemCancel() {
        lineDialog.setVisible(false);
         lineDialog.dispose();
         lineDialog = null;
    }
   
   
    
}
