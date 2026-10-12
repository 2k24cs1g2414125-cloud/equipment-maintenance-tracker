package com.example.equipment;

import java.time.LocalDate;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {

    private final EquipmentRepository equipmentRepo;
    private final ServiceRecordRepository serviceRepo;

    public EquipmentController(EquipmentRepository equipmentRepo, ServiceRecordRepository serviceRepo) {
        this.equipmentRepo = equipmentRepo;
        this.serviceRepo = serviceRepo;
    }

    private Equipment find(Long id) {
        return equipmentRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipment not found: " + id));
    }

    @GetMapping
    public List<Equipment> all() {
        return equipmentRepo.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Equipment add(@RequestBody Equipment equipment) {
        equipment.setId(null);
        return equipmentRepo.save(equipment);
    }

    @GetMapping("/{id}")
    public Equipment one(@PathVariable Long id) {
        return find(id);
    }

    @PutMapping("/{id}")
    public Equipment update(@PathVariable Long id, @RequestBody Equipment updated) {
        Equipment existing = find(id);
        existing.setName(updated.getName());
        existing.setType(updated.getType());
        existing.setSerialNumber(updated.getSerialNumber());
        existing.setNextServiceDate(updated.getNextServiceDate());
        return equipmentRepo.save(existing);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        Equipment existing = find(id);
        serviceRepo.deleteAll(serviceRepo.findByEquipmentId(id));
        equipmentRepo.delete(existing);
    }

    // equipment due for service within the next N days (default 30)
    @GetMapping("/due")
    public List<Equipment> due(@RequestParam(defaultValue = "30") int days) {
        return equipmentRepo.findByNextServiceDateLessThanEqual(LocalDate.now().plusDays(days));
    }

    // log a service done on this equipment
    @PostMapping("/{id}/services")
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceRecord addService(@PathVariable Long id, @RequestBody ServiceRecord record) {
        Equipment equipment = find(id);
        record.setId(null);
        record.setEquipmentId(id);
        if (record.getServiceDate() == null) {
            record.setServiceDate(LocalDate.now());
        }
        equipment.setLastServiceDate(record.getServiceDate());
        equipmentRepo.save(equipment);
        return serviceRepo.save(record);
    }

    @GetMapping("/{id}/services")
    public List<ServiceRecord> history(@PathVariable Long id) {
        find(id);
        return serviceRepo.findByEquipmentId(id);
    }
}
