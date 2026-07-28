package org.example.lab03.repository;

import org.example.lab03.domain.Employee;
import org.example.lab03.domain.Show;
import org.example.lab03.domain.Ticket;
import org.hibernate.SessionFactory;

import org.hibernate.cfg.Configuration;

public class HibernateUtils {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory(){
        if ((sessionFactory==null)||(sessionFactory.isClosed()))
            sessionFactory=createNewSessionFactory();
        return sessionFactory;
    }

    private static  SessionFactory createNewSessionFactory(){
        sessionFactory = new Configuration()
                .addAnnotatedClass(Employee.class)
                .addAnnotatedClass(Show.class)
                .addAnnotatedClass(Ticket.class)
                .buildSessionFactory();
        return sessionFactory;
    }

    public static  void closeSessionFactory(){
        if (sessionFactory!=null)
            sessionFactory.close();
    }
}
