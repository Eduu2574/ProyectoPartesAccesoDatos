package ribera.practicapartes.DAO;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ribera.practicapartes.Utils.AlertUtils;
import ribera.practicapartes.Utils.HibernateUtil;
import ribera.practicapartes.Models.ParteIncidencia;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class PartesDAO {
    SessionFactory factory = HibernateUtil.getSessionFactory();

    public ArrayList<ParteIncidencia> obtenerPartes(){
        Transaction transaction = null;
        ArrayList<ParteIncidencia> listaPartes = new ArrayList<>();
        try(Session session = factory.openSession()){
            transaction = session.beginTransaction();
            List<ParteIncidencia> parteIncidencias = session.createQuery("from ParteIncidencia", ParteIncidencia.class).getResultList();
            listaPartes.addAll(parteIncidencias);
            transaction.commit();
        }catch (Exception e){
            AlertUtils.mostrarError(e.getMessage());
            e.printStackTrace();
        }
        return listaPartes;
    }


    public boolean crearParte(ParteIncidencia parteIncidencia) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.save(parteIncidencia);
            transaction.commit();
            return true;
        } catch (Exception e) {
            AlertUtils.mostrarError(e.getMessage());
            return false;
        }
    }


    public List<ParteIncidencia> obtenerPartesAlumno(int id_alumno){
        Transaction transaction = null;
        List listaPartes = new ArrayList<>();
        try(Session session = factory.openSession()){
            transaction = session.beginTransaction();
            Query query = session.createQuery("FROM ParteIncidencia WHERE alumno.id_alum = :id_alum", ParteIncidencia.class);
            query.setParameter("id_alum", id_alumno);
            listaPartes = query.getResultList();
            transaction.commit();
        }catch (Exception e){
            AlertUtils.mostrarError(e.getMessage());
            e.printStackTrace();
        }
        return listaPartes;
    }

    public boolean actualizarParte(ParteIncidencia parte) {
        Transaction transaction = null;
        boolean cambios = false;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.update(parte);  // Actualiza el objeto en la base de datos
            cambios = true;
            transaction.commit();  // Confirma la transacción
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();  // Si ocurre un error, deshacemos los cambios
            }
            AlertUtils.mostrarError("Error al actualizar el parte: " + e.getMessage());
        }
        return cambios;  // Retorna si hubo cambios exitosos o no
    }


    static public ObservableList<ParteIncidencia> cargarPartes () {
        ObservableList<ParteIncidencia> partes = FXCollections.observableArrayList();
        Transaction t = null;

        try (Session sesion = HibernateUtil.getSession()) {
            t = sesion.beginTransaction();

            List<ParteIncidencia> listaPartes =sesion.createQuery("FROM ParteIncidencia", ParteIncidencia.class).list();
            partes.addAll(listaPartes);

            t.commit();
        } catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
            e.printStackTrace();
        }

        return partes;
    }
}
