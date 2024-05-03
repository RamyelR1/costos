/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ctrl;

import Dal.MateriaDal;
import Modelo.MateriaMd;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;

/**
 *
 * @author hp
 */
public class MateriaCtrl extends GenericForwardComposer {

    private List<MateriaMd> allproyecto = new ArrayList<MateriaMd>();
    MateriaDal renglonDal = new MateriaDal();
    private Combobox profesor;
    private Combobox seccion;
    private Textbox materia;
    MateriaMd us = new MateriaMd();
    String User;

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        allproyecto = renglonDal.allproyecto();
        profesor.setModel(new ListModelList(allproyecto));
        User = desktop.getSession().getAttribute("USUARIO").toString();

    }

    public void onClick$btnGuardar(Event e) throws SQLException, ClassNotFoundException {
        int op = 0;

        if (materia.getText().trim().equals("")) {
            op = 1;
        }
        if (seccion.getSelectedIndex() == -1) {
            op = 1;
        }
        if (profesor.getSelectedIndex() == -1) {
            op = 1;
        }

        if (op == 0) {
            us = new MateriaMd();
            us.setMateria(materia.getText().toUpperCase());
            us.setProCodigo(profesor.getSelectedItem().getValue().toString());
            us.setSeccion(seccion.getSelectedItem().getValue().toString());
            us.setUsuario(User);

            us = renglonDal.savegrado(us);
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
        materia.setText("");
        profesor.setSelectedIndex(-1);
        seccion.setSelectedIndex(-1);

    }

}
