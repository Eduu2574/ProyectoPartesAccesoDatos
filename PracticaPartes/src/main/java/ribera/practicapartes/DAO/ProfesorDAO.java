package ribera.practicapartes.DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ribera.practicapartes.Models.Profesor;
import ribera.practicapartes.Utils.HibernateUtil;

import java.util.Collections;
import java.util.Objects;

public class ProfesorDAO {

    public ProfesorDAO() {}

    public boolean authenticate(String codigo, String password) {
        Session session = HibernateUtil.getSession();
        try {
            Query<Profesor> query = session.createQuery(
                    "FROM Profesor WHERE numero_asignado = :codigo AND contrasena = :password",
                    Profesor.class
            );
            query.setParameter("codigo", codigo);
            query.setParameter("password", password);

            Profesor profesor = query.uniqueResult();

            return profesor != null; // Retorna true si el usuario existe
        } finally {
            session.close();
        }
    }

    public Profesor obtenerProfesor(String codigo) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = null;
        Profesor profesor = null;
        try {
            transaction = session.beginTransaction();
            profesor = session.createQuery("from Profesor where numero_asignado = :codigo", Profesor.class).setParameter("codigo", codigo)
                    .uniqueResult();;
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null)
                transaction.rollback();
        }
        return profesor;
    }
}
