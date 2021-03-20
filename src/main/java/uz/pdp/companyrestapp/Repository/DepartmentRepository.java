package uz.pdp.companyrestapp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.companyrestapp.Entity.Department;

public interface DepartmentRepository extends JpaRepository<Department,Integer> {
    boolean existsByNameAndCompanyId(String name, Integer company_id);
}
