package com.akash.gupta.BackendStoreCRUD.security;

import com.akash.gupta.BackendStoreCRUD.dtos.UserDto;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class JwtResponse{

    private String jwtToken;
    private UserDto user;
    //these are the requirements to get the data from the jwt and
    //give it to the Response

}
