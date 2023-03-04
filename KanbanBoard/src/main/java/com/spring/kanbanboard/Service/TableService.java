package com.spring.kanbanboard.Service;

import com.spring.kanbanboard.modals.Person;
import com.spring.kanbanboard.modals.Tables;
import com.spring.kanbanboard.repositories.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TableService {
    private final TableRepository tableRepository;

    @Autowired
    public TableService(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    public List<Tables> findTablesByPersonId(Person person) {
        return this.tableRepository.findByOwner(person);
    }
    public void deleteById(int id){
        this.tableRepository.deleteById(id);
    }
    public List<Tables> findByOwner(Person person){
        return this.tableRepository.findByOwner(person);
    }
    public Optional<Tables> findById(int id){
        return this.tableRepository.findById(id);
    }
    public void save(Tables tables){
        this.tableRepository.save(tables);
    }


}
