package ribera.practicapartes.Models;

import javax.persistence.*;

@Entity
@Table(name = "profesores")
public class Profesor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_profesor")
    private int id_profesor;

    @Column(name = "contrasena")
    private String contrasena;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "numero_asignado")
    private String numero_asignado;

    @Column(name = "tipo")
    @Enumerated(EnumType.STRING)
    private TipoProfesor tipo;


    public Profesor() {
    }

    public Profesor(int id_profesor, String contrasena, String nombre, String numero_asignado, TipoProfesor tipo) {
        this.id_profesor = id_profesor;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.numero_asignado = numero_asignado;
        this.tipo = tipo;
    }




    public int getId_profesor() {
        return id_profesor;
    }

    public void setId_profesor(int id_profesor) {
        this.id_profesor = id_profesor;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumero_asignado() {
        return numero_asignado;
    }

    public void setNumero_asignado(String numero_asignado) {
        this.numero_asignado = numero_asignado;
    }

    public TipoProfesor getTipo() {
        return tipo;
    }

    public void setTipo(TipoProfesor tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Profesor{" +
                "id_profesor=" + id_profesor +
                ", contrasena='" + contrasena + '\'' +
                ", nombre='" + nombre + '\'' +
                ", numero_asignado='" + numero_asignado + '\'' +
                ", tipo=" + tipo +
                '}';
    }
}
