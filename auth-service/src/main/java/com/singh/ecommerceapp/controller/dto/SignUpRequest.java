package com.singh.ecommerceapp.controller.dto;

import com.singh.ecommerceapp.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;

public record SignUpRequest(
        @Schema(example = "user3") @NotBlank String username,
        @Schema(example = "user3") @NotBlank String password,
        @Schema(example = "User3") @NotBlank String firstName,
        @Schema(example = "User3") @NotBlank String lastName,
        @Schema(example = "user3@mycompany.com") @Email String email, Role role
) {
}
