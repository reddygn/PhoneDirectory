package com.naveen.PhoneDirectory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.naveen.PhoneDirectory.dao.Contact;

public interface ContacRepository extends JpaRepository<Contact, Integer>{

	List<Contact> findAllByFirstName(String name);

	List<Contact> findAllByLastName(String name);

//	ContactsDao findAllByFirstNameOrLastName(String name);

}
