package Enviroment;



import Dal.LoginDal;
import Modelo.LoginMd;
import Util.Bitacora;
import Util.Cripto;
import Util.RestClient;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * 
 */
public class Login extends GenericForwardComposer {

    private static final long serialVersionUID = 1L;
    private static String key = "epqCitas*8965*";
    public static String USER = "";
    public static final String PATH_INICIAL = "menu.zul";
    private Textbox txtUser;
    private Textbox txtPass;
    private Label lab0;
    Cripto cp = new Cripto();
    LoginDal at = new LoginDal();
    RestClient rc = new RestClient();

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        Execution exec = Executions.getCurrent();
        HttpServletRequest request = (HttpServletRequest) exec.getNativeRequest();
        String idd = request.getParameter("p1");
        String idd2 = request.getParameter("p2");
        String idd3 = request.getParameter("p3");
        System.out.println("PARAMETRO #1 RECIBIDO..: " + idd);
        System.out.println("PARAMETRO #2 RECIBIDO..: " + idd2);
        System.out.println("PARAMETRO #3 RECIBIDO..: " + idd3);
        String data = rc.views("1", "1");
        System.out.println("VISTAS PERMITIDAS..: "+data);
        lab0.setVisible(false);
    }

    public void onClick$btnLogin(Event evt) throws SQLException {
        String usuario = "";
        String passR = "";
        String pass1 = "";
        LoginMd data = new LoginMd();
        if (txtUser.getValue() == null || txtUser.getValue().toString().trim().equals("")) {
            return;
        }

        if (txtPass.getValue() == null || txtPass.getValue().toString().trim().equals("")) {
            return;
        }

        System.out.println("CLAVE.: " + txtPass.getText());
        passR = cp.reset();
        pass1 = cp.encrypt(txtPass.getText(), key);
        System.out.println("CLAVE CAJA TEXTO.: " + pass1);
        System.out.println("CLAVE RESET.: " + passR);

        if (pass1.equals(passR)) {
            //BUSCA SIN CLAVE
            //data = at.UsuarioExiste(txtUser.getValue(), txtPass.getValue());
            data = at.BuscaUsuario(txtUser.getValue());

            String pwd = data.getPass();

            if (pass1.equals(pwd)) {
                //if (txtPass.getText().equals(pwd)) {

                EventQueues.lookup("myEventQueue", EventQueues.DESKTOP, true)
                        .publish(new Event("onChangeNickname", null, txtUser.getText()));

                //INVOCAR MODAL
                Window window = (Window) Executions.createComponents(
                        "/Views/CambioPass.zul", null, null);
                window.doModal();
            } else {
                Clients.showNotification("Credenciales <br/>  <br/> Incorrectas <br/> <br/>Intentelo de Nuevo",
                        Clients.NOTIFICATION_TYPE_WARNING, null, "middle_center", 2000);
            }
        } else {
            try {
                //data = at.UsuarioExiste(txtUser.getValue(), pass1);
                //data = at.UsuarioExiste(txtUser.getValue(), txtPass.getText());
                data = at.BuscaUsuario(txtUser.getValue());
                System.out.println("RESPUESTA.: "+data.getRespuesta());
                
                
                if (data.getRespuesta().equals("1") ) {
                    int x = 0;
                    if (txtPass.getText().equals(data.getPass())) {
                        x = 1;
                        System.out.println("PASSWORD SIN ENCRIPTAR.: " + data.getPass());
                    }
                    if (pass1.equals(data.getPass())) {
                        x = 1;
                        System.out.println("PASSWORD ENCRIPTADA.: " + data.getPass());
                    }
                    if (x == 1) {
                        System.out.println("VALOR DE USUARIO..: " + data.getUseario());
                        Session InventarioSession = Sessions.getCurrent();
                        InventarioSession.setAttribute("USUARIO", data.getUseario());
                        
                        //InventarioSession.setAttribute("PASSWORD", txtPass.getValue());

                Bitacora bt = new Bitacora();
                String rps = bt.login(data.getUseario(), "CitasEPQ", "Login", "null", 0, 0, "Inicio de Session");
                        lab0.setValue("1");
                        desktop.getSession().setAttribute("USUARIO", txtUser.getValue());
                        execution.sendRedirect(PATH_INICIAL);
                    } else {
                        Clients.showNotification("Credenciales <br/>  <br/> Incorrectas <br/> <br/>Intentelo de Nuevo",
                            Clients.NOTIFICATION_TYPE_ERROR, null, "top_center", 2000);
                    }
                } else {
                    lab0.setValue("0");
                    Bitacora bt = new Bitacora();
                    String rps = bt.login(usuario, "CitasEPQ", "Login", "null", 0, 0, "Credenciales Incorrectas");
                    Clients.showNotification("Credenciales <br/>  <br/> Incorrectas <br/> <br/>Intentelo de Nuevo",
                            Clients.NOTIFICATION_TYPE_ERROR, null, "top_center", 2000);
                    //Messagebox.show("Credenciales Incorrectas", "Informacion", Messagebox.OK, Messagebox.INFORMATION);
                }
            } catch (Exception e) {
                Messagebox.show("Ocurrió un error al intentar validar el usuario, intente nuevamente." + e.getMessage().toString(),
                        "Informacion", Messagebox.OK, Messagebox.INFORMATION);
            }
        }

    }
    
     public void onOK$txtPass(Event evt) throws SQLException {
        onClick$btnLogin(evt);
        
    }

}
