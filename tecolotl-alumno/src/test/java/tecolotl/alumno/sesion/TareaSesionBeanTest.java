package tecolotl.alumno.sesion;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import tecolotl.alumno.modelo.AlumnoModelo;
import tecolotl.alumno.modelo.EscribirBaseModelo;
import tecolotl.alumno.modelo.TareaModelo;
import tecolotl.alumno.entidad.*;
import tecolotl.alumno.validacion.EscribirNuevoValidacion;
import tecolotl.nucleo.herramienta.LoggerProducer;
import tecolotl.nucleo.herramienta.ValidadorSessionBean;
import tecolotl.nucleo.modelo.PersonaModelo;
import tecolotl.nucleo.persistencia.entidad.CatalagoEntidad;
import tecolotl.nucleo.persistencia.entidad.PersonaEntidad;
import tecolotl.nucleo.sesion.PersonaSesionBean;
import tecolotl.nucleo.validacion.PersonaNuevaValidacion;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(Arquillian.class)
public class TareaSesionBeanTest {

    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage(PersonaModelo.class.getPackage())
                .addPackage(PersonaNuevaValidacion.class.getPackage())
                .addPackage(AlumnoModelo.class.getPackage())
                .addPackage(TareaModelo.class.getPackage())
                .addPackage(PersonaEntidad.class.getPackage())
                .addClasses(PersonaEntidad.class, CatalagoEntidad.class, GradoEscolarEntidad.class,
                        AlumnoEntidad.class, TareaEntidad.class, TipoEstudianteEntidad.class,
                        TareaGlosarioActividadEntidad.class, TareaVideoEntidad.class,
                        TareaGlosarioActividadEntidadPK.class, TareaVideoEntidadPK.class,
                        NivelLenguajeEntidad.class, PersonaSesionBean.class, AlumnoSesionBean.class,
                        TareaSesionBean.class, GradoEscolarSesionBean.class, LoggerProducer.class, ValidadorSessionBean.class,
                        GlosarioEntidad.class, ActividadEntidad.class, TareaEscribirActividadEntidad.class, TareaEscribirActividadEntidadPK.class,
                        EscribirActividadEntidad.class, EscribirActividadEntidadPK.class, ClaseGlosarioEntidad.class, GlosarioActividadEntidad.class,
                        GlosarioActividadEntidadPK.class, EscribirEntidad.class, EscribirNuevoValidacion.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    private TareaSesionBean tareaSesionBean;

    @Test
    public void busca(){
        List<TareaModelo> tareaModeloLista = tareaSesionBean.busca();
        Assert.assertNotNull(tareaModeloLista);
        Assert.assertFalse(tareaModeloLista.isEmpty());
        for (TareaModelo tareaModelo : tareaModeloLista){
            Assert.assertNotNull(tareaModelo);
        }
    }

    @Test
    public void inserta(){
        TareaModelo tareaModelo = new TareaModelo();
        tareaSesionBean.inserta(tareaModelo.getId());
        Assert.assertNotNull(tareaModelo);
        Assert.assertNotNull(tareaModelo.getId());
    }

    @Test
    public void insertaEscribir() {
        EscribirBaseModelo escribirBaseModelo1 = new EscribirBaseModelo(1);
        EscribirBaseModelo escribirBaseModelo2 = new EscribirBaseModelo(2);
        TareaModelo tareaModelo = new TareaModelo();
        tareaModelo.setEscribirBaseModeloLista(Arrays.asList(escribirBaseModelo1, escribirBaseModelo2));
        tareaSesionBean.inserta(tareaModelo, "JcMtWwiyzpU");
        Assert.assertNotNull(tareaModelo.getId());
        Assert.assertNotEquals(tareaModelo.getId(), Long.valueOf(0));
    }
}
