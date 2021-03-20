package uz.pdp.companyrestapp.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.companyrestapp.DTO.DepartmentDTO;
import uz.pdp.companyrestapp.DTO.Result;
import uz.pdp.companyrestapp.DTO.WorkerDTO;
import uz.pdp.companyrestapp.Entity.Address;
import uz.pdp.companyrestapp.Entity.Company;
import uz.pdp.companyrestapp.Entity.Department;
import uz.pdp.companyrestapp.Entity.Worker;
import uz.pdp.companyrestapp.Repository.AddressRepository;
import uz.pdp.companyrestapp.Repository.DepartmentRepository;
import uz.pdp.companyrestapp.Repository.WorkerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {
    @Autowired
    WorkerRepository workerRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    AddressRepository addressRepository;

    // READ all the workers
    public List<Worker> getAllWorkers() {
        List<Worker> workers = workerRepository.findAll();
        return workers;
    }

    // READ one worker by id
    public Worker getOneWorker(Integer id) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        return optionalWorker.orElse(null);
    }

    // CREATE new worker
    public Result addWorker(WorkerDTO workerDTO) {
        boolean exists = workerRepository.existsByFullNameAndPhoneNumber(workerDTO.getFullName(), workerDTO.getPhoneNumber());
        if (exists) {
            return new Result("Worker already exists", true);
        }

        // department
        Optional<Department> optionalDepartment = departmentRepository.findById(workerDTO.getDepartmentId());
        if (optionalDepartment.isEmpty()) {
            return new Result("Department with this id is not found", false);
        }

        // address
        Optional<Address> optionalAddress = addressRepository.findById(workerDTO.getAddressId());
        if (optionalAddress.isEmpty()) {
            return new Result("Address with this id is not found", false);
        }
        Worker worker = new Worker();
        worker.setFullName(workerDTO.getFullName());
        worker.setPhoneNumber(workerDTO.getPhoneNumber());

        worker.setDepartment(optionalDepartment.get());
        worker.setAddress(optionalAddress.get());

        workerRepository.save(worker);
        return new Result("Worker added", true);
    }

    // UPDATE worker by id
    public Result editWorker(Integer id, WorkerDTO workerDTO) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (optionalWorker.isPresent()) {
            boolean exists = workerRepository.existsByFullNameAndPhoneNumber(workerDTO.getFullName(), workerDTO.getPhoneNumber());
            if (exists) {
                return new Result("Worker already exists", true);
            }

            // department
            Optional<Department> optionalDepartment = departmentRepository.findById(workerDTO.getDepartmentId());
            if (optionalDepartment.isEmpty()) {
                return new Result("Department with this id is not found", false);
            }

            // address
            Optional<Address> optionalAddress = addressRepository.findById(workerDTO.getAddressId());
            if (optionalAddress.isEmpty()) {
                return new Result("Address with this id is not found", false);
            }
            Worker worker = new Worker();
            worker.setFullName(workerDTO.getFullName());
            worker.setPhoneNumber(workerDTO.getPhoneNumber());

            worker.setDepartment(optionalDepartment.get());
            worker.setAddress(optionalAddress.get());

            workerRepository.save(worker);
            return new Result("Worker updated", true);
        }
        return new Result("Worker with this id is not found", false);
    }

    // DELETE worker by id
    public Result deleteWorker(Integer id) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (optionalWorker.isPresent()) {
            workerRepository.deleteById(id);
            return new Result("Worker deleted", true);
        }
        return new Result("Worker with this id is not found", false);
    }
}
