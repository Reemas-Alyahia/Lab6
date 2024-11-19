package com.example.lab6.Control;

import com.example.lab6.ApiResponse.ApiResponse;
import com.example.lab6.Model.Employee;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/employee")

public class EmployeeController {
    ArrayList<Employee> employees = new ArrayList<>();

    @GetMapping("/get")
    public ResponseEntity getEmp() {
        return ResponseEntity.status(200).body(employees);
    }

    @PostMapping("/add")
    public ResponseEntity addEmp(@RequestBody @Valid Employee employee, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        employees.add(employee);
        return ResponseEntity.status(200).body(new ApiResponse("Done from ADD"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateEmp(@PathVariable String id, @RequestBody @Valid Employee employee, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId().equalsIgnoreCase(id)) {
                employees.set(i, employee);
                return ResponseEntity.status(200).body("Done from update");
            }

        }

        return ResponseEntity.status(400).body(new ApiResponse("Cannot found the Employee"));
    }


    @DeleteMapping("/delet")
    public ResponseEntity deletEmp(@PathVariable int index) {
        employees.remove(index);
        return ResponseEntity.status(200).body(new ApiResponse("Done from Deleting"));
    }
//. Search Employees by Position:

    @GetMapping("/search")
    public ResponseEntity search(@RequestParam String position) {
        for (Employee em : employees) {
            if (em.getPosition().equalsIgnoreCase(position)) {
            }
            return ResponseEntity.status(200).body("There your Employee:  " + em.getName() + ": " + em.getPosition());
        }
        return ResponseEntity.status(400).body(new ApiResponse("Cannot found the Employee"));
    }


    //Get Employees by Age Range:
    @GetMapping("/age")
    public ResponseEntity age(@RequestParam @Valid int minAge, @RequestParam @Valid int maxAge) {
        ArrayList<Employee> newE = new ArrayList<>();
        for (Employee em : employees) {
            if (em.getAge() >= minAge && em.getAge() <= maxAge) {
                newE.add(em);
            }
        }
        return ResponseEntity.status(201).body(newE);
    }
///////////////////

    @PutMapping("/check/{id}")
    public ResponseEntity check(@PathVariable String id) {
        for (Employee em : employees) {
            if (em.getId().equalsIgnoreCase(id)) {
                em.setOnLeave(true);
                em.setAnnualLeave(em.getAnnualLeave() - 1);
                return ResponseEntity.status(201).body("Done from Verify that the employee exists" + employees);
            }
            if (em.isOnLeave()) {
                return ResponseEntity.status(400).body("The employee must not be on leave");
            }
            if (em.getAnnualLeave() == 0) {
                return ResponseEntity.status(400).body("The employee must have at least one day of annual leave remaining");
            }
        }

        return ResponseEntity.status(400).body(new ApiResponse("Cannot found the Employee"));
    }

//////////////////////////

    @GetMapping("/noAnnual")
    public ResponseEntity noAnnual() {
        ArrayList<Employee> newEm = new ArrayList<>();
        for (Employee em : employees) {
            if (em.getAnnualLeave() == 0) {
                newEm.add(em);
            }
        }
        return ResponseEntity.status(200).body("The Employees with No Annual Leave: " + newEm);
    }

    ///////////////////
    //9. Promote Employee: Allows a supervisor to promote an employee to the position
    //of supervisor if they meet certain criteria. Note:
    //▪ Verify that the employee with the specified ID exists.*
    //▪ Ensure that the requester (user making the request) is a supervisor.*
    //▪ Validate that the employee's age is at least 30 years.*
    //▪ Confirm that the employee is not currently on leave.*
    //▪ Change the employee's position to "supervisor" if they meet the criteria.

    @PutMapping("/promote")
    public ResponseEntity promote(@RequestParam String id, @RequestBody @Valid Employee employee, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        if (!employee.getPosition().equalsIgnoreCase("supervisor")) {
            return ResponseEntity.status(400).body(new ApiResponse("user making the request must be supervisor"));
        }
        //////////////////////////////
        for (Employee em : employees) {
            if (em.getId().equalsIgnoreCase(id)) {
                if (em.getAge() < 30) {
                    return ResponseEntity.status(400).body("Employee must be at least 30 years");
                }
                if (em.isOnLeave()) {
                    return ResponseEntity.status(400).body("the employee must not currently on leave");
                }
                em.setPosition("supervisor");
                return ResponseEntity.status(200).body(new ApiResponse("Congrats! you deserve it :) .... "));

            }
        }
        return ResponseEntity.status(400).body("Cannot found the Employee");
    }


}


















