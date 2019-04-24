package com.epam.dashboard.service.impl;

import com.epam.dashboard.dto.BoardDto;
import com.epam.dashboard.model.Board;
import com.epam.dashboard.model.Metadata;
import com.epam.dashboard.model.Note;
import com.epam.dashboard.model.User;
import com.epam.dashboard.model.enums.Gender;
import com.epam.dashboard.model.enums.NoteStatus;
import com.epam.dashboard.repository.BoardRepository;
import com.epam.dashboard.repository.NoteRepository;
import com.epam.dashboard.repository.UserRepository;
import com.epam.dashboard.service.BoardService;
import lombok.AllArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    @Override
    public Board create(BoardDto boardDto) throws InvocationTargetException, IllegalAccessException {
        Board board = new Board();
        BeanUtils.copyProperties(board, boardDto);
        return boardRepository.save(board);
    }

    @Override
    public Board createTestBoard() {
        LocalDate dateNow = LocalDate.now();

        User user = User.builder()
                .firstName("First name")
                .lastName("Last name")
                .gender(Gender.MALE)
                .dateOfBirth(dateNow.minusYears(20))
                .username("Username")
                .email("Unique email")
                .password("Password")
                .build();
        userRepository.save(user);

        Metadata metadata = new Metadata(user, dateNow, dateNow);

        Note note = Note.builder()
                .title("Default title")
                .content("Default content")
                .status(NoteStatus.TODO)
                .deadline(dateNow.plusMonths(1))
                .metadata(metadata)
                .build();
        noteRepository.save(note);

        Board board = Board.builder()
                .title("Default title")
                .desc("Default description")
                .maxSize(100)
                .notes(Collections.singletonList(note))
                .metadata(metadata)
                .build();

        return boardRepository.save(board);
    }

    @Override
    public Board findByTitle(String title) {
        return boardRepository.findByTitle(title)
                .orElseThrow(() -> new RuntimeException("Board is not found by provided title"));
    }

    @Override
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    @Override
    public List<Note> findNotesByBoardTitle(String title) {
        return boardRepository.findNotesByTitle(title);
    }
}
