package se.callista.springboot.rest.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import se.callista.springboot.rest.api.v1.Hospital;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hospital")
@EqualsAndHashCode(exclude = "careunits")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HospitalJPA {
    @Id
    @GeneratedValue
    private long id;

    private String name;
    private String address;

    @OneToMany(fetch= FetchType.LAZY, cascade= CascadeType.ALL,mappedBy = "hospital")
    @JsonManagedReference
    private Set<CareUnitJPA> careunits = new HashSet<>();

    public HospitalJPA(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public static Specification<HospitalJPA> nameContains(String name) {
        return name == null ? null :(ie, cq, cb) -> cb.like(ie.get("name"), "%" + name + "%");
    }

    public static Specification<HospitalJPA> addressContains(String address) {
        return address == null ? null :(ie, cq, cb) -> cb.like(ie.get("address"), "%" + address + "%");
    }

}
