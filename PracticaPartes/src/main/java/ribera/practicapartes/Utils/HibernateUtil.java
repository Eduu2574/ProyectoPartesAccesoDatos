package ribera.practicapartes.Utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ribera.practicapartes.Models.Alumno;
import ribera.practicapartes.Models.Grupos;
import ribera.practicapartes.Models.ParteIncidencia;
import ribera.practicapartes.Models.Profesor;

import static ribera.practicapartes.Utils.AlertUtils.mostrarError;

public class HibernateUtil {

    static SessionFactory factory = null;
    static {
        try {
            Configuration cfg = new Configuration();
            cfg.configure(R.getHibernate("hibernate.cfg.xml"));

            cfg.addAnnotatedClass(Alumno.class);
            cfg.addAnnotatedClass(Grupos.class);
            cfg.addAnnotatedClass(Profesor.class);
            cfg.addAnnotatedClass(ParteIncidencia.class);


            factory = cfg.buildSessionFactory();
        } catch (Exception e) {
            mostrarError("No pudo realizarse la conexión con la base de datos.");
        }

    }

    public static SessionFactory getSessionFactory() {
        return factory;
    }

    public static Session getSession() {
        if (factory != null) {
            return factory.openSession();
        }
        mostrarError("Ha ocurrido un error en la conexión con la base de datos.");
        System.out.println("Ha ocurrido un error en la conexión con la base de datos.");
        return null;
    }
}