package com.spring.kanbanboard.modals;

import javax.persistence.*;

@Entity
@Table(name = "Comment")
public class Comment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "comments")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "notes_id", referencedColumnName = "id")
    private Note noteOwner;

    public Comment(){}

    public Comment(String comment, Note note){
        this.comment = comment;
        this.noteOwner = note;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setNoteOwner(Note noteOwner) {
        this.noteOwner = noteOwner;
    }

    public int getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public Note getNoteOwner() {
        return noteOwner;
    }

    @Override
    public String toString(){
        return "Note{" +
                "id=" + id + ", noteOwner=" + noteOwner + '\'' +
                ", comment" + comment + "\'" + '}';
    }
}
