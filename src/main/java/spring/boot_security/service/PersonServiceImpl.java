package spring.boot_security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.boot_security.model.Person;
import spring.boot_security.repository.PersonRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;


@Service
public class PersonServiceImpl implements PersonService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository, @Lazy BCryptPasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional(readOnly = true)
    @Override
    public List<Person> listUsers() {
        return personRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Person getUser(long id) {
        return personRepository.findById(id).get();
    }

    @Transactional
    @Override
    public void deleteUser(long id) {
        personRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void saveUser(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        personRepository.save(person);
    }
}


