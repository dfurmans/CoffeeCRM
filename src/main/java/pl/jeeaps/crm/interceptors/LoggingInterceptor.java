/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.jeeaps.crm.interceptors;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 * JEEAPS - Coffee CRM
 *
 * @author Dawid Furman www.jeeaps.pl/autor
 */
public class LoggingInterceptor implements Serializable{

    @Resource
    private SessionContext sessionContext;

    @AroundInvoke
    public Object additionalInvokeForMethod(InvocationContext invocation) throws Exception {
        StringBuilder sb = new StringBuilder(" Wywołanie metody "
                + invocation.getTarget().getClass().getName() + "."
                + invocation.getMethod().getName());
        sb.append(" z tożsamością: " + sessionContext.getCallerPrincipal().getName());
        try {
            Object[] parameters = invocation.getParameters();
            if (null != parameters) {
                for (Object param : parameters) {
                    if (param != null) {
                        sb.append(" z parametrem " + param.getClass().getName() + "=" + param.toString());
                    } else {
                        sb.append(" z parametrem null");
                    }
                }
            }

            long startTime = System.currentTimeMillis();
            Object result = invocation.proceed();
            long duration = System.currentTimeMillis() - startTime;
            sb.append(" czas wykonania " + duration + " ms");

            if (result != null) {
                sb.append(" zwrócono " + result.getClass().getName() + "=" + result.toString());
            } else {
                sb.append(" zwrócono wartość null");
            }

            return result;
        } catch (Exception ex) {
            sb.append(" wystapil wyjatek " + ex);
            throw ex; //ponowne zgloszenie wyjatku
        } finally {
            Logger.global.log(Level.INFO, sb.toString());
        }
    }
}
