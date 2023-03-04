package com.spring.kanbanboard.repositories;

import com.spring.kanbanboard.modals.Person;
import com.spring.kanbanboard.modals.Tables;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;


@Repository
public interface TableRepository extends JpaRepository<Tables, Integer>{
    List<Tables> findByOwner(Person person);


}
