package com.davidegiannetti.controller;

import com.davidegiannetti.dto.role.InputRoleDto;
import com.davidegiannetti.dto.role.OutputRoleDto;
import com.davidegiannetti.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/role")
@RestController
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<OutputRoleDto> create (@RequestBody InputRoleDto inputRoleDto){
        return new ResponseEntity<>(roleService.create(inputRoleDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id){
        roleService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
