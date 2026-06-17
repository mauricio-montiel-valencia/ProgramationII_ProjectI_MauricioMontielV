package Controller;

import Model.DataBaseConnection;
import Model.ReceptionModel;
import View.Reception;
import View.Reports;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import javax.swing.JOptionPane;

public class ReportsController {
    
    String currentUsername, currentPassword, currentUserType;
    Reports reportsView;
    
    DataBaseConnection dbConnection = new DataBaseConnection();
    PreparedStatement ps;
    ResultSet rs;
    
    public ReportsController(Reports reportsView, String currentUsername, String currentPassword, String currentUserType){
    
        this.reportsView = reportsView;
        this.currentUsername = currentUsername;
        this.currentPassword = currentPassword;
        this.currentUserType = currentUserType;
        addActionListeners();
        loadAllUsers();
    }
    
    
    private void loadAllUsers(){
    
        try{
            ps = dbConnection.connection_.prepareStatement("select userName from users order by userName");
            rs = ps.executeQuery();
            
            while(rs.next()){reportsView.addRowToTable(new Object[]{rs.getString("userName")});}
                    
        }catch(Exception ex){System.out.println("Error: " + ex);}
    }
    
    private void loadUserHistory(String userName){
    
        try{
        
            reportsView.setupHistoryTable();
            
            ps = dbConnection.connection_.prepareStatement("select idusers from users where userName = ?");
            ps.setString(1, userName);
            rs = ps.executeQuery();
            
            int userID = -1;
            
            if(rs.next()){userID = rs.getInt("idusers");}
            
            ps = dbConnection.connection_.prepareStatement("select rm.resourceName, rm.startDate, rm.startHour, rm.finalDate, rm.finalHour, rm.status, rm.startDate as solicitudeDate, case when s.ReSName is not null then 'Space Resource' when g.resourceGearName is not null then 'Gear Resource' else 'Unknown' end as resourceType from reservation_managements rm left join resourcem_space s on rm.resourceName = s.ReSName left join resourcem_gear g on rm.resourceName = g.resourceGearName where rm.idusers = ? order by rm.startDate desc, rm.startHour desc");
            ps.setInt(1, userID);
            rs = ps.executeQuery();
            
            int count = 0;
            
            while(rs.next()){
            
                String resourceName = rs.getString("resourceName");
                String resourceType = rs.getString("resourceType");
                String startDate = rs.getString("startDate");
                String startHour = rs.getString("startHour");
                String finalDate = rs.getString("finalDate");
                String finalHour = rs.getString("finalHour");
                String solicitudeDate = rs.getString("solicitudeDate");
                
                String totalHours = calculateTotalHours(startDate, startHour, finalDate, finalHour);
                
                reportsView.addRowToTable(new Object[]{userName, resourceName, resourceType, startDate + " | " + startHour, finalDate + " | " + finalHour, totalHours, solicitudeDate});
                
                count++;
            }
            
            if(count == 0){JOptionPane.showMessageDialog(reportsView, "No reservation history found for user: " + userName); returnToUserTable();}
            else{reportsView.setTitle("Reservation History of: " + userName);}
            
        }catch(Exception ex){System.out.println("Error: " + ex);}
    }
    
    private void returnToUserTable(){
    
        reportsView.createUserTable();
        loadAllUsers();
    }
    
    private String calculateTotalHours(String startDate, String startHour, String finalDate, String finalHour){
    
        try{
        
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            
            LocalDate startD = LocalDate.parse(startDate, dateFormatter);
            LocalTime startT = LocalTime.parse(startHour, timeFormatter);
            
            LocalDate finalD = LocalDate.parse(finalDate, dateFormatter);
            LocalTime finalT = LocalTime.parse(finalHour, timeFormatter);
            
            long daysBetween = ChronoUnit.DAYS.between(startD, finalD);
            
            long startMinutes = startT.getHour() * 60 + startT.getMinute();
            long finalMinutes = finalT.getHour() * 60 + finalT.getMinute();
            
            long totalMinutes = (daysBetween * 24 * 60) + (finalMinutes - startMinutes);
            
            long hours = totalMinutes / 60;
            long minutes = totalMinutes % 60;
            
            if(hours > 0 && minutes > 0){return hours + "h " + minutes + "m";}
            else if(hours > 0){return hours + " hours";}
            else{return minutes + " minutes";}
            
        }catch(Exception ex){return "N/A";}
    }
    
    private void addActionListeners(){
    
        reportsView.returnButton.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e){
            
                reportsView.setVisible(false);
                ReceptionModel model = new ReceptionModel();
                model.setName(currentUsername);
                model.setPassword(currentPassword);
                model.setUserType(currentUserType);
                
                Reception reception = new Reception();
                ReceptionController receptionController = new ReceptionController(model, reception);
            }
        });
        
        reportsView.userHistoryButton.addActionListener(new ActionListener(){
        
           @Override
           public void actionPerformed(ActionEvent e){
           
               String selectedUser = reportsView.getSelectedUser();
               
               if(selectedUser == null){JOptionPane.showMessageDialog(reportsView, "Please select a user first"); return;}
               
               loadUserHistory(selectedUser);
           } 
        });
        
        reportsView.returnToUsersButton.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e){
            
                reportsView.createUserTable();
                loadAllUsers();
            }
        });
    }
}
