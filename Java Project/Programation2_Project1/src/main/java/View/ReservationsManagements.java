package View;

import View.Reception;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ReservationsManagements extends JFrame{
    
    public JPanel panel;
    public TextField resourceNameTXT, startDateTXT, startHourTXT, finalDateTXT, finalHourTXT, reasonTXT;
    public JButton addReservation, cancelReservation, reservationDisponibility, returnButton;
    
    public DefaultTableModel tableModel;
    public JTable table;
    
    public ReservationsManagements(){
        
        initializeComponents();
    }
    
    public void initializeComponents(){
    
        makeWindow();
        makePanel();
        setLabels();
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
    
        JLabel resourceName = new JLabel("Resource:");
        resourceName.setBounds(50, 100, 100, 30);
        panel.add(resourceName);
        
        JLabel startDate = new JLabel("Start Date:");
        startDate.setBounds(50, 150, 100, 30);
        panel.add(startDate);
        
        JLabel startHour = new JLabel("Start Hour:");
        startHour.setBounds(50, 200, 100, 30);
        panel.add(startHour);
        
        JLabel finalDate = new JLabel("Final Date:");
        finalDate.setBounds(50, 250, 100, 30);
        panel.add(finalDate);
        
        JLabel finalHour = new JLabel("Final Hour:");
        finalHour.setBounds(50, 300, 100, 30);
        panel.add(finalHour);
        
        JLabel reason = new JLabel("Reason:");
        reason.setBounds(50, 350, 100, 30);
        panel.add(reason);
    }
    
    private void createTextFields(){
    
        resourceNameTXT = new TextField();
        resourceNameTXT.setBounds(150, 100, 100, 30);
        panel.add(resourceNameTXT);
        
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
    
        addReservation = new JButton("Add Reservation");
        addReservation.setBounds(150, 400, 150, 30);
        panel.add(addReservation);
        
        reservationDisponibility = new JButton("Reservation Disponibility");
        reservationDisponibility.setBounds(125, 450, 200, 30);
        panel.add(reservationDisponibility);
        
        cancelReservation = new JButton("Cancel Reservation");
        cancelReservation.setBounds(150, 500, 150, 30);
        panel.add(cancelReservation);
        
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
