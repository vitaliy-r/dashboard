package com.epam.dashboard.repository;

import com.epam.dashboard.model.Board;
import com.epam.dashboard.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends MongoRepository<Board, String> {

    Optional<Board> findByTitle(String title);

    List<Note> findNotesByTitle(String title);

}
