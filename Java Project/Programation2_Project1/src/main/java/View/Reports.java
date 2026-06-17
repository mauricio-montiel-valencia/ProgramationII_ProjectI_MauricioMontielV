package View;

import View.Reception;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class Reports extends JFrame{
    
    public JPanel panel;
    public DefaultTableModel tableModel;
    public JTable table;
    public JButton userHistoryButton, returnButton, returnToUsersButton;
    public JScrollPane scrollPane;
 
    public Reports(){
        
        initializeComponents();
    }
    
    public void initializeComponents(){
    
        createWindow();
        createPanel();
        createUserTable();
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
    
    public void createUserTable(){
        
        if(scrollPane != null){panel.remove(scrollPane);}
        
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        tableModel.addColumn("Usernames");
        
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(100, 100, 900, 400);
        panel.add(scrollPane);
        
        revalidate();
        repaint();
    }
    
    public void addRowToTable(Object[] row){if(tableModel != null){tableModel.addRow(row);}}
    
    public String getSelectedUser(){
    
        int selectedRow = table.getSelectedRow();
        
        if(selectedRow != -1 && tableModel != null){return tableModel.getValueAt(selectedRow, 0).toString();}
        
        return null;
    }
    
    public void setupHistoryTable(){
        
        if(scrollPane != null){panel.remove(scrollPane);}
    
        if(tableModel != null){tableModel.setRowCount(0); tableModel.setColumnCount(0);}
        else{
            tableModel = new DefaultTableModel();
            table = new JTable(tableModel);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }
        
        tableModel.addColumn("Username");
        tableModel.addColumn("Resource Name");
        tableModel.addColumn("Resource Type");
        tableModel.addColumn("Start Hour / Date");
        tableModel.addColumn("Final Hour / Date");
        tableModel.addColumn("Total Hours");
        tableModel.addColumn("Solicitude Date");
    
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(100, 100, 900, 400);
        panel.add(scrollPane);
        
        revalidate();
        repaint();
    }
    
    private void createButtons(){
    
        userHistoryButton = new JButton("User Historial");
        userHistoryButton.setBounds(490, 520, 150, 30);
        panel.add(userHistoryButton);
        
        returnButton = new JButton("Return");
        returnButton.setBounds(10, 10, 100, 30);
        panel.add(returnButton);
        
        returnToUsersButton = new JButton("Users");   
        returnToUsersButton.setBounds(10, 50, 100, 30);
        panel.add(returnToUsersButton);
    }
}
