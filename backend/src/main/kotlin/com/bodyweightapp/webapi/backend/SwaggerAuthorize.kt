package com.bodyweightapp.webapi.backend

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement

@Operation(security = [SecurityRequirement(name = BearerSecurityName)])
annotation class SwaggerAuthorize