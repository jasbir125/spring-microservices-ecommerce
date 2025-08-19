package com.singh.ecommerceapp.dtos;

import com.singh.ecommerceapp.entity.Role;
import com.singh.ecommerceapp.entity.RoleEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateRoleDto {
	@NotBlank(message = "The name is required")
	private String name;

	private String description;

	private boolean isDefault;

	public Role toRole() {
		return new Role().setName(RoleEnum.valueOf(name)).setDescription(this.description);
	}
}