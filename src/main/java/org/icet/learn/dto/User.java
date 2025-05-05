package org.icet.learn.dto;

import lombok.Data;
import org.icet.learn.enums.UserRole;

@Data
public class User {

    private Long id;

    private String email;

    private String name;

    private UserRole userRole;

}
