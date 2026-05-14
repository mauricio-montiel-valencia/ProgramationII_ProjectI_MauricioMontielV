package Main;

import Controller.LogInWindowController;
import Model.DataBaseConnection;
import View.LogInWindow;


public class Main {

    public static void main(String[] args) {
        
        LogInWindow logInWindow = new LogInWindow();
        
        LogInWindowController logInController = new LogInWindowController(logInWindow);
        
        DataBaseConnection dbConnection = new DataBaseConnection();
        
        logInController.initializeLogInWindow();
    }
}
