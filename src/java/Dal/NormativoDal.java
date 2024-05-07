package Dal;

import Conexion.Conexion;
import Modelo.NormativoMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

public class NormativoDal {

    Conexion obtener = new Conexion();
    Connection conexion;
    NormativoMd nor = new NormativoMd();

    public List<NormativoMd> RSelect() throws SQLException, ClassNotFoundException {
        Statement st = null;
        ResultSet rs = null;
        List<NormativoMd> allNormativo = new ArrayList<NormativoMd>();

        String query = "SELECT NOR_ID, NOR_NOMBRE,\n"
                + "DECODE(NOR_CATEGORIA, '1','CATEGORÍA 1', '2','CATEGORÍA 2'), NOR_VALOR,\n"
                + "NOR_CANTIDAD,\n"
                + "TO_CHAR (NOR_VALIDEZ, 'DD/MM/YYYY')\n"
                + "FROM    SOPEPQ.COST_NORMATIVO\n"
                + "ORDER BY NOR_ID";

        try {
            conexion = obtener.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            NormativoMd rg;
            while (rs.next()) {
                rg = new NormativoMd();
                rg.setCod(rs.getString(1));
                rg.setNombre(rs.getString(2));
                rg.setCategoria(rs.getString(3));
                rg.setValor(rs.getString(4));
                rg.setCantidad(rs.getString(5));
                rg.setValidez(rs.getString(6));

                allNormativo.add(rg);
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
        return allNormativo;
    }

    public NormativoMd saveNormativo(NormativoMd data) {
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = "";
        int resp = 0;
        nor = new NormativoMd();

        String query1 = "SELECT MAX(NOR_ID) + 1 AS id FROM SOPEPQ.COST_NORMATIVO"; 

        String sql = "INSERT INTO SOPEPQ.COST_NORMATIVO (NOR_ID, NOR_NOMBRE, NOR_CATEGORIA, NOR_VALOR, NOR_CANTIDAD, NOR_VALIDEZ) " 
                + "VALUES (?, ?, ?, ?, ?, ?)";
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
                ps.setString(2, data.getNombre());
                ps.setString(3, data.getCategoria());
                ps.setString(4, data.getValor());
                ps.setString(5, data.getCantidad());
                ps.setString(6, data.getValidez());

                ps.executeUpdate();
                ps.close();
                conexion.commit();
                nor.setResp("1");
                nor.setMsg("Registro Guardado Exitosamente");
            }
            conexion.close();
            conexion = null;

        } catch (Exception e) {
            System.out.println("ERROR CATCH: " + e.getMessage());
            nor.setResp("0");
            nor.setMsg(e.getMessage());

        }

        return nor;
    }

    public NormativoMd updateNormativo(NormativoMd data) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int resp = 0;
        nor = new NormativoMd();

        String sql = "UPDATE SOPEPQ.COST_NORMATIVO SET NOR_NOMBRE=?, NOR_CATEGORIA=?, NOR_VALOR=?, NOR_CANTIDAD=?, "
                + "NOR_VALIDEZ=? WHERE NOR_ID=?"; 

        try {
            conexion = obtener.Conexion();
            conexion.setAutoCommit(false);

            ps = conexion.prepareStatement(sql);

            ps.setString(1, data.getNombre());
            ps.setString(2, data.getCategoria());
            ps.setString(3, data.getValor());
            ps.setString(4, data.getCantidad());
            ps.setString(5, data.getValidez());
            ps.setString(6, data.getCod());

            resp = ps.executeUpdate();

            if (resp > 0) {
                conexion.commit();
                nor.setResp("1");
                nor.setMsg("Registro Actualizado Exitosamente");
            } else {
                conexion.rollback();
                nor.setResp("0");
                nor.setMsg("No se pudo actualizar el registro");
            }

            ps.close();
            conexion.close();

        } catch (SQLException e) {
            System.out.println("ERROR CATCH: " + e.getMessage());
            nor.setResp("0");
            nor.setMsg(e.getMessage());
        }

        return nor;
    }

    public boolean existeRegistro(String codigo) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean existe = false;

        String query = "SELECT COUNT(*) FROM SOPEPQ.COST_NORMATIVO WHERE NOR_ID = ?"; 
        
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