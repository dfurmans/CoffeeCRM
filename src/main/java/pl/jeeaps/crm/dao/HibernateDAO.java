/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.jeeaps.crm.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import pl.jeeaps.crm.exception.AbstractApplicationException;

/**
 *
 * @autor Dawid
 */
public abstract class HibernateDAO<T, ID extends Serializable> implements GenericDAOInterface<T, ID> {
    // usunięto poniweważ nie wspiera parametryzacji PU w klasach encyjnych !
    /*@PersistenceContext(unitName = "PU_MySQL")
     private EntityManager em;*/

    // abstrakt zwracający konkretną referencję do EntityManagera - parametryzacja PU ! Dzięki temu w sposób jawny
    // możemy zdefiniowanć konkretne PU na poziomie samej klasy encyjnej
    protected abstract EntityManager getEntityManager();
    private Class<T> entityGenericClass;

    // Konstruktor
    public HibernateDAO(Class<T> entityGenericClass) {
        this.entityGenericClass = entityGenericClass;
    }

    // Implementacje interfejsu GenericDAOInterface<T,ID>
    @Override
    public void create(T object) {
        getEntityManager().persist(object);
    }

    @Override
    public T update(T object) throws AbstractApplicationException {
        return getEntityManager().merge(object);
    }

    @Override
    public void delete(T object) {
        getEntityManager().remove(object);
    }

    @Override
    public T findById(ID id) {
        return getEntityManager().find(entityGenericClass, id);
    }
    
    @Override
    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityGenericClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    @Override
    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityGenericClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    @Override
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityGenericClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
}
