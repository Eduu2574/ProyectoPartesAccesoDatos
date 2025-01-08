package ribera.practicapartes.DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ribera.practicapartes.Models.Profesor;
import ribera.practicapartes.Utils.HibernateUtil;

public class ProfesorDAO {

    public ProfesorDAO() {}

    public boolean authenticate(String codigo, String password) {
        try (Session session = HibernateUtil.getSession()) {
            assert session != null;
            Query<Profesor> query = session.createQuery(
                    "FROM Profesor WHERE numero_asignado = :codigo AND contrasena = :password",
                    Profesor.class
            );
            query.setParameter("codigo", codigo);
            query.setParameter("password", password);

            Profesor profesor = query.uniqueResult();

            return profesor != null; // Retorna true si el usuario existe
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Profesor obtenerProfesor(String codigo) {
        Profesor profesor = null;
        try {
            Session session = HibernateUtil.getSession();
            Transaction transaction = null;
            try {
                assert session != null;
                transaction = session.beginTransaction();
                profesor = session.createQuery("from Profesor where numero_asignado = :codigo", Profesor.class).setParameter("codigo", codigo)
                        .uniqueResult();
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null)
                    transaction.rollback();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return profesor;
    }
    public boolean crearProfesor(Profesor profesor) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Iniciar transacción
            transaction = session.beginTransaction();

            // Guardar el objeto profesor en la base de datos
            session.save(profesor);

            // Confirmar transacción
            transaction.commit();
            return true;

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Revertir transacción en caso de error
            }
            System.err.println("Error al crear el profesor: " + e.getMessage());
            return false;
        }
    }
}
