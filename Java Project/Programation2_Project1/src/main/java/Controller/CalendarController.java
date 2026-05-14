package Controller;

import Model.ReceptionModel;
import View.Calendar;
import View.Reception;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;

public class CalendarController {
    
    Calendar calendarView;
    String currentUsername, currentPassword, currentUserType;
    
    public CalendarController(Calendar calendarView, String currentUsername, String currentPassword, String currentUserType){
   
        this.calendarView = calendarView;
        this.currentUsername = currentUsername;
        this.currentPassword = currentPassword;
        this.currentUserType = currentUserType;
        addActionListeners();
    }
    
    private void addActionListeners(){
    
        ActionListener actionListenerReturn = new ActionListener(){
        
            public void actionPerformed(ActionEvent e){
            
                calendarView.setVisible(false);
                ReceptionModel model = new ReceptionModel();
                model.setName(currentUsername);
                model.setPassword(currentPassword);
                model.setUserType(currentUserType);
                
                Reception reception = new Reception(model);
                ReceptionController receptionController = new ReceptionController(model, reception);
            }
        };
        
        calendarView.returnButton.addActionListener(actionListenerReturn);
        
        ActionListener actionListenerDateData = new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e){
            
                Date selectedDate = calendarView.dateChooser.getDate();
                
                if(selectedDate != null){
                
                    LocalDate dateChose = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    
                    int day = dateChose.getDayOfMonth();
                    String month = dateChose.getMonth().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("en"));
                    int year = dateChose.getYear();
                    
                    calendarView.dateLabel.setText(month + " " + day + ", " + year);
                }
            }
        };
        
        calendarView.showDateData.addActionListener(actionListenerDateData);
    }
}
