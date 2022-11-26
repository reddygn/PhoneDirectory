package com.naveen.PhoneDirectory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naveen.PhoneDirectory.dao.ContactsDao;
import com.naveen.PhoneDirectory.model.ContactDto;
import com.naveen.PhoneDirectory.service.DirectoryService;

@RestController
@RequestMapping(value = "/api/contact")
public class DirectoryController {

	@Autowired
	DirectoryService directoryService;

	// 1. Get All Contacts
	@GetMapping
	public List<ContactsDao> getAllContacts() {
		return directoryService.getAllContacts();
	}

	// 2. Get All Contacts By Name
	@GetMapping(value = "/{name}")
	public List<ContactsDao> getAllContactsByName(@PathVariable String name) {
		return directoryService.getAllContactsByName(name);
	}

	// 3. Get A Specific Contact By Id
	@GetMapping(value = "/id/{id}")
	public ContactsDao getContactById(@PathVariable("id") Integer id) {
		return directoryService.getContactById(id);
	}

	// 4. Delete A Contact By Id
	@DeleteMapping(value = "/{id}")
	public List<ContactsDao> deleteContactById(@PathVariable Integer id) {
		return directoryService.deleteContactById(id);
	}

	// 5. Update A Contact
	@PutMapping(value = "/{id}")
	public ContactsDao updateContact(@org.springframework.web.bind.annotation.RequestBody ContactDto contactDto,
			@PathVariable Integer id) {
		return directoryService.updateContact(contactDto, id);
	}

	// 6. Add A Contact
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ContactDto addContact(@org.springframework.web.bind.annotation.RequestBody ContactDto contactRequest) {

		return directoryService.addContact(contactRequest);
	}

	// 7. Delete All Contacts
	@DeleteMapping
	public List<ContactsDao> deleteAllContacts() {
		return directoryService.deleteAllContacts();
	}

}
