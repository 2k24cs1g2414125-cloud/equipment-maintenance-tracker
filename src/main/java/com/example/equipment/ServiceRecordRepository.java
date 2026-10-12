package com.example.equipment;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRecordRepository extends JpaRepository<ServiceRecord, Long> {

    List<ServiceRecord> findByEquipmentId(Long equipmentId);
}
