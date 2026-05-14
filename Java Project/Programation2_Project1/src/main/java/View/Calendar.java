package View;


import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Calendar extends JFrame{
    
    public JPanel panel = new JPanel();
    public JButton returnButton, showDateData;
    public DefaultTableModel tableModel;
    public JTable table;
    public JComboBox comboBox;
    public JLabel dateLabel, filterLabel;
    public JDateChooser dateChooser;
    
    public String[] filters = {"Username", "Resource", "Type", "Status", "Date"};
    
    
    public Calendar(){
        
        initializeComponents();
    }
    
    public void initializeComponents(){
    
        makeWindow();
        makePanel();
        createButtons();
        createCalendar();
        addTable();
        addComboBox();
        createLabel();
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
    
    private void createButtons(){
    
        returnButton = new JButton("Return");
        returnButton.setBounds(10, 10, 100, 30);
        panel.add(returnButton);
        
        showDateData = new JButton("Show Date Data");
        showDateData.setBounds(475, 10, 150, 30);
        panel.add(showDateData);
    }
    
    private void createCalendar(){
    
        dateChooser = new JDateChooser();

        // 2. Configurar el formato de fecha (opcional pero recomendado)
        dateChooser.setDateFormatString("dd-MM-yyyy");

        // 3. Añadirlo a un panel o al frame directamente
        // Si usas un layout nulo o específico, recuerda darle tamaño:
        dateChooser.setBounds(800, 10, 200, 30); 
        panel.add(dateChooser);
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

        comboBox = new JComboBox(filters);
        comboBox.setBounds(200, 10, 100, 30);
        panel.add(comboBox);
    }
    
    private void createLabel(){
    
        dateLabel = new JLabel();
        dateLabel.setBounds(510, 70, 100, 30);
        panel.add(dateLabel);
        
        filterLabel = new JLabel("Filter:");
        filterLabel.setBounds(150, 10, 70, 30);
        panel.add(filterLabel);
    }
    
    
}
