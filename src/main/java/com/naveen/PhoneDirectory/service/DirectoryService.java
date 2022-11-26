package com.naveen.PhoneDirectory.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naveen.PhoneDirectory.dao.Contact;
import com.naveen.PhoneDirectory.dao.Phone;
import com.naveen.PhoneDirectory.model.ContactDto;
import com.naveen.PhoneDirectory.model.PhoneDto;
import com.naveen.PhoneDirectory.repository.ContacRepository;
import com.naveen.PhoneDirectory.repository.PhoneRepository;

@Service
public class DirectoryService {

	private static final Logger logger = LoggerFactory.getLogger(DirectoryService.class);

	@Autowired
	ContacRepository contacRepository;

	@Autowired
	PhoneRepository phoneRepository;

	public List<ContactDto> getAllContacts() {

		List<Contact> contacts = contacRepository.findAll();

		List<ContactDto> contactsDto = getAllContactsHelper(contacts);

		return contactsDto;
	}

	private List<ContactDto> getAllContactsHelper(List<Contact> contacts) {
		List<ContactDto> contactsDto = new ArrayList<ContactDto>();

		for (Contact contact : contacts) {
			ContactDto c = new ContactDto();

			c.setCompany(contact.getCompany());
			c.setEmailAddress(contact.getEmailAddress());
			c.setFirstName(contact.getFirstName());
			c.setLastName(contact.getLastName());
			List<PhoneDto> phonesDto = new ArrayList<PhoneDto>();
			PhoneDto pDto = new PhoneDto();

			List<Phone> phones = phoneRepository.findAllByContactId(contact.getId());
			for (Phone p : phones) {
				pDto.setPhoneNumber(p.getPhoneNumber());
				phonesDto.add(pDto);
			}

			c.setPhoneNumbers(phonesDto);
			contactsDto.add(c);
		}
		return contactsDto;
	}

	public List<ContactDto> getAllContactsByName(String name) {

		List<Contact> contactResponse = contacRepository.findAllByFirstName(name);
		contactResponse.addAll(contacRepository.findAllByLastName(name));

		List<ContactDto> contactsDto = getAllContactsHelper(contactResponse);

		return contactsDto;
	}

	public ContactDto getContactById(Integer id) {

		Contact c = contacRepository.findById(id).orElse(null);

		List<Contact> contactResponse = new ArrayList<Contact>();
		contactResponse.add(c);
		List<ContactDto> contactsDto = getAllContactsHelper(contactResponse);

		return contactsDto.get(0);

	}

	public List<Contact> deleteContactById(Integer id) {
		try {

			Contact c = contacRepository.findById(id).orElse(null);
			if (c != null) {
				List<Phone> phoneList = phoneRepository.findAllByContactId(c.getId());
				if (phoneList.size() > 0) {
					for (Phone ph : phoneList) {
						if (ph != null) {
							phoneRepository.delete(ph);

						}
					}
				}
				contacRepository.deleteById(id);

			} else {
				throw new NullPointerException();
			}

		} catch (NullPointerException e) {
			logger.error("No record found for given Id = " + id, e);

		}

		return contacRepository.findAll();
	}

	public Contact updateContact(ContactDto contactDto, Integer id) {

		Contact contact = new Contact();
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

			addContactHelper(contactRequest);

		} catch (Exception e) {
			logger.error("Exception occured while adding the contact. Details: {}", e.getMessage(), e);
		}

		return contactRequest;
	}

	private void addContactHelper(ContactDto contactRequest) {
		Contact contactDao = new Contact();

		BeanUtils.copyProperties(contactRequest, contactDao);
		Contact contactResponse = contacRepository.saveAndFlush(contactDao);

		List<PhoneDto> phoneNumbers = contactRequest.getPhoneNumbers();

		for (PhoneDto p : phoneNumbers) {

			Phone phone = new Phone();
			phone.setPhoneNumber(p.getPhoneNumber());
			phone.setContactId(contactResponse.getId());

			phoneRepository.saveAndFlush(phone);
		}
	}

}
