package com.spring.kanbanboard.controllers;

import com.spring.kanbanboard.Service.NoteService;
import com.spring.kanbanboard.Service.PersonDetailsService;
import com.spring.kanbanboard.Service.RegistrationService;
import com.spring.kanbanboard.Service.TableService;
import com.spring.kanbanboard.modals.Note;
import com.spring.kanbanboard.modals.Person;
import com.spring.kanbanboard.modals.Tables;
import com.spring.kanbanboard.repositories.NoteRepository;
import com.spring.kanbanboard.repositories.PersonRepository;
import com.spring.kanbanboard.repositories.TableRepository;
import com.spring.kanbanboard.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final PersonValidator personValidator;
    private final RegistrationService registrationService;
    private final PersonDetailsService personDetailsService;
    private final TableService tableService;
    private final NoteService noteService;

    @Autowired
    public AuthController(PersonValidator personValidator, RegistrationService registrationService, PersonDetailsService personDetailsService, TableService tableService,  NoteService noteService) {
        this.personValidator = personValidator;
        this.registrationService = registrationService;
        this.personDetailsService = personDetailsService;

        this.tableService = tableService;
        this.noteService = noteService;
    }

    @GetMapping("/signin")
    public String sign(){
        return "signIn";
    }

    @GetMapping("/login")
    public String registrationPage(@ModelAttribute("person")Person person) {
        return "LogIn";
    }

    @PostMapping("/login")
    public String performRegistration(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person, bindingResult);

        if(bindingResult.hasErrors()){
            return "LogIn";
        }
        registrationService.register(person);
        return "redirect:/auth/signin";
    }

    @GetMapping("/hello")
    public String hello(Model model)  throws Exception {
        TreeMap <Tables, List<Note>> map = new TreeMap<>();
        Optional<Person> person = personDetailsService.findByUsername(personDetailsService.getPrincipalName());
        Person person1 = personDetailsService.findById(person.get().getId());
        List<Tables> tables = tableService.findTablesByPersonId(person1);
        List<Note> note = null;
        for (int i = 0; i < tables.size(); i++) {
            note = noteService.findNotesByTableId(tables.get(i));
            map.put(tables.get(i), note);
        }
        model.addAttribute("tableName1", map);
        return "kanban";
    }
}
