package tecolotl.web.administracion.controlador;

import tecolotl.administracion.modelo.coordinador.CoordinadorModelo;
import tecolotl.administracion.modelo.coordinador.CoordinadorMotivoBloqueoModelo;
import tecolotl.administracion.sesion.CoordinadorMotivoBloqueoSesionBean;
import tecolotl.administracion.sesion.CoordinadorSesionBean;
import tecolotl.web.controlador.TablaControlador;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.model.CollectionDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@ViewScoped
@Named
public class CoordinadorControlador extends TablaControlador<CoordinadorModelo> implements Serializable {

    @Inject
    private CoordinadorSesionBean coordinadorSesionBean;

    @Inject
    private CoordinadorMotivoBloqueoSesionBean coordinadorMotivoBloqueoSesionBean;

    private List<CoordinadorMotivoBloqueoModelo> coordinadorMotivoBloqueoModeloLista;
    private CoordinadorModelo coordinadorModelo;
    private CoordinadorMotivoBloqueoModelo coordinadorMotivoBloqueoModelo;
    private String claveCentroTrabajo;

    @PostConstruct
    public void init() {
        claveCentroTrabajo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("escuela");
        coordinadorMotivoBloqueoModeloLista = coordinadorMotivoBloqueoSesionBean.buscaFiltro("Sin Bloqueo");
        actualizaDataModel();
    }

    public void limpiaCoordinador() {
        coordinadorModelo = new CoordinadorModelo();
    }

    public void agrega() {
        coordinadorModelo.setClaveCentroTrabajo(claveCentroTrabajo);
        coordinadorSesionBean.agreaga(coordinadorModelo);
        limpiaCoordinador();
        actualizaDataModel();
    }

    public void elimina() {
        coordinadorSesionBean.elimina(coordinadorModelo);
        limpiaCoordinador();
        actualizaDataModel();
    }

    public void actualiza() {
        coordinadorSesionBean.actualiza(coordinadorModelo);
        limpiaCoordinador();
        actualizaDataModel();
    }

    public void bloqueo() {
        coordinadorModelo.setClaveCentroTrabajo(claveCentroTrabajo);
        coordinadorSesionBean.estatus(coordinadorModelo, coordinadorMotivoBloqueoModelo.getClave());
        limpiaCoordinador();
        actualizaDataModel();
    }

    public void desbloquea() {
        coordinadorSesionBean.estatus(coordinadorModelo, (short)1);
        limpiaCoordinador();
        actualizaDataModel();
    }

    @Override
    public void actualizaDataModel() {
        setCollectionDataModel(new CollectionDataModel<>(coordinadorSesionBean.busca(claveCentroTrabajo)));
    }

    public CoordinadorModelo getCoordinadorModelo() {
        return coordinadorModelo;
    }

    public void setCoordinadorModelo(CoordinadorModelo coordinadorModelo) {
        this.coordinadorModelo = coordinadorModelo;
    }

    public List<CoordinadorMotivoBloqueoModelo> getCoordinadorMotivoBloqueoModeloLista() {
        return coordinadorMotivoBloqueoModeloLista;
    }

    public void setCoordinadorMotivoBloqueoModeloLista(List<CoordinadorMotivoBloqueoModelo> coordinadorMotivoBloqueoModeloLista) {
        this.coordinadorMotivoBloqueoModeloLista = coordinadorMotivoBloqueoModeloLista;
    }

    public CoordinadorMotivoBloqueoModelo getCoordinadorMotivoBloqueoModelo() {
        return coordinadorMotivoBloqueoModelo;
    }

    public void setCoordinadorMotivoBloqueoModelo(CoordinadorMotivoBloqueoModelo coordinadorMotivoBloqueoModelo) {
        this.coordinadorMotivoBloqueoModelo = coordinadorMotivoBloqueoModelo;
    }
}
