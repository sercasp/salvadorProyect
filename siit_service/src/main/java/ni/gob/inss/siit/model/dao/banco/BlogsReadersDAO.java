package ni.gob.inss.siit.model.dao.banco;

import ni.gob.inss.barista.model.dao.BaseGenericDAO;
import ni.gob.inss.siit.model.entity.banco.BlogsReader;
import ni.gob.inss.siit.model.entity.banco.Readers;

import java.util.List;
import java.util.Map;

public interface BlogsReadersDAO extends BaseGenericDAO<BlogsReader,Integer> {

    void actualizar(BlogsReader blogsReader) throws Exception;

    void guardar(BlogsReader blogsReader ) throws Exception;

    void eliminar(int id) throws Exception;

    List<Map<String,Object>> listaBlogsReaders (String buscar);

    BlogsReader obtenerBlogReaderPorId(int id) throws Exception;

}
