package tecolotl.administracion.sesion;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import tecolotl.administracion.modelo.direccion.ColoniaModelo;
import tecolotl.administracion.modelo.escuela.ContactoModelo;
import tecolotl.administracion.modelo.escuela.EscuelaBaseModelo;
import tecolotl.administracion.modelo.escuela.LicenciaModelo;
import tecolotl.administracion.persistencia.entidad.ColoniaEntidad;
import tecolotl.administracion.persistencia.entidad.CoordinadorEntidad;
import tecolotl.administracion.persistencia.entidad.EscuelaEntidad;
import tecolotl.administracion.validacion.direccion.ColoniaNuevaValidacion;
import tecolotl.administracion.validacion.escuela.ContactoLlavePrimariaValidacion;
import tecolotl.administracion.validacion.escuela.ContactoNuevoValidacion;
import tecolotl.administracion.validacion.escuela.LicenciaActualizaValidacion;
import tecolotl.nucleo.herramienta.LoggerProducer;
import tecolotl.nucleo.herramienta.ValidadorSessionBean;
import tecolotl.nucleo.modelo.CatalogoModelo;
import tecolotl.nucleo.modelo.PersonaMotivoBloqueoModelo;
import tecolotl.nucleo.persistencia.entidad.CatalagoEntidad;
import tecolotl.nucleo.persistencia.entidad.PersonaEntidad;
import tecolotl.nucleo.persistencia.entidad.PersonaMotivoBloqueoEntidad;
import tecolotl.nucleo.sesion.CatalogoSesionBean;
import tecolotl.nucleo.validacion.CatalogoNuevoValidacion;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class LicenciaSesionBeanTest {

    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
            .addPackage(ColoniaModelo.class.getPackage())
            .addPackage(ContactoModelo.class.getPackage())
            .addPackage(ColoniaEntidad.class.getPackage())
            .addPackage(ColoniaNuevaValidacion.class.getPackage())
            .addPackage(ContactoLlavePrimariaValidacion.class.getPackage())
            .addPackage(LoggerProducer.class.getPackage())
            .addPackage(ColoniaNuevaValidacion.class.getPackage())
            .addPackage(CatalogoNuevoValidacion.class.getPackage())
            .addPackage(ContactoLlavePrimariaValidacion.class.getPackage())
            .addClasses(LicenciaSesionBean.class, CatalogoModelo.class, CatalagoEntidad.class, CatalogoSesionBean.class,
                    PersonaEntidad.class, PersonaMotivoBloqueoModelo.class, PersonaMotivoBloqueoEntidad.class)
            .addAsResource("META-INF/persistence.xml")
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    private LicenciaSesionBean licenciaSesionBean;

    @Test
    public void busca() {
        List<LicenciaModelo> licenciaModeloLista = licenciaSesionBean.busca("0000000000");
        Assert.assertNotNull(licenciaModeloLista);
        Assert.assertFalse(licenciaModeloLista.isEmpty());
        for (LicenciaModelo licenciaModelo : licenciaModeloLista) {
            Assert.assertNotNull(licenciaModelo);
            Assert.assertNotNull(licenciaModelo.getContador());
            Assert.assertNotNull(licenciaModelo.getClaveCentroTrabajo());
            Assert.assertNotNull(licenciaModelo.getAdquisicion());
            Assert.assertNotNull(licenciaModelo.getInicio());
        }
    }

    @Test
    public void elimina() {
        LicenciaModelo licenciaModelo = new LicenciaModelo();
        licenciaModelo.setClaveCentroTrabajo("21DBS0029K");
        licenciaModelo.setContador((short)8);
        licenciaSesionBean.elimina(licenciaModelo);
        Assert.assertNotNull(licenciaModelo);
    }

    @Test
    public void buscaVacio() {
        List<LicenciaModelo> licenciaModeloLista = licenciaSesionBean.busca("111111111");
        Assert.assertNotNull(licenciaModeloLista);
        Assert.assertTrue(licenciaModeloLista.isEmpty());
    }

    @Test
    public void inserta() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        licenciaSesionBean.inserta("21DBS0029K");
    }

    @Test
    public void actualiza() {
        LicenciaModelo licenciaModelo = new LicenciaModelo((short)1, "0000000000");
        licenciaModelo.setInicio(new Date());
        int modificados = licenciaSesionBean.actualiza(licenciaModelo);
        Assert.assertFalse(modificados == 0);
    }

    @Test
    public void cuentaPorEscuela() {
        int total = licenciaSesionBean.cuenta("21DJN1326E");
        assertNotEquals(total, 0);
    }
}
