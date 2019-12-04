package com.zy.mvp.database.manager;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class BaseManager<T, K> {
    protected AbstractDao<T, K> mDao;

    public BaseManager(AbstractDao<T, K> dao) {
        this.mDao = dao;
    }

    public void save(T item) {
        mDao.insert(item);
    }

    @SafeVarargs
    public final void save(T... items) {
        mDao.insertInTx(items);
    }

    public void save(List<T> items) {
        mDao.insertInTx(items);
    }

    public void saveOrUpdate(T item) {
        mDao.insertOrReplace(item);
    }

    @SafeVarargs
    public final void saveOrUpdate(T... items) {
        mDao.insertOrReplaceInTx(items);
    }

    public void saveOrUpdate(List<T> items) {
        mDao.insertOrReplaceInTx(items);
    }

    public void deleteByKey(K key) {
        mDao.deleteByKey(key);
    }

    public void delete(T item) {
        mDao.delete(item);
    }

    @SafeVarargs
    public final void delete(T... items) {
        mDao.deleteInTx(items);
    }

    public void delete(List<T> items) {
        mDao.deleteInTx(items);
    }

    public void deleteAll() {
        mDao.deleteAll();
    }


    public void update(T item) {
        mDao.update(item);
    }

    @SafeVarargs
    public final void update(T... items) {
        mDao.updateInTx(items);
    }

    public void update(List<T> items) {
        mDao.updateInTx(items);
    }

    public T query(K key) {
        return mDao.load(key);
    }

    public List<T> queryAll() {
        return mDao.loadAll();
    }

    public List<T> query(String where, String... params) {

        return mDao.queryRaw(where, params);
    }

    public QueryBuilder<T> queryBuilder() {

        return mDao.queryBuilder();
    }

    public long count() {
        return mDao.count();
    }

    public void refresh(T item) {
        mDao.refresh(item);

    }

    public void detach(T item) {
        mDao.detach(item);
    }
}