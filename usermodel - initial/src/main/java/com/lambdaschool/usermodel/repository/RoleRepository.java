package com.lambdaschool.usermodel.repository;

import com.lambdaschool.usermodel.models.Role;
import com.lambdaschool.usermodel.views.JustTheCount;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RoleRepository extends CrudRepository<Role, Long>
{
    Role findByNameIgnoreCase(String name);

    @Query(value = "SELECT COUNT(*) as count FROM userroles WHERE userid = :userid AND roleid = :roleid",
            nativeQuery = true)
    JustTheCount checkUserRolesCombo(long userid,
                                     long roleid);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO UserRoles(userid, roleid) VALUES (:userid, :roldeid)",
           nativeQuery = true)
    void insertUserRoles(long userid,
                         long roleid);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM UserRoles WHERE userid = :userid AND roleid = :roleid")
    void deleteUserRoles(long userid,
                         long roleid);
}
