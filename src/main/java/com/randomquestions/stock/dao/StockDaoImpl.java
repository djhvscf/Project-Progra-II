package com.randomquestions.stock.dao;

import com.randomquestions.stock.model.Stock;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.transaction.TransactionStatus;

import java.util.List;

public class StockDaoImpl implements StockDao{

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private SessionFactory sessionFactory;
    public Session getSession() {
        try {
            return sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            return sessionFactory.openSession();
        }
    }

    public void save(Stock stock){
        Transaction tx = getSession().beginTransaction();
        getSession().save(stock);
        if(!tx.getStatus().isOneOf(org.hibernate.resource.transaction.spi.TransactionStatus.COMMITTED)) {
            tx.commit();
        }
    }

    public void update(Stock stock){
        Transaction tx = getSession().beginTransaction();
        getSession().update(stock);
        if(!tx.getStatus().isOneOf(org.hibernate.resource.transaction.spi.TransactionStatus.COMMITTED)) {
            tx.commit();
        }
    }

    public void delete(Stock stock){
        Transaction tx = getSession().beginTransaction();
        getSession().delete(stock);
        if(!tx.getStatus().isOneOf(org.hibernate.resource.transaction.spi.TransactionStatus.COMMITTED)) {
            tx.commit();
        }
    }

    public Stock findByStockCode(String stockCode){
        String query = "from Stock where stockCode=" + stockCode;
        List list = getSession().createQuery(query).list();
        return (Stock)list.get(0);
    }

}