package com.clothingstore.dto.customer;

import java.util.UUID;

public record CustomerResponse(UUID id, String email, String firstName, String lastName, String address){
}
