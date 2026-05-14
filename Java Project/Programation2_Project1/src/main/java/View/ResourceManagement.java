package View;

import View.Reception;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class ResourceManagement extends JFrame{
    
    public JPanel panel;
    public JComboBox typeOfResource, spaceType, gearStatus;
    public String[] types = {"Space", "Gear"};
    public String[] spaceType_ = {"Classroom", "Laboratory"};
    public TextField resourceNameTXT, capacityTXT, locationTXT, restrictionsTXT;
    public TextField brandTXT, modelTXT, serialTXT, statusTXT;
    public JButton insertButton, registerButton, editButton, eliminateButton, returnButton;
    
    public JLabel resourceNameLabel, capacityLabel, restrictionsLabel, spaceTypeLabel, locationLabel;
    public JLabel brandLabel, modelLabel, serialLabel, statusLabel;
    
    public JTable table;
    public DefaultTableModel tableModel;
    
    public int selectedRow, selectedRow2, counter = 0;
    
    public ResourceManagement(){

        initializeComponents();
    }
    
    private void initializeComponents(){
    
        createWindow();
        createPanel();
        typeOfResource();
        addButtons();
        createLabelSpaceResources();
        createWindowResources_S();
        createGearLabels();
        createWindowGearResources();
        addTable();
    }
    
    private void createWindow(){
    
        setSize(1000, 600);
        setTitle("Resource Management");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    private void createPanel(){
    
        panel = new JPanel();
        panel.setLayout(null);
        this.add(panel);
    }
    
    private void typeOfResource(){
    
        typeOfResource = new JComboBox(types);
        typeOfResource.setBounds(50, 50, 100, 30);
        panel.add(typeOfResource);
    }
    
    private void addButtons(){
    
        insertButton = new JButton("Insert");
        insertButton.setBounds(150, 50, 100, 30);
        panel.add(insertButton);
        
        registerButton = new JButton("Register");
        registerButton.setBounds(100, 500, 100, 30);
        panel.add(registerButton);
        registerButton.setVisible(false);
        
        editButton = new JButton("Edit");
        editButton.setBounds(200, 500, 70, 30);
        panel.add(editButton);
        editButton.setVisible(false);
        
        eliminateButton = new JButton("Eliminate");
        eliminateButton.setBounds(270, 500, 100, 30);
        panel.add(eliminateButton);
        eliminateButton.setVisible(false);
        
        returnButton = new JButton("Return");
        returnButton.setBounds(10, 10, 100, 30);
        panel.add(returnButton);
    }
    
    private void createLabelSpaceResources(){
        
        resourceNameLabel = new JLabel("Resource Name:");
        resourceNameLabel.setBounds(50, 150, 100, 30);
        panel.add(resourceNameLabel);
        resourceNameLabel.setVisible(false);
    
        capacityLabel = new JLabel("Capacity:");
        capacityLabel.setBounds(50, 200, 100, 30);
        panel.add(capacityLabel);
        capacityLabel.setVisible(false);
        
        locationLabel = new JLabel("Location:");
        locationLabel.setBounds(50, 250, 100, 30);
        panel.add(locationLabel);
        locationLabel.setVisible(false);
        
        spaceTypeLabel = new JLabel("Space Type:");
        spaceTypeLabel.setBounds(50, 300, 100, 30);
        panel.add(spaceTypeLabel);
        spaceTypeLabel.setVisible(false);
        
        restrictionsLabel = new JLabel("Restrictions:");
        restrictionsLabel.setBounds(50, 350, 100, 30);
        panel.add(restrictionsLabel);
        restrictionsLabel.setVisible(false);
    }
    
    private void createWindowResources_S(){
    
        //capacityTXT, location, spaceType,restrictions;
        //AVERIGUAR COMO ORDENAR;
        
        //resourceNameTXT = new TextField();
        //resourceNameTXT.setBounds(220, 150, 100, 30);
        //panel.add(resourceNameTXT);
        //resourceNameTXT.setVisible(false);
        
        capacityTXT = new TextField();
        capacityTXT.setBounds(200, 200, 100, 30);
        panel.add(capacityTXT);
        
        capacityTXT.setVisible(false);
        
        locationTXT = new TextField();
        locationTXT.setBounds(200, 250, 100, 30);
        panel.add(locationTXT);
        
        locationTXT.setVisible(false);
        
        spaceType = new JComboBox(spaceType_);
        spaceType.setBounds(200, 300, 100, 30);
        panel.add(spaceType);
        
        spaceType.setVisible(false);
        
        restrictionsTXT = new TextField();
        restrictionsTXT.setBounds(200, 350, 100, 30);
        panel.add(restrictionsTXT);
        
        restrictionsTXT.setVisible(false);
    }
    
    private void createGearLabels(){
        
        resourceNameLabel = new JLabel("Resource Name:");
        resourceNameLabel.setBounds(50, 150, 100, 30);
        panel.add(resourceNameLabel);
        resourceNameLabel.setVisible(false);
    
        brandLabel = new JLabel("Brand:");
        brandLabel.setBounds(50, 200, 40, 30);
        panel.add(brandLabel);
        brandLabel.setVisible(false);
        
        modelLabel = new JLabel("Model:");
        modelLabel.setBounds(50, 250, 40, 30);
        panel.add(modelLabel);
        modelLabel.setVisible(false);
        
        serialLabel = new JLabel("Serial:");
        serialLabel.setBounds(50, 300, 40, 30);
        panel.add(serialLabel);
        serialLabel.setVisible(false);
        
        statusLabel = new JLabel("Status:");
        statusLabel.setBounds(50, 350, 40, 30);
        panel.add(statusLabel);
        statusLabel.setVisible(false);
    }
    
    private void createWindowGearResources(){
    
        //brand, model, serial, status;
        
        resourceNameTXT = new TextField();
        resourceNameTXT.setBounds(150, 150, 100, 30);
        panel.add(resourceNameTXT);
        resourceNameTXT.setVisible(false);
        
        brandTXT = new TextField();
        brandTXT.setBounds(100, 200, 100, 30);
        panel.add(brandTXT);
        brandTXT.setVisible(false);
        
        modelTXT = new TextField();
        modelTXT.setBounds(100, 250, 100, 30);
        panel.add(modelTXT);
        modelTXT.setVisible(false);
        
        serialTXT = new TextField();
        serialTXT.setBounds(100, 300, 100, 30);
        panel.add(serialTXT);
        serialTXT.setVisible(false);
        
        statusTXT = new TextField();
        statusTXT.setBounds(100, 350, 100, 30);
        panel.add(statusTXT);
        statusTXT.setVisible(false);
    }
    
    
    
    public void addSpaceRLabels(Boolean signal){
    
        if(signal){
        
            resourceNameLabel.setVisible(true);
            capacityLabel.setVisible(true);
            locationLabel.setVisible(true);
            spaceTypeLabel.setVisible(true);
            restrictionsLabel.setVisible(true);
        }
        
        if(!signal){
            
            capacityLabel.setVisible(false);
            locationLabel.setVisible(false);
            spaceTypeLabel.setVisible(false);
            restrictionsLabel.setVisible(false);
        }
    }
    
    public void spaceResources(Boolean signal){
    
        if(signal){
        
            resourceNameTXT.setVisible(true);
            capacityTXT.setVisible(true);
            locationTXT.setVisible(true);
            spaceType.setVisible(true);
            restrictionsTXT.setVisible(true);
            registerButton.setVisible(true);
            editButton.setVisible(true);
            eliminateButton.setVisible(true);
        }
        if(!signal){
        
            capacityTXT.setVisible(false);
            locationTXT.setVisible(false);
            spaceType.setVisible(false);
            locationTXT.setVisible(false);
            restrictionsTXT.setVisible(false);
        }
    }
    
    public void addGearRLabels(Boolean signal){
    
        if(signal){
            
            resourceNameLabel.setVisible(true);
            brandLabel.setVisible(true);
            modelLabel.setVisible(true);
            serialLabel.setVisible(true);
            statusLabel.setVisible(true);
        }
        
        if(!signal){
            
            resourceNameLabel.setVisible(false);
            brandLabel.setVisible(false);
            modelLabel.setVisible(false);
            serialLabel.setVisible(false);
            statusLabel.setVisible(false);
        }
    }
    
    public void typeResources(Boolean signal){
    
        if(signal){
            
            resourceNameTXT.setVisible(true);
            brandTXT.setVisible(true);
            modelTXT.setVisible(true);
            serialTXT.setVisible(true);
            statusTXT.setVisible(true);
            registerButton.setVisible(true);
            editButton.setVisible(true);
            eliminateButton.setVisible(true);
        }
        
        if(!signal){
        
            resourceNameTXT.setVisible(false);
            brandTXT.setVisible(false);
            modelTXT.setVisible(false);
            serialTXT.setVisible(false);
            statusTXT.setVisible(false);
        }
    }
    
    
    
    private void addTable(){
    
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        
        tableModel.addColumn("Resource Name");
        tableModel.addColumn("Resource Class");
        tableModel.addColumn("Type");
        tableModel.addColumn("Date");
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(400, 150, 400, 300);
        panel.add(scrollPane);
    }
    
    public void addRows(String[] tableComponents){
    
        tableModel.addRow(tableComponents);
    }
    
    public void clearTable(){
        
    tableModel.setRowCount(0);
    }

}
