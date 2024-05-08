package Enviroment;

import Util.Bitacora;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;

@SuppressWarnings("serial")
public class menuCtrl extends GenericForwardComposer {

    @Wire
    private Label lblUser;

    @Wire
    private Include rootPagina;

    private A linkLAD;
    String User;

    @SuppressWarnings("unchecked")
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        linkLAD.setVisible(false);

        User = desktop.getSession().getAttribute("USUARIO").toString();
        System.out.println("USUARIO..: " + User);
        if (User.equals("costos")) {
            linkLAD.setVisible(true);
        }

        lblUser.setValue(User);
        rootPagina.setSrc("/Views/Principal.zul");
        Clients.showNotification("¡Hola, " + User + " Bienvenido al Sistema!<br/>",
                Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 2000);
    }

    @SuppressWarnings("rawtypes")
    EventListener evtOp = new EventListener() {
        @Override
        public void onEvent(Event evt) throws Exception {
        }
    };

    public void onClick$linkHOME(Event evt) {
        rootPagina.setSrc("/Views/Principal.zul");
    }

    public void onClick$linkLUS(Event evt) {
        rootPagina.setSrc("/Views/Usuario.zul");
    }

    public void onClick$linkLUSUP(Event evt) {
        rootPagina.setSrc("/Views/UsuarioUP.zul");
    }

    public void onClick$linkPARA(Event evt) {
        rootPagina.setSrc("/Views/Parametros.zul");
    }

    public void onClick$linkPERS(Event evt) {
        rootPagina.setSrc("/Views/Personal.zul");
    }

    public void onClick$linkNOR(Event evt) {
        rootPagina.setSrc("/Views/Normativo.zul");
    }

    public void onClick$linkTAR(Event evt) {
        rootPagina.setSrc("/Views/Tarifa.zul");
    }
    //MENU LIKS

    public void onClick$linkSalir(Event evt) {
        String User = desktop.getSession().getAttribute("USUARIO").toString();
        Bitacora bt = new Bitacora();
        String rps = bt.login(User, "CitasEPQ", "Salir", "null", 0, 0, "Cerrando Session");
        desktop.getSession().invalidate();
        execution.sendRedirect("/login.zul");
    }

}
