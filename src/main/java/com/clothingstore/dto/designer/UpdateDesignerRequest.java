package com.clothingstore.dto.designer;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;



public record UpdateDesignerRequest(

        @Size(max = 100)
        String brand,

        @Size(max = 500)
        String billingAddress,

        @Size(max = 225)
        @Email
        String email,

        @Size(max = 20)
        @Pattern(regexp = "^[0-9+\\- ]+$")
        String phoneNumber,

        @Size(max = 2000)
        String description) {

}

