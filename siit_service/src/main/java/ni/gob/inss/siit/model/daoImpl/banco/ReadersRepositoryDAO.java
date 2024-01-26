package ni.gob.inss.siit.model.daoImpl.banco;

import com.googlecode.genericdao.search.Search;
import ni.gob.inss.barista.model.daoImpl.BaseGenericDAOImpl;

import ni.gob.inss.siit.model.dao.banco.ReadersDAO;
import ni.gob.inss.siit.model.entity.banco.Readers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class ReadersRepositoryDAO extends BaseGenericDAOImpl<Readers, Integer> implements ReadersDAO {

    @Override
    public List<Readers> listReaders(String prBuscar) {
        Search search = new Search();
        search.addSort("name", false);
        if (prBuscar != null) {
            search.addFilterILike("name", "%" + prBuscar + "%");
        }
        return this.search(search);
    }

    @Override
    public Readers obtenerReaderPorId(int id) throws Exception {
        return this.find(id);
    }

    @Override
    public void actualizar(Readers readers) throws Exception {
        this.update(readers);
    }

    @Override
    public void guardar(Readers readers) throws Exception {
        this.save(readers);
    }

    @Override
    public void eliminar(int id) throws Exception {
        this.removeId(id);
    }

}
