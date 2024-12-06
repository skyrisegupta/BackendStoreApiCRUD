package com.akash.gupta.BackendStoreCRUD.services.impl;

import com.akash.gupta.BackendStoreCRUD.dtos.PageableResponse;
import com.akash.gupta.BackendStoreCRUD.dtos.UserDto;
import com.akash.gupta.BackendStoreCRUD.entities.Role;
import com.akash.gupta.BackendStoreCRUD.entities.User;
import com.akash.gupta.BackendStoreCRUD.exceptions.ResourceNotFoundException;
import com.akash.gupta.BackendStoreCRUD.helper.Helper;
import com.akash.gupta.BackendStoreCRUD.repositories.RoleRepository;
import com.akash.gupta.BackendStoreCRUD.repositories.UserRepository;
import com.akash.gupta.BackendStoreCRUD.services.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ModelMapper mapper;

    @Value("${normal.role.id}")
    private String normalroleid;

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Value("${user.profile.image.path}")
    private String imagePath;

    @Override
    public UserDto createUser(UserDto userDto) {
//      DTO - > ENTITY
//      DTO - > ENTITY
        //generate unique id in string format
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);

        //encoding Password
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        //fetch role and set it to the user
        Role role = roleRepository.findById(normalroleid).get();


        User user = dtoToEntity(userDto);
        user.getRoles().add(role);
        User savedUser = userRepository.save(user);


//        ENTITY -> DTO
        UserDto newDto = entityToDto(savedUser);

        return newDto;
    }

    private User dtoToEntity(UserDto userDto) {
;

        return mapper.map(userDto, User.class);


    }

    private UserDto entityToDto(User savedUser) {
//       UserDto userDto =  UserDto.builder().userId(savedUser.getUserId())
//                .name(savedUser.getName()).
//                email(savedUser.getEmail())
//                .about(savedUser.getAbout())
//                .password(savedUser.getPassword())
//                .about(savedUser.getAbout())
//                .gender(savedUser.getGender())
//                .imagename(savedUser.getImagename())
//                .build();
//                 return userDto;


//            the above code is replace by this
        return mapper.map(savedUser, UserDto.class);
//                           source  , destination
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not Found with Give id"));
        user.setName(userDto.getName());
//      if you have reuirment in your project you can set your emAIL
//        user.setEmail(userDto.getEmail());

        user.setAbout(userDto.getAbout());
        user.setGender(userDto.getGender());
        user.setPassword(userDto.getPassword());
        user.setImageName(userDto.getImageName());

        //save data
        User updatedUser = userRepository.save(user);
        UserDto userDto1 = entityToDto(updatedUser);
        return userDto1;

    }

    @Override
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given id"));

        //delete user profile image
//         images/user/abc.png
        String fullPath = imagePath + user.getImageName();
        try {
            Path path = Paths.get(fullPath);
            Files.delete(path);


        } catch (NoSuchFileException ex) {

            logger.info("User Image not Found in Folder");
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //delete user
        userRepository.delete(user);

    }

    @Override
    public UserDto getUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given id"));

        return entityToDto(user);
//                  we fetch the entity but we return here the dto
    }

    // GETALL
    @Override
    public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        //        pageNumber default starts from 0


//                     this pageable containing the page number and pagesize
//                                                              now page will start from 1
//        PageRequest pageable = PageRequest.of(pageNumber-1 , pageSize ,sort);
        PageRequest pageable = PageRequest.of(pageNumber, pageSize, sort);

        //          it will return the page object of  type User
        Page<User> page = userRepository.findAll(pageable);

        PageableResponse<UserDto> response = Helper.getPageableResponse(page, UserDto.class);
        return response;
    }

    @Override
    public UserDto getUserByEmail(String email) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User Not found with give email id"));

        return entityToDto(user);

    }

    @Override
    public List<UserDto> searchUser(String keyword) {

        List<User> users = userRepository.findByNameContaining(keyword);
//        List<UserDto> dtoList = users.stream().map( User -> entityToDto(User).collect(Collectors.toList());
        List<UserDto> dtoList = users.stream()
                .map(user -> entityToDto(user))
                .collect(Collectors.toList());

        return dtoList;

    }

    @Override
    public Optional<User> findUserByEmailOptional(String email) {
        return userRepository.findByEmail(email);
    }


}
