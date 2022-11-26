package com.naveen.PhoneDirectory.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naveen.PhoneDirectory.ContacRepository;
import com.naveen.PhoneDirectory.dao.ContactsDao;
import com.naveen.PhoneDirectory.model.ContactDto;

@Service
public class DirectoryService {

	private static final Logger logger = LoggerFactory.getLogger(DirectoryService.class);

	@Autowired
	ContacRepository contacRepository;

	public List<ContactsDao> getAllContacts() {
		List<ContactsDao> contacts = contacRepository.findAll();
		return contacts;
	}

	public List<ContactsDao> getAllContactsByName(String name) {

		List<ContactsDao> contactResponse = contacRepository.findAllByFirstName(name);
		contactResponse.addAll(contacRepository.findAllByLastName(name));

		return contactResponse;
	}

	public ContactsDao getContactById(Integer id) {

		ContactsDao c = contacRepository.findById(id).orElse(null);
		return c;

	}

	public List<ContactsDao> deleteContactById(Integer id) {
		try {
			ContactsDao c = contacRepository.findById(id).orElse(null);
			if (c != null)
				contacRepository.deleteById(id);
			else
				throw new NullPointerException();

		} catch (NullPointerException e) {
			logger.error("No record found for given Id = " + id, e);

		}

		return contacRepository.findAll();
	}

	public ContactsDao updateContact(ContactDto contactDto, Integer id) {

		ContactsDao contact = new ContactsDao();
		try {
			contact = contacRepository.findById(id).orElse(null);
			if (contact != null) {

				BeanUtils.copyProperties(contactDto, contact);

				contacRepository.saveAndFlush(contact);
			} else {
				throw new NullPointerException();
			}

		} catch (NullPointerException e) {
			logger.error("No record found for given Id = " + id, e);

		}

		return contact;
	}

	public ContactDto addContact(ContactDto contactRequest) {

		try {

			ContactsDao contactDao = new ContactsDao();

			BeanUtils.copyProperties(contactRequest, contactDao);

			contacRepository.saveAndFlush(contactDao);
		} catch (Exception e) {
			logger.error("Exception occured while fetching the contatcs. Details: {}", e.getMessage(), e);
		}

		return contactRequest;
	}

	public List<ContactsDao> deleteAllContacts() {
		contacRepository.deleteAll();

		return contacRepository.findAll();
	}
}
