package Dal;

import Conexion.Conexion;
import Modelo.PersonalMd;
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
 * @author EL R1
 */
public class PersonalDal {

    Conexion obtener = new Conexion();
    Connection conexion;
    PersonalMd par = new PersonalMd();

    public List<PersonalMd> RSelect() throws SQLException, ClassNotFoundException {
        Statement st = null;
        ResultSet rs = null;
        List<PersonalMd> allPersonal = new ArrayList<PersonalMd>();

        String query = "SELECT PER_CODIGO,PER_NOMBRE,\n"
                + "       PER_DEPTO,\n"
                + "       PER_GERENCIA,\n"
                + "       PER_RENGLON,\n"
                + "       PER_TELEFONO\n"
                + "FROM   SOPEPQ.COST_PERSONAL\n"
                + "ORDER BY PER_CODIGO";

        try {
            conexion = obtener.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            PersonalMd rg;
            while (rs.next()) {
                rg = new PersonalMd();
                rg.setCod(rs.getString(1));
                rg.setNom(rs.getString(2));
                rg.setDep(rs.getString(3));
                rg.setGer(rs.getString(4));
                rg.setReg(rs.getString(5));
                rg.setTel(rs.getString(6));

                allPersonal.add(rg);
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
        return allPersonal;
    }

    public PersonalMd savePersonal(PersonalMd data) {
        PreparedStatement ps = null;
        par = new PersonalMd();

        String sql = "INSERT INTO SOPEPQ.cost_parametros2 (par_nombre, par_moneda, par_tipocambio, par_anio, par_usuario_crea, par_fecha_crea, par_estatus) "
                + "VALUES (?, ?, ?, ?, ?, SYSDATE, ?)";
        try {
            conexion = obtener.Conexion();
            conexion.setAutoCommit(false);

            ps = conexion.prepareStatement(sql);

            ps.setString(1, data.getCod());
            ps.setString(2, data.getNom());
            ps.setString(3, data.getDep());
            ps.setString(4, data.getGer());
            ps.setString(5, data.getReg());
            ps.setString(6, data.getTel());

            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                par.setResp("1");
                par.setMsg("Registro Guardado Exitosamente. ID generado: " + id);
            }

            conexion.commit();
            ps.close();
            conexion.close();
            conexion = null;

        } catch (Exception e) {
            System.out.println("ERROR CATCH: " + e.getMessage());
            par.setResp("0");
            par.setMsg(e.getMessage());

        }

        return par;
    }

    public PersonalMd updateParametro(PersonalMd data) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int resp = 0;
        par = new PersonalMd();

        String sql = "UPDATE SOPEPQ.COST_PERSONAL "
                + "SET PER_NOMBRE = ?, "
                + "    PER_DEPTO = ?, "
                + "    PER_GERENCIA = ?, "
                + "    PER_RENGLON = ?, "
                + "    PER_TELEFONO = ? "
                + "WHERE PER_CODIGO = ?";

        try {
            conexion = obtener.Conexion();
            conexion.setAutoCommit(false);

            ps = conexion.prepareStatement(sql);

            ps.setString(1, data.getCod());
            ps.setString(2, data.getNom());
            ps.setString(3, data.getDep());
            ps.setString(4, data.getGer());
            ps.setString(5, data.getReg());
            ps.setString(6, data.getTel());

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
