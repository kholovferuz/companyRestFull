package uz.pdp.companyrestapp.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.companyrestapp.DTO.Result;
import uz.pdp.companyrestapp.Entity.Address;
import uz.pdp.companyrestapp.Repository.AddressRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    @Autowired
    AddressRepository addressRepository;

    // READ all addresses
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    // READ one address by id
    public Address getOneAddress(Integer id) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        return optionalAddress.orElse(null);
    }

    // CREATE new address
    public Result addAddress(Address address) {
        boolean exists = addressRepository.existsByStreetAndHomeNumber(address.getStreet(), address.getHomeNumber());
        if (exists) {
            return new Result("This address already exists", false);
        }

        Address newAddress = new Address();
        newAddress.setStreet(address.getStreet());
        newAddress.setHomeNumber(address.getHomeNumber());
        addressRepository.save(address);
        return new Result("Address added", true);
    }

    // UPDATE address by id
    public Result updateAddress(Integer id, Address address) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isPresent()) {
            boolean exists = addressRepository.existsByStreetAndHomeNumber(address.getStreet(), address.getHomeNumber());
            if (exists) {
                return new Result("This address already exists", false);
            }
            Address editedAddress = optionalAddress.get();
            editedAddress.setStreet(address.getStreet());
            editedAddress.setHomeNumber(address.getHomeNumber());
            addressRepository.save(editedAddress);
            return new Result("Address updated", true);
        }
        return new Result("Address with this id is not found", false);
    }

    // DELETE address by id
    public Result deleteAddressById(Integer id) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isPresent()) {
            addressRepository.deleteById(id);
            return new Result("Address deleted", true);
        }
        return new Result("Address with this id is not found", false);
    }
}
