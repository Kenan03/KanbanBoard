package com.spring.kanbanboard.repositories;

import com.spring.kanbanboard.modals.Note;
import com.spring.kanbanboard.modals.Tables;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;


@Repository
public interface NoteRepository extends JpaRepository<Note, Integer>{
    List<Note> findByTableOwner(Tables tables);
    Optional<Note> findById(int id);


}
