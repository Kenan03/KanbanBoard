package com.spring.kanbanboard.modals;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Tables")
public class Tables implements Comparable<Tables> {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "table_name")
    private String table_name;

    @OneToMany(mappedBy = "tableOwner")
    private List<Note> noteList;
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;


    public Tables(){};

    public Tables(String table_name, Person person){
        this.owner = person;
        this.table_name = table_name;

    }

    public List<Note> getNoteList() {
        return noteList;
    }

    public void setNoteList(List<Note> noteList) {
        this.noteList = noteList;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Person getOwner() {
        return owner;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public int getId() {
        return id;
    }


    public String getTable_name() {
        return table_name;
    }

    @Override
    public String toString(){
        return "Tables{" +
                "id=" + id + ", person_id=" + owner + '\'' +
                ", table_name=" + table_name + "\'" + '}';
    }

    @Override
    public int compareTo(Tables o) {
        return o.id;
    }
}
