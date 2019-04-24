package com.epam.dashboard.controller;

import com.epam.dashboard.dto.BoardDto;
import com.epam.dashboard.model.Board;
import com.epam.dashboard.model.Note;
import com.epam.dashboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/create-test")
    public Board createTestBoard() {
        return boardService.createTestBoard();
    }

    @PostMapping("/create")
    public Board createBoard(@RequestBody @Valid BoardDto board)
            throws InvocationTargetException, IllegalAccessException {
        log.info("createBoard: board - {}", board);
        return boardService.create(board);
    }

    @GetMapping("/retrieve")
    public List<Board> getBoards(@RequestParam(required = false) String title) {
        return Objects.isNull(title) ? boardService.findAll() :
                Collections.singletonList(boardService.findByTitle(title));
    }

    @GetMapping("/notes")
    public List<Note> getNotes(@RequestParam String boardTitle) {
        return boardService.findNotesByBoardTitle(boardTitle);
    }
}
