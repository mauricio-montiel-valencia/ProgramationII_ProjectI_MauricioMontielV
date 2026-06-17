package Controller;

import Model.DataBaseConnection;
import Model.ReceptionModel;
import View.Calendar;
import View.Reception;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;
import javax.swing.table.DefaultTableModel;

public class CalendarController {
    
    Calendar calendarView;
    String currentUsername, currentPassword, currentUserType;
    
    DataBaseConnection dbConnection = new DataBaseConnection();
    PreparedStatement ps;
    ResultSet rs;
    
    java.util.List<Object[]> originalData = new java.util.ArrayList<>();
    
    public CalendarController(Calendar calendarView, String currentUsername, String currentPassword, String currentUserType){
   
        this.calendarView = calendarView;
        this.currentUsername = currentUsername;
        this.currentPassword = currentPassword;
        this.currentUserType = currentUserType;
        addActionListeners();
        loadAllReservations();
    }
    
    private void loadAllReservations(){
    
        try{
        
            DefaultTableModel model = (DefaultTableModel) calendarView.table.getModel();
            model.setRowCount(0);
            originalData.clear();
            
            ps = dbConnection.connection_.prepareStatement("select u.userName, rm.resourceName, case when s.ReSName is not null then 'Space Resource' when g.resourceGearName is not null then 'Gear Resource' else 'Unknown' end as resourceType, rm.status, rm.startDate as solicitudeDate from reservation_managements rm inner join users u on rm.idusers = u.idusers left join resourcem_space s on rm.resourceName = s.ReSName left join resourcem_gear g on rm.resourceName = g.resourceGearName order by rm.startDate, rm.startHour");
            rs = ps.executeQuery();
            
            int rowCount = 0;
            
            while(rs.next()){
            
                String userName = rs.getString("userName");
                String resourceName = rs.getString("resourceName");
                String resourceType = rs.getString("resourceType");
                String status = rs.getString("status");
                String solicitudeDate = rs.getString("solicitudeDate");
                
                model.addRow(new Object[]{userName, resourceName, resourceType, status, solicitudeDate});
                originalData.add(new Object[]{userName, resourceName, resourceType, status, solicitudeDate});
                rowCount++;
            }
            
            if(rowCount == 0){calendarView.dateLabel.setText("No reservations found");}
            else{calendarView.dateLabel.setText("Showing all reservations");}
        }
        catch(Exception ex){System.out.println("Error: " + ex);}
        
    }
    
    private void addActionListeners(){
    
        ActionListener actionListenerReturn = new ActionListener(){
        
            public void actionPerformed(ActionEvent e){
            
                calendarView.setVisible(false);
                ReceptionModel model = new ReceptionModel();
                model.setName(currentUsername);
                model.setPassword(currentPassword);
                model.setUserType(currentUserType);
                
                Reception reception = new Reception();
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
                    
                    String formattedDate = year + "-" + String.format("%02d", dateChose.getMonthValue()) + "-" + String.format("%02d", day);
                    
                    calendarView.dateLabel.setText(month + " " + day + ", " + year);
                    
                    showReservationsTable(formattedDate);
                }
                else{calendarView.dateLabel.setText("No Date Selected");}
            }
        };
        
        calendarView.showDateData.addActionListener(actionListenerDateData);
        
