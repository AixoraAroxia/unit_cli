package com.unitTestGenerator.persistence.repositories;

import com.unitTestGenerator.ioc.anotations.Component;
import com.unitTestGenerator.ioc.anotations.Inyect;
import com.unitTestGenerator.ioc.anotations.Singleton;
import com.unitTestGenerator.persistence.mapper.DataDaoMapper;
import com.unitTestGenerator.persistence.model.Data;

import com.unitTestGenerator.persistence.model.DataPojo;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import java.util.List;
import java.util.Properties;

@Component
@Singleton
public class DataDAOServicesImplement implements IDaoService {

    private static SessionFactory sessionFactory = null;
    private static ServiceRegistry serviceRegistry = null;

    @Inyect
    private static DataDaoMapper mapper;

    public DataDAOServicesImplement() {
        configureSessionFactory();
    }


    private static SessionFactory configureSessionFactory() throws HibernateException {
        Configuration configuration = new Configuration();
        configuration.configure();
        Properties properties = configuration.getProperties();
        serviceRegistry = new ServiceRegistryBuilder().applySettings(properties).buildServiceRegistry();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        return sessionFactory;
    }

    @Override
    public void update(DataPojo dataPojo) {
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            if(dataPojo != null){
                session.update(mapper.pojoToEntity(dataPojo));
            }
            session.flush();
            tx.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            tx.rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void save(DataPojo dataPojo) {
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            if(dataPojo != null) {
                session.save(mapper.pojoToEntity(dataPojo));
            }
            // Committing the change in the database.
            session.flush();
            tx.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            // Rolling back the changes to make the data consistent in case of any failure
            // in between multiple database write operations.
            tx.rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<DataPojo> findAll() {
        Session session = null;
        Transaction tx = null;
        List<Data> list = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            list = session.createQuery("from Data").list();
        } catch (Exception ex) {
            ex.printStackTrace();
            tx.rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return mapper.listEntityToListPojo(list);
    }

    @Override
    public DataPojo findById(Long id) {
        Session session = null;
        Transaction tx = null;
        Data data = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            String idS = String.valueOf(id);
            data = (Data) session.get(Data.class, id);
            tx.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            tx.rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return mapper.entityToPojo(data);
    }

    @Override
    public List<DataPojo> findById2(Long id) {
        Session session = null;
        Transaction tx = null;
        List<Data> data = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            String hql = "FROM Data WHERE id = :id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            data = query.list();
            tx.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return mapper.listEntityToListPojo(data);
    }

    @Override
    public List<DataPojo> findByName(String name) {
        Session session = null;
        Transaction tx = null;
        List<Data> data = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            String hql = "FROM Data WHERE name = :name";
            Query query = session.createQuery(hql);
            query.setParameter("name", name);
            data = query.list();
            tx.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            tx.rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return mapper.listEntityToListPojo(data);
    }

    @Override
    public void delete(DataPojo data) {

    }
}
