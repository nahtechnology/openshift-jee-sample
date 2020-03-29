package tecolotl.web.administracion.controlador;

import tecolotl.administracion.modelo.escuela.EscuelaBaseModelo;
import tecolotl.profesor.modelo.CicloEscolarModelo;
import tecolotl.profesor.sesion.CicloEscolarSessionBean;

import javax.annotation.PostConstruct;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

@ViewScoped
@Named
public class CiclosEscolaresControlador implements Serializable {

    @Inject
    private CicloEscolarSessionBean cicloEscolarSessionBean;

    @Inject
    private Logger logger;

    private List<CicloEscolarModelo> cicloEscolarModeloLista;
    private CicloEscolarModelo cicloEscolarModelo;
    private String claveCentroTrabajo;
    private EscuelaBaseModelo escuelaBaseModelo;
    private Long totalGrupo;

    @PostConstruct
    public void buscaCiclosEscolares() {
        cicloEscolarModelo = new CicloEscolarModelo();
    }

    public void inicia() {
        cicloEscolarModelo.setIdEscuela(claveCentroTrabajo);
        cicloEscolarModeloLista = cicloEscolarSessionBean.busca(claveCentroTrabajo);
        escuelaBaseModelo = new EscuelaBaseModelo(claveCentroTrabajo);
    }

    public void inserta() {
        cicloEscolarModelo.setIdEscuela(escuelaBaseModelo.getClaveCentroTrabajo());
        cicloEscolarSessionBean.inserta(cicloEscolarModelo);
        cicloEscolarModeloLista = cicloEscolarSessionBean.busca(escuelaBaseModelo.getClaveCentroTrabajo());
        renuevaModelo();
    }

    public void activa() {
        cicloEscolarModelo.setActivo(!cicloEscolarModelo.getActivo());
        cicloEscolarSessionBean.actualiza(cicloEscolarModelo);
    }

    public void actualiza() {
        cicloEscolarSessionBean.actualiza(cicloEscolarModelo);
        cicloEscolarModeloLista = cicloEscolarSessionBean.busca(escuelaBaseModelo.getClaveCentroTrabajo());
        renuevaModelo();
    }

    public void elimina() {
        cicloEscolarSessionBean.elimina(cicloEscolarModelo);
        cicloEscolarModeloLista.remove(cicloEscolarModelo);
        renuevaModelo();
    }

    public void buscaTotalGrupo() {
        totalGrupo = cicloEscolarSessionBean.totalGrupo(cicloEscolarModelo);
    }

    public void renuevaModelo() {
        cicloEscolarModelo = new CicloEscolarModelo();
    }

    public List<CicloEscolarModelo> getCicloEscolarModeloLista() {
        return cicloEscolarModeloLista;
    }

    public void setCicloEscolarModeloLista(List<CicloEscolarModelo> cicloEscolarModeloLista) {
        this.cicloEscolarModeloLista = cicloEscolarModeloLista;
    }

    public CicloEscolarModelo getCicloEscolarModelo() {
        return cicloEscolarModelo;
    }

    public void setCicloEscolarModelo(CicloEscolarModelo cicloEscolarModelo) {
        this.cicloEscolarModelo = cicloEscolarModelo;
    }

    public String getClaveCentroTrabajo() {
        return claveCentroTrabajo;
    }

    public void setClaveCentroTrabajo(String claveCentroTrabajo) {
        this.claveCentroTrabajo = claveCentroTrabajo;
    }

    public Long getTotalGrupo() {
        return totalGrupo;
    }

    public void setTotalGrupo(Long totalGrupo) {
        this.totalGrupo = totalGrupo;
    }

    public EscuelaBaseModelo getEscuelaBaseModelo() {
        return escuelaBaseModelo;
    }

    public void setEscuelaBaseModelo(EscuelaBaseModelo escuelaBaseModelo) {
        this.escuelaBaseModelo = escuelaBaseModelo;
    }
}
