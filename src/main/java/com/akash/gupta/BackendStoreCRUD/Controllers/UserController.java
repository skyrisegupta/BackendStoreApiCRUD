package com.akash.gupta.BackendStoreCRUD.Controllers;

import com.akash.gupta.BackendStoreCRUD.dtos.ApiResponseMessage;
import com.akash.gupta.BackendStoreCRUD.dtos.ImageResponse;
import com.akash.gupta.BackendStoreCRUD.dtos.PageableResponse;
import com.akash.gupta.BackendStoreCRUD.dtos.UserDto;
import com.akash.gupta.BackendStoreCRUD.services.FileService;
import com.akash.gupta.BackendStoreCRUD.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "User Controller", description = "REST APIs related to perform user operations")
@SecurityRequirement(name="scheme1")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    //CREATE
    @PostMapping
    @Operation(summary = "Create User", description = "Create a new user", tags = {"User Controller"})
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {

        UserDto userDto1 = userService.createUser(userDto);
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);

    }

    //UPDATE
    @PutMapping("/{userId}")
    @Operation(summary = "Update User Details", description = "Update user details by ID", tags = {"User Controller"})
    public ResponseEntity<UserDto> updateUser(@PathVariable("userId") String userId,
                                              @Valid @RequestBody UserDto userDto) {
        UserDto userDto2 = userService.updateUser(userDto, userId);
        return new ResponseEntity<>(userDto2, HttpStatus.OK);

    }

    //DELETE
    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete User", description = "Delete user by ID", tags = {"User Controller"})

    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId) {
        try {
            userService.deleteUser(userId);
            ApiResponseMessage message = ApiResponseMessage.builder()
                    .message("User is Deleted successfully !! ")
                    .success(true)
                    .status(HttpStatus.OK)
                    .build();
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e) {
            // Handle the foreign key constraint violation
            ApiResponseMessage errorMessage = ApiResponseMessage.builder()
                    .message(e.getMessage())
                    .success(false)
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }


    }

    //GETALL
    @GetMapping
    @Operation(summary = "Get All Users", description = "Get all users from the database", tags = {"User Controller"})
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy, @RequestParam(value = "sortDir", defaultValue = "asc", required = true) String sortDir) {

        return new ResponseEntity<>(userService.getAllUser(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);


    }

    //GET single
    @GetMapping("/{userId}")
    @Operation(summary = "Get User by User ID", description = "Get user details by User ID", tags = {"User Controller"})
    public ResponseEntity<UserDto> getUser(@PathVariable String userId) {
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    //GET by mail
    @GetMapping("/email/{email}")
    @Operation(summary = "Get User by Email", description = "Get user details by email", tags = {"User Controller"})
    public ResponseEntity<UserDto> getUserByMail(@PathVariable String email) {
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }


    //Search User
    @GetMapping("/search/{keyword}")
    @Operation(summary = "Search Users", description = "Search users by keyword", tags = {"User Controller"})
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword) {
        return new ResponseEntity<>(userService.searchUser(keyword), HttpStatus.OK);

    }

    //upload user image
    @PostMapping("/image/{userId}")
    @Operation(summary = "Upload or Update User's Profile Image", description = "Upload or update user's profile image by ID", tags = {"User Controller"})
    public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("userImage") MultipartFile image,
                                                         @PathVariable String userId) throws IOException {

        String imageName = fileService.uploadFile(image, imageUploadPath);
        UserDto user = userService.getUserById(userId);
        user.setImageName(imageName);
        UserDto userDto = userService.updateUser(user, userId);
        ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).success(true).status(HttpStatus.CREATED).build();
        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
    }

    @GetMapping("/image/{userId}")
    @Operation(summary = "Get User's Profile Image", description = "Get user's profile image by ID", tags = {"User Controller"})

    public void serveUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {
        UserDto user = userService.getUserById(userId);
        logger.info("User image : {}", user.getImageName());
//        we can read the data from this and give it into the response
        InputStream resource = fileService.getResource(imageUploadPath, user.getImageName());

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());


    }


}
//  For any Query Contact : akashguptaworks@gmail.com or skyrisegupta@gmail.com