/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author DELL
 */
public class InLineDialog extends JDialog{
    private JTextField itemNameField;
    private JTextField itemPriceField;
    private JTextField itemCountField;
    private JLabel itemNameLbl;
    private JLabel itemPriceLbl;
    private JLabel itemCountLbl;
    private JButton okBtn;
    private JButton cancelBtn;
    
    public InLineDialog(Frame frame) {
        itemNameField = new JTextField(15);
        itemNameLbl = new JLabel("  Item Name");
        
        itemPriceField = new JTextField(15);
        itemPriceLbl = new JLabel("  Item Price");
        
        itemCountField = new JTextField(15);
        itemCountLbl = new JLabel("  Item Count");
        
        okBtn = new JButton("OK");
        cancelBtn = new JButton("Cancel");
        
        okBtn.setActionCommand("createItemOK");
        cancelBtn.setActionCommand("createItemCancel");
        
        okBtn.addActionListener(frame.getController());
        cancelBtn.addActionListener(frame.getController());
        setLayout(new GridLayout(4, 2));
        setLocation(450, 300);
        
        add(itemNameLbl);
        add(itemNameField);
        add(itemPriceLbl);
        add(itemPriceField);
        add(itemCountLbl);
        add(itemCountField);
        add(okBtn);
        add(cancelBtn);
        
        pack();
    }

    public JTextField getItemNameField() {
        return itemNameField;
    }

    public JTextField getItemPriceField() {
        return itemPriceField;
    }
    
    public JTextField getItemCountField() {
        return itemCountField;
    }
}
