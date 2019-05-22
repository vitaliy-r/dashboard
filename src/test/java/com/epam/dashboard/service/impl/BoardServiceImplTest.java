package com.epam.dashboard.service.impl;

import static java.time.format.DateTimeFormatter.ofPattern;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.dashboard.dto.BoardDto;
import com.epam.dashboard.dto.NoteDto;
import com.epam.dashboard.exception.InvalidIdException;
import com.epam.dashboard.exception.RecordIsNotFoundException;
import com.epam.dashboard.model.Board;
import com.epam.dashboard.model.Metadata;
import com.epam.dashboard.model.Note;
import com.epam.dashboard.model.enums.NoteStatus;
import com.epam.dashboard.repository.BoardRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.reflect.Whitebox;

@RunWith(MockitoJUnitRunner.class)
public class BoardServiceImplTest {

  private static final LocalDateTime localDateTime = LocalDateTime.now();

  @InjectMocks
  private BoardServiceImpl boardService;

  @Mock
  private BoardRepository repository;

  private Board testBoard;
  private Note testNote;
  private BoardDto testBoardDto;
  private NoteDto testNoteDto;

  @Before
  public void initTestData() {
    testBoard = generateBoard();
    testNote = generateNote();
    testBoardDto = generateBoardDto();
    testNoteDto = generateNoteDto();
  }

  @Test
  public void findByIdTest() {
    when(repository.findById(anyString())).thenReturn(Optional.of(testBoard));

    BoardDto boardDto = boardService.findById(testBoard.getId());

    assertThat(boardDto, hasProperty("boardId", is(testBoard.getId())));
  }

  @Test
  public void findNoteByIdTest() {
    when(repository.findById(anyString())).thenReturn(Optional.of(testBoard));

    NoteDto noteDto = boardService
        .findNoteById(testBoard.getId(), testBoard.getNotes().get(0).getId());

    assertThat(noteDto,
        hasProperty("noteId", is(testBoard.getNotes().get(0).getId())));
  }

  @Test
  public void isBoardExistsWithTitleTest() {
    when(repository.findByTitle(anyString())).thenReturn(Optional.of(testBoard));
    assertTrue(boardService.isBoardExistsWithTitle(testBoard.getTitle()));
  }

  @Test
  public void findAllTest() {
    when(repository.findAll()).thenReturn(Collections.singletonList(testBoard));

    List<BoardDto> boardDTOs = boardService.findAll();

    assertThat(boardDTOs, hasItem(hasProperty("boardId", is(testBoard.getId()))));
  }

  @Test
  public void findNotesByBoardIdTest() {
    when(repository.findById(anyString())).thenReturn(Optional.of(testBoard));

    List<NoteDto> noteDTOs = boardService.findNotesByBoardId(testBoard.getId());

    assertThat(noteDTOs,
        hasItem(hasProperty("noteId", is(testBoard.getNotes().get(0).getId()))));
  }

  @Test
  public void createTest() {
    testBoardDto.setBoardId(null);
    when(repository.insert(any(Board.class))).thenReturn(null);

    BoardDto boardDto = boardService.create(testBoardDto);

    assertThat(boardDto, hasProperty("title", is(testBoardDto.getTitle())));
    verify(repository).insert(any(Board.class));
  }

  @Test
  public void addNoteByBoardIdTest() {
    testNoteDto.setNoteId(null);
    testBoard.setNotes(null);
    when(repository.findById(anyString())).thenReturn(Optional.of(testBoard));
    when(repository.save(any(Board.class))).thenReturn(null);

    NoteDto noteDto = boardService.addNoteByBoardId(testNoteDto);

    assertThat(noteDto.getNoteId(), notNullValue());
    verify(repository).save(any(Board.class));
  }

  @Test
  public void updateBoardTest() {
    when(repository.findById(anyString())).thenReturn(Optional.of(testBoard));
    when(repository.save(any(Board.class))).thenReturn(null);

    BoardDto boardDto = boardService.updateBoard(testBoardDto);

    assertThat(boardDto, is(equalTo(testBoardDto)));
    verify(repository).save(any(Board.class));
  }

  @Test
  public void updateNoteTest() {
    testBoard.setNotes(new ArrayList<>(testBoard.getNotes()));
    when(repository.findById(anyString())).thenReturn(Optional.of(testBoard));
    when(repository.save(any(Board.class))).thenReturn(null);

    NoteDto noteDto = boardService.updateNote(testNoteDto);

    assertThat(noteDto, is(equalTo(testNoteDto)));
    verify(repository).save(any(Board.class));
  }

  @Test
  public void deleteAllBoardsTest() {
    doNothing().when(repository).deleteAll();

    boardService.deleteAllBoards();

    verify(repository).deleteAll();
  }

