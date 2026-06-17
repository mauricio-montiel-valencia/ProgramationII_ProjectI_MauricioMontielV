package View;

import Model.ReceptionModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Reception extends JFrame{
    
    public JPanel panel;
    public JButton resourceManagement, reservationsManagements, checkAvailabilityAndCalendar, reportsButton, logOutButton;
    public String name, password, userType;
    
    public Reception(){
   
        initializeComponents();
    }
    
    public void initializeComponents(){
    
        createWindow();
        createPanel();
        addButtons();
    }
    
    private void createWindow(){
        
        setSize(800, 600);
        setTitle("Reception");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
    
    private void createPanel(){
    
        panel = new JPanel();
        panel.setLayout(null);
        this.add(panel);
    }
    
    private void addButtons(){
    
        resourceManagement = new JButton("Resource Management");
        resourceManagement.setBounds(220, 100, 200, 30);
        panel.add(resourceManagement);
        
        reservationsManagements = new JButton("Reservations Managements");
        reservationsManagements.setBounds(220, 200, 250, 30);
        panel.add(reservationsManagements);
        
        checkAvailabilityAndCalendar = new JButton("Check Availability and Calendar");
        checkAvailabilityAndCalendar.setBounds(200, 300, 350, 30);
        panel.add(checkAvailabilityAndCalendar);
        
        reportsButton = new JButton("Reports");
        reportsButton.setBounds(290, 400, 100, 30);
        panel.add(reportsButton);
        
        logOutButton = new JButton("Log Out");
        logOutButton.setBounds(10, 10, 100, 30);
        panel.add(logOutButton);
    }
}
