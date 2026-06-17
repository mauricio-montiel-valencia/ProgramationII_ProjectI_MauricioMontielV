package View;

import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Calendar extends JFrame{
    
    public JPanel panel = new JPanel();
    public JButton returnButton, showDateData, filterButton, resetFiltersButton, dateRangeFilterButton;
    public DefaultTableModel tableModel;
    public JTable table;
    public JComboBox comboBoxFilter, comboBoxStatusFilter;
    public JLabel dateLabel, filterLabel;
    public JDateChooser dateChooser, dateRangeFilter1, dateRangeFilter2;
    public JTextField nameFilterTXT, resourceFilterTXT;
    public JRadioButton typeFilterR1, typeFilterR2;
    
    public String[] filters = {"Username", "Resource", "Type", "Status", "Date"};
    public String[] statusFilters = {"Approved", "Disapproved", "Pending"};
    
    
    public Calendar(){
        
        initializeComponents();
    }
    
    public void initializeComponents(){
    
        makeWindow();
        makePanel();
        addJTextFields();
        createButtons();
        createCalendar();
        addTable();
        addComboBox();
        createLabel();
        addRadioButtons_Type();
    }
    
    private void makeWindow(){
    
        setSize(1100, 600);
        setTitle("Calendar");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    private void makePanel(){
    
        panel = new JPanel();
        panel.setLayout(null);
        this.add(panel);
    }
    
    private void addJTextFields(){
    
        nameFilterTXT = new JTextField();
        nameFilterTXT.setBounds(320, 10, 100, 30);
        panel.add(nameFilterTXT);
        nameFilterTXT.setVisible(false);
        
        resourceFilterTXT = new JTextField();
        resourceFilterTXT.setBounds(320, 10, 100, 30);
        panel.add(resourceFilterTXT);
        resourceFilterTXT.setVisible(false);
    }
    
    private void createButtons(){
    
        returnButton = new JButton("Return");
        returnButton.setBounds(10, 10, 100, 30);
        panel.add(returnButton);
        
        resetFiltersButton = new JButton("Reset Filter");
        resetFiltersButton.setBounds(10, 50, 100, 30);
        panel.add(resetFiltersButton);
        
        showDateData = new JButton("Show Date Data");
        showDateData.setBounds(475, 10, 150, 30);
        panel.add(showDateData);
        
        filterButton = new JButton("Filter");
        filterButton.setBounds(320, 50, 100, 30);
        panel.add(filterButton);
        filterButton.setVisible(false);
        
        dateRangeFilterButton = new JButton("Filter Date");
        dateRangeFilterButton.setBounds(420, 10, 100, 30);
        panel.add(dateRangeFilterButton);
        dateRangeFilterButton.setVisible(false);
    }
    
    private void createCalendar(){
    
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd-MM-yyyy");
        dateChooser.setBounds(800, 10, 200, 30); 
        panel.add(dateChooser);
        
        dateRangeFilter1 = new JDateChooser();
        dateRangeFilter1.setDateFormatString("dd-MM-yyyy");
        dateRangeFilter1.setBounds(320, 10, 100, 30);
        panel.add(dateRangeFilter1);
        dateRangeFilter1.setVisible(false);
        
        dateRangeFilter2 = new JDateChooser();
        dateRangeFilter2.setDateFormatString("dd-MM-yyyy");
        dateRangeFilter2.setBounds(320, 50, 100, 30);
        panel.add(dateRangeFilter2);
        dateRangeFilter2.setVisible(false);
    }
    
    private void addTable(){
    
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        
        tableModel.addColumn("Username");
        tableModel.addColumn("Resource Name");
        tableModel.addColumn("Resource Type");
        tableModel.addColumn("Status");
        tableModel.addColumn("Solicitude Date");
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(100, 100, 900, 400);
        panel.add(scrollPane);
    }
    
    private void addComboBox(){

        comboBoxFilter = new JComboBox(filters);
        comboBoxFilter.setBounds(200, 10, 100, 30);
        panel.add(comboBoxFilter);
        
        comboBoxStatusFilter = new JComboBox(statusFilters);
        comboBoxStatusFilter.setBounds(320, 10, 100, 30);
        panel.add(comboBoxStatusFilter);
        comboBoxStatusFilter.setVisible(false);
    }
    
    private void createLabel(){
    
        dateLabel = new JLabel();
        dateLabel.setBounds(500, 70, 180, 30);
        panel.add(dateLabel);
        
        filterLabel = new JLabel("Filter:");
        filterLabel.setBounds(150, 10, 70, 30);
        panel.add(filterLabel);
    }
    
    private void addRadioButtons_Type(){
    
        typeFilterR1 = new JRadioButton("Space Resource");
        typeFilterR1.setBounds(320, 10, 150, 30);
        panel.add(typeFilterR1);
        typeFilterR1.setVisible(false);
        
        typeFilterR2 = new JRadioButton("Gear Resource");
        typeFilterR2.setBounds(320, 50, 150, 30);
        panel.add(typeFilterR2);
        typeFilterR2.setVisible(false);
        
        ButtonGroup buttonGroup = new ButtonGroup(); 
        buttonGroup.add(typeFilterR1);
        buttonGroup.add(typeFilterR2);
    }
}