  @Test
  public void deleteBoardByIdTest() {
    when(repository.findById(anyString())).thenReturn(Optional.of(testBoard));
    doNothing().when(repository).delete(any());

    boardService.deleteBoardById(testBoard.getId());

    verify(repository).delete(any());
  }

  @Test
  public void deleteAllNotesByBoardIdTest() {
    testBoard.setNotes(new ArrayList<>(testBoard.getNotes()));
    when(repository.findById(anyString())).thenReturn(Optional.of(testBoard));
    when(repository.save(any(Board.class))).thenReturn(null);

    boardService.deleteAllNotesByBoardId(testBoard.getId());

    verify(repository).save(any(Board.class));
  }

  @Test
  public void deleteNoteByBoardAndNoteIdTest() {
    testBoard.setNotes(new ArrayList<>(testBoard.getNotes()));
    when(repository.findById(anyString())).thenReturn(Optional.of(testBoard));
    when(repository.save(any(Board.class))).thenReturn(null);

    boardService.deleteNoteByBoardAndNoteId(testBoard.getId(), testBoard.getNotes().get(0).getId());

    verify(repository).save(any(Board.class));
  }

  @Test
  public void getBoardByIdWithValidationTest() throws Exception {
    when(repository.findById(anyString())).thenReturn(Optional.of(testBoard));

    Board extractedBoard = Whitebox.invokeMethod(boardService,
        "getBoardByIdWithValidation", testBoard.getId());

    assertThat(extractedBoard, is(equalTo(testBoard)));
  }

  @Test(expected = InvalidIdException.class)
  public void getBoardByIdWithValidationInvalidIdExceptionTest() throws Exception {
    Whitebox.invokeMethod(boardService,
        "getBoardByIdWithValidation", "");
  }

  @Test(expected = RecordIsNotFoundException.class)
  public void getBoardByIdWithValidationRecordIsNotFoundExceptionTest() throws Exception {
    when(repository.findById(anyString())).thenReturn(Optional.empty());
    Whitebox.invokeMethod(boardService,
        "getBoardByIdWithValidation", testBoard.getId());
  }

  @Test
  public void getNoteByIdWithValidationTest() throws Exception {
    Note extractedNote = Whitebox.invokeMethod(boardService,
        "getNoteByIdWithValidation", testBoard.getNotes(), testNote.getId());

    assertThat(extractedNote, is(equalTo(testNote)));
  }

  @Test(expected = InvalidIdException.class)
  public void getNoteByIdWithValidationInvalidIdExceptionTest() throws Exception {
    Whitebox.invokeMethod(boardService,
        "getNoteByIdWithValidation", testBoard.getNotes(), "");
  }

  @Test(expected = RecordIsNotFoundException.class)
  public void getNoteByIdWithValidationWithEmptyListExceptionTest() throws Exception {
    Whitebox.invokeMethod(boardService,
        "getNoteByIdWithValidation", Collections.emptyList(), testNote.getId());
  }

  @Test(expected = RecordIsNotFoundException.class)
  public void getNoteByIdWithValidationRecordIsNotFoundExceptionTest() throws Exception {
    Whitebox.invokeMethod(boardService, "getNoteByIdWithValidation",
        testBoard.getNotes(), "e2fa382b34ad418ea86f1012"); // not existed id
  }

  private static Board generateBoard() {
    Board board = new Board();
    board.setId("5cd601825b36631c48036439");
    board.setTitle("Test board title");
    board.setDesc("Test board description");
    board.setMaxSize(1);
    board.setNotes(Collections.singletonList(generateNote()));
    board.setMetadata(new Metadata(null, localDateTime, localDateTime));

    return board;
  }

  private static BoardDto generateBoardDto() {
    BoardDto boardDto = new BoardDto();
    boardDto.setBoardId("5cd601825b36631c48036439");
    boardDto.setTitle("Test board title");
    boardDto.setDesc("Test board description");
    boardDto.setMaxSize(1);

    return boardDto;
  }

  private static Note generateNote() {
    Note note = new Note();
    note.setId("e2fa382b34ad418ea86f10c5");
    note.setTitle("Test board title");
    note.setContent("Test content");
    note.setStatus(NoteStatus.TODO);
    note.setDeadline(LocalDate.parse("01-01-2019", ofPattern("dd-MM-yyyy")));
    note.setMetadata(new Metadata(null, localDateTime, localDateTime));

    return note;
  }

  private static NoteDto generateNoteDto() {
    NoteDto noteDto = new NoteDto();
    noteDto.setBoardId("5cd601825b36631c48036439");
    noteDto.setNoteId("e2fa382b34ad418ea86f10c5");
    noteDto.setTitle("Test board title");
    noteDto.setContent("Test content");
    noteDto.setStatus("TODO");
    noteDto.setDeadline("01-01-2019");

    return noteDto;
  }

}