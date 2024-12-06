package com.akash.gupta.BackendStoreCRUD.Controllers;

import com.akash.gupta.BackendStoreCRUD.dtos.UserDto;
import com.akash.gupta.BackendStoreCRUD.entities.User;
import com.akash.gupta.BackendStoreCRUD.exceptions.BadApiRequestException;
import com.akash.gupta.BackendStoreCRUD.security.JwtHelper;
import com.akash.gupta.BackendStoreCRUD.security.JwtRequest;
import com.akash.gupta.BackendStoreCRUD.security.JwtResponse;
import com.akash.gupta.BackendStoreCRUD.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication Controller", description = "REST APIs for Authentication")


//@Api(Value="AuthController" , description = "APIs for Authenticaion")
//@CrossOrigin(origins = "https://domain1.com")   //we will allow only this domain
//@CrossOrigin(origins ="http://localhost:4200",allowedHeaders = {"Authorization"},methods ={RequestMethod.GET,RequestMethod.POST}) we do configuation in a single place in security configuraiton that's why thisis no longer needed
@SecurityRequirement(name="scheme1")
public class AuthController{
    @Autowired
    private UserDetailsService userDetailsService;

//this manager will check if the password is correct or not this Manager have the method.
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper mapper;
// if we want to enable sign in with google .
//    @Value("${googleClientId}")
//    private String googleClientId;

    @Value("${newPassword}")
    private String newPassword;

    Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private JwtHelper helper;

    //Principal object is used to get the currently authenticated user's name.

    @GetMapping("/current")
    @Operation(summary = "Fetch Current User", description = "Endpoint to retrieve details of the current user",tags = {"Authentication Controller"})
    public ResponseEntity<UserDto> getCurrentUser(Principal principal){

        String name  =  principal.getName();
        return new ResponseEntity<>(mapper.map(userDetailsService.
                loadUserByUsername(name) ,UserDto.class), HttpStatus.OK);



    }

    @PostMapping("/login")
    @Operation(summary = "User Login", description = "Endpoint to authenticate a user (Through JWT authentication)", tags = {"Authentication Controller"})
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request){

        // in request we are getting our username and password
        this.doAuthenticate(request.getEmail() , request.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

        //here  we are generating the token
        String token = this.helper.generateToken(userDetails);
        UserDto userDto = mapper.map(userDetails , UserDto.class);
         //storing the token
        JwtResponse response = JwtResponse.builder().jwtToken(token).user(userDto).build();
        return new ResponseEntity<>(response,HttpStatus.OK);



    }

    private void doAuthenticate(String email, String password){
        UsernamePasswordAuthenticationToken authenticationToken =  new UsernamePasswordAuthenticationToken(email,password);
             try{

                 authenticationManager.authenticate(authenticationToken);
                 //authentication done . as username and password existing in the database .
             }catch(BadCredentialsException e){
                 throw new BadApiRequestException("Invalid Username or password exception");
                 //the user name and password is not correct , which is given by you
             }

        }


     // Modification Required Sign in , through Google account ,
     //You have to create a UI Client for this process , and writing as a backend developer i just tested my ability to sign in purpose if it goes to live
    //login with google api
    // as we dont know what is coming in the token so we use this because our map will store the object
    //in map key value
//    @PostMapping("/google")
//    @Operation(summary = "Google OAuth Login", description = "Endpoint for Google OAuth login", tags = {"Authentication Controller"})
//    public ResponseEntity<JwtResponse> getLoginWithGoogle(@RequestBody Map<String,Object> data) throws IOException {
//        //getting the id token from the request
//        String idToken = data.get("idToken").toString();
//
//        NetHttpTransport netHttpTransport = new NetHttpTransport();
//
//        //these classes used for Google Verifier
//        JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();
//
//        // we are passing the google client id
//        GoogleIdTokenVerifier.Builder verifier = new GoogleIdTokenVerifier.Builder(netHttpTransport, jacksonFactory).
//                                                    setAudience(Collections.singleton(googleClientId));
//
//        //
//        //now with the help of verifier we can give the idtoken to google  api /
//        //after verifiyinh the token we  get the googleIdToken
//        GoogleIdToken googleIdToken = GoogleIdToken.parse(verifier.getJsonFactory(),idToken);
//
//          // we give it to the api and they give us the payload
//        //now we have the token we can fetch the payload(data)
//        GoogleIdToken.Payload payload = googleIdToken.getPayload();
//        logger.info("{}",payload);
//
//        //now we have the payload we can fetch the emailid , and the details
//        String email = payload.getEmail();
//
//        User user = null;
//         user = userService.findUserByEmailOptional(email).orElse(null);
//        if (user == null){
//
//            //create a new user
//         user = this.saveUser(email ,data.get("name").toString() , data.get("photoUrl").toString());
//
//
//
//        }

         //now created jwt token
//          ResponseEntity<JwtResponse> jwtResponse = this.login(JwtRequest.builder().email(user.getEmail()).password(newPassword).build());
//            return jwtResponse;
//
//
//        //call google to verify the token
//
//
//
//    }

    private User saveUser(String email, String name, String photoUrl){

        UserDto newUser = UserDto.builder().name(name)
                .email(email)
                .password(newPassword)
                .imageName(photoUrl)
                .roles(new HashSet<>())
                .build();

        UserDto user = userService.createUser(newUser);
        return mapper.map(user, User.class);


    }
}

//  For any Query Contact : akashguptaworks@gmail.com or skyrisegupta@gmail.com