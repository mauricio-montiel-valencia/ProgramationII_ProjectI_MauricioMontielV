package View;

import View.Reception;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ReservationsManagements extends JFrame{
    
    public JPanel panel;
    public TextField startDateTXT, startHourTXT, finalDateTXT, finalHourTXT, reasonTXT;
    public JButton addReservationButton, cancelReservationButton, reservationDisponibilityButton, returnButton, approvedButton, disapprovedButton;
    public JComboBox resourceNameJC;
    public JLabel resourceName, startDate, startHour, finalDate, finalHour, reason;
    
    public DefaultTableModel tableModel;
    public JTable table;
    
    public ReservationsManagements(){
        
        initializeComponents();
    }
    
    public void initializeComponents(){
    
        makeWindow();
        makePanel();
        setLabels();
        createComboBox();
        createTextFields();
        addButtons();
        makeTable();
    }
    
    private void makeWindow(){
    
        setSize(1100, 600);
        setTitle("Reservations Managements");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    private void makePanel(){
    
        panel = new JPanel();
        panel.setLayout(null);
        this.add(panel);
    }
    
    private void setLabels(){
    
        resourceName = new JLabel("Resource:");
        resourceName.setBounds(50, 100, 100, 30);
        panel.add(resourceName);
        
        startDate = new JLabel("Start Date:");
        startDate.setBounds(50, 150, 100, 30);
        panel.add(startDate);
        
        startHour = new JLabel("Start Hour:");
        startHour.setBounds(50, 200, 100, 30);
        panel.add(startHour);
        
        finalDate = new JLabel("Final Date:");
        finalDate.setBounds(50, 250, 100, 30);
        panel.add(finalDate);
        
        finalHour = new JLabel("Final Hour:");
        finalHour.setBounds(50, 300, 100, 30);
        panel.add(finalHour);
        
        reason = new JLabel("Reason:");
        reason.setBounds(50, 350, 100, 30);
        panel.add(reason);
    }
        
    private void createComboBox(){
    
        resourceNameJC = new JComboBox();
        resourceNameJC.setBounds(150, 100, 100, 30);
        panel.add(resourceNameJC);
    }
    
    private void createTextFields(){
        
        startDateTXT = new TextField();
        startDateTXT.setBounds(150, 150, 100, 30);
        panel.add(startDateTXT); 
        
        startHourTXT = new TextField();
        startHourTXT.setBounds(150, 200, 100, 30);
        panel.add(startHourTXT);
        
        finalDateTXT = new TextField();
        finalDateTXT.setBounds(150, 250, 100, 30);
        panel.add(finalDateTXT);
        
        finalHourTXT = new TextField();
        finalHourTXT.setBounds(150, 300, 100, 30);
        panel.add(finalHourTXT);
        
        reasonTXT = new TextField();
        reasonTXT.setBounds(150, 350, 100, 30);
        panel.add(reasonTXT);
    }
    
    private void addButtons(){
    
        addReservationButton = new JButton("Add Reservation");
        addReservationButton.setBounds(150, 400, 150, 30);
        panel.add(addReservationButton);
        
        reservationDisponibilityButton = new JButton("Reservation Disponibility");
        reservationDisponibilityButton.setBounds(125, 450, 200, 30);
        panel.add(reservationDisponibilityButton);
        
        cancelReservationButton = new JButton("Cancel Reservation");
        cancelReservationButton.setBounds(150, 500, 150, 30);
        panel.add(cancelReservationButton);
        
        approvedButton = new JButton("Aproved Reservation");
        approvedButton.setBounds(100, 200, 170, 40);
        panel.add(approvedButton);
        
        disapprovedButton = new JButton("Disaproved Reservation");
        disapprovedButton.setBounds(100, 300, 180, 40);
        panel.add(disapprovedButton);
        
        returnButton = new JButton("Return");
        returnButton.setBounds(10, 10, 100, 30);
        panel.add(returnButton);
    }
    
    private void makeTable(){
    
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        
        tableModel.addColumn("Resource Name");
        tableModel.addColumn("Start Hour / Date");
        tableModel.addColumn("Finale Hour / Date");
        tableModel.addColumn("Status");
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(400, 50, 500, 400);
        panel.add(scrollPane);
    }
}
