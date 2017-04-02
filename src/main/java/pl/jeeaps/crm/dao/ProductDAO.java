/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.jeeaps.crm.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.jeeaps.crm.interceptors.LoggingInterceptor;
import pl.jeeaps.crm.model.Product;

/**
 *
 * @autor Dawid
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(LoggingInterceptor.class)
public class ProductDAO extends HibernateDAO<Product, Long> {

    @PersistenceContext(unitName = "PU_MySQL")
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
    
    public ProductDAO() {
        // wolamy konstruktor nadklasy - tworzmy konkretne DAO dla tej klasy encyjnej
        super(Product.class);
    }


}