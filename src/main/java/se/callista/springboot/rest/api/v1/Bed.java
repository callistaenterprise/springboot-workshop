package se.callista.springboot.rest.api.v1;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.callista.springboot.rest.domain.BedJPA;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Bed {
    private long id;
    @NotEmpty(message = "{bed.name.notempty}")
    private String name;
    private String room;
    private BedJPA.State state;
}
