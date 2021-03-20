package uz.pdp.companyrestapp.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String fullName;
    @Column(nullable = false)
    private String phoneNumber;
    @ManyToOne            // bir oilaning 3 a'zosi bitta addressga tegishli bolgani uchun ManyToOne qo'yildi.
    private Address address;
    @ManyToOne
    private Department department;
}
