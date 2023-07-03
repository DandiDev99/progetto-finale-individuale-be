package com.davidegiannetti.service;

import com.davidegiannetti.dto.role.InputRoleDto;
import com.davidegiannetti.dto.role.OutputRoleDto;

public interface RoleService {

    OutputRoleDto create (InputRoleDto inputRoleDto);
    void delete (Long id);

}
