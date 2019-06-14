package tecolotl.profesor.modelo;

import tecolotl.administracion.modelo.escuela.EscuelaBaseModelo;
import tecolotl.nucleo.modelo.PersonaModelo;
import tecolotl.nucleo.persistencia.entidad.PersonaEntidad;
import tecolotl.profesor.entidad.ProfesorEntidad;
import tecolotl.profesor.validacion.GrupoProfesorValidacion;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class ProfesorModelo extends PersonaModelo {

    @NotNull(message = "ID Profesor no puede ser nulo", groups = {GrupoProfesorValidacion.class})
    private Integer id;
    @NotNull
    @Valid
    private EscuelaBaseModelo escuelaBaseModelo;

    public ProfesorModelo() {
    }

    public ProfesorModelo(PersonaEntidad personaEntidad) {
        super(personaEntidad);
    }
    public ProfesorModelo(ProfesorEntidad profesorEntidad){
        this.id = profesorEntidad.getId();
        this.escuelaBaseModelo = new EscuelaBaseModelo(profesorEntidad.getEscuelaEntidad());
    }

    public ProfesorModelo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EscuelaBaseModelo getEscuelaBaseModelo() {
        return escuelaBaseModelo;
    }

    public void setEscuelaBaseModelo(EscuelaBaseModelo escuelaBaseModelo) {
        this.escuelaBaseModelo = escuelaBaseModelo;
    }
}
