package View;

import View.Reception;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Reports extends JFrame{
    
    public JPanel panel;
    public DefaultTableModel tableModel;
    public JTable table;
    public JButton userHistoryButton, returnButton;
 
    public Reports(){
        
        initializeComponents();
    }
    
    public void initializeComponents(){
    
        createWindow();
        createPanel();
        createTable();
        createButtons();
    }
    
    private void createWindow(){
    
        setSize(1100, 600);
        setTitle("Reports");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    private void createPanel(){
    
        panel = new JPanel();
        panel.setLayout(null);
        this.add(panel);
    }
    
    private void createTable(){
    
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        
        tableModel.addColumn("Username");
        tableModel.addColumn("Resource Name");
        tableModel.addColumn("Resource Type");
        tableModel.addColumn("Start Hour / Date");
        tableModel.addColumn("Final Hour / Date");
        tableModel.addColumn("Total Hours");
        tableModel.addColumn("Solicitude Date");
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(100, 100, 900, 400);
        panel.add(scrollPane);
    }
    
    private void createButtons(){
    
        userHistoryButton = new JButton("User Historial");
        userHistoryButton.setBounds(490, 520, 150, 30);
        panel.add(userHistoryButton);
        
        returnButton = new JButton("Return");
        returnButton.setBounds(10, 10, 100, 30);
        panel.add(returnButton);
    }
}
