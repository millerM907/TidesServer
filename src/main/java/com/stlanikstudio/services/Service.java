package com.stlanikstudio.services;

import com.stlanikstudio.dao.Dao;

public class Service<T> {

    private final Dao dao;

    public Service(Dao dao){
        this.dao = dao;
    }

    public void create(T entity){
        dao.openSessionWithTransaction();
        dao.create(entity);
        dao.closeSessionWithTransaction();
    }

    public void update(T entity){
        dao.openSessionWithTransaction();
        dao.update(entity);
        dao.closeSessionWithTransaction();
    }

    public T getById(Integer id){
        dao.openSession();
        T t = (T) dao.getById(id);
        dao.closeSession();
        return t;
    }
}
