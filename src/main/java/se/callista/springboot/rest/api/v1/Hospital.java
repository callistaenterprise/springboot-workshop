package se.callista.springboot.rest.api.v1;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import se.callista.springboot.rest.domain.CareUnitJPA;

import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "careunits")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Hospital {
    private long id;
    @NotEmpty(message = "{hospital.name.notempty}")
    private String name;
    private String address;
    private Set<CareUnitJPA> careunits = new HashSet<>();

    public Hospital(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
