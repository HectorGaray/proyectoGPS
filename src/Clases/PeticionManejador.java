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
public class PeticionManejador {
    private Connection conexion;
    private ConexionBase db;
    
    public PeticionManejador(){
        db=new ConexionBase();
    }
    
    
    public boolean insertaPeticion(String fechaSalida,String necesita,String lugar,String actividad,String idPersona,String idUsuario){
        
        conexion=db.getConexion();
        
        
        try {
            Statement st = conexion.createStatement();
            String sql= "INSERT INTO `viaticos`.`peticion` (`fecha_ini`, `actividad_rea`, `lugar_destino`, `vehiculo_inclui`, `personal_id_personal`, `usuario_id_usuario`, `fecha_emision`,`estado_p`)"
                    + " VALUES ('"+fechaSalida+"', '"+actividad+"', '"+lugar+"', '"+necesita+"', '"+idPersona+"', '"+idUsuario+"', curdate(),'no solicitada');";
//            String sql = "INSERT INTO `viaticos`.`peticion` "
//                    + "(`fecha_ini`, `actividad_rea`, `lugar_destino`, `vehiculo_inclui`,"
//                    + " `personal_id_personal`, `usuario_id_usuario`) VALUES ('"+fechaSalida+"', '"+actividad+"', '"+lugar+"', '"+necesita+"', '"+idPersona+"', '"+idUsuario+"');";



            st.executeUpdate(sql);

            conexion.close();
        } //try  
        catch (SQLException ex) {
            Logger.getLogger(PeticionManejador.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(PeticionManejador.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            return table;
        }

        
    }
    
    
    
}
