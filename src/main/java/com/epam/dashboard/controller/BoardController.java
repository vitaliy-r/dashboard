package com.epam.dashboard.controller;

import com.epam.dashboard.dto.BoardDto;
import com.epam.dashboard.dto.NoteDto;
import com.epam.dashboard.dto.validation.group.OnCreate;
import com.epam.dashboard.dto.validation.group.OnUpdate;
import com.epam.dashboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public List<BoardDto> getAllBoards() {
        return boardService.findAll();
    }

    @GetMapping("/{boardId}")
    public BoardDto getBoard(@PathVariable String boardId) {
        return boardService.findById(boardId);
    }

    @GetMapping("/{boardId}/note")
    public List<NoteDto> getAllNotes(@PathVariable String boardId) {
        return boardService.findNotesByBoardId(boardId);
    }

    @GetMapping("/{boardId}/note/{noteId}")
    public NoteDto getNote(@PathVariable String boardId, @PathVariable String noteId) {
        return boardService.findNoteById(boardId, noteId);
    }

    @PostMapping
    public BoardDto createBoard(@RequestBody @Validated(OnCreate.class) BoardDto boardDto) {
        return boardService.create(boardDto);
    }

    @PostMapping("/{boardId}")
    public NoteDto createNote(@PathVariable String boardId,
                              @RequestBody @Validated(OnCreate.class) NoteDto noteDto) {
        return boardService.addNoteByBoardId(boardId, noteDto);
    }

    @PutMapping("/{boardId}")
    public BoardDto updateBoard(@PathVariable String boardId,
                                @RequestBody @Validated(OnUpdate.class) BoardDto newBoardDto) {
        return boardService.updateBoard(boardId, newBoardDto);
    }

    @PutMapping("/{boardId}/{noteId}")
    public NoteDto updateNote(@PathVariable String boardId, @PathVariable String noteId,
                              @RequestBody @Validated(OnUpdate.class) NoteDto newNoteDto) {
        return boardService.updateNote(boardId, noteId, newNoteDto);
    }

    @DeleteMapping
    public void deleteAllBoards() {
        boardService.deleteAllBoards();
    }

    @DeleteMapping("/{boardId}")
    public void deleteBoard(@PathVariable String boardId) {
        boardService.deleteBoardById(boardId);
    }

    @DeleteMapping("/{boardId}/note")
    public void deleteAllNotes(@PathVariable String boardId) {
        boardService.deleteAllNotesByBoardId(boardId);
    }

    @DeleteMapping("/{boardId}/note/{noteId}")
    public void deleteNote(@PathVariable String boardId, @PathVariable String noteId) {
        boardService.deleteNoteByBoardAndNoteId(boardId, noteId);
    }
}
