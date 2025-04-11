package org.project.services;

import net.datafaker.Faker;
import org.project.models.PersonDto;
import org.project.enums.AdditionalFields;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;

@Service
public class FakeDataService {

    public List<PersonDto> generatePeople(int entriesQty, Locale locale, EnumSet<AdditionalFields> additionalFields) {
        List<PersonDto> people = new ArrayList<>();

        Faker faker = new Faker(locale);

        for (int i = 0; i < entriesQty; i++) {
            PersonDto person = new PersonDto();

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

            people.add(person);
        }

        return people;
    }
}
