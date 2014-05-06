
import com.nd.im.timer.ImTimerSessionBeanLocal;
import javax.ejb.Startup;
import javax.ejb.Stateless;

/**
 *
 * @author jianying9
 */
@Stateless
@Startup
public class ImTimerSessionBean implements ImTimerSessionBeanLocal {

    @Override
    public void allotWaitCustomer() {
    }
}
