package ni.gob.inss.siit.model.daoImpl.banco;

import com.googlecode.genericdao.search.Search;
import ni.gob.inss.barista.model.daoImpl.BaseGenericDAOImpl;
import ni.gob.inss.siit.model.dao.banco.BlogsReadersDAO;
import ni.gob.inss.siit.model.dao.banco.ReadersDAO;
import ni.gob.inss.siit.model.entity.banco.BlogsReader;
import ni.gob.inss.siit.model.entity.banco.Readers;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;
import java.util.Map;

@Repository
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class BlogsReadersRepositoryDAO extends BaseGenericDAOImpl<BlogsReader, Integer> implements BlogsReadersDAO {


    @Override
    public void actualizar(BlogsReader blogsReader) throws Exception {
        this.update(blogsReader);
    }

    @Override
    public void guardar(BlogsReader blogsReader) throws Exception {
        this.save(blogsReader);
    }

    @Override
    public void eliminar(int id) throws Exception {
        this.removeId(id);
    }

    @Override
    public List<Map<String,Object>> listaBlogsReaders(String buscar){
        if (buscar == null){
            buscar = "";
        }
        Query query = sessionFactory.getCurrentSession()
          .createNativeQuery("select * from salvador.obtener_blogs_reader(:buscar)");
        query.setParameter("buscar", buscar);
        ((NativeQueryImpl) query).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        return query.getResultList();
    }

    @Override
    public BlogsReader obtenerBlogReaderPorId(int id) throws Exception {
        return this.find(id);
    }
}
