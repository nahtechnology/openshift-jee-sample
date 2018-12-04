package tecolotl.alumno.sesion;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;

import tecolotl.alumno.persistencia.entidad.AlumnoEntidad;


public class AlumnoSesionBeanTest {
	private Logger logger = Logger.getLogger(getClass().getName());
	
	@Deployment
	public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage(AlumnoSesionBean.class.getPackage())
                .addPackage(AlumnoEntidad.class.getPackage())
                .addAsResource("META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	} 
    @PersistenceContext()
    private EntityManager entityManager;
	@Inject
	private AlumnoSesionBean alumnoSesionBean;


	@Test
	public void insertar() {
		Date date = new Date();
		alumnoSesionBean.insertar(5 ,"Guillermo", "Cuahuey","Estrada", "Memo", "cu.es.guillermo@nahtechnology.com", "pass", "a1", date , "guillermo@gamil.com", "pass");
	}
	
	@Test
	public void buscar() {
		Assert.assertTrue("Se no se ncontro el Alumno", alumnoSesionBean.busca("ESMERALDA", "ESMERALDA"));
	}
    @Test
    public void buscarContrasenia() {
    	TypedQuery<AlumnoEntidad> typedQuery = entityManager.createQuery("AlumnoEntidad.buscaContrasenia", AlumnoEntidad.class);
    	typedQuery.setParameter("apodo", "ESMERALDA");
    	typedQuery.setParameter("contrasenia", "contrasenia");
    	List<AlumnoEntidad> listaalumnos = typedQuery.getResultList();
    	Assert.assertNotNull("No encontro Alumno", listaalumnos );
    	
    }

}
