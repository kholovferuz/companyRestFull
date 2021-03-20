package uz.pdp.companyrestapp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.companyrestapp.DTO.Result;
import uz.pdp.companyrestapp.DTO.WorkerDTO;
import uz.pdp.companyrestapp.Service.WorkerService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/worker")
public class WorkerController {
    @Autowired
    WorkerService workerService;

    // READ all the workers
    @GetMapping
    public ResponseEntity<?> readAllWorkers() {
        return ResponseEntity.ok(workerService.getAllWorkers());
    }

    // READ one worker by id
    @GetMapping("/{id}")
    public ResponseEntity<?> readOneWorker(@PathVariable Integer id) {
        return ResponseEntity.ok(workerService.getOneWorker(id));
    }

    // CREATE new worker
    @PostMapping
    public ResponseEntity<?> createWorker(@Valid @RequestBody WorkerDTO workerDTO) {
        Result result = workerService.addWorker(workerDTO);
        return ResponseEntity.status(result.isSuccess() ? 201 : 409).body(result);
    }

    // UPDATE worker by id
    @PutMapping("/{id}")
    public ResponseEntity<?> updateWorker(@Valid @RequestBody WorkerDTO workerDTO, @PathVariable Integer id) {
        Result result = workerService.editWorker(id, workerDTO);
        return ResponseEntity.status(result.isSuccess() ? 202 : 409).body(result);
    }

    // DELETE worker by id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWorkerById(@PathVariable Integer id) {
        Result result = workerService.deleteWorker(id);
        return ResponseEntity.status(result.isSuccess() ? 202 : 409).body(result);
    }

    // XATOLIKLARNI QAYTARISH
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
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
