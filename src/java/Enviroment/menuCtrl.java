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
         if (User.equals("costos")){
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
    

    //INGRESO Y EGRESO DE CAMIONES
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

    public void onClick$linkLMateria(Event evt) {
        rootPagina.setSrc("/Views/Materia.zul");
    }

    public void onClick$linkLNota(Event evt) {
        rootPagina.setSrc("/Views/Notas.zul");
    }

    public void onClick$linkReporteN(Event evt) {
        rootPagina.setSrc("/Views/ReporteNota.zul");
    }

    public void onClick$linkPDF(Event evt) {
        rootPagina.setSrc("/Views/NotasPDF.zul");
    }

    public void onClick$linkPDFAdmin(Event evt) {
        rootPagina.setSrc("/Views/NotasPDFAdmi.zul");
    }

    public void onClick$linkLCM(Event evt) {
        rootPagina.setSrc("/Views/Grados.zul");
    }

    public void onClick$linkLNotaop(Event evt) {
        rootPagina.setSrc("/Views/NotasOP.zul");
    }

    public void onClick$linkPDFestudiantes(Event evt) {
        rootPagina.setSrc("/Views/ReporteEstudiantes.zul");
    }

    public void onClick$linkLGSPRU(Event evt) {
        rootPagina.setSrc("/Views/PruebaG.zul");
    }

    public void onClick$linkPromedio(Event evt) {
        rootPagina.setSrc("/Views/Promedio.zul");
    }

    public void onClick$linkCOMRPOR(Event evt) {
        rootPagina.setSrc("/Views/ReporteCompras.zul");
    }

    public void onClick$linkPDFAdminPro(Event evt) {
        rootPagina.setSrc("/Views/PromedioAdmin.zul");
    }

    public void onClick$linkLGSRPOR(Event evt) {
        rootPagina.setSrc("/Views/ReporteGastos.zul");
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
