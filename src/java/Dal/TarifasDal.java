package Dal;

import Conexion.Conexion;
import Modelo.TarifasMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

/**
 *
 * @author alfredo
 */
public class TarifasDal {

    Conexion obtener = new Conexion();
    Connection conexion;
    TarifasMd tar = new TarifasMd();
    
    public List<TarifasMd> RSelect() throws SQLException, ClassNotFoundException {
        Statement st = null;
        ResultSet rs = null;
        List<TarifasMd> allTarifas = new ArrayList<TarifasMd>();
        
        String query = "SELECT TAR_ID,TAR_NOMBRE,\n"
                + " TO_CHAR (TAR_FECHA, 'DD/MM/YYYY'),\n"
                + "DECODE(TAR_ESTADO, 'A','ACTIVO', 'I','INACTIVO')\n"
                + " FROM SOPEPQ.COST_TARIFAS\n"
                + "ORDER BY TAR_ID";  
        
        try {
            conexion = obtener.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            TarifasMd rg;
            while (rs.next()) {
                rg = new TarifasMd();
                rg.setCod(rs.getString(1));
                rg.setNom(rs.getString(2));
                rg.setEst(rs.getString(4));
                rg.setFec(rs.getString(3));

                allTarifas.add(rg);
            }

            st.close();
            rs.close();
            conexion.close();
            conexion = null;
        } catch (SQLException e) {
            conexion.close();
            conexion = null;
            Clients.showNotification("ERROR AL CONSULTAR (Rselect) <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return allTarifas;
        
    }

    public TarifasMd saveTarifas(TarifasMd data) {
        tar = new TarifasMd();
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = "";
        int resp = 0;
        tar = new TarifasMd();

        String query1 = "SELECT MAX(Tar_id) + 1 AS id FROM SOPEPQ.COST_TARIFAS";

        String sql = "INSERT INTO SOPEPQ.COST_TARIFAS (TAR_ID, TAR_NOMBRE, Tar_estado, Tar_fecha) "
                + "VALUES (?,?,?, TO_DATE(?, 'DD/MM/YY'))";

        try {
            conexion = obtener.Conexion();
            conexion.setAutoCommit(false);
            if (resp == 0) {
                st = conexion.createStatement();
                rs = st.executeQuery(query1);
                while (rs.next()) {
                    id = rs.getString("id");
                }
                st.close();
                rs.close();

                ps = conexion.prepareStatement(sql);

//                ps.setString(1, id);
                ps.setString(1, data.getCod());
                ps.setString(2, data.getNom());
                ps.setString(3, data.getEst());
                ps.setString(4, data.getFec());              

                ps.executeUpdate();
                ps.close();
                conexion.commit();
                tar.setResp("1");
                tar.setMsg("Registro Guardado Exitosamente");
            }
            conexion.close();
            conexion = null;

        } catch (Exception e) {
            System.out.println("ERROR CATCH: " + e.getMessage());
            tar.setResp("0");
            tar.setMsg(e.getMessage());

        }

        return tar;

    }

    public TarifasMd updateTarifas(TarifasMd data) throws SQLException, ClassNotFoundException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int resp = 0;
        tar = new TarifasMd();

        String sql = "UPDATE SOPEPQ.COST_TARIFAS SET Tar_nombre=?, tar_estado=?, tar_fecha=? WHERE Tar_id=?";

        try {
            conexion = obtener.Conexion();
            conexion.setAutoCommit(false);

            ps = conexion.prepareStatement(sql);

            ps.setString(1, data.getNom());
            ps.setString(2, data.getEst());
            ps.setString(3, data.getFec());

            resp = ps.executeUpdate();

            if (resp > 0) {
                conexion.commit();
                tar.setResp("1");
                tar.setMsg("Registro Actualizado Exitosamente");
            } else {
                conexion.rollback();
                tar.setResp("0");
                tar.setMsg("No se pudo actualizar el registro");
            }

            ps.close();
            conexion.close();

        } catch (SQLException e) {
            System.out.println("ERROR CATCH: " + e.getMessage());
            tar.setResp("0");
            tar.setMsg(e.getMessage());
        }

        return tar;
        
        
        
    }  
    
    
    public boolean existeRegistro(String codigo) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean existe = false;

    String query = "SELECT COUNT(*) FROM SOPEPQ.COST_TARIFAS WHERE tar_id = ?";
    
    
    try {
            conexion = obtener.Conexion();
            ps = conexion.prepareStatement(query);
            ps.setString(1, codigo);
            rs = ps.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                if (count > 0) {
                    existe = true;
                }
            }

            rs.close();
            ps.close();
            conexion.close();

        } catch (SQLException e) {
            System.out.println("ERROR CATCH: " + e.getMessage());
        }

        return existe;
        
        
    }  
     
}
