package Ctrl;

import Dal.NormativoDal;
import Modelo.NormativoMd;
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
import org.zkoss.zul.Datebox;

public class NormativoCtrl extends GenericForwardComposer {

    private Textbox codigo;
    private Textbox nombre; 
    private Combobox categoria; 
    private Textbox valor; 
    private Textbox cantidad; 
    private Datebox validez; 
    
    NormativoMd norMd = new NormativoMd();
    List<NormativoMd> allNormativo = new ArrayList<NormativoMd>();

    NormativoDal normativo = new NormativoDal();

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        allNormativo = normativo.RSelect();
        codigo.focus();
    }

    public void onChange$codigo(Event e) throws SQLException, ParseException {
        if (codigo.getText() == null) {
            clear();
        } else if (codigo.getText().isEmpty()) {
            clear();
        } else {
            for (NormativoMd dt : allNormativo) {
                if (dt.getCod().equals(codigo.getText())) {
                    codigo.focus();
                    codigo.setText(dt.getCod());
                    nombre.setText(dt.getNombre()); 
                    categoria.setText(dt.getCategoria()); 
                    valor.setText(dt.getValor()); 
                    cantidad.setText(dt.getCantidad()); 
                    validez.setText(dt.getValidez()); 
                }
            }
        }
    }

    public void onClick$btnGuardar(Event e) throws SQLException, ClassNotFoundException {
        int op = 0;

        if (codigo.getText().trim().equals("") || nombre.getText().trim().equals("") || 
            categoria.getSelectedIndex() == -1 || valor.getText().trim().equals("") || 
            cantidad.getText().trim().equals("") || validez.getText() == null) {
            op = 1;
        }

        if (op == 0) {
            norMd = new NormativoMd();
            norMd.setCod(codigo.getText());
            norMd.setNombre(nombre.getText()); 
            norMd.setCategoria(categoria.getSelectedItem().getValue().toString()); 
            norMd.setValor(valor.getText().toUpperCase()); 
            norMd.setCantidad(cantidad.getText().toUpperCase()); 
            norMd.setValidez(validez.getText()); 

            boolean existeRegistro = normativo.existeRegistro(norMd.getCod());

            if (existeRegistro) {
                norMd = normativo.updateNormativo(norMd);
            } else {
                norMd = normativo.saveNormativo(norMd);
            }

            if (norMd.getResp().equals("1")) {
                clear();
                Clients.showNotification(norMd.getMsg() + "<br/>",
                        Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 0);
            } else {
                Clients.showNotification(norMd.getMsg() + "<br/>",
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
        categoria.setSelectedIndex(-1); 
        valor.setText(""); 
        cantidad.setText("");
        validez.setValue(null);

        codigo.focus();
    }
}