package uz.pdp.companyrestapp.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.companyrestapp.DTO.DepartmentDTO;
import uz.pdp.companyrestapp.DTO.Result;
import uz.pdp.companyrestapp.Entity.Company;
import uz.pdp.companyrestapp.Entity.Department;
import uz.pdp.companyrestapp.Repository.CompanyRepository;
import uz.pdp.companyrestapp.Repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    CompanyRepository companyRepository;

    // READ all the departments
    public List<Department> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments;
    }

    // READ one department by id
    public Department getOneDepartment(Integer id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        return optionalDepartment.orElse(null);
    }

    // CREATE new department
    public Result addDepartment(DepartmentDTO departmentDTO) {


        boolean exists = departmentRepository.existsByNameAndCompanyId(departmentDTO.getName(), departmentDTO.getCompanyId());
        if (exists) {
            return new Result("Department already exists", false);
        }
        Department department = new Department();
        department.setName(departmentDTO.getName());


        // company
        Optional<Company> optionalCompany = companyRepository.findById(departmentDTO.getCompanyId());
        if (optionalCompany.isEmpty()) {
            return new Result("Company with this id is not found", false);
        }

        department.setCompany(optionalCompany.get());
        departmentRepository.save(department);
        return new Result("Department added", true);
    }

    // UPDATE department by id
    public Result editDepartment(Integer id, DepartmentDTO departmentDTO) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (optionalDepartment.isPresent()) {
            boolean exists = departmentRepository.existsByNameAndCompanyId(departmentDTO.getName(), departmentDTO.getCompanyId());
            if (exists) {
                return new Result("Department already exists", true);
            }
            Department editedDepartment = optionalDepartment.get();
            editedDepartment.setName(departmentDTO.getName());


            // company
            Optional<Company> optionalCompany = companyRepository.findById(departmentDTO.getCompanyId());
            if (optionalCompany.isEmpty()) {
                return new Result("Company with this id is not found", false);
            }

            editedDepartment.setCompany(optionalCompany.get());
            departmentRepository.save(editedDepartment);
            return new Result("Department updated", true);
        }
        return new Result("Department with this id is not found", false);
    }

    // DELETE department by id
    public Result deleteDepartment(Integer id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (optionalDepartment.isPresent()) {
            departmentRepository.deleteById(id);
            return new Result("Department deleted", true);
        }
        return new Result("Department with this id is not found", false);
    }

}
