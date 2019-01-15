package com.lardi.dao;

import com.lardi.entities.Contact;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Integer> {

    @Query("SELECT c FROM Contact c WHERE c.user.id = :id")
    List<Contact> findAllContactsByUserId(@Param("id") Integer id);

    // if userId != null
    @Query(("SELECT c FROM Contact c WHERE c.user.id = :id AND c.firstName LIKE concat('%',:firstName,'%') AND c.lastName LIKE concat('%',:lastName,'%') AND c.phoneNumberMobile LIKE concat('%',:phone,'%')"))
    List<Contact> findAllContactsBySearchParametersWithId(@Param("firstName") String firstName,
                                                          @Param("lastName") String lastName,
                                                          @Param("phone") String phoneNumberMobile,
                                                          @Param("id") Integer userId);

    // if userId == null
    @Query(("SELECT c FROM Contact c WHERE c.firstName LIKE concat('%',:firstName,'%') AND c.lastName LIKE concat('%',:lastName,'%') AND c.phoneNumberMobile LIKE concat('%',:phone,'%')"))
    List<Contact> findAllContactsBySearchParameters(@Param("firstName") String firstName,
                                                    @Param("lastName") String lastName,
                                                    @Param("phone") String phoneNumberMobile);
}
