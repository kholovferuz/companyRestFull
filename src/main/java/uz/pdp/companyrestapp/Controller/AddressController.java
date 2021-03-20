package uz.pdp.companyrestapp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.companyrestapp.DTO.Result;
import uz.pdp.companyrestapp.Entity.Address;
import uz.pdp.companyrestapp.Service.AddressService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/address")
public class AddressController {
    @Autowired
    AddressService addressService;

    // READ all addresses
    @GetMapping
    public ResponseEntity<List<Address>> readAddress() {
        return ResponseEntity.ok(addressService.getAllAddresses());
    }

    // READ one address by id
    @GetMapping("/{id}")
    public HttpEntity<Address> readAddressById(@PathVariable Integer id) {
        return ResponseEntity.ok(addressService.getOneAddress(id));
    }

    // CREATE new address
    @PostMapping
    public HttpEntity<Result> createAddress(@Valid @RequestBody Address address) {
        Result result = addressService.addAddress(address);
        return ResponseEntity.status(result.isSuccess() ? 201 : 409).body(result);
    }

    // UPDATE address by id
    @PutMapping("/{id}")
    public HttpEntity<Result> editAddress(@PathVariable Integer id, @Valid @RequestBody Address address) {
        Result result = addressService.updateAddress(id, address);
        return ResponseEntity.status(result.isSuccess() ? 201 : 409).body(result);
    }

    // DELETE address by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Result> deleteAddress(@PathVariable Integer id) {
        Result result = addressService.deleteAddressById(id);
        return ResponseEntity.status(result.isSuccess() ? 202 : 409).body(result);
    }

    // XATOLIKLARNI QAYTARISH UCHUN
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> mistakes = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            mistakes.put(fieldName, errorMessage);
        });
        return mistakes;
    }
}
