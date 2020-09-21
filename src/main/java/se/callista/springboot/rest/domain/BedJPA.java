package se.callista.springboot.rest.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Bed")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BedJPA {
    @Id
    @GeneratedValue
    private long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private State state = State.FREE;

    public enum State {
        FREE,
        RESERVED,
        OCCUPIED
    }

}
