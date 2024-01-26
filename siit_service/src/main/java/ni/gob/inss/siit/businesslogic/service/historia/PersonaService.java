package ni.gob.inss.siit.businesslogic.service.historia;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.entity.catalogo.Catalogo;
import ni.gob.inss.barista.model.entity.catalogo.DivisionPolitica;
import ni.gob.inss.barista.model.entity.catalogo.Localidad;
import ni.gob.inss.barista.model.entity.catalogo.Pais;
import ni.gob.inss.siit.model.entity.historia.Persona;

import java.util.List;

/**
 * Creado por jvillanueva1 el 01/12/2015
 * Modificado por jvillanueva 02/01/2018.
 */
public interface PersonaService {

    Persona obtenerPersonaPorId(int id) throws EntityNotFoundException;

    void agregar(Persona oTipoCatalogo) throws BusinessException, DAOException;

    void actualizar(Persona oTipoCatalogo) throws BusinessException, DAOException;

    void eliminar(Persona oTipoCatalogo) throws BusinessException, DAOException;

    List buscar(String prBuscar);

    List buscarPersonaLazy(String prBuscar, Integer limite, Integer pagina);

    List<Pais> obtenerPais(String codigoAlfa2);

    Pais obtenerPais306(String codigoAlfa2);

    List<DivisionPolitica> obtenerMunicipio(String codigo);

    List<Catalogo> obtenerCatalogo(String codigo);

    List<Localidad> obtenerLocalidad(int localidadId);

    List<Localidad> buscarLocalidades();

    List<Catalogo> buscarGruposSanguineos();

    List<Catalogo> buscarEscolaridades();

    List<Catalogo> buscarEtnias();

    List<Catalogo> buscarEstadosCiviles();

    List<Pais> buscarPaises();

    List<Pais> buscarPaisesExtranjeros();

    List<DivisionPolitica> buscarDepartamentos();

    List<DivisionPolitica> buscarMunicipios(int codigoDepartamento);

    List<DivisionPolitica> obtenerDepartamentoPorMunicipio(String codigoMunicipio);

    List<Persona> buscarPersonaByDni(String prDni);

    List<Pais> buscarPais(String codigo);

    List<Catalogo> buscarProfesion();

}
