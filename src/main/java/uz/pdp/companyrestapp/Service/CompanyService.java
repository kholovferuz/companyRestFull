package uz.pdp.companyrestapp.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.companyrestapp.DTO.CompanyDTO;
import uz.pdp.companyrestapp.DTO.Result;
import uz.pdp.companyrestapp.Entity.Address;
import uz.pdp.companyrestapp.Entity.Company;
import uz.pdp.companyrestapp.Repository.AddressRepository;
import uz.pdp.companyrestapp.Repository.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    AddressRepository addressRepository;

    // READ all the companies
    public List<Company> readAllCompanies() {
        List<Company> companies = companyRepository.findAll();
        return companies;
    }

    // READ one company by id
    public Company getOneCompany(Integer id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        return optionalCompany.orElse(null);
    }

    // CREATE new company
    public Result addCompany(CompanyDTO companyDTO) {
        // address
        Address address = new Address();
        boolean existsByStreetAndHomeNumber = addressRepository.existsByStreetAndHomeNumber(companyDTO.getStreet(), companyDTO.getHomeNumber());
        if (existsByStreetAndHomeNumber){
            return new Result("This address already exists",false);
        }
        address.setStreet(companyDTO.getStreet());
        address.setHomeNumber(companyDTO.getHomeNumber());
        Address savedAddress = addressRepository.save(address);

        boolean exists = companyRepository.existsByCorpName(companyDTO.getCorpName());
        if (exists) {
            return new Result("Company already exists", true);
        }
        Company company = new Company();
        company.setCorpName(companyDTO.getCorpName());
        company.setDirectorName(companyDTO.getDirectorName());
        company.setAddress(savedAddress);
        companyRepository.save(company);
        return new Result("Company added", true);
    }

    // UPDATE company by id
    public Result editCompany(Integer id, CompanyDTO companyDTO) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isPresent()) {
            boolean exists = companyRepository.existsByCorpName(companyDTO.getCorpName());
            if (exists) {
                return new Result("Company already exists", true);
            }
            Company updatedCompany = optionalCompany.get();
            updatedCompany.setCorpName(companyDTO.getCorpName());
            updatedCompany.setDirectorName(companyDTO.getDirectorName());

            // address
            Address address = updatedCompany.getAddress();
            boolean existsByStreetAndHomeNumber = addressRepository.existsByStreetAndHomeNumber(companyDTO.getStreet(), companyDTO.getHomeNumber());
            if (existsByStreetAndHomeNumber){
                return new Result("This address already exists",false);
            }
            address.setStreet(companyDTO.getStreet());
            address.setHomeNumber(companyDTO.getHomeNumber());
            Address editedAddress = addressRepository.save(address);

            updatedCompany.setAddress(editedAddress);
            companyRepository.save(updatedCompany);
            return new Result("Company updated", true);
        }
        return new Result("Company with this id is not found", false);
    }

    // DELETE company by id
    public Result deleteCompany(Integer id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isPresent()) {
            companyRepository.deleteById(id);
            return new Result("Company deleted", true);
        }
        return new Result("Company with this id is not found", false);
    }
}
