package com.naveen.PhoneDirectory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.naveen.PhoneDirectory.dao.Phone;

public interface PhoneRepository extends JpaRepository<Phone, Integer> {

	List<Phone> findAllByContactId(Integer id);

}
