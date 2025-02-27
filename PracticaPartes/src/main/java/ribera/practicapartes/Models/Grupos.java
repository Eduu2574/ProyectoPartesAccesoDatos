package ribera.practicapartes.Models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "grupos")
public class Grupos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_grupo")
    private int id_grupo;

    @Column(name = "nombre_grupo")
    private String nombre_grupo;

    @OneToMany(mappedBy = "grupo")
    private List<Alumno> alumnos;


    public Grupos() {
    }

    public Grupos(int id_grupo, String nombre_grupo) {
        this.id_grupo = id_grupo;
        this.nombre_grupo = nombre_grupo;
    }


    public int getId_grupo() {
        return id_grupo;
    }

    public void setId_grupo(int id_grupo) {
        this.id_grupo = id_grupo;
    }

    public void setNombre_grupo(String nombre_grupo) {
        this.nombre_grupo = nombre_grupo;
    }

    @Override
    public String toString() {
        return nombre_grupo;
    }

    public String getNombreGrupo() {
        return nombre_grupo;
    }

    public Grupos getGrupo() {
        return null;
    }
}
