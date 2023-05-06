package com.backend.tradeappbackend.admin;

import com.backend.tradeappbackend.admin.dto.UserAdminUpdateDTO;
import com.backend.tradeappbackend.user.User;
import com.backend.tradeappbackend.user.dto.UserInfoDTO;
import com.backend.tradeappbackend.user.response.DeleteSuccessfulResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("api/v1/admin")
public class AdminController {
    private final AdminService adminService;
    private final ModelMapper modelMapper;

    public AdminController(AdminService adminService, ModelMapper modelMapper) {
        this.adminService = adminService;
        this.modelMapper = modelMapper;
    }

    @PutMapping("/user")
    public ResponseEntity<?> updateUser(@RequestBody @Validated UserAdminUpdateDTO userDTO) {
        User user = this.modelMapper.map(userDTO, User.class);
        return ResponseEntity.ok(this.adminService.updateUser(user));
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        this.adminService.deleteUserById(id);
        return ResponseEntity.ok(new DeleteSuccessfulResponse());
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUsers(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(required = false, name = "size", defaultValue = "10") int size) {
        Collection<User> users = this.adminService.getAllUsers(page, size);

        Collection<UserAdminUpdateDTO> userInfoDTOs = new ArrayList<>();
        for (User user : users) {
            UserAdminUpdateDTO userInfoDTO = modelMapper.map(user, UserAdminUpdateDTO.class);
            userInfoDTOs.add(userInfoDTO);
        }

        return ResponseEntity.ok(userInfoDTOs);
    }

    @PostMapping("/user")
    public ResponseEntity<?> addUser(@RequestBody @Validated User user) {
        return ResponseEntity.ok(this.adminService.addUser(user));
    }
}
