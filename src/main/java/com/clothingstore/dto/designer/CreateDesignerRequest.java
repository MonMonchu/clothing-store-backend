package com.clothingstore.dto.designer;

import jakarta.validation.constraints.*;

public record CreateDesignerRequest(
        @NotBlank
        @Size(max = 100)
        String brand,

        @NotBlank
        @Size(max = 500)
        String billingAddress,

        @NotBlank
        @Size(max = 255)
        @Email
        String email,


        @NotBlank
        @Size(max = 20)
        @Pattern(regexp = "^[0-9+\\- ]+$")
        String phoneNumber,

        @Size(max = 2000)
        String description){

}






