package org.project.services;

import net.datafaker.Faker;
import org.project.dto.PersonDto;
import org.project.entities.Person;
import org.project.enums.AdditionalFields;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FakeDataService {

    public List<PersonDto> generatePeople(int entriesQty, Locale locale, EnumSet<AdditionalFields> additionalFields) {
        List<PersonDto> people = new ArrayList<>();

        Faker faker = new Faker(locale);

        for (int i = 0; i < entriesQty; i++) {
            Person person = generatePerson(faker, additionalFields);
            PersonDto personDto = convertToDto(person);
            people.add(personDto);
        }

        return people;
    }

    private Person generatePerson(Faker faker, EnumSet<AdditionalFields> additionalFields) {
        Person person = new Person();

        // Setting basic fields
        person.setName(faker.name().firstName());
        person.setSurname(faker.name().lastName());
        person.setDateOfBirth(faker.timeAndDate().birthday(17, 25, "dd-MM-yyyy"));

        // Setting additional fields(if exist)
        if (additionalFields.contains(AdditionalFields.ADDRESS)) {
            person.setAddress(faker.address().fullAddress());
        }
        if (additionalFields.contains(AdditionalFields.UNIVERSITY_NAME)) {
            person.setUniversityName(faker.university().name());
        }
        if (additionalFields.contains(AdditionalFields.COUNTRY_OF_ORIGIN)) {
            person.setCountryOfOrigin(faker.country().name());
        }
        if (additionalFields.contains(AdditionalFields.CREDIT_CARD_NUMBER)) {
            person.setCreditCardNumber(faker.finance().creditCard());
        }
        if (additionalFields.contains(AdditionalFields.FAVOURITE_COLOR)) {
            person.setFavouriteColor(faker.color().name());
        }
        if (additionalFields.contains(AdditionalFields.HOBBY)) {
            person.setHobby(faker.hobby().activity());
        }
        if (additionalFields.contains(AdditionalFields.HEIGHT)) {
            person.setHeight(faker.number().numberBetween(150, 210) + " cm");
        }
        if (additionalFields.contains(AdditionalFields.ILLNESS)) {
            person.setIllness(faker.disease().anyDisease());
        }
        if (additionalFields.contains(AdditionalFields.BLOOD_TYPE)) {
            person.setBloodType(faker.bloodtype().bloodGroup());
        }

        return person;
    }


    // Helper
    private PersonDto convertToDto(Person person) {
        PersonDto personDto = new PersonDto();
        Map<String, String> fieldsMap = personDto.getFieldsMap();

        fieldsMap.put("name", person.getName());
        fieldsMap.put("surname", person.getSurname());
        fieldsMap.put("dateOfBirth", person.getDateOfBirth());

        if (person.getAddress() != null) {
            fieldsMap.put("address", person.getAddress());
        }
        if (person.getUniversityName() != null) {
            fieldsMap.put("universityName", person.getUniversityName());
        }
        if (person.getCountryOfOrigin() != null) {
            fieldsMap.put("countryOfOrigin", person.getCountryOfOrigin());
        }
        if (person.getCreditCardNumber() != null) {
            fieldsMap.put("creditCardNumber", person.getCreditCardNumber());
        }
        if (person.getFavouriteColor() != null) {
            fieldsMap.put("favouriteColor", person.getFavouriteColor());
        }
        if (person.getHobby() != null) {
            fieldsMap.put("hobby", person.getHobby());
        }
        if (person.getHeight() != null) {
            fieldsMap.put("height", person.getHeight());
        }
        if (person.getIllness() != null) {
            fieldsMap.put("illness", person.getIllness());
        }
        if (person.getBloodType() != null) {
            fieldsMap.put("bloodType", person.getBloodType());
        }

        return personDto;
    }
}
