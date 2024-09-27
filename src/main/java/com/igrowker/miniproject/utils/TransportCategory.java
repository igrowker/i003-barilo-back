package com.igrowker.miniproject.utils;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema()
public enum TransportCategory {
    @Schema(description = "Transport basic")
    BASIC,
    @Schema(description = "Transport standard")
    STANDARD,
    @Schema(description = "Transport premium")
    PREMIUM
}
