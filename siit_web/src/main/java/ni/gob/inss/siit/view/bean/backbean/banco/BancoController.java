package ni.gob.inss.siit.view.bean.backbean.banco;

import lombok.Data;
import ni.gob.inss.barista.view.security.SystemSecurityException;
import ni.gob.inss.siit.model.dao.banco.BlogsDAO;
import ni.gob.inss.siit.model.dao.banco.BlogsReadersDAO;
import ni.gob.inss.siit.model.dao.banco.ReadersDAO;
import ni.gob.inss.siit.model.entity.banco.Blog;
import ni.gob.inss.siit.model.entity.banco.BlogsReader;
import ni.gob.inss.siit.model.entity.banco.Readers;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

//AUTOR scastillo

@Data
@Named
@Scope("view")
public class BancoController implements Serializable {

    private String buscar;

    private List<Readers> listaReaders;
    private Readers seleccionarReaders;
    private List<Blog> listaBlogs;
    private Blog seleccionarBlogs;
    private List<Map<String,Object>> listaBlosgReaders;
    private Map<String ,Object> seleccionarBlogsReaders;
    private Integer mostarPanel = 0;
    private String nombre;
    private String descripcion;
    private String titulo;

    // DAO
    private ReadersDAO objReadersDAO;
    private BlogsDAO objBlogsDAO;
    private BlogsReadersDAO objBlogsReadersDAO;

    @Inject
    @Autowired
    public BancoController(ReadersDAO objReadersDAO, BlogsDAO objBlogsDAO, BlogsReadersDAO objBlogsReadersDAO) {
        this.objReadersDAO = objReadersDAO;
        this.objBlogsDAO = objBlogsDAO;
        this.objBlogsReadersDAO = objBlogsReadersDAO;
    }

    @PostConstruct
    public void init() throws SystemSecurityException {

    }

    public void mostrarReaders() {
        try {
            listaReaders = objReadersDAO.listReaders(buscar);
            mostarPanel = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mostrarBlogs() {
        try {
            listaBlogs = objBlogsDAO.listaBlogs(buscar);
            mostarPanel = 2;
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void mostrarBlogsReaders() {
        try {
            listaBlosgReaders = objBlogsReadersDAO.listaBlogsReaders(buscar);
            mostarPanel = 3;
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void guardar() {
        try{

                Readers readers = new Readers();
                readers.setName(nombre);
                objReadersDAO.guardar(readers);
                PrimeFaces.current().executeScript("PF('modalReadersId').hide()");

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Guardado con éxito"));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void guardarBlogs(){
        try{
            Blog blog = new Blog();
            blog.setDescription(descripcion);
            blog.setTitle(titulo);
            objBlogsDAO.guardar(blog);
            PrimeFaces.current().executeScript("PF('modalBlogs').hide()");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Guardado con éxito"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void levantarModal(int ident){
        if (ident == 1){
            PrimeFaces.current().executeScript("PF('modalReadersId').show()");
        }
        if (ident == 2) {
            PrimeFaces.current().executeScript("PF('modalBlogs').show()");
        }
    }

    public void editarReaders(){
        try{

            Readers readers = objReadersDAO.obtenerReaderPorId(seleccionarReaders.getId());
            readers.setName(seleccionarReaders.getName());
            objReadersDAO.actualizar(readers);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Guardado con éxito"));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void eliminar(int id){
        try{

            objReadersDAO.eliminar(id);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Guardado con éxito"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void editarBlogs(){
        try{

            Blog blog = objBlogsDAO.obtenerBlogPorId(seleccionarBlogs.getId());
            blog.setTitle(seleccionarBlogs.getTitle());
            blog.setDescription(seleccionarBlogs.getDescription());
            objBlogsDAO.actualizar(blog);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Guardado con éxito"));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void eliminarBlog(int id){
        try{

            objBlogsDAO.eliminar(id);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Guardado con éxito"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void eliminarBlogReaders(){
        try{

            objBlogsReadersDAO.eliminar(Integer.parseInt(seleccionarBlogsReaders.get("br_id").toString()));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Eliminado con éxito"));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}