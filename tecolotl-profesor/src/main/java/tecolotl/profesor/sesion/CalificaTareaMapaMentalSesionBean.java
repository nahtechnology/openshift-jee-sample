package tecolotl.profesor.sesion;

import tecolotl.alumno.entidad.TareaEntidad;
import tecolotl.profesor.entidad.CalificaTareaMapamentalEntidad;
import tecolotl.profesor.entidad.CalificaTareaMapamentalEntidadPK;
import tecolotl.profesor.entidad.TareaAlumnoEntidad;
import tecolotl.profesor.entidad.TareaMapaMentalEntidad;
import tecolotl.profesor.modelo.CalificaTareaMapaMentalModelo;
import tecolotl.profesor.modelo.TareaAlumnoModelo;
import tecolotl.profesor.modelo.TareaMapaMentalModelo;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Stateless
public class CalificaTareaMapaMentalSesionBean {

    @Inject
    private Logger logger;

    @PersistenceContext
    private EntityManager entityManager;


    /**
     * Metodo que busca todas las tareas para calificar
     * @return una coleccion de {@link CalificaTareaMapaMentalModelo}
     */
    public List<CalificaTareaMapaMentalModelo> busca(){
        TypedQuery<CalificaTareaMapamentalEntidad> typedQuery = entityManager.createNamedQuery("CalificaTareaMapamental.busca", CalificaTareaMapamentalEntidad.class);
        List<CalificaTareaMapamentalEntidad> calificaTareaMapamentalEntidadLista = typedQuery.getResultList();
        logger.info("Total de tareas para calificar: ".concat(String.valueOf(calificaTareaMapamentalEntidadLista.size())));
        return calificaTareaMapamentalEntidadLista.stream().map(CalificaTareaMapaMentalModelo::new).collect(Collectors.toList());
    }

    /**
     * Agrega una respuesta relacionado a un mapa mental
     * @param calificaTareaMapaMentalModelo
     */
    public void agrega(@NotNull @Valid CalificaTareaMapaMentalModelo calificaTareaMapaMentalModelo) {
        CalificaTareaMapamentalEntidadPK calificaTareaMapamentalEntidadPK = new CalificaTareaMapamentalEntidadPK();
        calificaTareaMapamentalEntidadPK.setTareaEntidad(new TareaEntidad(calificaTareaMapaMentalModelo.getIdTarea()));
        calificaTareaMapamentalEntidadPK.setCardinalidad(calificaTareaMapaMentalModelo.getCardinalidad());
        calificaTareaMapamentalEntidadPK.setVuelta(calificaTareaMapaMentalModelo.getVuelta());
        CalificaTareaMapamentalEntidad calificaTareaMapamentalEntidad =
                new CalificaTareaMapamentalEntidad(calificaTareaMapamentalEntidadPK);
        calificaTareaMapamentalEntidad.setComentario(calificaTareaMapaMentalModelo.getComentario());
        calificaTareaMapamentalEntidad.setPuntaje(calificaTareaMapaMentalModelo.getPuntaje());
        entityManager.persist(calificaTareaMapamentalEntidad);
        calificaTareaMapaMentalModelo.setMomento(calificaTareaMapamentalEntidad.getMomento());
    }

    /**
     * Actualiza las respuesta de un mapa mental
     * @param calificaTareaMapaMentalModelo
     * @return
     */
    public int respuesta(@NotNull @Valid CalificaTareaMapaMentalModelo calificaTareaMapaMentalModelo) {
        Query query = entityManager.createNamedQuery("CalificaTareaMapamentalEntidad.califica");
        query.setParameter("idTarea", calificaTareaMapaMentalModelo.getIdTarea())
                .setParameter("cardinalidad", calificaTareaMapaMentalModelo.getCardinalidad())
                .setParameter("comentario", calificaTareaMapaMentalModelo.getComentario())
                .setParameter("puntaje", calificaTareaMapaMentalModelo.getPuntaje())
                .setParameter("vuelta", calificaTareaMapaMentalModelo.getVuelta());
        return query.executeUpdate();
    }

    /**
     * Busca las tareas asignadas de un alumno
     * @param idTarea Identificador de las tareas
     * @return Colección de {@link TareaAlumnoModelo}
     */
    public List<TareaAlumnoModelo> busca(@NotNull Integer idTarea) {
        Query query = entityManager.createNativeQuery("SELECT * FROM profesor.tareas_alumno(?)", TareaAlumnoEntidad.class);
        query.setParameter(1, idTarea);
        return ((List<TareaAlumnoEntidad>)query.getResultList()).stream().map(TareaAlumnoModelo::new).collect(Collectors.toList());
    }

    /**
     * Busca una tarea mapa mental por su datos como llave primaria
     * @param idTarea Identificador de la tarea
     * @param cardinalidad Cardinalidad de la tarea
     * @param intento Nuevo de vuelta
     * @return {@link CalificaTareaMapaMentalModelo} con los datos encontrados
     */
    public CalificaTareaMapaMentalModelo busca(@NotNull Integer idTarea, @NotNull Short cardinalidad, @NotNull Short intento) {
        CalificaTareaMapamentalEntidadPK calificaTareaMapamentalEntidadPK = new CalificaTareaMapamentalEntidadPK();
        calificaTareaMapamentalEntidadPK.setVuelta(intento);
        calificaTareaMapamentalEntidadPK.setCardinalidad(cardinalidad);
        calificaTareaMapamentalEntidadPK.setTareaEntidad(new TareaEntidad(idTarea));
        logger.info(calificaTareaMapamentalEntidadPK.toString());
        CalificaTareaMapamentalEntidad calificaTareaMapamentalEntidad = entityManager.find(CalificaTareaMapamentalEntidad.class, calificaTareaMapamentalEntidadPK);
        return new CalificaTareaMapaMentalModelo(calificaTareaMapamentalEntidad);
    }

    /**
     * Busca las calificaciones de una  mapa mental
     * @param idTarea Identificador de la tarea
     * @return Coleccion de
     */
    public List<TareaMapaMentalModelo> buscaCalificados(@NotNull Integer idTarea) {
        Query query = entityManager.createNativeQuery("SELECT tma.cardinalidad, count(tma.texto_respuesta) as total_respuesta, ctmm.puntaje, ctmm.vuelta " +
                "FROM alumno.tarea t JOIN alumno.tarea_mapamental_actividad tma ON t.id = tma.id_tarea JOIN profesor.califica_tarea_mapamental ctmm ON t.id = ctmm.id_tarea " +
                "WHERE t.id = ? GROUP BY tma.id_tarea, ctmm.puntaje, tma.cardinalidad, ctmm.vuelta", TareaMapaMentalEntidad.class);
        query.setParameter(1, idTarea);
        return ((List<TareaMapaMentalEntidad>)query.getResultList()).stream().map(TareaMapaMentalModelo::new).collect(Collectors.toList());
    }

}
