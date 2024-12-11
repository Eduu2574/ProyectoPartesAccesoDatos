package com.example.practicapartes;

import com.example.practicapartes.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    static SessionFactory factory = null;
    static {
        Configuration cfg = new Configuration();
        cfg.configure("configuration/hibernate.cfg.xml");

        cfg.addAnnotatedClass(Alumno.class);
        cfg.addAnnotatedClass(Grupos.class);
        cfg.addAnnotatedClass(Profesor.class);
        cfg.addAnnotatedClass(ParteIncidencia.class);


        factory = cfg.buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return factory;
    }

    public static Session getSession() {
        return factory.openSession();
    }
}