package com.spring.kanbanboard.Service;


import com.spring.kanbanboard.modals.Person;
import com.spring.kanbanboard.repositories.PersonRepository;
import com.spring.kanbanboard.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public PersonDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Person> person = personRepository.findByUsername(s);
        if(person.isEmpty())
            throw new UsernameNotFoundException("user not found");

        return new PersonDetails(person.get());
    }

    public Optional<Person> findByUsername(String name){
        return personRepository.findByUsername(name);
    }
    public Person findById(int id){
        return personRepository.findById(id);
    }
    public String getPrincipalName() throws Exception{

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated()) {
            return auth.getName();
        } else {
            throw new Exception("Not authenticated");
        }

    }

}
