package ribera.practicapartes.Models;

import javax.persistence.*;

@Entity
@Table(name = "partes_incidencia")
public class ParteIncidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_parte")
    private int id_parte;

    @ManyToOne
    @JoinColumn(name="id_alum")
    private Alumno alumno;
    @ManyToOne
    @JoinColumn(name = "id_grupo")
    private Grupos grupo;
    @JoinColumn(name = "id_profesor")
    private int id_profesor;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha")
    private String fecha;

    @Column(name = "hora")
    private String hora;

    @Column(name = "sancion")
    private String sancion;


    @Column(name = "color")
    @Enumerated(EnumType.STRING)
    private ColorParte color;

    @Column(name = "puntos_partes")
    private int puntos_parte;


    public ParteIncidencia() {
    }

    public ParteIncidencia(Alumno alumno, int idProfesor, Grupos grupo, String fecha, String hora, String descripcion, String sancion, ColorParte color) {
        this.alumno = alumno;
        this.id_profesor = idProfesor;
        this.grupo = grupo;
        this.fecha = fecha;
        this.hora = hora;
        this.descripcion = descripcion;
        this.sancion = sancion;
        this.color = color;
        this.puntos_parte = color.getPuntos(); // Inicializar los puntos basados en el color
    }




    public int getId_parte() {
        return id_parte;
    }

    public void setId_parte(int id_parte) {
        this.id_parte = id_parte;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Grupos getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupos grupo) {
        this.grupo = grupo;
    }

    public int getId_profesor() {
        return id_profesor;
    }

    public void setId_profesor(int id_profesor) {
        this.id_profesor = id_profesor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getSancion() {
        return sancion;
    }

    public void setSancion(String sancion) {
        this.sancion = sancion;
    }

    public ColorParte getColor() {
        return color;
    }

    public void setColor(ColorParte color) {
        this.color = color;
        //this.puntos_parte = color.getPuntos(); // Al cambiar el color, actualizar los puntos
    }




    public int getPuntos_parte() {
        return puntos_parte;
    }

    public void setPuntos_parte(int puntos_parte) {
        this.puntos_parte = puntos_parte;
    }



    public String getExpediente_alumno () {
        return alumno.getNumero_expediente();
    }
}
