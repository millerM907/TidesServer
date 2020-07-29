package com.stlanikstudio.dao;

import com.stlanikstudio.accessingdatamysql.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class Dao<T> implements DaoInterface<T, Integer> {

    private T entity;

    private Session session;
    private Transaction transaction;

    public Session openSession(){
        session = getSessionFactory().openSession();
        return session;
    }

    public Session openSessionWithTransaction(){
        session = getSessionFactory().openSession();
        transaction = session.beginTransaction();
        return session;
    }

    public void closeSession(){
        session.close();
    }

    public void closeSessionWithTransaction(){
        transaction.commit();
        session.close();
    }

    private static SessionFactory getSessionFactory(){
        return HibernateSessionFactoryUtil.getSessionFactory();
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public void create(T entity) {
        getSession().save(entity);
    }

    @Override
    public void update(T entity) {
        getSession().update(entity);
    }

    @Override
    public T getById(Integer id) {
        T t = (T) getSession().get(entity.getClass(), id);
        return t;
    }
}
