package ribera.practicapartes.Models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "alumnos")
public class Alumno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alum")
    private int id_alum;

    @Column(name = "nombre_alum")
    private String nombre_alum;

    @Column(name = "numero_expediente")
    private String numero_expediente;

    @Column(name = "puntos_acumulados")
    private int puntos_acumulados;

    @ManyToOne
    @JoinColumn(name="id_grupo", referencedColumnName = "id_grupo")
    private Grupos grupo;
    @OneToMany(mappedBy = "alumno", cascade = CascadeType.ALL)
    private List<ParteIncidencia> partes;


    public Alumno() {
    }

    public Alumno(String nombre_alum, String numero_expediente, Grupos grupo) {
        this.nombre_alum = nombre_alum;
        this.numero_expediente = numero_expediente;
        this.grupo = grupo;
    }

    public int getId_alum() {
        return id_alum;
    }

    public void setId_alum(int id_alum) {
        this.id_alum = id_alum;
    }

    public String getNombre_alum() {
        return nombre_alum;
    }

    public void setNombre_alum(String nombre_alum) {
        this.nombre_alum = nombre_alum;
    }

    public String getNumero_expediente() {
        return numero_expediente;
    }

    public void setNumero_expediente(String numero_expediente) {
        this.numero_expediente = numero_expediente;
    }

    public int getPuntos_acumulados() {
        return puntos_acumulados;
    }

    public void setPuntos_acumulados(int puntos_acumulados) {
        this.puntos_acumulados = puntos_acumulados;
    }

    public Grupos getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupos grupo) {
        this.grupo = grupo;
    }

    @Override
    public String toString() {
        return nombre_alum;
    }
}
