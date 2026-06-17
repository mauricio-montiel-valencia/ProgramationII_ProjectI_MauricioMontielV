package Controller;

import Model.DataBaseConnection;
import Model.ReceptionModel;
import View.Reception;
import View.ReservationsManagements;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class ReservationsManagementsController {
    
    private ReservationsManagements rManagements;
    private String currentUsername, currentPassword, currentUserType;
    
    PreparedStatement ps;
    ResultSet rs;
    DataBaseConnection dbConnection = new DataBaseConnection();
    
    private int selectedReservationId = -1;
    
    private Map<Integer, Integer> rowReservationID = new HashMap<>();
    
    public ReservationsManagementsController(ReservationsManagements rManagements, String currentUsername, String currentPassword, String currentUserType){
    
        this.rManagements = rManagements;
        this.currentUsername = currentUsername;
        this.currentPassword = currentPassword;
        this.currentUserType = currentUserType;
        appearButtons();
        addOptionsToComboBoxResourceName();
        loadUserReservations();
        addActionListeners();
        addTableSelectionListener();
    }
    
    private void appearButtons(){
    
        boolean flagAD = true;
        boolean flagACD = true;
        
        try{
            
            ps = dbConnection.connection_.prepareStatement("select userType from users where userName = ? and userPassword = ?");
            ps.setString(1, currentUsername);
            ps.setString(2, currentPassword);
            
            rs = ps.executeQuery();
            
            if(rs.next()){
            
                String userTypeDB = rs.getString("userType");
                
                switch(userTypeDB){
                
                    case "Final User": flagAD = false; break;
                    case "Person in Charge": flagAD = true; flagACD = false; break;
                    case "Administrator": flagAD = true; flagACD = false; break;
                }
            }
            
        }catch(Exception ex){System.out.println("Error: " + ex);}
        
        appearDisappearElements(flagAD, flagACD);
    }
    
    private void appearDisappearElements(boolean flagAD, boolean flagACD){
    
        rManagements.approvedButton.setVisible(flagAD);
        rManagements.disapprovedButton.setVisible(flagAD);
        rManagements.addReservationButton.setVisible(flagACD);
        rManagements.cancelReservationButton.setVisible(flagACD);
        rManagements.reservationDisponibilityButton.setVisible(flagACD);
        rManagements.finalDateTXT.setVisible(flagACD);
        rManagements.finalHourTXT.setVisible(flagACD);
        rManagements.reasonTXT.setVisible(flagACD);
        rManagements.resourceNameJC.setVisible(flagACD);
        rManagements.startDateTXT.setVisible(flagACD);
        rManagements.startHourTXT.setVisible(flagACD);
        rManagements.finalDate.setVisible(flagACD);
        rManagements.finalHour.setVisible(flagACD);
        rManagements.reason.setVisible(flagACD);
        rManagements.resourceName.setVisible(flagACD);
        rManagements.startDate.setVisible(flagACD);
        rManagements.startHour.setVisible(flagACD);
    }
    
    private void addOptionsToComboBoxResourceName(){
    
        try{
        
            ps = dbConnection.connection_.prepareStatement("select resourceGearName from resourcem_gear order by resourceGearName");
            rs = ps.executeQuery();
            
            DefaultComboBoxModel comboModel = new DefaultComboBoxModel();
            
            while(rs.next()){comboModel.addElement(rs.getString("resourceGearName"));}
            
            ps = dbConnection.connection_.prepareStatement("select reSName from resourcem_space order by reSName");
            rs = ps.executeQuery();
            
            while(rs.next()){comboModel.addElement(rs.getString("reSName"));}
                    
            rManagements.resourceNameJC.setModel(comboModel);
                    
        }catch(Exception ex){System.out.println("Error: " + ex);}
    }
    
    private void addActionListeners(){
    
        rManagements.returnButton.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e){
            
                returnToReception();
            }
        });
        
        rManagements.addReservationButton.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e){
            
                addReservations();
            }
        });
        
        rManagements.reservationDisponibilityButton.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e){
            
                showReservationDuration();
            }
        });
        
        rManagements.cancelReservationButton.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e){
            
                cancelReservation();
            }
        });
        
        rManagements.approvedButton.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e){
            
                updateReservationStatus("Approved");
            }
        });
        
        rManagements.disapprovedButton.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e){
            
                updateReservationStatus("Disapproved");
            }
        });
    }
    
    private void returnToReception(){
    
        rManagements.setVisible(false);
        ReceptionModel model = new ReceptionModel();
        model.setName(currentUsername);
        model.setPassword(currentPassword);
        model.setUserType(currentUserType);

        Reception reception = new Reception();
        ReceptionController recepController = new ReceptionController(model, reception);
    }
    
    private void addReservations(){
    
        String getResource = rManagements.resourceNameJC.getSelectedItem().toString();
                String getStartDate = rManagements.startDateTXT.getText().trim();
                String getFinalDate = rManagements.finalDateTXT.getText().trim();
                String getStartHour = rManagements.startHourTXT.getText().trim();
                String getFinalHour = rManagements.finalHourTXT.getText().trim();
                String getReason = rManagements.reasonTXT.getText().trim();
                
                if(getStartDate.isEmpty() || getFinalDate.isEmpty() || getStartHour.isEmpty() || getFinalHour.isEmpty()){
                
                    JOptionPane.showMessageDialog(null, "Please fill all the gaps first"); return;
                }
                
                try{
                    
                    if(!askResourceDisponibility(getResource, getStartDate, getStartHour, getFinalDate, getFinalHour)){
                    
                        JOptionPane.showMessageDialog(null, "The resource is already reserved.\nPlease Choose another time or resource."); return;}
                    
                    int userID = getCurrentUserID();
                    if(userID == -1){JOptionPane.showMessageDialog(null, "User not found"); return;}
                    
                    int calendarID = getCalendarID(getStartDate);
                    if(calendarID == -1){JOptionPane.showMessageDialog(null, "Error with the calendar reference"); return;}
                    
                    ps = dbConnection.connection_.prepareStatement("insert into reservation_managements(resourceName, startDate, startHour, finalDate, finalHour, reason, status, idusers, idcalendar) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
                    ps.setString(1, getResource);
                    ps.setDate(2, Date.valueOf(getStartDate));
                    ps.setTime(3, Time.valueOf(getStartHour + ":00"));
                    ps.setDate(4, Date.valueOf(getFinalDate));
                    ps.setTime(5, Time.valueOf(getFinalHour + ":00"));
                    ps.setString(6, getReason);
                    ps.setString(7, "Pending");
                    ps.setInt(8, userID);
                    ps.setInt(9, calendarID);
                    
                    int result = ps.executeUpdate();
                    
                    if(result > 0){JOptionPane.showMessageDialog(null, "Reservation Successfully sent"); cleanTXTFields(); loadUserReservations();}
                    else{JOptionPane.showMessageDialog(null, "Reservation Unsuccessfully sent");}
                            
                }catch(IllegalArgumentException ex){JOptionPane.showMessageDialog(null, "Format Erro: You have to use YYYY-MM-DD for dates and HH:MM:SS for hours.");}
                catch(Exception ex){System.out.println("Error: " + ex);}
    }
    
    private boolean askResourceDisponibility(String resourceName, String startDate, String startHour, String finalDate, String finalHour){
    
        try{
            
        ps = dbConnection.connection_.prepareStatement("select count(*) from reservation_managements where resourceName = ? and not (finalDate < ? or (finalDate = ? and finalHour <= ?) or startDate > ? or (startDate = ? and startHour >= ?))");
        ps.setString(1, resourceName);
        ps.setString(2, startDate);
        ps.setString(3, startDate);
        ps.setString(4, startHour);
        ps.setString(5, finalDate);
        ps.setString(6, finalDate);
        ps.setString(7, finalHour);
        
        rs = ps.executeQuery();
        
        if(rs.next()){int count = rs.getInt(1); return count == 0;}
        
        }catch(Exception ex){System.out.println("Error: " + ex);}
        
        return false;
    }
    
    private void updateReservationStatus(String newStatus){
    
        if(selectedReservationId == -1){JOptionPane.showMessageDialog(null, "Please select a reservation first"); return;}
        
        try{
        
            ps = dbConnection.connection_.prepareStatement("select status from reservation_managements where idreservation_managements = ?");
            ps.setInt(1, selectedReservationId);
            rs = ps.executeQuery();
            
            if(rs.next()){
                
                String currentStatus = rs.getString("status");
                
                if(!currentStatus.equals("Pending")){JOptionPane.showMessageDialog(null, "Only pending reservations can be approved or disapproved.\nCurrent status: " + currentStatus); return;}
            }
        }
        catch(Exception ex){System.out.println("Error: " + ex); return;}
        
        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to " + newStatus + " this reservation?", "Confirm " + newStatus, JOptionPane.YES_NO_OPTION);
        
        if(confirm == JOptionPane.YES_OPTION){
        
            try{
            
                ps = dbConnection.connection_.prepareStatement("update reservation_managements set status = ? where idreservation_managements = ?");
                ps.setString(1, newStatus);
                ps.setInt(2, selectedReservationId);
                
                int result = ps.executeUpdate();
                
                if(result > 0){JOptionPane.showMessageDialog(null, "Reservation " + newStatus + " successfully"); selectedReservationId = -1; loadUserReservations();}
                else{JOptionPane.showMessageDialog(null, "Error updating reservation status");}
                
            }catch(Exception ex){System.out.println("Error: " + ex);}
        }
    }
    
     private void loadUserReservations() {
        try {
            
            rowReservationID.clear();

            DefaultTableModel model = (DefaultTableModel) rManagements.table.getModel();
            model.setRowCount(0);
            
            ps = dbConnection.connection_.prepareStatement("select idreservation_managements, resourceName, startDate, startHour, finalDate, finalHour, status from reservation_managements order by startDate, startHour");
            rs = ps.executeQuery();                
                        
            int rowIndex = 0;
            
            while(rs.next()) {
                
                int reservationId = rs.getInt("idreservation_managements");
                String resourceName = rs.getString("resourceName");
                String startDate = rs.getString("startDate");
                String startHour = rs.getString("startHour");
                String finalDate = rs.getString("finalDate");
                String finalHour = rs.getString("finalHour");
                String status = rs.getString("status");
                
                String startInfo = startDate + " " + startHour;
                String finalInfo = finalDate + " " + finalHour;
                
                model.addRow(new Object[]{resourceName, startInfo, finalInfo, status});
                rowReservationID.put(rowIndex, reservationId);
                rowIndex++;   
            }
                
        } catch(Exception ex) {System.out.println("Error loading reservations: " + ex);}
    }
     
    private void addTableSelectionListener(){
        
        rManagements.table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            
            @Override
            public void valueChanged(ListSelectionEvent e) {
                
                if(!e.getValueIsAdjusting()) {
                    
                    int selectedRow = rManagements.table.getSelectedRow();
                    
                    if(selectedRow != -1) {
                        
                        selectedReservationId = rowReservationID.getOrDefault(selectedRow, -1);
                        System.out.println("Selected reservation ID: " + selectedReservationId);
                    }
                    else{selectedReservationId = -1;}
                }
            }
        });
    }
    
    private void showReservationDuration(){
        
        if(selectedReservationId == -1){JOptionPane.showMessageDialog(null, "Please select a reservation first"); return;}
        
        try{
        
            ps = dbConnection.connection_.prepareStatement("select resourceName, startDate, startHour, finalDate, finalHour from reservation_managements where idreservation_managements = ?");
            ps.setInt(1, selectedReservationId);
            rs = ps.executeQuery();
            
            if(rs.next()){
            
                String resourceName = rs.getString("resourceName");
                String startDate = rs.getString("startDate");
                String startHour = rs.getString("startHour");
                String finalDate = rs.getString("finalDate");
                String finalHour = rs.getString("finalHour");
                
                String duration = "Resource: " + resourceName + "\n\n" + "Start Date: " + startDate + "\n" + "Start Time: " + startHour + "\n\n"
                                  + "End Date: " + finalDate + "\n" + "End Time: " + finalHour;
                
                JOptionPane.showMessageDialog(null, duration, "Reservation Duration", JOptionPane.INFORMATION_MESSAGE);
            }
            else{JOptionPane.showMessageDialog(null, "Reservation not found");}
            
        }catch(Exception ex){System.out.println("Error: " + ex);}
    }
    
    private void cancelReservation() {
        
        if(selectedReservationId == -1) {
            
            JOptionPane.showMessageDialog(null, "Please select a reservation to cancel");
            return;
        }
        
        try{
            
            ps = dbConnection.connection_.prepareStatement("select idusers from reservation_managements where idreservation_managements = ?");
            ps.setInt(1, selectedReservationId);
            rs = ps.executeQuery();
            
            if(rs.next()){
            
                int reservationOwnerID = rs.getInt("idusers");
                
                if(reservationOwnerID != getCurrentUserID()){JOptionPane.showMessageDialog(null, "¡You can only cancel your own reservations!"); return;}
            }
            
        }catch(Exception ex){System.out.println("Error: " + ex); return;}
        
        
        
        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel this reservation?", "Confirm Cancellation", JOptionPane.YES_NO_OPTION);
        
        if(confirm == JOptionPane.YES_OPTION) {
            
            try {
                ps = dbConnection.connection_.prepareStatement("DELETE FROM reservation_managements WHERE idreservation_managements = ?");
                ps.setInt(1, selectedReservationId);
                int result = ps.executeUpdate();
                
                if(result > 0) {
                    JOptionPane.showMessageDialog(null, "Reservation cancelled successfully");
                    selectedReservationId = -1;
                    loadUserReservations();
                } 
                else {
                    JOptionPane.showMessageDialog(null, "Error cancelling reservation");
                }
                
            } catch(Exception ex) {System.out.println("Error: " + ex);}
        }
    }
    
    private int getCurrentUserID(){
    
        try{
        
            ps = dbConnection.connection_.prepareStatement("select idusers from users where userName = ?");
            ps.setString(1, currentUsername);
            rs = ps.executeQuery();
            
            if(rs.next()){return rs.getInt("idusers");}
        }catch(Exception ex){}
        
        return -1;
    }
    
    private int getCalendarID(String date){
        
        try{
            ps = dbConnection.connection_.prepareStatement("select idcalendar from calendar where calendarDate = ?");
            ps.setString(1, date);
            rs = ps.executeQuery();
            
            if(rs.next()){return rs.getInt("idcalendar");}
            else{
            
                ps = dbConnection.connection_.prepareStatement("insert into calendar (calendarDate, status) values (?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
            }
            
            ps.setString(1, date);
            ps.setString(2, "Available");
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            
            if(rs.next()){return rs.getInt(1);}
        }
        catch(Exception ex){System.out.println("Error:" + ex);}
    
        return -1;
    }
    
    private void cleanTXTFields(){
   
        rManagements.startDateTXT.setText("");
        rManagements.finalDateTXT.setText("");
        rManagements.startHourTXT.setText("");
        rManagements.finalHourTXT.setText("");
        rManagements.reasonTXT.setText("");
    }
}
