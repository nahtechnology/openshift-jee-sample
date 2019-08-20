package tecolotl.alumno.modelo.escribir;

import tecolotl.alumno.entidad.escribir.EscribirEntidad;
import tecolotl.alumno.validacion.escribir.EscribirLlavePrimariaValidacion;
import tecolotl.alumno.validacion.escribir.EscribirNuevoValidacion;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.StringJoiner;

public class EscribirBaseModelo {

    private Integer id;
    private String pregunta;

    public EscribirBaseModelo() {
    }

    public EscribirBaseModelo(Integer id) {
        this.id = id;
    }

    public EscribirBaseModelo(EscribirEntidad escribirEntidad) {
        this.id = escribirEntidad.getId();
        this.pregunta = escribirEntidad.getPregunta();
    }

    @NotNull(groups = {EscribirNuevoValidacion.class, EscribirLlavePrimariaValidacion.class})
    @Min(value = 1, groups = {EscribirNuevoValidacion.class, EscribirLlavePrimariaValidacion.class})
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NotNull
    @Size(max = 100, min = 2)
    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", EscribirBaseModelo.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("pregunta='" + pregunta + "'")
                .toString();
    }
}