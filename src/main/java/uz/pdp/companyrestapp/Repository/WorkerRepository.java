package uz.pdp.companyrestapp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.companyrestapp.Entity.Worker;

public interface WorkerRepository extends JpaRepository<Worker,Integer> {
    boolean existsByFullNameAndPhoneNumber(String fullName, String phoneNumber);
}
