package com.example.practicapartes.DAO;


import com.example.practicapartes.Alerta;
import com.example.practicapartes.HibernateUtil;
import com.example.practicapartes.model.ParteIncidencia;
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
            Alerta.Error(e.getMessage());
            e.printStackTrace();
        }
        return listaPartes;
    }


    public void crearParte(ParteIncidencia parteIncidencia) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.save(parteIncidencia);
            transaction.commit();
        } catch (Exception e) {
            Alerta.Error(e.getMessage());
        }
    }//insertarParte


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
            Alerta.Error(e.getMessage());
            e.printStackTrace();
        }
        return listaPartes;
    }

    public boolean actualizarParte(ParteIncidencia parte) {
        Transaction transaction = null;
        boolean cambios = false;
        try(Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.update(parte);
            cambios = true;
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
        }
        return cambios;
    }
}
