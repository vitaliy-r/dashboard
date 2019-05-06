package com.epam.dashboard.repository;

import com.epam.dashboard.model.Board;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends MongoRepository<Board, String> {

    Optional<Board> findByTitle(String title);

}
