package com.igrowker.miniproject.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ActivityDTO {
    Long id;
    String name;
    String price;
    String description; 
    String destinationName;
}
