package uz.pdp.companyrestapp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.companyrestapp.DTO.DepartmentDTO;
import uz.pdp.companyrestapp.DTO.Result;
import uz.pdp.companyrestapp.Service.DepartmentService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    // READ all departments
    @GetMapping
    public ResponseEntity<?> readAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    // READ department by id
    @GetMapping("/{id}")
    public ResponseEntity<?> readOneDepartment(@PathVariable Integer id) {
        return ResponseEntity.ok(departmentService.getOneDepartment(id));
    }

    // CREATE new department
    @PostMapping
    public ResponseEntity<?> createDepartment(@Valid @RequestBody DepartmentDTO departmentDTO) {
        Result result = departmentService.addDepartment(departmentDTO);
        return ResponseEntity.status(result.isSuccess() ? 201 : 409).body(result);
    }

    // UPDATE department by id
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDepartment(@Valid @RequestBody DepartmentDTO departmentDTO, @PathVariable Integer id) {
        Result result = departmentService.editDepartment(id, departmentDTO);
        return ResponseEntity.status(result.isSuccess() ? 202 : 409).body(result);
    }

    // DELETE department by id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartmentById(@PathVariable Integer id) {
        Result result = departmentService.deleteDepartment(id);
        return ResponseEntity.status(result.isSuccess() ? 202 : 409).body(result);
    }

    // XATOLIKLARNI QAYTARIH
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    private Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> mistakes = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            mistakes.put(fieldName, errorMessage);
        });
        return mistakes;
    }
}
