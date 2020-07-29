package com.stlanikstudio.dao;

public interface DaoInterface<T, Id> {

    public void create(T entity);

    public void update(T entity);

    public T getById(Id id);
}
