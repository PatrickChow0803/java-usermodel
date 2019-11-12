package com.lambdaschool.usermodel.controllers;

import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController
{

    @Autowired
    private UserService userService;

    // http://localhost:2019/users/users
    @GetMapping(value = "/users",
                produces = {"application/json"})
    public ResponseEntity<?> listAllUsers()
    {
        List<User> myUsers = userService.findAll();
        return new ResponseEntity<>(myUsers,
                                    HttpStatus.OK);
    }


    // http://localhost:2019/users/user/7
    @GetMapping(value = "/user/{userId}",
                produces = {"application/json"})
    public ResponseEntity<?> getUserById(
            @PathVariable
                    Long userId)
    {
        User u = userService.findUserById(userId);
        return new ResponseEntity<>(u,
                                    HttpStatus.OK);
    }

    // http://localhost:2019/users/user/name/cinnamon
    @GetMapping(value = "/user/name/{userName}",
                produces = {"application/json"})
    public ResponseEntity<?> getUserByName(
            @PathVariable
                    String userName)
    {
        User u = userService.findByName(userName);
        return new ResponseEntity<>(u,
                                    HttpStatus.OK);
    }

    // http://localhost:2019/users/user/name/like/da
    @GetMapping(value = "/user/name/like/{userName}",
                produces = {"application/json"})
    public ResponseEntity<?> getUserLikeName(
            @PathVariable
                    String userName)
    {
        List<User> u = userService.findByNameContaining(userName);
        return new ResponseEntity<>(u,
                                    HttpStatus.OK);
    }

    // http://localhost:2019/users/user
    /*
            {
                "username": "Mojo",
                "primaryemail": "mojo@lambdaschool.local",
                "password" : "Coffee123",
                "useremails": [
                    {
                        "useremail": "mojo@mymail.local"
                    },
                    {
                        "useremail": "mojo@email.local"
                    }
                    ],
                "userroles": [
                    {
                       "role": {
                           "roleid": 1
                        }
                    },
                    {
                       "role": {
                           "roleid": 2
                       }
                    }
                    ]
            }
     */
    @PostMapping(value = "/user",
                 consumes = {"application/json"})
    public ResponseEntity<?> addNewUser(@Valid
                                        @RequestBody
                                                User newuser) throws URISyntaxException
    {
        newuser = userService.save(newuser);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder.fromCurrentRequest()
                                                    .path("/{userid}")
                                                    .buildAndExpand(newuser.getUserid())
                                                    .toUri();
        responseHeaders.setLocation(newUserURI);

        return new ResponseEntity<>(null,
                                    responseHeaders,
                                    HttpStatus.CREATED);
    }


    // http://localhost:2019/users/user/7
/*

        {
            "username": "cinabun",
            "primaryemail": "cinabun@lambdaschool.home",
            "useremails": [
            {
                    "useremail": "cinnamon@mymail.home"
            },
            {
                    "useremail": "hops@mymail.home"
            },
            {
                    "useremail": "bunny@email.home"
            }
            ]
        }

*/
    @PutMapping(value = "/user/{id}",
                consumes = {"application/json"})
    public ResponseEntity<?> updateUser(
            @RequestBody
                    User updateUser,
            @PathVariable
                    long id)
    {
        userService.update(updateUser,
                           id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    // http://localhost:2019/users/user/14
    @DeleteMapping(value = "/user/{id}")
    public ResponseEntity<?> deleteUserById(
            @PathVariable
                    long id)
    {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    // http://localhost:2019/users/user/7/role/2
    @DeleteMapping(value = "/user/{userid}/role/{roleid}")
    public ResponseEntity<?> deleteUserRoleByIds(
            @PathVariable
                    long userid,
            @PathVariable
                    long roleid)
    {
        userService.deleteUserRole(userid,
                                   roleid);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    // http://localhost:2019/users/user/15/role/2
    @PostMapping(value = "/user/{userid}/role/{roleid}")
    public ResponseEntity<?> postUserRoleByIds(
            @PathVariable
                    long userid,
            @PathVariable
                    long roleid)
    {
        userService.addUserRole(userid,
                                roleid);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // http://localhost:2019/users/user/email/count
    @GetMapping(value = "/user/email/count",
                produces = {"application/json"})
    public ResponseEntity<?> getNumUserEmails()
    {
        return new ResponseEntity<>(userService.getCountUserEmails(),
                                    HttpStatus.OK);
    }
}