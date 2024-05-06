package Ctrl;

import Dal.ParametrosDal;
import Modelo.ParametrosMd;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zul.Textbox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;

public class ParametrosCtrl extends GenericForwardComposer {

    private Textbox codigo;
    private Textbox n_moneda;
    private Textbox moneda;
    private Textbox cambio;
    private Textbox anio;
    private Combobox status;

    ParametrosMd prtMd = new ParametrosMd();
    List<ParametrosMd> allParametros = new ArrayList<ParametrosMd>();

    private Listbox lb2;
    
    ParametrosDal parametro = new ParametrosDal();

    String User;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        allParametros = parametro.RSelect();
        codigo.focus();
        User = desktop.getSession().getAttribute("USUARIO").toString();
    }

    public void onChange$codigo(Event e) throws SQLException, ParseException {
        if (codigo.getText() == null) {
            clear();
        } else if (codigo.getText().isEmpty()) {
            clear();
        } else {
            for (ParametrosMd dt : allParametros) {
                if (dt.getCod().equals(codigo.getText())) {
                    codigo.focus();
                    codigo.setText(dt.getCod());
                    n_moneda.setText(dt.getnMod());
                    moneda.setText(dt.getMod());
                    cambio.setText(dt.getCam());
                    anio.setText(dt.getAnio());
                    status.setText(dt.getSta());
                }
            }
        }
    }

    public void onClick$btnGuardar(Event e) throws SQLException, ClassNotFoundException {
        int op = 0;

        if (codigo.getText().trim().equals("") || n_moneda.getText().trim().equals("") || 
            moneda.getText().trim().equals("") || cambio.getText().trim().equals("") || 
            anio.getText().trim().equals("") || status.getSelectedIndex() == -1) {
            op = 1;
        }

        if (op == 0) {
            prtMd = new ParametrosMd();
            prtMd.setCod(codigo.getText());
            prtMd.setnMod(n_moneda.getText().toUpperCase());
            prtMd.setMod(moneda.getText().toUpperCase());
            prtMd.setCam(cambio.getText().toUpperCase());
            prtMd.setAnio(anio.getText().toUpperCase());
            prtMd.setSta(status.getSelectedItem().getValue().toString());
            prtMd.setUser(desktop.getSession().getAttribute("USUARIO").toString());

            // Verificar si el registro ya existe
            boolean existeRegistro = parametro.existeRegistro(prtMd.getCod());

            if (existeRegistro) {
                // Actualizar registro
                prtMd = parametro.updateParametro(prtMd);
            } else {
                // Guardar nuevo registro
                prtMd = parametro.saveParametro(prtMd);
            }

            if (prtMd.getResp().equals("1")) {
                clear();
                Clients.showNotification(prtMd.getMsg() + "<br/>",
                        Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 0);
            } else {
                Clients.showNotification(prtMd.getMsg() + "<br/>",
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
        moneda.setText("");
        n_moneda.setText("");
        cambio.setText("");
        anio.setText("");
        status.setText("");

        codigo.focus();
    }
}
