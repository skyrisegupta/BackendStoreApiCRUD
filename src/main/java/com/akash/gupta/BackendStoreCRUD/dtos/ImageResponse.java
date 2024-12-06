package com.akash.gupta.BackendStoreCRUD.dtos;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageResponse{

      private String imageName;
      private boolean success;
      private HttpStatus status;




}
//  For any Query Contact : akashguptaworks@gmail.com or skyrisegupta@gmail.com
