package Controller;

import Model.DataBaseConnection;
import Model.ReceptionModel;
import View.Calendar;
import View.LogInWindow;
import View.Reception;
import View.Reports;
import View.ReservationsManagements;
import View.ResourceManagement;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReceptionController {
    
    private Reception view;
    private ReceptionModel model;
    String username, password, userType;
    
    PreparedStatement ps;
    ResultSet rs;
    DataBaseConnection dbConnection = new DataBaseConnection();
 
    
    public ReceptionController(ReceptionModel model, Reception view){
        
        this.model = model;
        this.view = view;
        addActionListeners();
        configureButtonsByUser();
    }
    
     private void configureButtonsByUser(){
        
        username = model.getName();
        password = model.getPassword();
        userType = model.getUserType();
        
        boolean resourceMgmtFlag = true;
        boolean reservationsMgmtFlag = true;
        boolean reportsFlag = true;
        
        try{
        
            ps = dbConnection.connection_.prepareStatement("select userType from users where userName = ? and userPassword = ?");
            
            ps.setString(1, username);
            ps.setString(2, password);
            
            rs = ps.executeQuery();
            
            if(rs.next()){
            
                String userTypeDB = rs.getString("userType");
                
                switch(userTypeDB){
                
                    case "Final User": resourceMgmtFlag = false; reportsFlag = false; break;
                    case "Person in Charge": resourceMgmtFlag = false; reservationsMgmtFlag = true; break;
                    case "Administrator": resourceMgmtFlag = true; reservationsMgmtFlag = true; reportsFlag = true; break;
                }
            }
            
        }catch(Exception ex){System.out.println("Error: " + ex);}
        
        view.resourceManagement.setEnabled(resourceMgmtFlag);
        view.reservationsManagements.setEnabled(reservationsMgmtFlag);
        view.reportsButton.setEnabled(reportsFlag);
        view.checkAvailabilityAndCalendar.setEnabled(true);
        view.logOutButton.setEnabled(true);
    }
    
    private void addActionListeners(){
        
        username = model.getName();
        password = model.getPassword();
        userType = model.getUserType();
        
        ActionListener actionListenerResourceM = new ActionListener(){
        
            public void actionPerformed(ActionEvent e){
                
                view.setVisible(false);
                ResourceManagement resourceManagement = new ResourceManagement();
                ResourceManagementsController rsMController = new ResourceManagementsController(resourceManagement, username, password, userType);
            }
        };
        
        view.resourceManagement.addActionListener(actionListenerResourceM);
        
        ActionListener actionListenerReservationsM = new ActionListener(){
        
            public void actionPerformed(ActionEvent e){
            
                view.setVisible(false);
                ReservationsManagements reservationsManagements = new ReservationsManagements();
                ReservationsManagementsController reMController = new ReservationsManagementsController(reservationsManagements, username, password, userType);
            }
        };
        
        view.reservationsManagements.addActionListener(actionListenerReservationsM);
        
        ActionListener actionListenerCalendar = new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e){
            
                view.setVisible(false);
                Calendar calendar = new Calendar();
                CalendarController calendarControler = new CalendarController(calendar, username, password, userType);
            }
        };
        
        view.checkAvailabilityAndCalendar.addActionListener(actionListenerCalendar);
        
        ActionListener actionListenerReports = new ActionListener(){
        
            public void actionPerformed(ActionEvent e){
            
                view.setVisible(false);
                Reports reports = new Reports();
                ReportsController reportsController = new ReportsController(reports, username, password, userType);
            }
        };
        
        view.reportsButton.addActionListener(actionListenerReports);
        
        ActionListener actionListenerLogOut = new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e){
            
                view.setVisible(false);
                LogInWindow logIn = new LogInWindow();
                LogInWindowController loginController = new LogInWindowController(logIn);
                loginController.initializeLogInWindow();
            }
        };
        
        view.logOutButton.addActionListener(actionListenerLogOut);
    }
}
