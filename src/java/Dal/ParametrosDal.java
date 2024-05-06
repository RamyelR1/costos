package Dal;

import Conexion.Conexion;
import Modelo.ParametrosMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

public class ParametrosDal {

    Conexion obtener = new Conexion();
    Connection conexion;
    ParametrosMd par = new ParametrosMd();

    public List<ParametrosMd> RSelect() throws SQLException, ClassNotFoundException {
        Statement st = null;
        ResultSet rs = null;
        List<ParametrosMd> allParametros = new ArrayList<ParametrosMd>();

        String query = "SELECT par_id,par_nombre,\n"
                + "       par_moneda,par_tipocambio,\n"
                + "       par_anio,\n"
                + "DECODE(par_estatus, 'A','ACTIVO', 'I','INACTIVO')\n"
                + "FROM    sopepq.cost_parametros2\n"
                + "ORDER BY par_id";

        try {
            conexion = obtener.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            ParametrosMd rg;
            while (rs.next()) {
                rg = new ParametrosMd();
                rg.setCod(rs.getString(1));
                rg.setnMod(rs.getString(2));
                rg.setMod(rs.getString(3));
                rg.setCam(rs.getString(4));
                rg.setAnio(rs.getString(5));
                rg.setSta(rs.getString(6));

                allParametros.add(rg);
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
        return allParametros;
    }

    public ParametrosMd saveParametro(ParametrosMd data) {
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = "";
        int resp = 0;
        par = new ParametrosMd();

        String query1 = "SELECT MAX(par_id) + 1 AS id FROM SOPEPQ.cost_parametros2";

        String sql = "INSERT INTO SOPEPQ.cost_parametros2 (par_id, par_nombre, par_moneda, par_tipocambio, par_anio, par_usuario_crea, par_fecha_crea, par_estatus) "
                + "VALUES (?, ?, ?, ?, ?, ?, SYSDATE, ?)";
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

                ps.setString(1, id);
                ps.setString(2, data.getnMod());
                ps.setString(3, data.getMod());
                ps.setString(4, data.getCam());
                ps.setString(5, data.getAnio());
                ps.setString(6, data.getUser());
                ps.setString(7, data.getSta());

                ps.executeUpdate();
                ps.close();
                conexion.commit();
                par.setResp("1");
                par.setMsg("Registro Guardado Exitosamente");
            }
            conexion.close();
            conexion = null;

        } catch (Exception e) {
            System.out.println("ERROR CATCH: " + e.getMessage());
            par.setResp("0");
            par.setMsg(e.getMessage());

        }

        return par;
    }

    public ParametrosMd updateParametro(ParametrosMd data) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int resp = 0;
        par = new ParametrosMd();

        String sql = "UPDATE SOPEPQ.cost_parametros2 SET par_nombre=?, par_moneda=?, par_tipocambio=?, par_anio=?, "
                + "par_usuario_mod=?, par_fecha_mod=SYSDATE, par_estatus=? WHERE par_id=?";

        try {
            conexion = obtener.Conexion();
            conexion.setAutoCommit(false);

            ps = conexion.prepareStatement(sql);

            ps.setString(1, data.getnMod());
            ps.setString(2, data.getMod());
            ps.setString(3, data.getCam());
            ps.setString(4, data.getAnio());
            ps.setString(5, data.getUser());
            ps.setString(6, data.getSta());
            ps.setString(7, data.getCod());

            resp = ps.executeUpdate();

            if (resp > 0) {
                conexion.commit();
                par.setResp("1");
                par.setMsg("Registro Actualizado Exitosamente");
            } else {
                conexion.rollback();
                par.setResp("0");
                par.setMsg("No se pudo actualizar el registro");
            }

            ps.close();
            conexion.close();

        } catch (SQLException e) {
            System.out.println("ERROR CATCH: " + e.getMessage());
            par.setResp("0");
            par.setMsg(e.getMessage());
        }

        return par;
    }

    public boolean existeRegistro(String codigo) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean existe = false;

        String query = "SELECT COUNT(*) FROM SOPEPQ.cost_parametros2 WHERE par_id = ?";

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
