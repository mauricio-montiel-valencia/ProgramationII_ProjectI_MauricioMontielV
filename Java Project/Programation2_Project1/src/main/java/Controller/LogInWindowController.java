package Controller;

import Model.DataBaseConnection;
import Model.ReceptionModel;
import View.LogInWindow;
import View.Reception;
import View.ResourceManagement;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class LogInWindowController {
    
    LogInWindow logInController;
    ReceptionModel receptionModel = new ReceptionModel();
    Boolean response = false;
    
    ResourceManagement rsManagement;
    PreparedStatement ps;
    ResultSet rs;
    DataBaseConnection dbConnection = new DataBaseConnection();
    
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
                
                if(authenticateUserInDatabase(getName, getPassword, getType)){
                
                    logInController.setVisible(false);
                    
                    ReceptionModel model = new ReceptionModel();
                    model.setName(getName);
                    model.setPassword(getPassword);
                    model.setUserType(getType);
                    
                    Reception rView = new Reception();
                    ReceptionController receptionController = new ReceptionController(model, rView);
                    response = true;
                }
                
                if(!response){
                
                    JOptionPane.showMessageDialog(null, "This User doesn't exist, please try with another one.");
                }
            }
        };
        
        logInController.logInButton.addActionListener(actionListener);
        
        
        logInController.createUserButton.addActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent e){
            
                String getUsername = logInController.nameTXT.getText().trim();
                String getPassword = logInController.passwordTXT.getText().trim();
                String getUserType = logInController.comboBox.getSelectedItem().toString();
                
                try{
                    
                    ps = dbConnection.connection_.prepareStatement("select * from users where userName = ?");
                    
                    ps.setString(1, getUsername);
                    rs = ps.executeQuery();
                    
                    if(rs.next()){
                    
                        JOptionPane.showMessageDialog(null, "This user already exits, please try with another one"); return;
                    }
                    
                    ps = dbConnection.connection_.prepareStatement("insert into users(userName, userPassword, userType) values (?, ?, ?)");
                    ps.setString(1, getUsername);
                    ps.setString(2, getPassword);
                    ps.setString(3, getUserType);
                    
                    int result = ps.executeUpdate();
                    
                    if(result > 0){
                        
                        System.out.println("Data Successfully Inserted");
                        JOptionPane.showMessageDialog(null, "User Successfully Created");
                        
                        logInController.nameTXT.setText("");
                        logInController.passwordTXT.setText("");
                        }
                        else{
                        
                        System.out.println("Data Unsuccessfully Inserted");
                        JOptionPane.showMessageDialog(null, "This User couldn't be created");
                        }
                        
                    }catch(Exception ex){System.out.println("Error: " + ex);}
            }
        });  
    }
    
    private boolean authenticateUserInDatabase(String username, String password, String userType){
        
        try{
            ps = dbConnection.connection_.prepareStatement("select * from users where userName = ? and userPassword = ? and userType = ?");
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, userType);
            
            rs = ps.executeQuery();            
            
            if(rs.next()){
                return true;
            }
            
        } catch(Exception ex){System.out.println("Authentication error: " + ex);}
        
        return false;
    }

}
