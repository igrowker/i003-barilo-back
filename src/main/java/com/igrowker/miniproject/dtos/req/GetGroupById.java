package com.igrowker.miniproject.dtos.req;

import com.igrowker.miniproject.dtos.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetGroupById {
    private Long id;
    private String name;
    private Integer studentsQuantity;
    private List<UserDto> users;
    private Boolean isMember;
}
