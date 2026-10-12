package com.example.equipment;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

    // equipment whose next service date is on or before the given date
    List<Equipment> findByNextServiceDateLessThanEqual(LocalDate date);
}
