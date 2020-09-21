package se.callista.springboot.rest.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Bed")
@EqualsAndHashCode(exclude = "careunit")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BedJPA {
    @Id
    @GeneratedValue
    private long id;

    private String name;
    private String room;

    @Enumerated(EnumType.STRING)
    private State state = State.FREE;

    @ManyToOne(fetch= FetchType.LAZY)
    @JsonBackReference
    private CareUnitJPA careunit;

    public enum State {
        FREE,
        RESERVED,
        OCCUPIED
    }

}
