package uz.pdp.companyrestapp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.companyrestapp.DTO.CompanyDTO;
import uz.pdp.companyrestapp.DTO.Result;
import uz.pdp.companyrestapp.Entity.Company;
import uz.pdp.companyrestapp.Service.CompanyService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/company")
public class CompanyController {
    @Autowired
    CompanyService companyService;

    // READ all companies
    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        return ResponseEntity.ok(companyService.readAllCompanies());
    }

    // READ company by id
    @GetMapping("/{id}")
    public HttpEntity<Company> readOneCompany(@PathVariable Integer id) {
        return ResponseEntity.ok(companyService.getOneCompany(id));
    }

    // CREATE new company
    @PostMapping
    public ResponseEntity<?> createCompany(@Valid @RequestBody CompanyDTO companyDTO) {
        Result result = companyService.addCompany(companyDTO);
        return ResponseEntity.status(result.isSuccess() ? 201 : 409).body(result);
    }

    // UPDATE company by id
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCompany(@Valid @RequestBody CompanyDTO companyDTO, @PathVariable Integer id) {
        Result result = companyService.editCompany(id, companyDTO);
        return ResponseEntity.status(result.isSuccess() ? 202 : 409).body(result);
    }

    // DELETE company by id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCompanyById(@PathVariable Integer id) {
        Result result = companyService.deleteCompany(id);
        return ResponseEntity.status(result.isSuccess() ? 202 : 409).body(result);
    }

    // XATOLIKLARNI QAYTARISH
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> mistake = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            mistake.put(fieldName, errorMessage);
        });
        return mistake;
    }
}
