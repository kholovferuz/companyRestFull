package uz.pdp.companyrestapp.DTO;

import lombok.Data;

@Data
public class CompanyDTO {
    private String corpName;
    private String directorName;

    // address
    private String street;
    private String homeNumber;
}
