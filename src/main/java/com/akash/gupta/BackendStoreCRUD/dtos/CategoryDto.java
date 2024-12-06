package com.akash.gupta.BackendStoreCRUD.dtos;

import com.akash.gupta.BackendStoreCRUD.validate.ImageNameValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {


    private String categoryId;

    @NotBlank(message = "Title is required")
    @Size(min = 4 , message = "Title must be minimum of 4 characters")
    private String title;

    @NotBlank(message = "Description Required !!")
    @Size(min = 10 , message = "Title must be minimum of 4 characters")
    private String description;

     @ImageNameValid
     private String coverImage;


    //other attributes you can add here
}
//  For any Query Contact : akashguptaworks@gmail.com or skyrisegupta@gmail.com
