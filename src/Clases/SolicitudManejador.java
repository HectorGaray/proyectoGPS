/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author tepic
 */
public class SolicitudManejador {
    private Connection conexion;
    private ConexionBase db;
    
    public SolicitudManejador(){
        db=new ConexionBase();
    }
    
    
    public boolean insertaSolicitud(String fechaSalida,String fechaRetorno,String lugar,String pernota,String actividad){
        
        conexion=db.getConexion();
        
        
        try {
            Statement st = conexion.createStatement();
            String sql = "INSERT INTO `viaticos`.`solicitud` (`folio`, `fecha_ini`, `fecha_fin`, `actividad`, `pernota`, `Empleado_id_empleado`,"
                    + " `lugar`) VALUES ('14', '"+fechaSalida+"', '"+fechaRetorno+"', '"+actividad+"', '"+pernota+"', '1', '"+lugar+"');";



            st.executeUpdate(sql);

            conexion.close();
        } //try  
        catch (SQLException ex) {
            Logger.getLogger(SolicitudManejador.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return true;
        
        
        
    }
    
    public DefaultTableModel VerSolicitudes(){
        
        
         DefaultTableModel table = new DefaultTableModel();

        try {
            table.addColumn("Folio");
            table.addColumn("Actividad");
            table.addColumn("Lugar");
            table.addColumn("Pernota");
            table.addColumn("Fecha Salida");
            table.addColumn("Fecha Retorno");
            
            
            //sql
            String sql = "Select folio,actividad,lugar,pernota,fecha_ini,fecha_fin from solicitud;";
            Connection c = db.getConexion();
            Statement st = c.createStatement();
            Object datos[] = new Object[6];
            ResultSet rs = st.executeQuery(sql);

            //llenar tabla
            while (rs.next()) {
                datos[0] = rs.getObject(1);
                datos[1] = rs.getObject(2);
                datos[2] = rs.getObject(3);
                datos[3] = rs.getObject(4);
                datos[4] = rs.getObject(5);
                datos[5] = rs.getObject(6);
                
                table.addRow(datos);
           }

            c.close();
        } catch (SQLException ex) {
            System.out.printf("Error getTabla Cliente SQL");
            Logger.getLogger(SolicitudManejador.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            return table;
        }

        
    }
    
    
    
}
