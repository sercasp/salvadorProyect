package ni.gob.inss.siit.model.entity.banco;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "readers", schema = "salvador")
@SequenceGenerator(name = "reader_sq", sequenceName = "salvador.reader_sq", allocationSize = 1)
public class Readers implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy =  GenerationType.SEQUENCE, generator = "reader_sq")
    private Integer id;

    @Basic
    @Column(name = "name")
    private String name;

}
