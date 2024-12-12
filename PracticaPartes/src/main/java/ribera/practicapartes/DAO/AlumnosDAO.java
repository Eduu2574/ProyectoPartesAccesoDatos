package ribera.practicapartes.DAO;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ribera.practicapartes.Models.ParteIncidencia;
import ribera.practicapartes.Models.Alumno;
import ribera.practicapartes.Alerta;
import ribera.practicapartes.Utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class AlumnosDAO {
    SessionFactory factory = HibernateUtil.getSessionFactory();

    public ArrayList<AlumnosDAO> obtenerAlumnos(){
        Transaction transaction;
        ArrayList<AlumnosDAO> listaAlumnos=new ArrayList<>();
        try(Session session = factory.openSession()){
            transaction = session.beginTransaction();
            List<AlumnosDAO> alumnos =session.createQuery("from Alumno", AlumnosDAO.class).getResultList();
            listaAlumnos.addAll(alumnos);
            transaction.commit();
        }catch (Exception e){
            Alerta.Error(e.getMessage());
        }
        return listaAlumnos;
    }

    public AlumnosDAO buscarAlumnoPorExpediente(String expediente) {
        Session session = factory.openSession();  // Asegúrate de abrir la sesión
        AlumnosDAO alumno = null;
        try {
            session.beginTransaction();  // Comienza la transacción
            Query query = session.createQuery("FROM Alumno WHERE numero_expediente = :numero_expediente");
            query.setParameter("numero_expediente", expediente);
            alumno = (AlumnosDAO) query.uniqueResult();
            session.getTransaction().commit();  // Comete la transacción
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();  // En caso de error, realiza rollback
            }
            Alerta.Error(e.getMessage());
        }
        return alumno;
    }

    public void actualizarPuntosAlumno(Alumno alumno, ParteIncidencia parte) {
        Transaction transaction = null;
        try(Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            if (alumno != null) {
                int nuevosPuntos = alumno.getPuntos_acumulados() + parte.getPuntos_parte();
                alumno.setPuntos_acumulados(nuevosPuntos);

                session.update(alumno);
            }
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
        }
    }

    static public ObservableList<Alumno> cargarAlumnos () {
        ObservableList<Alumno> alumnos = FXCollections.observableArrayList();
        Transaction t = null;

        try (Session sesion = HibernateUtil.getSession()) {
            t = sesion.beginTransaction();

            List<Alumno> listaPartes =sesion.createQuery("FROM Alumno", Alumno.class).list();
            alumnos.addAll(listaPartes);

            t.commit();
        } catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
            e.printStackTrace();
        }

        return alumnos;
    }
}