        calendarView.comboBoxFilter.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e){
                
                String getSelectedItem = calendarView.comboBoxFilter.getSelectedItem().toString();
            
                calendarView.dateRangeFilterButton.setVisible(false);
                    calendarView.typeFilterR1.setVisible(false);
                    calendarView.typeFilterR2.setVisible(false);
                    calendarView.resourceFilterTXT.setVisible(false);
                    calendarView.nameFilterTXT.setVisible(false);
                    calendarView.filterButton.setVisible(false);
                    calendarView.dateRangeFilter1.setVisible(false);
                    calendarView.dateRangeFilter2.setVisible(false);
                    calendarView.comboBoxStatusFilter.setVisible(false);
                
                if(getSelectedItem.equals("Username")){
                
                    calendarView.dateRangeFilterButton.setVisible(false);
                    calendarView.typeFilterR1.setVisible(false);
                    calendarView.typeFilterR2.setVisible(false);
                    calendarView.comboBoxStatusFilter.setVisible(false);
                    calendarView.dateRangeFilter1.setVisible(false);
                    calendarView.dateRangeFilter2.setVisible(false);
                    calendarView.resourceFilterTXT.setVisible(false);
                    calendarView.nameFilterTXT.setVisible(true);
                    calendarView.filterButton.setVisible(true);
                }
                else if(getSelectedItem.equals("Resource")){
                
                    calendarView.dateRangeFilterButton.setVisible(false);
                    calendarView.typeFilterR1.setVisible(false);
                    calendarView.typeFilterR2.setVisible(false);
                    calendarView.comboBoxStatusFilter.setVisible(false);
                    calendarView.dateRangeFilter1.setVisible(false);
                    calendarView.dateRangeFilter2.setVisible(false);
                    calendarView.nameFilterTXT.setVisible(false);
                    calendarView.resourceFilterTXT.setVisible(true);
                    calendarView.filterButton.setVisible(true);
                }
                else if(getSelectedItem.equals("Date")){
                
                    calendarView.typeFilterR1.setVisible(false);
                    calendarView.typeFilterR2.setVisible(false);
                    calendarView.comboBoxStatusFilter.setVisible(false);
                    calendarView.resourceFilterTXT.setVisible(false);
                    calendarView.nameFilterTXT.setVisible(false);
                    calendarView.filterButton.setVisible(false);
                    calendarView.dateRangeFilter1.setVisible(true);
                    calendarView.dateRangeFilter2.setVisible(true);
                    calendarView.dateRangeFilterButton.setVisible(true);
                }
                else if(getSelectedItem.equals("Status")){
                    
                    calendarView.dateRangeFilterButton.setVisible(false);
                    calendarView.typeFilterR1.setVisible(false);
                    calendarView.typeFilterR2.setVisible(false);
                    calendarView.resourceFilterTXT.setVisible(false);
                    calendarView.nameFilterTXT.setVisible(false);
                    calendarView.filterButton.setVisible(false);
                    calendarView.dateRangeFilter1.setVisible(false);
                    calendarView.dateRangeFilter2.setVisible(false);
                    calendarView.comboBoxStatusFilter.setVisible(true);
                    statusFilter(calendarView.comboBoxStatusFilter.getSelectedItem().toString());
                }
                else if(getSelectedItem.equals("Type")){
                    
                    calendarView.dateRangeFilterButton.setVisible(false);
                    calendarView.resourceFilterTXT.setVisible(false);
                    calendarView.nameFilterTXT.setVisible(false);
                    calendarView.filterButton.setVisible(false);
                    calendarView.dateRangeFilter1.setVisible(false);
                    calendarView.dateRangeFilter2.setVisible(false);
                    calendarView.comboBoxStatusFilter.setVisible(false);
                    calendarView.typeFilterR1.setVisible(true);
                    calendarView.typeFilterR2.setVisible(true);
                }
            }
        });
        
        calendarView.filterButton.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e){
            
                String getSelectedItem = calendarView.comboBoxFilter.getSelectedItem().toString();
                
                if(getSelectedItem.equals("Username")){
                
                    String getUsername = calendarView.nameFilterTXT.getText().trim();
                    
                    if(!getUsername.isEmpty()){filterByUsername(getUsername);}
                    else{calendarView.dateLabel.setText("Please enter a username");}
                }
                if(getSelectedItem.equals("Resource")){
                
                    String getResourceName = calendarView.resourceFilterTXT.getText().trim();
                    
                    if(!getResourceName.isEmpty()){filterByResource(getResourceName);}
                }   else{calendarView.dateLabel.setText("Please enter a resource name");}
            }
        });
        
        calendarView.dateRangeFilterButton.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e){
            
                Date startDate = calendarView.dateRangeFilter1.getDate();
                Date endDate = calendarView.dateRangeFilter2.getDate();
                
                if(startDate != null && endDate != null){
                
                    LocalDate start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate end = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    
                    if(start.isBefore(end) || start.isEqual(end)){filterByDateRange(start, end);}
                    else{calendarView.dateLabel.setText("Start date must be before or equal to end date");}
                }
                else{calendarView.dateLabel.setText("Please select both start and end dates");}
            }
        });
        
        calendarView.comboBoxStatusFilter.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e){
                
                String statusSelected = calendarView.comboBoxStatusFilter.getSelectedItem().toString();
                statusFilter(statusSelected);
            }
        });
        
        calendarView.typeFilterR1.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e){
            
                if(calendarView.typeFilterR1.isSelected()){
                    
                    typeFilter("Space Resource");
                    calendarView.dateLabel.setText("Filtered: Space Resources Only");
                }
            }
        });
        
        calendarView.typeFilterR2.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e){
            
                if(calendarView.typeFilterR2.isSelected()){
                    
                    typeFilter("Gear Resource");
                    calendarView.dateLabel.setText("Filtered: Gear Resources Only");
                }
            }
        });
        
        calendarView.resetFiltersButton.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e){
            
                try{
                
                    DefaultTableModel model = (DefaultTableModel) calendarView.table.getModel();
                    
                    if(originalData.isEmpty()){calendarView.dateLabel.setText("No filtered data to reset"); return;}
                    
                    model.setRowCount(0);
                    
                    for(Object[] row : originalData){model.addRow(row);}
                    
                    calendarView.nameFilterTXT.setText("");
                    calendarView.resourceFilterTXT.setText("");
                    
                    calendarView.dateRangeFilter1.setDate(null);
                    calendarView.dateRangeFilter2.setDate(null);
                    
                    calendarView.typeFilterR1.setSelected(false);
                    calendarView.typeFilterR2.setSelected(false);
                    
                    calendarView.typeFilterR1.setVisible(false);
                    calendarView.typeFilterR2.setVisible(false);
                    calendarView.resourceFilterTXT.setVisible(false);
                    calendarView.nameFilterTXT.setVisible(false);
                    calendarView.filterButton.setVisible(false);
                    calendarView.dateRangeFilter1.setVisible(false);
                    calendarView.dateRangeFilter2.setVisible(false);
                    calendarView.dateRangeFilterButton.setVisible(false);
                    calendarView.comboBoxStatusFilter.setVisible(false);
                    
                    loadAllReservations();
                    calendarView.dateChooser.setDate(null);
                    
                    calendarView.dateLabel.setText("All filters has been reset!");
                    
                }catch(Exception ex){System.out.println("Error: " + ex);}
            }
        });
    }
    
    private void showReservationsTable(String date){
    
        try{
        
            DefaultTableModel model = (DefaultTableModel) calendarView.table.getModel();
            model.setRowCount(0);
            originalData.clear();
            
            ps = dbConnection.connection_.prepareStatement("select u.userName, rm.resourceName, case when s.ReSName is not null then 'Space Resource' when g.resourceGearName is not null then 'Gear Resource' else 'Unknown' end as resourceType, rm.status, rm.startDate as solicitudeDate from reservation_managements rm inner join users u on rm.idusers = u.idusers left join resourcem_space s on rm.resourceName = s.ReSName left join resourcem_gear g on rm.resourceName = g.resourceGearName where rm.startDate <= ? and rm.finalDate >= ? order by rm.startDate, rm.startHour");
            ps.setString(1, date);
            ps.setString(2, date);
            
            rs = ps.executeQuery();
            
            int rowCount = 0;
            
            while(rs.next()){
            
                String userName = rs.getString("userName");
                String resourceName = rs.getString("resourceName");
                String resourceType = rs.getString("resourceType");
                String status = rs.getString("status");
                String solicitudeDate = rs.getString("solicitudeDate");
                
                model.addRow(new Object[]{userName, resourceName, resourceType, status, solicitudeDate});
                originalData.add(new Object[]{userName, resourceName, resourceType, status, solicitudeDate});
                rowCount++;
            }
            
            if(rowCount == 0){calendarView.dateLabel.setText("No Reservations for this date");}
            
        }catch(Exception ex){
            
            System.out.println("Error: " + ex);
            ex.printStackTrace();
            DefaultTableModel model = (DefaultTableModel) calendarView.table.getModel();
            model.setRowCount(0);
            model.addRow(new Object[]{"Error loading reservations: " + ex.getMessage(), "", "" , "", ""});
        }
    }
    private void statusFilter(String status){
    
        try{
        
            DefaultTableModel model = (DefaultTableModel) calendarView.table.getModel();
            
            if(originalData.isEmpty()){calendarView.dateLabel.setText("No data to filter"); return;}
            
            model.setRowCount(0);
            
            int count = 0;
            
            for(Object[] row : originalData){
                
                String currentStatus = row[3].toString();
                
                if(currentStatus.equalsIgnoreCase(status)){model.addRow(row); count++;}
            }
            
            if(count == 0){calendarView.dateLabel.setText("No " + status + " reservations found");}
            else{calendarView.dateLabel.setText("Showing " + count + " " + status + " reservations");}
        
        }catch(Exception ex){System.out.println("Error: " + ex);}
    }
    
    private void typeFilter(String resourceType){
    
        try{
        
            DefaultTableModel model = (DefaultTableModel) calendarView.table.getModel();
            
            if(originalData.isEmpty()){calendarView.dateLabel.setText("No data to filter"); return;}
            
            model.setRowCount(0);
            
            int count = 0;
            
            for(Object[] row : originalData){
            
                String currentResourceType = row[2].toString();
                
                if(currentResourceType.equals(resourceType)){model.addRow(row); count++;}
            }
            
            if(count == 0){calendarView.dateLabel.setText("No " + resourceType + " reservations found");}
            else{calendarView.dateLabel.setText("Showing " + count + " " + resourceType + " reservations");}
          
            
        }catch(Exception ex){System.out.println("Error: " + ex);}
    }
    
    private void filterByResource(String resourceName){
        
        try{
            
            DefaultTableModel model = (DefaultTableModel) calendarView.table.getModel();
            
            if(originalData.isEmpty()){calendarView.dateLabel.setText("No data to filter"); return;}
            
            model.setRowCount(0);
            
            int count = 0;
            
            for(Object[] row : originalData){
             
                String currentResourceName = row[1].toString();
                
                if(currentResourceName.toLowerCase().contains(resourceName.toLowerCase())){
                    
                    model.addRow(row);
                    count++;
                }
            }
            
            if(count == 0){calendarView.dateLabel.setText("No reservations found for resource: " + resourceName);}
            else{calendarView.dateLabel.setText("Showing " + count + " reservations for resource containing " + resourceName);}
        }catch(Exception ex){System.out.println("Error: " + ex);}
    }
    
    private void filterByUsername(String username){
    
        try{
        
            DefaultTableModel model = (DefaultTableModel) calendarView.table.getModel();
            
            if(originalData.isEmpty()){calendarView.dateLabel.setText("No data to filter");}
            
            model.setRowCount(0);
            
            int count = 0;
            
            for(Object[] row : originalData){
            
                String currentUsername = row[0].toString();
                
                if(currentUsername.toLowerCase().contains(username.toLowerCase())){
                
                    model.addRow(row);
                    count++;
                }
                
                if(count == 0){calendarView.dateLabel.setText("No reservations found for user: " + username);}
                else{calendarView.dateLabel.setText("Showing " + count + " reservations from the user: " + username);}
            }
        }catch(Exception ex){System.out.println("Error: " + ex);}
    }
    
    private void filterByDateRange(LocalDate startDate, LocalDate endDate){
    
        try{
        
            DefaultTableModel model = (DefaultTableModel) calendarView.table.getModel();
            
            if(originalData.isEmpty()){calendarView.dateLabel.setText("No data to filter"); return;}
            
            model.setRowCount(0);
            
            int count = 0;
            
            for(Object[] row : originalData){
            
                String solicitudeDate_ = row[4].toString();
                
                LocalDate solicitudeDate = LocalDate.parse(solicitudeDate_);
                
                if((solicitudeDate.isEqual(startDate) || solicitudeDate.isAfter(startDate)) && (solicitudeDate.isEqual(endDate) || solicitudeDate.isBefore(endDate))){
                
                    model.addRow(row);
                    count++;
                }
            }
            
            if(count == 0){calendarView.dateLabel.setText("No reservations found between " + startDate + " and " + endDate);}
            else{calendarView.dateLabel.setText("Showing Reservations from " + startDate + " to " + endDate);}
            
        }catch(Exception ex){System.out.println("Error: " + ex);}
    }
}
