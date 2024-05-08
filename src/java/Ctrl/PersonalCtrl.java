package Ctrl;

import Dal.PersonalDal;
import Modelo.ParametrosMd;
import Modelo.PersonalMd;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zul.Textbox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Listbox;

/**
 *
 * @author EL R1
 */
public class PersonalCtrl {

    private Textbox codigo;
    private Textbox nombre;
    private Combobox departamento;
    private Combobox gerencia;
    private Combobox renglon;
    private Textbox telefono;

    PersonalMd prsMd = new PersonalMd();
    List<PersonalMd> allPersonal = new ArrayList<PersonalMd>();

    PersonalDal personal = new PersonalDal();

    String User;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        allPersonal = personal.RSelect();
        codigo.focus();
        User = desktop.getSession().getAttribute("USUARIO").toString();
    }

    public void onChange$codigo(Event e) throws SQLException, ParseException {
        if (codigo.getText() == null) {
            clear();
        } else if (codigo.getText().isEmpty()) {
            clear();
        } else {
            for (PersonalMd dt : allPersonal) {
                if (dt.getCod().equals(codigo.getText())) {
                    codigo.focus();
                    codigo.setText(dt.getCod());
                    nombre.setText(dt.getNom());
                    departamento.setText(dt.getDep());
                    gerencia.setText(dt.getGer());
                    renglon.setText(dt.getReg());
                    telefono.setText(dt.getTel());

                }
            }
        }
    }

    public void onClick$btnGuardar(Event e) throws SQLException, ClassNotFoundException {
        int op = 0;

        if (codigo.getText().trim().equals("") || nombre.getText().trim().equals("")
                || departamento.getSelectedIndex() == -1 || gerencia.getSelectedIndex() == -1
                || renglon.getSelectedIndex() == -1 || telefono.getText().trim().equals("")) {
            op = 1;
        }

        if (op == 0) {
            prsMd = new PersonalMd();
            prsMd.setCod(codigo.getText());
            prsMd.setNom(nombre.getText().toUpperCase());
            prsMd.setDep(departamento.getSelectedItem().getValue().toString());
            prsMd.setGer(gerencia.getSelectedItem().getValue().toString());
            prsMd.setReg(renglon.getSelectedItem().getValue().toString());
            prsMd.setTel(telefono.getText().toUpperCase());

            prsMd.setUser(desktop.getSession().getAttribute("USUARIO").toString());

            // Verificar si el registro ya existe
            boolean existeRegistro = personal.existeRegistro(prsMd.getCod());

            if (existeRegistro) {
                // Actualizar registro
                prsMd = personal.updateParametro(prsMd);
            } else {
                // Guardar nuevo registro
                prsMd = personal.saveParametro(prsMd);
            }

            if (prsMd.getResp().equals("1")) {
                clear();
                Clients.showNotification(prsMd.getMsg() + "<br/>",
                        Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 0);
            } else {
                Clients.showNotification(prsMd.getMsg() + "<br/>",
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
        departamento.setText("");
        gerencia.setText("");
        renglon.setText("");
        telefono.setText("");

        codigo.focus();
    }
}
