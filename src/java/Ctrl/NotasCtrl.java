/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ctrl;

import Dal.MateriaDal;
import Dal.NotasDal;
import Modelo.MateriaMd;
import Modelo.NotasMd;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;

/**
 *
 * @author hp
 */
public class NotasCtrl extends GenericForwardComposer {

    private Combobox estudiante;
    private Combobox profesor;
    private Textbox codigo;
    private Combobox materia;
    private Combobox grado;
    private Doublebox nota;
    private Combobox bimestre;
    private List<MateriaMd> allproyecto1 = new ArrayList<MateriaMd>();
    MateriaDal renglonDal1 = new MateriaDal();
    NotasDal renglonDal = new NotasDal();
    private List<NotasMd> allproyecto = new ArrayList<NotasMd>();
    NotasMd us = new NotasMd();
    String User;

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        allproyecto = renglonDal.allproyecto();
        estudiante.setModel(new ListModelList(allproyecto));
        allproyecto1 = renglonDal1.allproyecto();
        profesor.setModel(new ListModelList(allproyecto1));
        //para mostar materias
        allproyecto = renglonDal.allMateria();
        materia.setModel(new ListModelList(allproyecto));
        //para mostrar grados
        allproyecto = renglonDal.allGrado();
        grado.setModel(new ListModelList(allproyecto));

        User = desktop.getSession().getAttribute("USUARIO").toString();

    }
    
     public void onChange$nota(Event e) {
        if (nota.getText().equals("")) {
            Clients.showNotification("<br/>" + "NOTA NO PUEDE ESTAR VACIA",
                    Clients.NOTIFICATION_TYPE_WARNING, null, "middle_center", 3000);
            nota.setText("0");
           
            nota.focus();
        } else {
            float nota1 = Float.parseFloat(nota.getText());
            if(nota1>100 || nota1<0){
                 Clients.showNotification("<br/>" + "NOTA NO PUEDE SER MAYOR A 100 O MENOR A 0",
                    Clients.NOTIFICATION_TYPE_WARNING, null, "middle_center", 3000);
            nota.setText("0");
           
            nota.focus();
            }
            
           

        }

    }

    public void onClick$btnGuardar(Event e) throws SQLException, ClassNotFoundException {
        int op = 0;

        if (estudiante.getSelectedIndex() == -1) {
            op = 1;
        }
        if (materia.getSelectedIndex() == -1) {
            op = 1;
        }
        if (grado.getSelectedIndex() == -1) {
            op = 1;
        }

        if (profesor.getSelectedIndex() == -1) {
            op = 1;
        }
        if (nota.getText().trim().equals("")) {
            op = 1;
        }
        if (bimestre.getSelectedIndex() == -1) {
            op = 1;
        }

        if (op == 0) {
            us = new NotasMd();
            us.setEstCodigoMostrar(codigo.getText().toUpperCase());
            us.setEstCodigo(estudiante.getSelectedItem().getValue().toString());
            us.setMateCodigo(materia.getSelectedItem().getValue().toString());
            us.setGraCodigo(grado.getSelectedItem().getValue().toString());
            us.setProCodigo(profesor.getSelectedItem().getValue().toString());
            us.setNota(nota.getText().toUpperCase());
            us.setBimestre(bimestre.getSelectedItem().getValue().toString());

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

    public void onChange$estudiante(Event e) {
        us = new NotasMd();

        us = renglonDal.verSubRenglon(estudiante.getSelectedItem().getValue().toString());
        if (us.getResp().equals("1")) {
            codigo.setText(us.getEstCodigoMostrar());

        }

    }

    public void onChange$codigo(Event e) {
        us = new NotasMd();
        us = renglonDal.MostrarProducto(codigo.getText());
        if (us.getResp().equals("1")) {

            BuscaItem(us.getEstCodigo(), this.estudiante);

        } else {

            codigo.setText("");

            Clients.showNotification(us.getMsg() + "<br/>",
                    Clients.NOTIFICATION_TYPE_WARNING, null, "middle_center", 3000);
        }

    }
    
    public void onClick$btnNuevo(Event e) throws SQLException, ClassNotFoundException {
        clear();
    }

    public void clear() {
        codigo.setText("");
//        profesor.setSelectedIndex(-1);
        estudiante.setSelectedIndex(-1);
        nota.setText("");
        materia.setSelectedIndex(-1);
//        grado.setSelectedIndex(-1);
        bimestre.setSelectedIndex(-1);

    }

    public void BuscaItem(String letra, Combobox cb) {
        for (int i = 0; i < cb.getItemCount(); i++) {
            if (letra.equals(cb.getItemAtIndex(i).getValue())) {
                cb.setSelectedIndex(i);
                break;
            }
        }
    }

}
