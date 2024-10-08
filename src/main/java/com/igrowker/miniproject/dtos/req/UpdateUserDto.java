package com.igrowker.miniproject.dtos.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Update user, the imageUrl is not priority over image" )
public class UpdateUserDto {
    private String name;
    private String email;
    private String phone;
    @Schema(example = "https://example.com/image.jpg")
    private String imageUrl;
}
