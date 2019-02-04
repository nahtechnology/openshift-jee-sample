package tecolotl.administracion.dto;

import tecolotl.administracion.persistencia.entidad.TipoContactoEntidad;

import java.util.Objects;

public class TipoContactoDto extends CatalogoDto {

    public TipoContactoDto() {
        super();
    }

    public TipoContactoDto(Short id, String descripcion) {
        super(id, descripcion);
    }

    public TipoContactoDto(TipoContactoEntidad tipoContactoEntidad) {
        setId(tipoContactoEntidad.getId());
        setDescripcion(tipoContactoEntidad.getDescripcion());
    }

}
