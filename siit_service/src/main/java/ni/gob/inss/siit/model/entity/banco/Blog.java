package ni.gob.inss.siit.model.entity.banco;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "blogs", schema = "salvador")
@SequenceGenerator(name = "blogs_sq", sequenceName = "salvador.blogs_sq", allocationSize = 1)
public class Blog {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy =  GenerationType.SEQUENCE, generator = "blogs_sq")
    private Integer id;

    @Basic
    @Column(name = "title")
    private String title;

    @Basic
    @Column(name = "description")
    private String description;

}
