package uz.pdp.companyrestapp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.companyrestapp.Entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    boolean existsByStreetAndHomeNumber(String street, String homeNumber);
}
