package se.callista.springboot.rest.api.v1;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import se.callista.springboot.rest.domain.BedJPA;

import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "beds")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CareUnit {
    private long id;
    @NotEmpty(message = "{careunit.name.notempty}")
    private String name;
    private String phoneNumber;
    private String email;
    private Set<BedJPA> beds = new HashSet<>();
}
