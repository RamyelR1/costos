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
                    nombre.setText(dt.getNombre()); // Cambio: se establece el valor del campo nombre
                    categoria.setText(dt.getCategoria()); // Cambio: se establece el valor del campo categoria
                    valor.setText(dt.getValor()); // Cambio: se establece el valor del campo valor
                    cantidad.setText(dt.getCantidad()); // Cambio: se establece el valor del campo cantidad
                    validez.setText(dt.getValidez()); // Cambio: se establece el valor del campo validez
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
            norMd.setNombre(nombre.getText()); // Cambio: se obtiene el valor del campo nombre
            norMd.setCategoria(categoria.getSelectedItem().getValue().toString()); // Cambio: se obtiene el valor del campo categoria
            norMd.setValor(valor.getText().toUpperCase()); // Cambio: se obtiene el valor del campo valor
            norMd.setCantidad(cantidad.getText().toUpperCase()); // Cambio: se obtiene el valor del campo cantidad
            norMd.setValidez(validez.getText()); // Cambio: se obtiene el valor del campo validez

            // Verificar si el registro ya existe
            boolean existeRegistro = normativo.existeRegistro(norMd.getCod());

            if (existeRegistro) {
                // Actualizar registro
                norMd = normativo.updateNormativo(norMd);
            } else {
                // Guardar nuevo registro
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
        nombre.setText(""); // Cambio: se limpia el campo nombre
        categoria.setSelectedIndex(-1); // Cambio: se limpia el campo categoria
        valor.setText(""); // Cambio: se limpia el campo valor
        cantidad.setText(""); // Cambio: se limpia el campo cantidad
        validez.setValue(null); // Cambio: se limpia el campo validez

        codigo.focus();
    }
}