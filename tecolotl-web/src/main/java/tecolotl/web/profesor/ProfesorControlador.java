package tecolotl.web.profesor;

import tecolotl.administracion.modelo.escuela.EscuelaBaseModelo;
import tecolotl.profesor.modelo.CicloEscolarModelo;
import tecolotl.profesor.modelo.ProfesorModelo;
import tecolotl.profesor.sesion.ProfesorSesionBean;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

@SessionScoped
@Named
public class ProfesorControlador implements Serializable {

    @Inject
    private ProfesorSesionBean profesorSesionBean;

    private ProfesorModelo profesorModelo;
    private EscuelaBaseModelo escuelaBaseModelo;

    @PostConstruct
    public void inicio() {
        escuelaBaseModelo = new EscuelaBaseModelo("21DBA0014J");
        profesorModelo = profesorSesionBean.busca(1);
        profesorModelo.setId(1);
    }

    public ProfesorModelo getProfesorModelo() {
        return profesorModelo;
    }

    public void setProfesorModelo(ProfesorModelo profesorModelo) {
        this.profesorModelo = profesorModelo;
    }

    public EscuelaBaseModelo getEscuelaBaseModelo() {
        return escuelaBaseModelo;
    }

    public void setEscuelaBaseModelo(EscuelaBaseModelo escuelaBaseModelo) {
        this.escuelaBaseModelo = escuelaBaseModelo;
    }
}