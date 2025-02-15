package dev.promporn.assignment1;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class electricianController {
    private final StoragRepository repository;

    electricianController(StoragRepository repository){
        this.repository = repository;

    }

    @GetMapping("/elecTools")
    List<Storge> findAll(){
        return repository.findAll();
    }
    @GetMapping("/elecTools/{id}")
    Storge findOne(@PathVariable Long id) {
        Optional<Storge> electool =  repository.findById(id);
        if (electool.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no handy tool by given id");
        }
        return electool.get();
    }
    //searchหาเจ้าของเครื่องมือ
    @GetMapping("/elecTools/search")
    List<Storge> searchTools(
        @RequestParam(required = false) Boolean borrowed,
        @RequestParam(required = false) String ownerName) {
    if (borrowed != null) {
        return repository.findByBorrowed(borrowed);
    } else if (ownerName != null && !ownerName.isBlank()) {
        return repository.findByOwnerNameContainingIgnoreCase(ownerName);
    }
    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please specify a search parameter"); 
    }

    @GetMapping("/elecTools/search/borrower/{borrowerName}")
    List<Storge> findByBorrowerName(@PathVariable String borrowerName) {
        return repository.findByBorrowerNameContainingIgnoreCase(borrowerName);
    }

    @PostMapping("/elecTools")
    Storge newElectool(@RequestBody Storge electool) {        
        return repository.save(electool);
    }
    
    @PutMapping("/elecTools/{id}")
    Storge saveElectool(@RequestBody Storge newElectool, @PathVariable Long id) {
        return repository.findById(id).map(electool -> {
            if (newElectool.getBorrowed() != null) {
                electool.setBorrowed(newElectool.getBorrowed());  // อัปเดตสถานะการยืม
            }
            if (newElectool.getBorrowerName() != null) {
                electool.setBorrowerName(newElectool.getBorrowerName());  // อัปเดตชื่อผู้ยืม
            }
            if (newElectool.getLocationName() != null) {
                electool.setLocationName(newElectool.getLocationName());  // ถ้าส่งข้อมูล locationName มาจะอัปเดตด้วย
            }
            if (newElectool.getOwnerName() != null) {
                electool.setOwnerName(newElectool.getOwnerName());  // ถ้าส่งข้อมูล ownerName มาจะอัปเดตด้วย
            }
            if (newElectool.getToolDetail() != null) {
                electool.setToolDetail(newElectool.getToolDetail());  // ถ้าส่งข้อมูล toolDetail มาจะอัปเดตด้วย
            }
            return repository.save(electool);  // บันทึกการอัปเดต
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tool not found"));
    }
    
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/elecTools/{id}")
    void deleteElectool(@PathVariable Long id) {
        repository.deleteById(id);
    }




}
