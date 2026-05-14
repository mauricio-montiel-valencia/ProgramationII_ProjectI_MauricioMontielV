package Controller;

import Model.ReceptionModel;
import View.Calendar;
import View.LogInWindow;
import View.Reception;
import View.Reports;
import View.ReservationsManagements;
import View.ResourceManagement;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReceptionController {
    
    private Reception view;
    private ReceptionModel model;
    String username, password, userType;
    
    
    
    public ReceptionController(ReceptionModel model, Reception view){
        
        this.model = model;
        this.view = view;
        addActionListeners();
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
                //logIn.setVisible(true);
            }
        };
        
        view.logOutButton.addActionListener(actionListenerLogOut);
    }
}
