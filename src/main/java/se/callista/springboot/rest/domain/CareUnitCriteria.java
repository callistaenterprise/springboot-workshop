package se.callista.springboot.rest.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CareUnitCriteria {
    private String name;
    private String phoneNumber;
    private String email;
}
