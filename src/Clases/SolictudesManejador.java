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
public class SolictudesManejador {
   private Connection conexion;
    private ConexionBase db;
    

    
   public SolictudesManejador(){
        db=new ConexionBase();
        
        
    }
    
   
   
   public String[] InformacionUsuario(String folioId){
        
        
        String id[]=new String[3];
        
        try {
            
            String sql = "select concat(per.nombre,' ',per.apellido_pa,' ',per.apellido_ma)nombre, r.puesto, a.area from peticion e,area a, puesto r,personal per,usuario u where "
                    + "u.personal_id_personal=id_personal and puesto_id_puesto=id_puesto and area_id_area=id_area and e.usuario_id_usuario=u.id_usuario and e.folio="+folioId+";";

            conexion = db.getConexion(); //obtenemos conexion 
            Statement st = conexion.createStatement(); //crear obteno de consulta
            ResultSet resultados = st.executeQuery(sql); //ejecutar consulta
            //vemos si encontro coincidencias
            if (resultados.next()) {
                
                id[0]= resultados.getObject(1).toString();
                id[1]= resultados.getObject(2).toString();
                id[2]=resultados.getObject(3).toString();
            }

            conexion.close();
        } //esAdministrador
        catch (SQLException ex) {
            Logger.getLogger(LoginManejador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, "No Hay Conexion a la Base de Datos");
        }

        return id;
        

       
    }
//    select concat(per.nombre,' ',per.apellido_pa,' ',per.apellido_ma)nombre, r.puesto, a.area from area a, puesto r,personal per,usuario u where personal_id_personal=id_personal and puesto_id_puesto=id_puesto and area_id_area=id_area and id_usuario=1
 

public boolean NuevaSolicitudNueva(String fechaSalida,String necesita,String lugar,String actividad,String idPersona,String idUsuario){
        
        conexion=db.getConexion();
        
        
        try {
            Statement st = conexion.createStatement();
            String sql= "INSERT INTO `viaticos`.`peticion` (`fecha_ini`, `actividad_rea`, `lugar_destino`, `vehiculo_inclui`, `personal_id_personal`, `usuario_id_usuario`, `fecha_emision`)"
                    + " VALUES ('"+fechaSalida+"', '"+actividad+"', '"+lugar+"', '"+necesita+"', '"+idPersona+"', '"+idUsuario+"', curdate());";
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
public DefaultTableModel Solicitudes(){
        
        
         DefaultTableModel table = new DefaultTableModel();

        try {
            table.addColumn("Vaticos Para:");
            table.addColumn("Puesto");
            table.addColumn("Area");
            table.addColumn("Fecha Salida");
            table.addColumn("Destino");
            table.addColumn("Folio");
            
            
            //sql
            String sql = "select concat(per.nombre,' ',per.apellido_pa,' ',per.apellido_ma)nombre, r.puesto,a.area,s.fecha_salida,s.lugar,p.folio from personal per,"+
            "puesto r,area a,solicitud s,peticion p where p.personal_id_personal=per.id_personal and puesto_id_puesto=r.id_puesto and r.area_id_area=a.id_area and peticion_folio=p.folio;";
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
            Logger.getLogger(SolictudesManejador.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            return table;
        }

        
    }
 public String RetornoEstado(String folioId){
        
        String estado="";
        
        try {
            
            String sql = "SELECT estado_solicitud from solicitud where peticion_folio="+folioId+"";

            conexion = db.getConexion(); //obtenemos conexion 
            Statement st = conexion.createStatement(); //crear obteno de consulta
            ResultSet resultados = st.executeQuery(sql); //ejecutar consulta
            //vemos si encontro coincidencias
            if (resultados.next()) {
                
                estado=resultados.getObject(1).toString();
            }

            conexion.close();
        } //esAdministrador
        catch (SQLException ex) {
            Logger.getLogger(LoginManejador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, "No Hay Conexion a la Base de Datos");
        }

        return estado;
        

       
    }















   
}
