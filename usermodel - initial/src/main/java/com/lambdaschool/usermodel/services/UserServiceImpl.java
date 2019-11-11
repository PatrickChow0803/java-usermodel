package com.lambdaschool.usermodel.services;

import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.models.Useremail;
import com.lambdaschool.usermodel.repository.RoleRepository;
import com.lambdaschool.usermodel.repository.UserRepository;
import com.lambdaschool.usermodel.views.UserNameCountEmails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "userService")
public class UserServiceImpl implements UserService
{

    @Autowired
    private UserRepository userrepos;

    @Autowired
    private RoleRepository rolerepos;

    public User findUserById(long id) throws EntityNotFoundException
    {
        return userrepos.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("User id " + id + " not found!"));
    }

    @Override
    public List<User> findByNameContaining(String username)
    {
        return userrepos.findByUsernameContainingIgnoreCase(username.toLowerCase());
    }

    @Override
    public List<User> findAll()
    {
        List<User> list = new ArrayList<>();
        userrepos.findAll()
                 .iterator()
                 .forEachRemaining(list::add);
        return list;
    }

    @Transactional
    @Override
    public void delete(long id)
    {
        userrepos.findById(id)
                 .orElseThrow(() -> new EntityNotFoundException("User id " + id + " not found!"));
        userrepos.deleteById(id);
    }

    @Override
    public User findByName(String name)
    {
        User uu = userrepos.findByUsername(name.toLowerCase());
        if (uu == null)
        {
            throw new EntityNotFoundException("User name " + name + " not found!");
        }
        return uu;
    }

    @Transactional
    @Override
    public User save(User user)
    {
        if (userrepos.findByUsername(user.getUsername()
                                         .toLowerCase()) != null)
        {
            throw new EntityNotFoundException(user.getUsername() + " is already taken!");
        }

        User newUser = new User();
        newUser.setUsername(user.getUsername()
                                .toLowerCase());
        newUser.setPassword(user.getPassword());
        newUser.setPrimaryemail(user.getPrimaryemail()
                                    .toLowerCase());

        for (Useremail ue : user.getUseremails())
        {
            newUser.getUseremails()
                   .add(new Useremail(newUser,
                                      ue.getUseremail()));
        }

        return userrepos.save(newUser);
    }

    @Transactional
    @Override
    public User update(User user,
                       long id)
    {

        User currentUser = findUserById(id);

        if (user.getUsername() != null)
        {
            currentUser.setUsername(user.getUsername()
                                        .toLowerCase());
        }

        if (user.getPassword() != null)
        {
            currentUser.setPassword(user.getPassword());
        }

        if (user.getPrimaryemail() != null)
        {
            currentUser.setPrimaryemail(user.getPrimaryemail()
                                            .toLowerCase());
        }

        if (user.getUseremails()
                .size() > 0)
        {
            for (Useremail ue : user.getUseremails())
            {
                currentUser.getUseremails()
                           .add(new Useremail(currentUser,
                                              ue.getUseremail()));
            }
        }

        return userrepos.save(currentUser);
    }

    @Override
    public List<UserNameCountEmails> getCountUserEmails() {
        return userrepos.getCountUserEmails();
    }

    @Override
    public void deleteUserRole(long userid, long roleid) {
        userrepos.findById(userid)
                .orElseThrow(() -> new EntityNotFoundException("User id " + userid + " not found!"));
        rolerepos.findById(roleid)
                .orElseThrow(() -> new EntityNotFoundException("Role id " + roleid + " not found!"));
        if (rolerepos.checkUserRolesCombo(userid,
                roleid)
                .getCount() > 0)
        {
            rolerepos.deleteUserRoles(userid,
                    roleid);
        } else
        {
            throw new EntityNotFoundException("Role and User Combination Does Not Exists");
        }
    }

    @Transactional
    @Override
    public void addUserRole(long userid,
                            long roleid)
    {
        userrepos.findById(userid)
                .orElseThrow(() -> new EntityNotFoundException("User id " + userid + " not found!"));
        rolerepos.findById(roleid)
                .orElseThrow(() -> new EntityNotFoundException("Role id " + roleid + " not found!"));
        if (rolerepos.checkUserRolesCombo(userid,
                roleid)
                .getCount() <= 0)
        {
            rolerepos.insertUserRoles(userid,
                    roleid);
        } else
        {
            throw new EntityNotFoundException("Role and User Combination Already Exists");
        }
    }



}
