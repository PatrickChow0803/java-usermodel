package com.lambdaschool.usermodel.models;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role extends Auditable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long roleid;

    @Column(nullable = false,
            unique = true)
    private String name;

    public Role()
    {
    }

    public Role(String name)
    {
        this.name = name.toUpperCase();
    }

    public long getRoleid()
    {
        return roleid;
    }

    public void setRoleid(long roleid)
    {
        this.roleid = roleid;
    }

    public String getName()
    {
        if (name == null)
        {
            return null;
        } else
        {
            return name.toUpperCase();
        }
    }

    public void setName(String name)
    {
        this.name = name.toUpperCase();
    }
}
