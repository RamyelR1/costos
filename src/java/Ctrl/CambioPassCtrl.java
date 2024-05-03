/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ctrl;


import Dal.ResetPassDal;
import Util.Cripto;
import java.security.Key;
import java.sql.SQLException;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author alfonsochang
 */
public class CambioPassCtrl extends GenericForwardComposer {

    //private Connection conexion = null;
    private Textbox email;
    private Textbox usuario;
    private Textbox token;
    private Textbox pass;
    private Textbox pass2;
    Window modalDialog;
    Cripto cp = new Cripto();
    ResetPassDal rp = new ResetPassDal();

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        //System.out.println("Id Trans.: " + pass.getText());
        //pass.setDisabled(true);
        //pass2.setDisabled(true);
        
        EventQueues.lookup("myEventQueue", EventQueues.DESKTOP, true)
                .subscribe(new EventListener() {
                    public void onEvent(Event event) throws Exception {
                        usuario.setText(event.getData().toString());
                        System.out.println("Usuario Dentro de EVENT.: " + event.getData());
                    }});
        
    }



    public void onClick$actualizarBtn(Event e) throws SQLException, ClassNotFoundException {

        if(pass.getText().equals(pass2.getText())){
            rp.cambioPass(usuario.getText(), pass.getText());
            modalDialog.detach();
        } else {
            Clients.showNotification("Las Contraseñas <br/>  <br/> No Coinciden <br/> <br/>Intentelo de Nuevo",
                            Clients.NOTIFICATION_TYPE_WARNING, null, "middle_center", 0);
        }
//        String pas = parseJWT(token.getText());
//        System.out.println("PASS.: "+pas);
//        Cripto cp = new Cripto();
//        String newpass2 = cp.decrypt(pas,key);
//        System.out.println("DESENCRIPTA PASS.: "+newpass2);
//        if(newpass2.equals("nuevosistema*20") && (pass.getText().equals(pass2.getText()))){
//            String newpas = cp.encrypt(pass.getText(), key);
//            UsuariosDal ap = new UsuariosDal();
//            ap.ActualizaPass(usuario.getText(), newpas);
//            Clients.showNotification("Actualizacion Exitosa <br/>  <br/> Inicie sesion con la nueva Contraseña <br/>");
//            modalDialog.detach();
//        }else{
//            Clients.showNotification("Contraseñas no coinsiden <br/>  <br/> Ingrese de nuevo la contraseña <br/>");
//        }
    }
    
    public void onClick$salirBtn(Event e){ modalDialog.detach(); }
    
    

}
