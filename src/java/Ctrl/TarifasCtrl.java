package Ctrl;

import Dal.TarifasDal;
import Modelo.TarifasMd;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Textbox;

/**
 *
 * @author user
 */
public class TarifasCtrl extends GenericForwardComposer {

    private Textbox codigo;
    private Textbox nombre;
    private Combobox estado;
    private Datebox fec_crea;

    TarifasMd tarMd = new TarifasMd();
    List<TarifasMd> allTarifas = new ArrayList<TarifasMd>();

    TarifasDal Tarifa = new TarifasDal();

    String User;

    @Override

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        Clients.showNotification("Bienvenido al Modulo de Tarifas" + "<br/>",
                Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 0);
        allTarifas = Tarifa.RSelect();
        codigo.focus();
         User = desktop.getSession().getAttribute("USUARIO").toString();
    }
    
    
    public void onChange$codigo(Event e) throws SQLException, ParseException {
            System.out.println("entro al onchange");
        
        if (codigo.getText() == null) {
            clear();
        } else if (codigo.getText().isEmpty()) {
            clear();
        } else {
            for (TarifasMd dt : allTarifas) {
                if (dt.getCod().equals(codigo.getText())) {
                    codigo.focus();
                    codigo.setText(dt.getCod());
                    nombre.setText(dt.getNom());
                    estado.setText(dt.getEst());
                    fec_crea.setText(dt.getFec());
                   
                }
            }
        }
    }
    
    public void onClick$btnGuardar(Event e) throws SQLException, ClassNotFoundException {        int op = 0;

        if (codigo.getText().trim().equals("") || nombre.getText().trim().equals("") || 
            (estado.getSelectedIndex()==-1) || (fec_crea.getText().trim().equals("") )) {
            op = 1;
        }
     
        if (op == 0) {
            tarMd = new TarifasMd();
            tarMd.setCod(codigo.getText());
            tarMd.setNom(nombre.getText().toUpperCase());
            tarMd.setEst(estado.getSelectedItem().getValue().toString());
            tarMd.setFec(fec_crea.getText().toUpperCase());
           tarMd.setUser(desktop.getSession().getAttribute("USUARIO").toString());
            // tarMd.setUser("usuario");

            
            // Verificar si el registro ya existe
            boolean existeRegistro = Tarifa.existeRegistro(tarMd.getCod());
             

            if (existeRegistro) {
                // Actualizar registro
                tarMd = Tarifa.updateTarifas(tarMd);
            } else {
                // Guardar nuevo registro
                tarMd = Tarifa.saveTarifas(tarMd);
            }

            if (tarMd.getResp().equals("1")) {
                clear();
                Clients.showNotification(tarMd.getMsg() + "<br/>",
                        Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 0);
            } else {
                Clients.showNotification(tarMd.getMsg() + "<br/>",
                        Clients.NOTIFICATION_TYPE_WARNING, null, "middle_center", 0);
            }

        } else {
            Clients.showNotification("No puede dejar <br/>  <br/> Campos Vacios <br/> <br/>Intentelo de Nuevo",
                    Clients.NOTIFICATION_TYPE_WARNING, null, "middle_center", 0);
        }

        
        }
    
    
   public void onClick$btnNuevo(Event e) throws SQLException, ClassNotFoundException {
        clear();
    }
    
    public void clear() {
        
       codigo.setText("");
       nombre.setText("");
       estado.setText("");
       fec_crea.setText("");
    }
}
