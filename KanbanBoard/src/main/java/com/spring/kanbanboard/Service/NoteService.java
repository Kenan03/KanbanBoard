package com.spring.kanbanboard.Service;

import com.spring.kanbanboard.modals.Note;
import com.spring.kanbanboard.modals.Tables;
import com.spring.kanbanboard.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class NoteService {
    private final NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> findNotesByTableId(Tables tables) {
        return this.noteRepository.findByTableOwner(tables);
    }

    public Optional<Note> findById(int id){
        return this.noteRepository.findById(id);
    }

    public List<Note> findAll(){
        return this.noteRepository.findAll();
    }
    public void delete(Note note){
        this.noteRepository.delete(note);
    }

    public void deleteById(int id){
        this.noteRepository.deleteById(id);
    }
    public void save(Note note){
        this.noteRepository.save(note);
    }
    public List<Note> findByTableOwner(Tables tables) {
        return this.noteRepository.findByTableOwner(tables);
    }

}
