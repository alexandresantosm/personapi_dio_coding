package com.digitalinnovationone.personapi.mapper;

import com.digitalinnovationone.personapi.dto.request.PersonDTO;
import com.digitalinnovationone.personapi.dto.request.PhoneDTO;
import com.digitalinnovationone.personapi.entity.Person;
import com.digitalinnovationone.personapi.entity.Phone;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.digitalinnovationone.personapi.utils.PersonUtils.createFakePersonDTO;
import static com.digitalinnovationone.personapi.utils.PersonUtils.createFakePersonEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonMapperTest {

    @Mock
    private PersonMapper personMapper;

    @Test
    void testGivenPersonDTOThenReturnPersonEntity() {
        PersonDTO personDTO = createFakePersonDTO();
        Person expectedPerson = createFakePersonEntity();

        when(personMapper.toModel(personDTO)).thenReturn(expectedPerson);

        assertEquals(personDTO.getFirstName(), expectedPerson.getFirstName());
        assertEquals(personDTO.getLastName(), expectedPerson.getLastName());
        assertEquals(personDTO.getCpf(), expectedPerson.getCpf());

        PhoneDTO phoneDTO = personDTO.getPhones().get(0);
        Phone phone = expectedPerson.getPhones().get(0);

        assertEquals(phoneDTO.getType(), phone.getType());
        assertEquals(phoneDTO.getNumber(), phone.getNumber());
    }

    @Test
    void testGivenPersonEntityThenReturnPersonDTO() {
        Person person = createFakePersonEntity();
        PersonDTO expectedPersonDTO = createFakePersonDTO();

        when(personMapper.toDTO(person)).thenReturn(expectedPersonDTO);

        assertEquals(person.getFirstName(), expectedPersonDTO.getFirstName());
        assertEquals(person.getLastName(), expectedPersonDTO.getLastName());
        assertEquals(person.getCpf(), expectedPersonDTO.getCpf());

        Phone phone = person.getPhones().get(0);
        PhoneDTO phoneDTO = expectedPersonDTO.getPhones().get(0);

        assertEquals(phone.getType(), phoneDTO.getType());
        assertEquals(phone.getNumber(), phoneDTO.getNumber());
    }
}
