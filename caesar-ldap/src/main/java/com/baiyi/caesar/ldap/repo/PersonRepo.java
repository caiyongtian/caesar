package com.baiyi.caesar.ldap.repo;

import com.baiyi.caesar.ldap.entry.Person;

import java.util.List;

/**
 * @Author baiyi
 * @Date 2019/12/27 4:16 下午
 * @Version 1.0
 */
public interface PersonRepo {

    List<String> getAllPersonNames();

    List<Person> getPersonList();

    Person findPersonWithDn(String dn);

    Boolean create(Person person);

    Boolean update(Person person);

    Boolean delete(String username);

    Boolean checkPersonInLdap(String username);

    List<String> searchUserGroupByUsername(String username);

}
