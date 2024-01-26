package ni.gob.inss.siit.model.daoImpl.banco;

import com.googlecode.genericdao.search.Search;
import ni.gob.inss.barista.model.dao.BaseGenericDAO;
import ni.gob.inss.barista.model.daoImpl.BaseGenericDAOImpl;
import ni.gob.inss.siit.model.dao.banco.BlogsDAO;
import ni.gob.inss.siit.model.entity.banco.Blog;
import ni.gob.inss.siit.model.entity.banco.BlogsReader;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class BlogsRepositoryDAO extends BaseGenericDAOImpl<Blog,Integer> implements BlogsDAO {

    @Override
    public void actualizar(Blog blog) throws Exception {
        this.update(blog);
    }

    @Override
    public void guardar(Blog blog) throws Exception {
        this.save(blog);
    }

    @Override
    public void eliminar(int id) throws Exception {
        this.removeId(id);
    }

    @Override
    public List<Blog> listaBlogs(String buscarBlog) {
        Search search = new Search();
        search.addSort("title", false);
        search.addSort("description", false);
        if (buscarBlog != null) {
            search.addFilterILike("description", "%" + buscarBlog + "%");
        }
        return this.search(search);
    }

    @Override
    public Blog obtenerBlogPorId(int id) throws Exception {
        return this.find(id);
    }
}
