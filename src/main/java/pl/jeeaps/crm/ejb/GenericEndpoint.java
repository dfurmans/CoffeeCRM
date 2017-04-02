/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.jeeaps.crm.ejb;

import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

/**
 *
 * @autor Dawid Klasa zawiera metody zwrotne, które są wykonywane na rzecz
 * komponentów EJB Stateful
 */
public abstract class GenericEndpoint {

    @Resource
    SessionContext sctx;
    protected static final Logger LOGGER = Logger.getGlobal();
    private String transactionId;

    // Przywróć "wykomentowane" linie aby uzyskać prostą implementację SessionSynchronization
    public void afterBegin() throws EJBException {
        transactionId = Long.toString(System.currentTimeMillis())
                + ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
        LOGGER.log(Level.INFO, "Transakcja TXid={0} rozpoczęta w {1}, tożsamość: {2}",
                new Object[]{transactionId, this.getClass().getName(), sctx.getCallerPrincipal().getName()});
    }

    public void beforeCompletion() throws EJBException {
        LOGGER.log(Level.INFO, "Transakcja TXid={0} przed zatwierdzeniem w {1}, tożsamość {2}",
                new Object[]{transactionId, this.getClass().getName(), sctx.getCallerPrincipal().getName()});
    }

    public void afterCompletion(boolean committed) throws EJBException {
        LOGGER.log(Level.INFO, "Transakcja TXid={0} zakończona w {1} poprzez {3}, tożsamość {2}",
                new Object[]{transactionId, this.getClass().getName(), sctx.getCallerPrincipal().getName(),
            committed ? "ZATWIERDZENIE" : "ODWOŁANIE"});
    }
}
//public abstract class GenericEndpoint<T, DAO extends HibernateDAO> {
//
//    @EJB 
//    private DAO dao;
//    
//    public void create(T object){
//        dao.create(object);
//    }
//    
//}
