
package Ctrl;

import Dal.UsuarioDal;
import Modelo.UsuarioMd;
import java.sql.SQLException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Textbox;

public class UsuarioCtrl extends GenericForwardComposer {

    private Textbox nom;
    private Textbox tel;
    private Textbox obs;
    private Textbox usu;
    private Textbox clave;
    private Combobox rol;
    private Combobox sexo;
    private Datebox nacimi;
    String User;

    UsuarioMd us = new UsuarioMd();
    UsuarioDal usd = new UsuarioDal();

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
//        rol.setSelectedIndex(0);
    }

    public void onClick$btnGuardar(Event e) throws SQLException, ClassNotFoundException {
        int op = 0;

        if (usu.getText().trim().equals("")) {
            op = 1;
        }
        if (clave.getText().trim().equals("")) {
            op = 1;
        }
       
        if (op == 0) {
            us.setUser(usu.getText());
            us.setPass(clave.getText());
            
           
            
            us = usd.saveUS(us);

            if (us.getResp().equals("1")) {
                clear();
                Clients.showNotification(us.getMsg() + "<br/>",
                        Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 0);
            } else {
                Clients.showNotification(us.getMsg() + "<br/>",
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
        usu.setText("");
        clave.setText("");
    }

}
