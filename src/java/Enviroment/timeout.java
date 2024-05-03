
package Enviroment;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;

/**
 *
 * @author E.P.Q
 */
public class timeout extends GenericForwardComposer {

    private static final long serialVersionUID = 1L;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        desktop.getSession().invalidate();
        execution.sendRedirect("login.zul");
    }

}
