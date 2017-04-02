/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.jeeaps.crm.dao;

import java.io.Serializable;
import java.util.List;
import pl.jeeaps.crm.exception.AbstractApplicationException;

/**
 *
 * @author Dawid
 */
//https://community.jboss.org/wiki/GenericDataAccessObjects/
public interface GenericDAOInterface<T, ID extends Serializable> {

//Basic CRUD operation support DAO interface
    public void create(T object);

    public T update(T object) throws AbstractApplicationException;

    public void delete(T object);
//Basic find operation support DAO interface

    public T findById(ID id);
//Extended find operation    

    public List<T> findAll();

    public List<T> findRange(int[] range);
// Counting object

    public int count();
}
