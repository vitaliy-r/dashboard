package com.epam.dashboard.repository;

import com.epam.dashboard.model.Board;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends MongoRepository<Board, String> {

  Optional<Board> findByTitle(String title);

}
