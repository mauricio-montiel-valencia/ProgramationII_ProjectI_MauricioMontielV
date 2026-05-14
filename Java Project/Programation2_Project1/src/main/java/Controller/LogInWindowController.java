package Controller;

import Model.ReceptionModel;
import View.LogInWindow;
import View.Reception;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class LogInWindowController {
    
    LogInWindow logInController;
    ReceptionModel receptionModel = new ReceptionModel();
    Boolean response = false;
    
    public LogInWindowController(LogInWindow logInWindow) {
        this.logInController = logInWindow;
    }
    
    public void initializeLogInWindow(){
        
        actionListener();
    }
    
    private void actionListener(){
    
        ActionListener actionListener = new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e){
            
                String getName = logInController.nameTXT.getText().trim();
                String getPassword = new String(logInController.passwordTXT.getText()).trim();
                String getType = logInController.comboBox.getSelectedItem().toString();
                System.out.println(getType);
                
                if(getName.equals("Valeria") && getPassword.equals("1234") && getType.equals("Administrator")){
                
                    logInController.setVisible(false);
                    
                    ReceptionModel model = new ReceptionModel();
                    model.setName(getName);
                    model.setPassword(getPassword);
                    model.setUserType(getType);
                    Reception Rview = new Reception(model);
                    ReceptionController receptionController = new ReceptionController(model, Rview);
                    response = true;
                }
                if(getName.equals("Melody") && getPassword.equals("5678") && getType.equals("Person in Charge")){
                
                    logInController.setVisible(false);
                    
                    ReceptionModel model = new ReceptionModel();
                    model.setName(getName);
                    model.setPassword(getPassword);
                    model.setUserType(getType);
                    Reception Rview = new Reception(model);
                    ReceptionController receptionController = new ReceptionController(model, Rview);
                    response = true;
                }
                if(getName.equals("Michael") && getPassword.equals("ñ") && getType.equals("Final User")){
                
                    ReceptionModel model = new ReceptionModel();
                    model.setName(getName);
                    model.setPassword(getPassword);
                    model.setUserType(getType);
                    Reception Rview = new Reception(model);   
                    ReceptionController receptionController = new ReceptionController(model, Rview);
                    response = true;
                }
                
                if(!response){
                
                    JOptionPane.showMessageDialog(null, "This User doesn't exist, please try with another one.");
                }
            }
        };
        
        logInController.logInButton.addActionListener(actionListener);
    }
}
