package com.spring.kanbanboard.controllers;

import com.spring.kanbanboard.Service.*;
import com.spring.kanbanboard.modals.*;
import com.spring.kanbanboard.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Controller
public class MainController {
    private final TableRepository tableRepository;
    private final NoteRepository noteRepository;
    private final CommentService commentService;
    private final TrashService trashService;
    private final NoteService noteService;
    private final PersonDetailsService personDetailsService;
    private final PersonRepository personRepository;
    private final TableService tableService;
    private final TrashRepository trashRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public MainController(TableRepository tableRepository, NoteRepository noteRepository, CommentService commentService, TrashService trashService, NoteService noteService, PersonDetailsService personDetailsService, PersonRepository personRepository, TableService tableService, TrashRepository trashRepository, CommentRepository commentRepository) {
        this.tableRepository = tableRepository;
        this.noteRepository = noteRepository;
        this.commentService = commentService;
        this.trashService = trashService;
        this.noteService = noteService;
        this.personDetailsService = personDetailsService;
        this.personRepository = personRepository;
        this.tableService = tableService;
        this.trashRepository = trashRepository;
        this.commentRepository = commentRepository;
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/newTable")
    public String newTable() {
        return "NewTable";
    }

    @GetMapping("/settings")
    public String settings(Model model) throws Exception {
        Optional<Person> person = personDetailsService.findByUsername(personDetailsService.getPrincipalName());
        Person person1 = personDetailsService.findById(person.get().getId());
//        Optional<Person> person = personRepository.findByUsername(personDetailsService.getPrincipalName());
//        Person person1 = personRepository.getById(person.get().getId());

//        Person person1 = personRepository.findAll().get((person.get().getId()) - 1);

        model.addAttribute("personInfo", person1);
        return "settings";
    }

    @GetMapping("/updateNote")
    public String updateNote(Model model, @RequestParam int updateNote) {
        Optional<Note> note = noteService.findById(updateNote);
//        Optional<Note> note = noteRepository.findById(updateNote);
        model.addAttribute("update", note);
        return "updateNote";
    }

    @PostMapping("/infoTable")
    public String deleteTable(Model model, @RequestParam int deleteTable) throws Exception {
//        Optional<Person> person = personRepository.findByUsername(personDetailsService.getPrincipalName());
//        Person person1 = personRepository.getById(person.get().getId());
        Optional<Person> person = personDetailsService.findByUsername(personDetailsService.getPrincipalName());
        Person person1 = personDetailsService.findById(person.get().getId());
//        Person person1 = personRepository.findAll().get((person.get().getId()) - 1);
        System.out.println(deleteTable);
        List<Integer> notes = new ArrayList<>();
        for(int i = 0; i < noteService.findAll().size(); i++)
        {
            if(noteService.findAll().get(i).getTableOwner().getId() == deleteTable)
            {
                Note note = noteService.findAll().get(i);
                notes.add(note.getId());
            }
        }
        for(int i = 0; i < notes.size(); i++){
            String name = noteService.findById(notes.get(i)).get().getNotes_name();
            String description = noteService.findById(notes.get(i)).get().getDescription();
            LocalDate date = noteService.findById(notes.get(i)).get().getTimeAndDate();
            LocalDate createDate = noteService.findById(notes.get(i)).get().getCreateDate();
            LocalTime time = noteService.findById(notes.get(i)).get().getTime();
            Trash trash = new Trash(name, description, date, createDate, time, person1.getId(), deleteTable);
            trashService.saveTrash(trash);
            //trashRepository.save(trash);
            Note note = noteService.findById(notes.get(i)).get();
            noteService.delete(note);
            //noteRepository.delete(note);
            List<Integer> commentsid = new ArrayList<>();
            System.out.println(commentService.findAll().size());
            for (int j = 0; j < commentService.findAll().size(); j++) {
                if (commentService.findAll().get(j).getNoteOwner() == null) {
                    Comment comment = commentService.findAll().get(j);
                    commentsid.add(comment.getId());
                }
            }
            for (int k = 0; k < commentsid.size(); k++) {
                commentService.deleteById(commentsid.get(k));
                //commentRepository.deleteById(commentsid.get(k));
            }
        }
        notes.clear();
        tableService.deleteById(deleteTable);
        //tableRepository.deleteById(deleteTable);
        for(int i = 0; i < noteService.findAll().size(); i++)
        {
            if(noteService.findAll().get(i).getTableOwner() == null)
            {
                Note note = noteService.findAll().get(i);
                notes.add(note.getId());
            }
        }
        for (int i = 0; i < notes.size(); i++) {
            noteService.deleteById(notes.get(i));
        }
        return "redirect:/auth/hello";
    }

    @PostMapping("/updateNote")
    public String update(Model model, @RequestParam int id, @RequestParam String nameNote, @RequestParam String description, @RequestParam String date) {
        LocalDate cd = LocalDate.parse(date);
        System.out.println(id + " " + nameNote + " " + description + " " + date);
        Optional<Note> note = noteService.findById(id);
        noteService.findById(id).get().setNotes_name(nameNote);
        noteService.findById(id).get().setDescription(description);
        noteService.findById(id).get().setTimeAndDate(cd);
        noteService.save(note.get());

        return info(model, id);
    }

    @PostMapping("/info")
    public String info(Model model, @RequestParam int info) {
        System.out.println(info);
        List<Comment> comments = new ArrayList<>();
        Optional<Note> note = noteService.findById(info);
        for (int i = 0; i < commentService.findAll().size(); i++) {
            int value = commentService.findAll().get(i).getNoteOwner().getId();
            if (info == value) {
                Comment comment = commentService.findAll().get(i);
                comments.add(comment);
            }
        }
        model.addAttribute("comments", comments);
        model.addAttribute("noteInfo", note);
        return "info";
    }

    @PostMapping("/delete")
    public String delete(Model model, @RequestParam int idNote, @RequestParam int idTable, @RequestParam String nameNote, @RequestParam String descriptionNote, @RequestParam String dateNote, @RequestParam String time, @RequestParam String createDate) throws Exception {
//        Optional<Person> person = personRepository.findByUsername(personDetailsService.getPrincipalName());
//        Person person1 = personRepository.getById(person.get().getId());
        Optional<Person> person = personDetailsService.findByUsername(personDetailsService.getPrincipalName());
        Person person1 = personDetailsService.findById(person.get().getId());


        //Person person1 = personRepository.findAll().get((person.get().getId()) - 1);
        System.out.println(person1.getId() + " " + idNote + " " + nameNote + " " + descriptionNote + " " + dateNote + " " + idTable);
        LocalDate date = LocalDate.parse(dateNote);
        LocalDate dateCreate = LocalDate.parse(createDate);
        LocalTime time1 = LocalTime.parse(time);
        Trash trash = new Trash(nameNote, descriptionNote, date, dateCreate, time1, person1.getId(), idTable);
        trashService.saveTrash(trash);
        Note note = noteService.findById(idNote).get();
        noteService.delete(note);
        List<Integer> commentsid = new ArrayList<>();
        System.out.println(commentService.findAll().size());
        for (int i = 0; i < commentService.findAll().size(); i++) {
            if (commentService.findAll().get(i).getNoteOwner() == null) {
                Comment comment = commentService.findAll().get(i);
                commentsid.add(comment.getId());
            }
        }
        for (int i = 0; i < commentsid.size(); i++) {
            commentService.deleteById(commentsid.get(i));
        }
        return "redirect:/auth/hello";
    }

    @PostMapping("/trash")
    public String delete(Model model) throws Exception {
//        Optional<Person> person = personRepository.findByUsername(personDetailsService.getPrincipalName());
//        Person person1 = personRepository.getById(person.get().getId());
        Optional<Person> person = personDetailsService.findByUsername(personDetailsService.getPrincipalName());
        Person person1 = personDetailsService.findById(person.get().getId());

//        Person person1 = personRepository.findAll().get((person.get().getId()) - 1);
        List<Trash> trashes = new ArrayList<>();
        Trash trash = new Trash();
        for (int i = 0; i < trashService.findAll().size(); i++) {
            int id = trashService.findAll().get(i).getPerson_id();
            if (id == person1.getId()) {
                trash = trashService.findAll().get(i);
                trashes.add(trash);
            }
        }
        if(trashes.size() == 0)
        {
            model.addAttribute("nothing", "You don't have trashes");
        }
        model.addAttribute("trashes", trashes);
        return "trash";
    }

    @PostMapping("/comment")
    public String comment(Model model, @RequestParam int noteId, @RequestParam String comment) throws Exception {
        if(!comment.equals("")) {
            System.out.println(comment + " " + noteId);
            Note note = noteService.findById(noteId).get();
            Comment comment1 = new Comment(comment, note);
            commentService.save(comment1);
        }
        return info(model, noteId);
    }

    @PostMapping("/findNote")
    public String find(Model model, @RequestParam String find) throws Exception {
//        Optional<Person> person = personRepository.findByUsername(personDetailsService.getPrincipalName());
//        Person person1 = personRepository.getById(person.get().getId());
        Optional<Person> person = personDetailsService.findByUsername(personDetailsService.getPrincipalName());
        Person person1 = personDetailsService.findById(person.get().getId());

//        Person person1 = personRepository.findAll().get((person.get().getId()) - 1);
        List<Tables> tables = tableService.findByOwner(person1);
        List<Note> note = new ArrayList<>();
        Note note1 = new Note();
        List<Note> notes = new ArrayList<>();
        System.out.println(find);
        int value;
        boolean val = false;
        try {
            value = Integer.parseInt(String.valueOf(find.charAt(0)));
            val = true;
            System.out.println("itsokat");
        } catch (NumberFormatException e) {
            System.out.println("Input String cannot be parsed to Integer.");
        }
        if (val) {
            for (int i = 0; i < tables.size(); i++) {
                Tables tables1 = tables.get(i);
                notes.addAll(noteService.findByTableOwner(tables1));
            }
            for (int j = 0; j < notes.size(); j++) {
                String date = String.valueOf(notes.get(j).getCreateDate());
                if (date.contains(find)) {
                    note1 = notes.get(j);
                    note.add(note1);
                }
            }
        } else {
            for (int i = 0; i < tables.size(); i++) {
                Tables tables1 = tables.get(i);
                notes.addAll(noteService.findByTableOwner(tables1));
            }
            for (int j = 0; j < notes.size(); j++) {
                String name = notes.get(j).getNotes_name();
                if (name.contains(find)) {
                    note1 = notes.get(j);
                    note.add(note1);
                }
            }
        }
        model.addAttribute("fineNote", note);
        return "findNote";
    }

    @GetMapping("/newNote")
    public String newNote(Model model) throws Exception {
//        Optional<Person> person = personRepository.findByUsername(personDetailsService.getPrincipalName());
//        Person person1 = personRepository.getById(person.get().getId());
        Optional<Person> person = personDetailsService.findByUsername(personDetailsService.getPrincipalName());
        Person person1 = personDetailsService.findById(person.get().getId());

//        Person person1 = personRepository.findAll().get((person.get().getId()) - 1);
        System.out.println(tableService.findByOwner(person1).size());
        model.addAttribute("notes", tableService.findByOwner(person1));
        return "NewNote";
    }

    @GetMapping("/moveToTable")
    public String moveToTable(Model model, @RequestParam int id, @RequestParam int id1) throws Exception {
//        Optional<Person> person = personRepository.findByUsername(personDetailsService.getPrincipalName());
//        Person person1 = personRepository.getById(person.get().getId());

        Optional<Person> person = personDetailsService.findByUsername(personDetailsService.getPrincipalName());
        Person person1 = personDetailsService.findById(person.get().getId());

//        Person person1 = personRepository.findAll().get((person.get().getId()) - 1);
        System.out.println(tableRepository.findByOwner(person1).size());

        model.addAttribute("tableInfo", tableService.findById(id1));
        model.addAttribute("noteInfo", noteService.findById(id));
        model.addAttribute("notes", tableService.findByOwner(person1));
        return "moveToTable";
    }

    @PostMapping("/moveToTable")
    public String move(Model model, @RequestParam int el, @RequestParam int updateNote, @RequestParam int moveTable) throws Exception {
        System.out.println(el + " " + updateNote + " " + moveTable);
        Optional<Tables> tables = tableService.findById(el);
        Optional<Note> note = noteService.findById(updateNote);
        noteService.findById(updateNote).get().setTableOwner(tables.get());
        noteService.save(note.get());
        return "redirect:/auth/hello";
    }

    @PostMapping("/newTable")
    public String table(Model model, @RequestParam String table) throws Exception {
//        Optional<Person> person = personRepository.findByUsername(personDetailsService.getPrincipalName());
//        Person person1 = personRepository.getById(person.get().getId());
        Optional<Person> person = personDetailsService.findByUsername(personDetailsService.getPrincipalName());
        Person person1 = personDetailsService.findById(person.get().getId());

//        Person person1 = personRepository.findAll().get((person.get().getId()) - 1);
        Tables tables = new Tables(table, person1);
        tableService.save(tables);
        return "redirect:/auth/hello";
    }

    @PostMapping("/newNote")
    public String table(Model model, @RequestParam String header, @RequestParam String description, @RequestParam String dateReady, @RequestParam LocalTime time,  @RequestParam String el) throws Exception {
        LocalDate ready = LocalDate.parse(dateReady);
        LocalDate create = LocalDate.now();
        System.out.println(create);
//        Optional<Person> person = personRepository.findByUsername(personDetailsService.getPrincipalName());
//        Person person1 = personRepository.getById(person.get().getId());
        Optional<Person> person = personDetailsService.findByUsername(personDetailsService.getPrincipalName());
        Person person1 = personDetailsService.findById(person.get().getId());

//        Person person1 = personRepository.findAll().get((person.get().getId()) - 1);
        Tables tables = null;
        for (int i = 0; i < tableService.findTablesByPersonId(person1).size(); i++) {
            String table = tableService.findTablesByPersonId(person1).get(i).getTable_name();
            if (table.equals(el)) {
                tables = tableService.findTablesByPersonId(person1).get(i);
                break;
            }

        }
//        LocalTime time = LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond());
        Note note = new Note(header, description, ready, tables, create, time);
        noteService.save(note);
        return "redirect:/auth/hello";
    }

}
