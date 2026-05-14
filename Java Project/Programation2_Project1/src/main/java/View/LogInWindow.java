package View;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class LogInWindow extends JFrame{
    
    public JPanel panel;
    public JLabel headline, nameLabel, passwordLabel, typeOfUser;
    public TextField nameTXT;
    public JPasswordField passwordTXT;
    public JButton logInButton;
    public String[] users = {"Administrator", "Person in Charge", "Final User"};
    public JComboBox comboBox;
    
    public LogInWindow(){
    
        initializeComponents();
    } 
    
    public void initializeComponents(){
    
        makeWindow();
        makePanel();
        makeLabels();
        makeTextFields();
        addComboBox();
        makeButton();
    }
    
    private void makeWindow(){
    
        setSize(800, 600);
        setTitle("Log In");
        setLocationRelativeTo(null);
        //setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    private void makePanel(){
    
        panel = new JPanel();
        panel.setLayout(null);
        this.add(panel);
    }
    
    private void makeLabels(){
    
        headline = new JLabel("Log In");
        headline.setFont(new Font("Times New Roman", 3, 24));
        headline.setBounds(350, 50, 100, 100);
        panel.add(headline);
        
        nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Times New Roman", 2, 16));
        nameLabel.setBounds(250, 160, 100, 30);
        panel.add(nameLabel);
        
        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Times New Roman", 2, 16));
        passwordLabel.setBounds(250, 300, 100, 30);
        panel.add(passwordLabel);
        
        typeOfUser = new JLabel("Type:");
        typeOfUser.setFont(new Font("Times New Roman", 2, 16));
        typeOfUser.setBounds(250, 390, 100, 30);
        panel.add(typeOfUser);
    }
    
    private void makeTextFields(){
    
        nameTXT = new TextField();
        nameTXT.setBounds(320, 190, 130, 30);
        panel.add(nameTXT);
        
        passwordTXT = new JPasswordField();
        passwordTXT.setBounds(320, 330, 130, 30);
        panel.add(passwordTXT);
    }
    
    private void addComboBox(){
    
        comboBox = new JComboBox(users);
        comboBox.setBounds(330, 420, 100, 40);
        panel.add(comboBox);
    }
    
    private void makeButton(){
     
        logInButton = new JButton("Log In");
        logInButton.setFont(new Font("Times New Roman", 3, 18));
        logInButton.setBounds(330, 500, 100, 40);
        panel.add(logInButton);
    }
}
