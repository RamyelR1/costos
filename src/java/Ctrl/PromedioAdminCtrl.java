/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ctrl;

import Dal.NotasDal;
import Dal.PromedioDal;
import Modelo.NotasMd;
import Modelo.PromedioMd;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

/**
 *
 * @author HP 15
 */
public class PromedioAdminCtrl extends GenericForwardComposer {

    private Combobox grado;
    private Textbox busca;
    private Listbox lb2;
    private List<PromedioMd> allData = new ArrayList<PromedioMd>();
    private List<PromedioMd> allItem = new ArrayList<PromedioMd>();
    PromedioDal bp = new PromedioDal();
    NotasDal renglonDal = new NotasDal();
    private List<NotasMd> allproyecto = new ArrayList<NotasMd>();
    String User;

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        allproyecto = renglonDal.allGrado();
        grado.setModel(new ListModelList(allproyecto));

        User = desktop.getSession().getAttribute("USUARIO").toString();

    }

    public void onChange$grado() throws SQLException, ClassNotFoundException {
        allData = bp.BuscarProAdmin(grado.getSelectedItem().getValue().toString());
        allItem.clear();
        PromedioMd bsp;
        for (PromedioMd item : allData) {
            bsp = new PromedioMd();
            bsp.setCodigo(item.getCodigo());
            bsp.setNombre(item.getNombre());
            bsp.setCurso(item.getCurso());
            bsp.setNota(item.getNota());
            bsp.setProfesor(item.getProfesor());

            allItem.add(bsp);

        }
        lb2.setModel(new ListModelList(allItem));

    }

    public void onChange$busca() throws SQLException, ClassNotFoundException {

        allItem.clear();
        PromedioMd bsp;
        if (busca.getText().equals("")) {
            onChange$grado();
        } else {

            for (PromedioMd item : allData) {
                if ((!busca.getText().equals("") && item.getNombre().contains(busca.getText().toUpperCase())) || (!busca.getText().equals("") && item.getCodigo().contains(busca.getText().toUpperCase()))) {
                    bsp = new PromedioMd();
                    bsp.setCodigo(item.getCodigo());
                    bsp.setNombre(item.getNombre());
                    bsp.setCurso(item.getCurso());
                    bsp.setNota(item.getNota());
                    bsp.setProfesor(item.getProfesor());

                    allItem.add(bsp);

                }
            }
            lb2.setModel(new ListModelList(allItem));
        }

    }

}
