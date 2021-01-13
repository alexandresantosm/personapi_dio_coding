package com.digitalinnovationone.personapi.services;

import com.digitalinnovationone.personapi.dto.request.PersonDTO;
import com.digitalinnovationone.personapi.dto.response.MessageResponseDTO;
import com.digitalinnovationone.personapi.entity.Person;
import com.digitalinnovationone.personapi.exception.PersonNotFoundException;
import com.digitalinnovationone.personapi.mapper.PersonMapper;
import com.digitalinnovationone.personapi.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.digitalinnovationone.personapi.utils.PersonUtils.createFakePersonDTO;
import static com.digitalinnovationone.personapi.utils.PersonUtils.createFakePersonEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PersonMapper personMapper;

    @InjectMocks
    private PersonService personService;

    @Test
    void testGivenPersonDTOThenReturnSuccessSavedMessage() {
        PersonDTO personDTO = createFakePersonDTO() ;
        Person expectedSavedPerson = createFakePersonEntity();

        lenient().when(personMapper.toModel(personDTO)).thenReturn(expectedSavedPerson);
        when(personRepository.save(any(Person.class))).thenReturn(expectedSavedPerson);

        MessageResponseDTO successMessage = personService.createPerson(personDTO);

        assertEquals("Created person with ID 1", successMessage.getMessage());
    }

    @Test
    void TestGivenValidPersonIdThenReturnThisPerson() throws PersonNotFoundException {
        var expectedValidId = 1L;
        PersonDTO expectedPersonDTO = createFakePersonDTO();
        expectedPersonDTO.setId(expectedValidId);
        Person expectedSavedPerson = createFakePersonEntity();
        expectedPersonDTO.setId(expectedSavedPerson.getId());

        lenient().when(personRepository.findById(expectedSavedPerson.getId())).thenReturn(Optional.of(expectedSavedPerson));
        lenient().when(personMapper.toDTO(expectedSavedPerson)).thenReturn(expectedPersonDTO);

        PersonDTO personDTO = personService.findById(expectedSavedPerson.getId());

        assertEquals(expectedPersonDTO.getFirstName(), personDTO.getFirstName());
        assertEquals(expectedPersonDTO.getLastName(), personDTO.getLastName());
        assertEquals(expectedPersonDTO.getCpf(), personDTO.getCpf());
    }

    @Test
    void testGivenInvalidPersonIdThenThrowException() {
        var invalidPersonId = 1L;

        when(personRepository.findById(invalidPersonId))
                .thenReturn(Optional.ofNullable(any(Person.class)));

        assertThrows(PersonNotFoundException.class, () -> personService.findById(invalidPersonId));
    }

    @Test
    void testGivenNoDataThenReturnAllPeopleRegistered() {
        List<Person> expectedRegisteredPeople = Collections.singletonList(createFakePersonEntity());
        PersonDTO personDTO = createFakePersonDTO();

        when(personRepository.findAll()).thenReturn(expectedRegisteredPeople);
        when(personMapper.toDTO(any(Person.class))).thenReturn(personDTO);

        List<PersonDTO> expectedPeopleDTOList = personService.listAll();

        assertFalse(expectedPeopleDTOList.isEmpty());
        assertEquals(expectedPeopleDTOList.get(0).getId(), personDTO.getId());
    }

    @Test
    void testGivenValidPersonIdAndUpdateInfoThenReturnSuccesOnUpdate() throws PersonNotFoundException {
        var updatePersonId = 2L;
        PersonDTO updatePersonDTO = createFakePersonDTO();
        updatePersonDTO.setId(updatePersonId);
        updatePersonDTO.setLastName("Peleias updated");

        Person expectedPersonUpdate = createFakePersonEntity();
        expectedPersonUpdate.setId(updatePersonId);
        expectedPersonUpdate.setLastName(updatePersonDTO.getLastName());

        when(personRepository.findById(updatePersonId)).thenReturn(Optional.of(expectedPersonUpdate));
        lenient().when(personMapper.toModel(updatePersonDTO)).thenReturn(expectedPersonUpdate);
        when(personRepository.save(any(Person.class))).thenReturn(expectedPersonUpdate);

        MessageResponseDTO successMessage = personService.updateById(updatePersonId, updatePersonDTO);

        assertEquals("Updated person with ID 2", successMessage.getMessage());
    }

    @Test
    void testGivenInvalidPersonIdAndUpdateInfoThenThrowExceptionOnUpdate() throws PersonNotFoundException {
        var invalidPersonId = 1L;

        PersonDTO updatePersonDTO = createFakePersonDTO();
        updatePersonDTO.setId(invalidPersonId);
        updatePersonDTO.setLastName("Peleias updated");

        when(personRepository.findById(invalidPersonId))
                .thenReturn(Optional.ofNullable(any(Person.class)));

        assertThrows(PersonNotFoundException.class, () -> personService.updateById(invalidPersonId, updatePersonDTO));
    }

    @Test
    void testGivenValidPersonIdThenReturnSuccesOnDelete() throws PersonNotFoundException {
        var deletedPersonId = 1L;
        Person expectedPersonToDelete = createFakePersonEntity();

        when(personRepository.findById(deletedPersonId)).thenReturn(Optional.of(expectedPersonToDelete));
        personService.deleteById(deletedPersonId);

        verify(personRepository, times(1)).deleteById(deletedPersonId);
    }

    @Test
    void testGivenInvalidPersonIdThenReturnSuccesOnDelete() throws PersonNotFoundException {
        var invalidPersonId = 1L;

        when(personRepository.findById(invalidPersonId))
                .thenReturn(Optional.ofNullable(any(Person.class)));

        assertThrows(PersonNotFoundException.class, () -> personService.deleteById(invalidPersonId));
    }
}
