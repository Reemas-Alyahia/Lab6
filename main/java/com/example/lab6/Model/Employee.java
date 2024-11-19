package com.example.lab6.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Employee {
    //ID , name, email , phoneNumber ,age, position, onLeave, hireDate and
    //annualLeave.
    @NotEmpty(message = "id can't be Empty")
    @Size(min=2 ,message = "id size must be more than 2")
    private String id;

    @NotEmpty(message = "name can't be Empty")
    @Size(min=4,message = "name size more than 4")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Must contain only characters (no numbers).")
    private String name;

    @Email(message = "must be email")
    private String email;

    @Pattern(regexp = "^05\\d+$", message = "Must start with '05' and contain only digits after it.")
    @Size (min=10,max = 10, message = "Must consists of exactly 10 digits")
    private String phoneNumber;

    @NotNull(message = "Age cannot be null.")
    @Min(value = 0, message = "Age must be a positive number.")
    @Max(value = 90, message = "Age must be realistic (less than or equal to 150).")
    private Integer age;

    @NotEmpty(message = " position cannot be null. ")
    @Pattern(regexp = "^(supervisor|coordinator)$", message = "Position must be either 'supervisor' or 'coordinator' only.")
    private String position;

    private boolean onLeave=false;

    @NotNull(message = "Hire date cannot be null.")
    @PastOrPresent(message = "Hire date must be in the present or the past.")
   @JsonFormat(pattern ="yyyy-MM-dd")
    private LocalDate hireDate;

    @NotNull(message = "Annual Leave cannot be null.")
    @Positive(message = "Annual Leave must be a positive number")
    private Integer annualLeave;




}

