package com.epam.dashboard.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/board")
public class BoardController implements BoardApi {

  private final BoardService boardService;
  private final BoardResourceAssembler boardResourceAssembler;
  private final NoteResourceAssembler noteResourceAssembler;

  @GetMapping
  @ResponseStatus(OK)
  @Override
  public Resources<BoardResource> getAllBoards() {
    log.info("getAllBoards: method is called");
    return boardResourceAssembler.toResource(boardService.findAll());
  }

  @GetMapping("/{boardId}")
  @ResponseStatus(OK)
  @Override
  public BoardResource getBoard(@PathVariable String boardId) {
    log.info("getBoard: called with boardId parameter - {}", boardId);
    return boardResourceAssembler.toResource(boardService.findById(boardId));
  }

  @GetMapping("/{boardId}/note")
  @ResponseStatus(OK)
  @Override
  public Resources<NoteResource> getAllNotes(@PathVariable String boardId) {
    log.info("getAllNotes: called with boardId parameter - {}", boardId);
    return noteResourceAssembler.toResource(boardService.findNotesByBoardId(boardId));
  }

  @GetMapping("/{boardId}/note/{noteId}")
  @ResponseStatus(OK)
  @Override
  public NoteResource getNote(@PathVariable String boardId, @PathVariable String noteId) {
    log.info("getNote: called with boardId - {}, nodeId - {}", boardId, noteId);
    return noteResourceAssembler.toResource(boardService.findNoteById(boardId, noteId));
  }

  @PostMapping
  @ResponseStatus(CREATED)
  @Override
  public BoardResource createBoard(@RequestBody @Validated(OnCreate.class) BoardDto boardDto) {
    log.info("createBoard: called with boardDto request body - {}", boardDto);
    return boardResourceAssembler.toResource(boardService.create(boardDto));
  }

  @PostMapping("/note")
  @ResponseStatus(CREATED)
  @Override
  public NoteResource createNote(@RequestBody @Validated(OnCreate.class) NoteDto noteDto) {
    log.info("createNote: called with noteDto request body - {}", noteDto);
    return noteResourceAssembler.toResource(boardService.addNoteByBoardId(noteDto));
  }

  @PutMapping
  @ResponseStatus(CREATED)
  @Override
  public BoardResource updateBoard(@RequestBody @Validated(OnUpdate.class) BoardDto newBoardDto) {
    log.info("updateBoard: called with newBoardDto request body - {}", newBoardDto);
    return boardResourceAssembler.toResource(boardService.updateBoard(newBoardDto));
  }

  @PutMapping("/note")
  @ResponseStatus(CREATED)
  @Override
  public NoteResource updateNote(@RequestBody @Validated(OnUpdate.class) NoteDto newNoteDto) {
    log.info("updateNote: called with newNoteDto request body - {}", newNoteDto);
    return noteResourceAssembler.toResource(boardService.updateNote(newNoteDto));
  }

  @DeleteMapping
  @ResponseStatus(OK)
  @Override
  public BoardResource deleteAllBoards() {
    log.info("deleteAllBoards: method is called");
    boardService.deleteAllBoards();
    return boardResourceAssembler.toResource(new BoardDto());
  }

  @DeleteMapping("/{boardId}")
  @ResponseStatus(OK)
  @Override
  public BoardResource deleteBoard(@PathVariable String boardId) {
    log.info("deleteBoard: called with boardId parameter - {}", boardId);
    boardService.deleteBoardById(boardId);
    return boardResourceAssembler.toResource(new BoardDto());
  }

  @DeleteMapping("/{boardId}/note")
  @ResponseStatus(OK)
  @Override
  public NoteResource deleteAllNotes(@PathVariable String boardId) {
    log.info("deleteAllNotes: called with boardId parameter - {}", boardId);
    boardService.deleteAllNotesByBoardId(boardId);
    return noteResourceAssembler.toResource(new NoteDto());
  }

  @DeleteMapping("/{boardId}/note/{noteId}")
  @ResponseStatus(OK)
  @Override
  public NoteResource deleteNote(@PathVariable String boardId, @PathVariable String noteId) {
    log.info("deleteNote: called with boardId - {}, noteId - {}", boardId, noteId);
    boardService.deleteNoteByBoardAndNoteId(boardId, noteId);
    return noteResourceAssembler.toResource(new NoteDto());
  }

}
