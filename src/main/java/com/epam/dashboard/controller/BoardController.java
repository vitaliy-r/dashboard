package com.epam.dashboard.controller;

import com.epam.dashboard.api.BoardApi;
import com.epam.dashboard.controller.assembler.BoardResourceAssembler;
import com.epam.dashboard.controller.assembler.NoteResourceAssembler;
import com.epam.dashboard.controller.resource.BoardResource;
import com.epam.dashboard.controller.resource.NoteResource;
import com.epam.dashboard.dto.BoardDto;
import com.epam.dashboard.dto.NoteDto;
import com.epam.dashboard.dto.validation.group.OnCreate;
import com.epam.dashboard.dto.validation.group.OnUpdate;
import com.epam.dashboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/board")
public class BoardController implements BoardApi {

    private final BoardService boardService;
    private final BoardResourceAssembler boardResourceAssembler;
    private final NoteResourceAssembler noteResourceAssembler;

    @GetMapping
    @Override
    public ResponseEntity<Resources<BoardResource>> getAllBoards() {
        return ResponseEntity.ok(boardResourceAssembler.toResource(boardService.findAll()));
    }

    @GetMapping("/{boardId}")
    @Override
    public ResponseEntity<BoardResource> getBoard(@PathVariable String boardId) {
        return ResponseEntity.ok(boardResourceAssembler.toResource(boardService.findById(boardId)));
    }

    @GetMapping("/{boardId}/note")
    @Override
    public ResponseEntity<Resources<NoteResource>> getAllNotes(@PathVariable String boardId) {
        return ResponseEntity.ok(noteResourceAssembler.toResource(boardService.findNotesByBoardId(boardId)));
    }

    @GetMapping("/{boardId}/note/{noteId}")
    @Override
    public ResponseEntity<NoteResource> getNote(@PathVariable String boardId, @PathVariable String noteId) {
        return ResponseEntity.ok(noteResourceAssembler.toResource(boardService.findNoteById(boardId, noteId)));
    }

    @PostMapping
    @Override
    public ResponseEntity<BoardResource> createBoard(@RequestBody @Validated(OnCreate.class) BoardDto boardDto) {
        return ResponseEntity.status(CREATED).body(
                boardResourceAssembler.toResource(boardService.create(boardDto)));
    }

    @PostMapping("/note")
    @Override
    public ResponseEntity<NoteResource> createNote(@RequestBody @Validated(OnCreate.class) NoteDto noteDto) {
        return ResponseEntity.status(CREATED).body(
                noteResourceAssembler.toResource(boardService.addNoteByBoardId(noteDto)));
    }

    @PutMapping
    @Override
    public ResponseEntity<BoardResource> updateBoard(@RequestBody @Validated(OnUpdate.class) BoardDto newBoardDto) {
        return ResponseEntity.status(CREATED).body(
                boardResourceAssembler.toResource(boardService.updateBoard(newBoardDto)));
    }

    @PutMapping("/note")
    @Override
    public ResponseEntity<NoteResource> updateNote(@RequestBody @Validated(OnUpdate.class) NoteDto newNoteDto) {
        return ResponseEntity.status(CREATED).body(
                noteResourceAssembler.toResource(boardService.updateNote(newNoteDto)));
    }

    @DeleteMapping
    @Override
    public ResponseEntity<Void> deleteAllBoards() {
        boardService.deleteAllBoards();
        return new ResponseEntity<>(NO_CONTENT);
    }

    @DeleteMapping("/{boardId}")
    @Override
    public ResponseEntity<Void> deleteBoard(@PathVariable String boardId) {
        boardService.deleteBoardById(boardId);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @DeleteMapping("/{boardId}/note")
    @Override
    public ResponseEntity<Void> deleteAllNotes(@PathVariable String boardId) {
        boardService.deleteAllNotesByBoardId(boardId);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @DeleteMapping("/{boardId}/note/{noteId}")
    @Override
    public ResponseEntity<Void> deleteNote(@PathVariable String boardId, @PathVariable String noteId) {
        boardService.deleteNoteByBoardAndNoteId(boardId, noteId);
        return new ResponseEntity<>(NO_CONTENT);
    }
}
