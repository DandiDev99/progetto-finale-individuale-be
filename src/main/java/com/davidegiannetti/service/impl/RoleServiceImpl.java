package com.davidegiannetti.service.impl;

import com.davidegiannetti.dto.role.InputRoleDto;
import com.davidegiannetti.dto.role.OutputRoleDto;
import com.davidegiannetti.entity.Role;
import com.davidegiannetti.repository.RoleRepository;
import com.davidegiannetti.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Override
    public OutputRoleDto create(InputRoleDto inputRoleDto){
        inputRoleDto.setAuthority("ROLE_"+inputRoleDto.getAuthority().toUpperCase());
        return modelMapper.map(roleRepository.save(modelMapper.map(inputRoleDto, Role.class)), OutputRoleDto.class);
    }

    @Override
    public void delete(Long id) {
        roleRepository.delete(roleRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "RUOLO non trovato")));
    }

}
