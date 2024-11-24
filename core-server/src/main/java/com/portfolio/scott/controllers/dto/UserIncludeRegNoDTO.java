package com.portfolio.scott.controllers.dto;

import com.portfolio.scott.domains.user.model.User;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserIncludeRegNoDTO extends UserDTO {

    private String regNo;

}
