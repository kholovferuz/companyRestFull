package uz.pdp.companyrestapp.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkerDTO {
    private String fullName;
    private String phoneNumber;

    private Integer addressId;

    private Integer departmentId;
}
