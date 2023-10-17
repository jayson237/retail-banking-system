/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tellerterminalclient;

import ejb.session.stateless.AtmCardSessionBeanRemote;
import ejb.session.stateless.CustomerSessionBeanRemote;
import ejb.session.stateless.DepositAccountSessionBeanRemote;
import javax.ejb.EJB;

/**
 *
 * @author jayso
 */
public class Main {

    @EJB
    private static AtmCardSessionBeanRemote atmCardSessionBean;

    @EJB
    private static DepositAccountSessionBeanRemote depositAccountSessionBean;

    @EJB
    private static CustomerSessionBeanRemote customerSessionBean;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MainApp mainApp = new MainApp(customerSessionBean, atmCardSessionBean, depositAccountSessionBean);
        mainApp.runApp();
    }

}
