package com.randomquestions.app;

import org.hibernate.Session;
import com.randomquestions.persistence.HibernateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class Entity implements java.io.Serializable {

    private final Logger LOGGER = LoggerFactory.getLogger(Entity.class);
    private long id = -1L;

    public Entity(long id) {
        this.setId(id);
    }

    public void save() throws SQLException {
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();
        session.save(this);
        session.getTransaction().commit();
    }

    public void delete() throws SQLException {
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();
        session.delete(this);
        session.getTransaction().commit();
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof Entity) {
            final Entity other = (Entity) object;
            return id != -1 && id == other.id;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return 31 * 1 + (int) (id ^ id >>> 32);
    }

    public long getId() {
        return id;
    }

    private void setId(long id) {
        this.id = id;
    }
}
