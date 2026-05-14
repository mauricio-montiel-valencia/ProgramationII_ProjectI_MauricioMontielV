package Controller;

import Model.DataBaseConnection;
import Model.ReceptionModel;
import Model.SaveContent;
import Model.SaveGearContent;
import Model.SaveSpaceContent;
import View.ResourceManagement;
import View.Reception;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ResourceManagementsController {
    
    private String currentUsername, currentPassword, currentUserType;
    
    public ArrayList<SaveContent> saveAllResources = new ArrayList<>();
    
    ResourceManagement rsManagement;
    PreparedStatement ps;
    ResultSet rs;
    
    
    DataBaseConnection dbConnection = new DataBaseConnection();
    
    
    public ResourceManagementsController(ResourceManagement rsManagement, String currentUsername, String currentPassword, String currentUserType){
        
        this.rsManagement = rsManagement;
        this.currentUsername = currentUsername;
        this.currentPassword = currentPassword;
        this.currentUserType = currentUserType;
        addActionListenerInsert();
        addActionListenerBelowButtons();
        
        loadDataFromDatabase();
    }

    
    private void addActionListenerInsert(){
        
    
        ActionListener insertActionListener = new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e){
            
                String getResourceType = rsManagement.typeOfResource.getSelectedItem().toString();
                
                if(getResourceType.equals("Space")){
                
                    rsManagement.addGearRLabels(false);
                    rsManagement.typeResources(false);
                    rsManagement.addSpaceRLabels(true);
                    rsManagement.spaceResources(true);
                }
                
                if(getResourceType.equals("Gear")){
                
                    rsManagement.addSpaceRLabels(false);
                    rsManagement.spaceResources(false);
                    rsManagement.addGearRLabels(true);
                    rsManagement.typeResources(true);
                }
            }
        };
        
        rsManagement.insertButton.addActionListener(insertActionListener);
        
        ActionListener actionListenerReturn = new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e){
            
                rsManagement.setVisible(false);
                ReceptionModel model = new ReceptionModel();
                model.setName(currentUsername);
                model.setPassword(currentPassword);
                model.setUserType(currentUserType);
                
                Reception reception = new Reception(model);
                ReceptionController Recepcontroller = new ReceptionController(model, reception); 
            }
        };
        
        rsManagement.returnButton.addActionListener(actionListenerReturn);
    }
    
    private void addActionListenerBelowButtons(){
    
        ActionListener actionListenerAdd = new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e){
                
                String getResourceType = rsManagement.typeOfResource.getSelectedItem().toString();
                String getResourceName = rsManagement.resourceNameTXT.getText().trim();
                
                LocalDate today = LocalDate.now();
                DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String date = today.format(dateTime);
               
                if(getResourceType.equals("Space")){
               
                    
                    String capacity = rsManagement.capacityTXT.getText().trim();
                    String location = rsManagement.locationTXT.getText().trim();
                    String restrictions = rsManagement.restrictionsTXT.getText().toString();
                    String type = rsManagement.spaceType.getSelectedItem().toString();
                    
                    if(getResourceName.isEmpty() || capacity.isEmpty() || location.isEmpty() || restrictions.isEmpty()){
                    
                        JOptionPane.showMessageDialog(null, "Please fill the gaps before register a resource");
                    }else{
                    
                    SaveSpaceContent spaceContent = new SaveSpaceContent(getResourceName, date, capacity, location, type, restrictions);
                    
                    
                    saveAllResources.add(spaceContent);
                    
                    String[] components = {getResourceName, getResourceType, type, date};
                    rsManagement.addRows(components);
                    
                   
                    
                    try{
                    
                        ps = dbConnection.connection_.prepareStatement("insert into resourcem_space(ReSName, ReSCapacity, ReSLocation, ReSType, ReSRestrictions) values ( ?, ?, ?, ?, ?)");
                        ps.setString(1, getResourceName);
                        ps.setString(2, capacity);
                        ps.setString(3, location);
                        ps.setString(4, type);
                        ps.setString(5, restrictions);
                        
                        int result = ps.executeUpdate();
                        loadDataFromDatabase();
                        
                        if(result > 0){System.out.println("Data Successfully Inserted");}
                        else{System.out.println("Data Unsuccessfully Inserted");}
                                
                    }catch(Exception ex){System.out.println("Error: " + ex);}
                    
                    }
                }
                if(getResourceType.equals("Gear")){
                
                    String getBrand = rsManagement.brandTXT.getText().trim();
                    String getModel = rsManagement.modelTXT.getText().trim();
                    String getSerial = rsManagement.serialTXT.getText().trim();
                    String getStatus = rsManagement.statusTXT.getText().trim();
                    
                    if(getResourceName.isEmpty() || getBrand.isEmpty() || getModel.isEmpty() || getSerial.isEmpty() || getStatus.isEmpty()){
                        
                        JOptionPane.showMessageDialog(null, "Please fill the gaps before register a resource");
                    }
                    else{
                    SaveGearContent gearContent = new SaveGearContent(
                    
                            getResourceName, date, getBrand, getModel,
                            getSerial, getStatus
                    );
                    
                    saveAllResources.add(gearContent);
                    
                    String[] components = {getResourceName, getResourceType, getBrand, date};
                    rsManagement.addRows(components);
                    
                    try{
                    
                        ps = dbConnection.connection_.prepareStatement("insert into resourcem_gear(resourceGearName, resourceGearBrand, resourceGearModel, resourceGearSerial, resourceGearStatus) values (?, ?, ?, ?, ?)");
                        ps.setString(1, getResourceName);
                        ps.setString(2, getBrand);
                        ps.setString(3, getModel);
                        ps.setString(4, getSerial);
                        ps.setString(5, getStatus);
                        
                        int result = ps.executeUpdate();
                        loadDataFromDatabase();
                        
                        if(result > 0){System.out.println("Data Successfully Inserted");}
                        else{System.out.println("Data Unsuccessfully Inserted");}
                        
                    }catch(Exception ex){System.out.println("Error: " + ex);}
                    }
                }
                
                rsManagement.resourceNameTXT.setText("");
                rsManagement.capacityTXT.setText("");
                rsManagement.locationTXT.setText("");
                rsManagement.restrictionsTXT.setText("");
                rsManagement.brandTXT.setText("");
                rsManagement.modelTXT.setText("");
                rsManagement.serialTXT.setText("");
                rsManagement.statusTXT.setText("");
            }
        };
        
        rsManagement.registerButton.addActionListener(actionListenerAdd);
        
        ActionListener actionListenerEdit = new ActionListener(){
    
    @Override
    public void actionPerformed(ActionEvent e){
        
        rsManagement.counter++;
        
        if(rsManagement.counter % 2 != 0){
            
            rsManagement.selectedRow = rsManagement.table.getSelectedRow();
        
            if(rsManagement.selectedRow != -1){
                
                rsManagement.resourceNameTXT.setText("");
                rsManagement.capacityTXT.setText("");
                rsManagement.locationTXT.setText("");
                rsManagement.restrictionsTXT.setText("");
                rsManagement.brandTXT.setText("");
                rsManagement.modelTXT.setText("");
                rsManagement.serialTXT.setText("");
                rsManagement.statusTXT.setText("");
            
                String getResource = rsManagement.table.getValueAt(rsManagement.selectedRow, 1).toString();
                 
                if(getResource.equals("Space")){
                     
                    String resourceName = rsManagement.table.getValueAt(rsManagement.selectedRow, 0).toString();
                    
                    try{
                        
                        ps = dbConnection.connection_.prepareStatement("select * from resourcem_space where ReSName = ?");
                        ps.setString(1, resourceName);
                        rs = ps.executeQuery();
                        
                        if(rs.next()){
                            
                            rsManagement.resourceNameTXT.setText(rs.getString("ReSName"));
                            rsManagement.capacityTXT.setText(rs.getString("ReSCapacity"));
                            rsManagement.locationTXT.setText(rs.getString("ReSLocation"));
                            rsManagement.restrictionsTXT.setText(rs.getString("ReSRestrictions"));
                            rsManagement.spaceType.setSelectedItem(rs.getString("ReSType"));
                        }
                    } catch(Exception ex){
                        
                        System.out.println("Error loading space: " + ex);
                    }
                }
                 
                if(getResource.equals("Gear")){
                 
                    String resourceName = rsManagement.table.getValueAt(rsManagement.selectedRow, 0).toString();
                    
                    try{
                        
                        ps = dbConnection.connection_.prepareStatement("select * from resourcem_gear where resourceGearName = ?");
                        ps.setString(1, resourceName);
                        rs = ps.executeQuery();
                        
                        if(rs.next()){
                            
                            rsManagement.resourceNameTXT.setText(rs.getString("resourceGearName"));
                            rsManagement.brandTXT.setText(rs.getString("resourceGearBrand"));
                            rsManagement.modelTXT.setText(rs.getString("resourceGearModel"));
                            rsManagement.serialTXT.setText(String.valueOf(rs.getInt("resourceGearSerial")));
                            rsManagement.statusTXT.setText(rs.getString("resourceGearStatus"));
                        }
                    } catch(Exception ex){
                        
                        System.out.println("Error loading gear: " + ex);
                    }
                }
            }
            else{
                
                JOptionPane.showMessageDialog(null, "Please, choose a row first.");
                rsManagement.counter--;
            }
        }
        else{
            
            String resourceType = rsManagement.table.getValueAt(rsManagement.selectedRow, 1).toString();
            String oldResourceName = rsManagement.table.getValueAt(rsManagement.selectedRow, 0).toString();
            
            try{
                
                if(resourceType.equals("Space")){
                    
                    String newResourceName = rsManagement.resourceNameTXT.getText().trim();
                    String newCapacity = rsManagement.capacityTXT.getText().trim();
                    String newLocation = rsManagement.locationTXT.getText().trim();
                    String newType = rsManagement.spaceType.getSelectedItem().toString();
                    String newRestrictions = rsManagement.restrictionsTXT.getText().trim();
                    
                    if(newResourceName.isEmpty() || newCapacity.isEmpty() || newLocation.isEmpty() || newRestrictions.isEmpty()){
                        
                        JOptionPane.showMessageDialog(null, "Please fill all fields");
                        return;
                    }
                    
                    if(!newCapacity.matches("\\d+")){
                        
                        JOptionPane.showMessageDialog(null, "Capacity must be a number");
                        return;
                    }
                    
                    ps = dbConnection.connection_.prepareStatement("update resourcem_space set ReSName=?, ReSCapacity=?, ReSLocation=?, ReSType=?, ReSRestrictions=? where ReSName=?");
                    ps.setString(1, newResourceName);
                    ps.setInt(2, Integer.parseInt(newCapacity));
                    ps.setString(3, newLocation);
                    ps.setString(4, newType);
                    ps.setString(5, newRestrictions);
                    ps.setString(6, oldResourceName);
                    
                    int result = ps.executeUpdate();
                    
                    if(result > 0){
                        
                        JOptionPane.showMessageDialog(null, "Modification successful");
                        loadDataFromDatabase();
                    } else {
                        
                        JOptionPane.showMessageDialog(null, "Modification failed");
                    }
                }
                
                else if(resourceType.equals("Gear")){
                    
                    String newResourceName = rsManagement.resourceNameTXT.getText().trim();
                    String newBrand = rsManagement.brandTXT.getText().trim();
                    String newModel = rsManagement.modelTXT.getText().trim();
                    String newSerial = rsManagement.serialTXT.getText().trim();
                    String newStatus = rsManagement.statusTXT.getText().trim();
                    
                    if(newResourceName.isEmpty() || newBrand.isEmpty() || newModel.isEmpty() || newSerial.isEmpty() || newStatus.isEmpty()){
                        
                        JOptionPane.showMessageDialog(null, "Please fill all fields");
                        return;
                    }
                    
                    if(!newSerial.matches("\\d+")){
                        
                        JOptionPane.showMessageDialog(null, "Serial must be a number");
                        return;
                    }
                    
                    ps = dbConnection.connection_.prepareStatement("update resourcem_gear set resourceGearName=?, resourceGearBrand=?, resourceGearModel=?, resourceGearSerial=?, resourceGearStatus=? where resourceGearName=?");
                    ps.setString(1, newResourceName);
                    ps.setString(2, newBrand);
                    ps.setString(3, newModel);
                    ps.setInt(4, Integer.parseInt(newSerial));
                    ps.setString(5, newStatus);
                    ps.setString(6, oldResourceName);
                    
                    int result = ps.executeUpdate();
                    
                    if(result > 0){
                        
                        JOptionPane.showMessageDialog(null, "Modification successful");
                        loadDataFromDatabase();
                    } else {
                        
                        JOptionPane.showMessageDialog(null, "Modification failed");
                    }
                }
                
                rsManagement.resourceNameTXT.setText("");
                rsManagement.capacityTXT.setText("");
                rsManagement.locationTXT.setText("");
                rsManagement.restrictionsTXT.setText("");
                rsManagement.brandTXT.setText("");
                rsManagement.modelTXT.setText("");
                rsManagement.serialTXT.setText("");
                rsManagement.statusTXT.setText("");
                
            } catch(Exception ex){
                
                System.out.println("Error: " + ex);
                JOptionPane.showMessageDialog(null, "Error editing resource");
            }
        }
    }

};
        
