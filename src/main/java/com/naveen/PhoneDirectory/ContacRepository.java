package com.naveen.PhoneDirectory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.naveen.PhoneDirectory.dao.ContactsDao;

public interface ContacRepository extends JpaRepository<ContactsDao, Integer>{

	List<ContactsDao> findAllByFirstName(String name);

	List<ContactsDao> findAllByLastName(String name);

//	ContactsDao findAllByFirstNameOrLastName(String name);

}
