/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ctrl;

import Dal.ParametrosDal;
import Modelo.ParametrosMd;
import java.sql.SQLException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Textbox;

/**
 *
 * @author hp
 */
public class GradosCtrl extends GenericForwardComposer {
    
     String User;
     private Textbox nomGrado;
     ParametrosMd us = new ParametrosMd();
    ParametrosDal usd = new ParametrosDal();

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
         User = desktop.getSession().getAttribute("USUARIO").toString();

    }
    
     public void onClick$btnGuardar(Event e) throws SQLException, ClassNotFoundException {
        int op = 0;

        if (nomGrado.getText().trim().equals("")) {
            op = 1;
        }
       
        if (op == 0) {
            us = new ParametrosMd();
            us.setGrado(nomGrado.getText().toUpperCase());
            us.setUsuario(User);

            us = usd.savegrado(us);
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
        public void clear(){
        nomGrado.setText("");
       
     
    }
    
}
