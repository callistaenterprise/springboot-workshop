package se.callista.springboot.rest.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "careunit")
@EqualsAndHashCode(exclude = {"hospital", "beds"} )
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CareUnitJPA {
    @Id
    @GeneratedValue
    private long id;

    private String name;
    private String phoneNumber;
    private String email;

    @ManyToOne(fetch=FetchType.LAZY)
    @JsonBackReference
    private HospitalJPA hospital;

    @OneToMany(fetch= FetchType.LAZY, cascade= CascadeType.ALL,mappedBy = "careunit")
    @JsonManagedReference
    private Set<BedJPA> beds = new HashSet<>();
}
