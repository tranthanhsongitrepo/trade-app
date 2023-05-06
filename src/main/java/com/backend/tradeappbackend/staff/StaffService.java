package com.backend.tradeappbackend.staff;

import com.backend.tradeappbackend.user.AuthProvider;
import com.backend.tradeappbackend.user.exception.UserNotFoundException;
import com.backend.tradeappbackend.user.exception.EmailExistsException;
import com.backend.tradeappbackend.userRole.UserRole;
import com.backend.tradeappbackend.userRole.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class StaffService {
    private final StaffRepository staffRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public StaffService(StaffRepository staffRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.staffRepository = staffRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Collection<Staff> getStaffs(int page, int size) {
        return this.staffRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    public Staff getStaffById(Long id) {
        return this.staffRepository.getStaffByUserId(id);
    }

    public Staff updateStaff(Long id, Staff staff) {
        if (staffRepository.existsByEmail(staff.getEmail())) {
            throw new EmailExistsException(staff.getEmail());
        }

        if (this.staffRepository.existsById(id)) {
            try {
                return this.staffRepository.save(staff);
            } catch (DataIntegrityViolationException dataIntegrityViolationException) {
                throw new EmailExistsException(staff.getEmail());
            }
        } else {
            throw new UserNotFoundException(id);
        }
    }

    public void deleteStaffById(Long id) {
        this.staffRepository.deleteById(id);
    }

    public Staff addStaff(Staff staff) throws EmailExistsException {
        if (staffRepository.existsByEmail(staff.getEmail())) {
            throw new EmailExistsException(staff.getEmail());
        }

        UserRole userRole = userRoleRepository.findRoleByRoleName("ROLE_STAFF");
        userRole.setUserWithRole(List.of(staff));
        staff.setUserRoles(List.of(userRole));
        staff.setProvider(AuthProvider.LOCAL);
        staff.setPassword(this.passwordEncoder.encode(staff.getPassword()));

        return this.staffRepository.save(staff);
    }
}
