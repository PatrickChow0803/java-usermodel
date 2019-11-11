package com.lambdaschool.usermodel.models;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass // Wont go into the database but the fields that have been extended will.
@EntityListeners(AuditingEntityListener.class) // When something happens that impacts one of these fields,
                                              // this will does what ever it's supposed to do.
abstract class Auditable {

    @CreatedBy
    protected String createdby;

    protected Date createddate;

    @LastModifiedBy
    protected String lastmodifiedby;

    protected Date lastmodifieddate;
}
