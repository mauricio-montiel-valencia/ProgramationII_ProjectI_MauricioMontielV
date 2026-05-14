package Controller;

import Model.ReceptionModel;
import View.Reception;
import View.Reports;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReportsController {
    
    String currentUsername, currentPassword, currentUserType;
    Reports reportsView;
    
    public ReportsController(Reports reportsView, String currentUsername, String currentPassword, String currentUserType){
    
        this.reportsView = reportsView;
        this.currentUsername = currentUsername;
        this.currentPassword = currentPassword;
        this.currentUserType = currentUserType;
        addActionListeners();
    }
    
    private void addActionListeners(){
    
        ActionListener actionListenerReturn = new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e){
            
                reportsView.setVisible(false);
                ReceptionModel model = new ReceptionModel();
                model.setName(currentUsername);
                model.setPassword(currentPassword);
                model.setUserType(currentUserType);
                
                Reception reception = new Reception(model);
                ReceptionController receptionController = new ReceptionController(model, reception);
            }
        };
        
        reportsView.returnButton.addActionListener(actionListenerReturn);
    }
}
