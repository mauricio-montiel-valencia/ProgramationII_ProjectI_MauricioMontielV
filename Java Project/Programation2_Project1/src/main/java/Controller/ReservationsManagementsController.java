package Controller;

import Model.ReceptionModel;
import View.Reception;
import View.ReservationsManagements;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReservationsManagementsController {
    
    ReservationsManagements rManagements;
    String currentUsername, currentPassword, currentUserType;
    
    public ReservationsManagementsController(ReservationsManagements rManagements, String currentUsername, String currentPassword, String currentUserType){
    
        this.rManagements = rManagements;
        this.currentUsername = currentUsername;
        this.currentPassword = currentPassword;
        this.currentUserType = currentUserType;
        addActionListeners();
    }
    
    private void addActionListeners(){
    
        ActionListener actionListenerReturn = new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e){
            
                rManagements.setVisible(false);
                ReceptionModel model = new ReceptionModel();
                model.setName(currentUsername);
                model.setPassword(currentPassword);
                model.setUserType(currentUserType);
                
                Reception reception = new Reception(model);
                ReceptionController recepController = new ReceptionController(model, reception);
            }
        };
        
        rManagements.returnButton.addActionListener(actionListenerReturn);
    }
}
