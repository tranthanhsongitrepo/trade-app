package com.backend.tradeappbackend.staff;

import com.backend.tradeappbackend.staff.dto.StaffInfoDTO;
import com.backend.tradeappbackend.staff.dto.StaffRegisterDTO;
import com.backend.tradeappbackend.user.exception.UserIdNotNullException;
import com.backend.tradeappbackend.user.exception.EmailExistsException;
import com.backend.tradeappbackend.user.response.ValidationErrorResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/staff")
public class StaffController {
    private final StaffService staffService;
    private final ModelMapper modelMapper;

    @Autowired
    public StaffController(StaffService staffService, ModelMapper modelMapper) {
        this.staffService = staffService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    public ResponseEntity<?> getStaffs(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(required = false, name = "size", defaultValue = "10") int size) {
        Collection<Staff> staffs = this.staffService.getStaffs(page, size);
        Collection<StaffInfoDTO> staffInfoDTOs = new ArrayList<>();

        for (Staff staff : staffs) {
            StaffInfoDTO staffInfoDTO = this.modelMapper.map(staff, StaffInfoDTO.class);
            staffInfoDTOs.add(staffInfoDTO);

        }
        return ResponseEntity.ok(staffInfoDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStaff(@PathVariable Long id) {
        Staff staff = this.staffService.getStaffById(id);
        StaffInfoDTO staffInfoDTO = this.modelMapper.map(staff, StaffInfoDTO.class);
        return ResponseEntity.ok(staffInfoDTO);
    }

    @PostMapping("/")
    public ResponseEntity<?> registerStaff(@RequestBody @Validated StaffRegisterDTO staffRegisterDTO, BindingResult result) {
        ResponseEntity<?> validationErrorResponses = validateBody(result);
        if (validationErrorResponses != null)
            return validationErrorResponses;

        try {
            Staff staff = modelMapper.map(staffRegisterDTO, Staff.class);

            return ResponseEntity.ok(this.staffService.addStaff(staff));
        } catch (EmailExistsException exception) {
            ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse("username", exception.getMessage());
            return ResponseEntity.badRequest().body(validationErrorResponse);
        } catch (UserIdNotNullException exception) {
            ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse("id", exception.getMessage());
            return ResponseEntity.badRequest().body(validationErrorResponse);
        }

    }

    private ResponseEntity<?> validateBody(BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            List<ValidationErrorResponse> validationErrorResponses = errors.stream().map(error -> new ValidationErrorResponse(((FieldError) error).getField(), error.getDefaultMessage())).toList();
            return ResponseEntity.badRequest().body(validationErrorResponses);
        }
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStaff(@PathVariable Long id, @RequestBody @Validated Staff staff) {
        return ResponseEntity.ok(this.staffService.updateStaff(id, staff));
    }

}
