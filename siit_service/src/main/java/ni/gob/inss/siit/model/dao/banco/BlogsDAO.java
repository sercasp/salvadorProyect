package ni.gob.inss.siit.model.dao.banco;

import ni.gob.inss.barista.model.dao.BaseGenericDAO;
import ni.gob.inss.siit.model.entity.banco.Blog;
import ni.gob.inss.siit.model.entity.banco.BlogsReader;
import ni.gob.inss.siit.model.entity.banco.Readers;

import java.util.List;

public interface BlogsDAO extends BaseGenericDAO<Blog, Integer> {
    void actualizar(Blog blog) throws Exception;

    void guardar(Blog blog) throws Exception;

    void eliminar(int id) throws Exception;

    List<Blog> listaBlogs(String buscarBlog);

    Blog obtenerBlogPorId(int id) throws Exception;
}
