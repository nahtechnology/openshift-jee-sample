package tecolotl.nucleo.persistencia.entidad;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@MappedSuperclass
public class PersonaEntidad {

    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String apodo;
    private byte[] contrasenia;

    @Basic
    @Column(name = "nombre")
    @Size(min = 3, max = 40)
    @NotNull
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Basic
    @Column(name = "apellido_paterno")
    @Size(min = 4, max = 50)
    @NotNull
    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    @Basic
    @Column(name = "apellido_materno")
    @Size(min = 4, max = 50)
    @NotNull
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    @Basic
    @Column(name = "apodo")
    @Size(min = 4, max = 50)
    @NotNull
    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    @Basic
    @Column(name = "contrasenia")
    @NotNull
    public byte[] getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(byte[] contrasenia) {
        this.contrasenia = contrasenia;
    }
}