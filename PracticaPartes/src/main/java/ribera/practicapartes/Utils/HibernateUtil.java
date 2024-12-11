package ribera.practicapartes.Utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ribera.practicapartes.Models.Alumno;
import ribera.practicapartes.Models.Grupos;
import ribera.practicapartes.Models.ParteIncidencia;
import ribera.practicapartes.Models.Profesor;

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