rsManagement.editButton.addActionListener(actionListenerEdit);
        
        
        ActionListener actionListenerDelete = new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e){
            
                rsManagement.selectedRow2 = rsManagement.table.getSelectedRow();
                
                if(rsManagement.selectedRow2 != -1){
                
                    int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure do you want to delete this resource?:",
                                       "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                    
                    if(confirmation == JOptionPane.YES_OPTION){
                    
                       String resourceName = rsManagement.table.getValueAt(rsManagement.selectedRow2, 0).toString();
                       String resourceType = rsManagement.table.getValueAt(rsManagement.selectedRow2, 1).toString();
                        
                       try{
                       
                           if(resourceType.equals("Space")){
                           
                               ps = dbConnection.connection_.prepareStatement("delete from resourcem_space where ReSName=?");
                               ps.setString(1, resourceName);
                               ps.executeUpdate();
                           }
                           if(resourceType.equals("Gear")){
                           
                               ps = dbConnection.connection_.prepareStatement("delete from resourcem_gear where resourceGearName=?");
                               ps.setString(1, resourceName);
                               ps.executeUpdate();
                           }
                           
                           loadDataFromDatabase();
                           
                           JOptionPane.showMessageDialog(null, "Resource Successfully Deleted");
                       }
                       catch(Exception ex){System.out.println("Error: " + ex);}
                       
                        rsManagement.resourceNameTXT.setText("");
                        rsManagement.capacityTXT.setText("");
                        rsManagement. locationTXT.setText("");
                        rsManagement.restrictionsTXT.setText("");
                        rsManagement.brandTXT.setText("");
                        rsManagement.modelTXT.setText("");
                        rsManagement.serialTXT.setText("");
                        rsManagement.statusTXT.setText("");
                    }
                }
                else{JOptionPane.showMessageDialog(null, "Please, choose a resource to delete.");}
            }
        };
        
        rsManagement.eliminateButton.addActionListener(actionListenerDelete);
    }
    
    public void loadDataFromDatabase(){
        
    rsManagement.clearTable();
    
    try{
        
        ps = dbConnection.connection_.prepareStatement("select ReSName, ReSType from resourcem_space");
        rs = ps.executeQuery();
        
        while(rs.next()){
            
            String[] rowData = {rs.getString("ReSName"), "Space", rs.getString("ReSType"),""};
            rsManagement.addRows(rowData);
        }
      
        ps = dbConnection.connection_.prepareStatement("select resourceGearName, resourceGearBrand from resourcem_gear");
        rs = ps.executeQuery();
        
        while(rs.next()){
            
            String[] rowData = {rs.getString("resourceGearName"), "Gear", rs.getString("resourceGearBrand"),""};
            rsManagement.addRows(rowData);
        }
        
    } catch(Exception ex){System.out.println("Error loading data: " + ex);}
}
}
