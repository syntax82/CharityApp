package pl.Lukasz.charity.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Integer quantity;
    String sity;
    String street;
    String zipCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate pickUpDate;
    LocalTime pickUpTime;
    String pickUpComment;
    @ManyToMany
    List<Category> categories = new ArrayList<>();
    @ManyToOne
    Institution institution;
    String phone;
    Boolean isPicked = false;
    LocalDateTime pickedDate;
    Boolean isTransfer = false;
    LocalDateTime trasferDate;
    @ManyToOne
    User user;
}
