package ni.gob.inss.barista.businesslogic.serviceImpl.catalogos;

import com.googlecode.genericdao.search.Search;
import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.businesslogic.service.catalogos.PaisService;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.dao.catalogos.PaisesDAO;
import ni.gob.inss.barista.model.entity.catalogo.Pais;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class PaisServiceImpl implements PaisService {
    /**
     * **********************************************************************************
     * CONSTRUCTOR
     * **********************************************************************************
     */
    private final PaisesDAO oPaisesDAO;

    public PaisServiceImpl(PaisesDAO oPaisesDAO) {
        this.oPaisesDAO = oPaisesDAO;
    }

    /**
     * ***********************************************************************************
     * METODOS
     * ***********************************************************************************
     */
    @Transactional
    public Pais obtener(int id) throws EntityNotFoundException {
        return this.oPaisesDAO.find(id);
    }

    @Transactional
    public void agregar(Pais oPais) throws BusinessException, DAOException {
        this.validar(oPais);
        this.oPaisesDAO.saveUpper(oPais);
    }

    @Transactional
    public void actualizar(Pais oPais) throws BusinessException, DAOException {
        this.validar(oPais);
        this.oPaisesDAO.updateUpper(oPais);
    }

    @Transactional
    public void eliminar(Pais oPais) {
        this.oPaisesDAO.remove(oPais);
    }

    @Transactional
    public List<Pais> buscar(String prBuscar) {
        Search oSearch = new Search();
        oSearch.addFilterILike("nombre", "%" + prBuscar + "%");
        oSearch.addSortAsc("nombre");
        return this.oPaisesDAO.search(oSearch);
    }

    private void validar(Pais oPais) throws BusinessException {
        Search oSearch;
        List lista;
        Search oSearchCodigoAlfa2;
        List listaValidacionCodigoAlfa2;
        Search oSearchValidacionNombre;
        List listaValidacionNombre;
        if (oPais.getId() == null) {
            oSearch = new Search();
            oSearch.addFilterEqual("codigo", oPais.getCodigo());
            lista = this.oPaisesDAO.search(oSearch);
            if (lista.size() > 0) {
                throw new BusinessException("Este código ya se encuentra registrado");
            }
            oSearchCodigoAlfa2 = new Search();
            oSearchCodigoAlfa2.addFilterILike("codigoAlfa2", oPais.getCodigoAlfa2().trim());
            listaValidacionCodigoAlfa2 = this.oPaisesDAO.search(oSearchCodigoAlfa2);
            if (listaValidacionCodigoAlfa2.size() > 0) {
                throw new BusinessException("Este código alfa2 ya se encuentra registrado");
            }
            oSearchValidacionNombre = new Search();
            oSearchValidacionNombre.addFilterILike("nombre", oPais.getNombre().trim());
            listaValidacionNombre = this.oPaisesDAO.search(oSearchValidacionNombre);
            if (listaValidacionNombre.size() > 0) {
                throw new BusinessException("Este nombre ya se encuentra registrado");
            }
        } else {
            oSearch = new Search();
            oSearch.addFilterEqual("codigo", oPais.getCodigo());
            oSearch.addFilterNotEqual("id", oPais.getId());
            lista = this.oPaisesDAO.search(oSearch);
            if (lista.size() > 0) {
                throw new BusinessException("Este código ya se encuentra registrado");
            }
            oSearchCodigoAlfa2 = new Search();
            oSearchCodigoAlfa2.addFilterILike("codigoAlfa2", oPais.getCodigoAlfa2().trim());
            oSearchCodigoAlfa2.addFilterNotEqual("id", oPais.getId());
            listaValidacionCodigoAlfa2 = this.oPaisesDAO.search(oSearchCodigoAlfa2);
            if (listaValidacionCodigoAlfa2.size() > 0) {
                throw new BusinessException("Este código alfa2 ya se encuentra registrado");
            }
            oSearchValidacionNombre = new Search();
            oSearchValidacionNombre.addFilterILike("nombre", oPais.getNombre().trim());
            oSearchValidacionNombre.addFilterNotEqual("id", oPais.getId());
            listaValidacionNombre = this.oPaisesDAO.search(oSearchValidacionNombre);
            if (listaValidacionNombre.size() > 0) {
                throw new BusinessException("Este nombre ya se encuentra registrado");
            }
        }
    }
}