package com.igrowker.miniproject.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GroupDto {
    private Long id;
    private String name;
    private Integer studentsQuantity;
    private List<UserDto> users;
}
