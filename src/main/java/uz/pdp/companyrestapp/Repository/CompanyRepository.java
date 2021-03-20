package uz.pdp.companyrestapp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.companyrestapp.Entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    boolean existsByCorpName(String name);
}
