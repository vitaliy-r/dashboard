package com.epam.dashboard.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epam.dashboard.controller.assembler.BoardResourceAssembler;
import com.epam.dashboard.controller.assembler.NoteResourceAssembler;
import com.epam.dashboard.controller.resource.BoardResource;
import com.epam.dashboard.controller.resource.NoteResource;
import com.epam.dashboard.dto.BoardDto;
import com.epam.dashboard.dto.NoteDto;
import com.epam.dashboard.service.BoardService;
import com.epam.dashboard.service.UserService;
import com.google.gson.Gson;
import java.util.Collections;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(BoardController.class)
public class BoardControllerTest {

  private static final MediaType HAL_JSON_CONTENT_TYPE = MediaType
      .valueOf("application/hal+json;charset=UTF-8");
  private static Gson mapper;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BoardService boardService;
  @MockBean
  private UserService userService;
  @MockBean
  private BoardResourceAssembler boardResourceAssembler;
  @MockBean
  private NoteResourceAssembler noteResourceAssembler;

  private BoardDto testBoard;
  private NoteDto testNote;
  private BoardResource testBoardResource;
  private NoteResource testNoteResource;
  private Resources<BoardResource> testBoardResources;
  private Resources<NoteResource> testNoteResources;

  @BeforeClass
  public static void initGlobal() {
    mapper = new Gson();
  }

  @Before
  public void initEach() {
    testBoard = generateBoardDto();
    testNote = generateNoteDto();
    testBoardResource = generateBoardResource();
    testNoteResource = generateNoteResource();
    testBoardResources = generateBoardResources();
    testNoteResources = generateNoteResources();
  }

  @Test
  public void getAllBoardsTest() throws Exception {
    when(boardService.findAll()).thenReturn(Collections.singletonList(testBoard));
    when(boardResourceAssembler.toResource(anyList())).thenReturn(testBoardResources);

    this.mockMvc.perform(get("/board"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(HAL_JSON_CONTENT_TYPE))
        .andExpect(jsonPath("$._embedded.boardResourceList.[0].boardId")
            .value(testBoardResources.getContent().iterator().next().getBoardDto().getBoardId()));
  }

  @Test
  public void getBoardTest() throws Exception {
    when(boardService.findById(anyString())).thenReturn(testBoard);
    when(boardResourceAssembler.toResource(any(BoardDto.class))).thenReturn(testBoardResource);

    this.mockMvc.perform(get(String.format("/board/%s", testBoard.getBoardId())))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(HAL_JSON_CONTENT_TYPE))
        .andExpect(jsonPath("$.boardId").value(testBoard.getBoardId()));
  }

  @Test
  public void getAllNotesTest() throws Exception {
    when(boardService.findNotesByBoardId(anyString()))
        .thenReturn(Collections.singletonList(testNote));
    when(noteResourceAssembler.toResource(anyList())).thenReturn(testNoteResources);

    this.mockMvc.perform(get(String.format("/board/%s/note", testNote.getBoardId())))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(HAL_JSON_CONTENT_TYPE))
        .andExpect(jsonPath("$._embedded.noteResourceList.[0].noteId")
            .value(testNoteResources.getContent().iterator().next().getNoteDto().getNoteId()));
  }

  @Test
  public void getNoteTest() throws Exception {
    when(boardService.findNoteById(anyString(), anyString())).thenReturn(testNote);
    when(noteResourceAssembler.toResource(any(NoteDto.class))).thenReturn(testNoteResource);

    this.mockMvc.perform(
        get(String.format("/board/%s/note/%s", testNote.getBoardId(), testNote.getNoteId())))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(HAL_JSON_CONTENT_TYPE))
        .andExpect(jsonPath("$.boardId").value(testNote.getBoardId()))
        .andExpect(jsonPath("$.noteId").value(testNote.getNoteId()));
  }

  @Test
  public void createBoardTest() throws Exception {
    // validation
    when(boardService.isBoardExistsWithTitle(anyString())).thenReturn(false); // unique title

    when(boardService.create(any())).thenReturn(testBoard);
    when(boardResourceAssembler.toResource(any(BoardDto.class))).thenReturn(testBoardResource);

    BoardDto inputBoardDto = generateBoardDto();
    inputBoardDto.setBoardId(null);

    this.mockMvc.perform(post("/board")
        .contentType(APPLICATION_JSON)
        .content(mapper.toJson(inputBoardDto)))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(content().contentType(HAL_JSON_CONTENT_TYPE))
        .andExpect(jsonPath("$.boardId").value(testBoard.getBoardId()));
  }

  @Test
  public void createNoteTest() throws Exception {
    // validation
    when(boardService.findById(anyString())).thenReturn(testBoard); // board exists
    when(boardService.findNotesByBoardId(anyString()))
        .thenReturn(Collections.emptyList()); // unique title

    when(boardService.addNoteByBoardId(any())).thenReturn(testNote);
    when(noteResourceAssembler.toResource(any(NoteDto.class))).thenReturn(testNoteResource);

    NoteDto inputNoteDto = generateNoteDto();
    inputNoteDto.setNoteId(null);

    this.mockMvc.perform(post("/board/note")
        .contentType(APPLICATION_JSON)
        .content(mapper.toJson(inputNoteDto)))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(content().contentType(HAL_JSON_CONTENT_TYPE))
        .andExpect(jsonPath("$.boardId").value(testNote.getBoardId()))
        .andExpect(jsonPath("$.noteId").value(testNote.getNoteId()));
  }

  @Test
  public void updateBoardTest() throws Exception {
    // validation
    when(boardService.findById(anyString())).thenReturn(testBoard); // board exists

    when(boardService.updateBoard(any())).thenReturn(testBoard);
    when(boardResourceAssembler.toResource(any(BoardDto.class))).thenReturn(testBoardResource);

    this.mockMvc.perform(put("/board")
        .contentType(APPLICATION_JSON)
        .content(mapper.toJson(testBoard)))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(content().contentType(HAL_JSON_CONTENT_TYPE))
        .andExpect(jsonPath("$.boardId").value(testBoard.getBoardId()));
  }

  @Test
  public void updateNoteTest() throws Exception {
    // validation
    when(boardService.findById(anyString())).thenReturn(testBoard); // board exists
    when(boardService.findNotesByBoardId(anyString()))
        .thenReturn(Collections.singletonList(testNote)); // unique title

    when(boardService.updateNote(any())).thenReturn(testNote);
    when(noteResourceAssembler.toResource(any(NoteDto.class))).thenReturn(testNoteResource);

    this.mockMvc.perform(put("/board/note")
        .contentType(APPLICATION_JSON)
        .content(mapper.toJson(testNote)))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(content().contentType(HAL_JSON_CONTENT_TYPE))
        .andExpect(jsonPath("$.boardId").value(testNote.getBoardId()))
        .andExpect(jsonPath("$.noteId").value(testNote.getNoteId()));
  }

  @Test
  public void deleteAllBoardsTest() throws Exception {
    doNothing().when(boardService).deleteAllBoards();
    when(boardResourceAssembler.toResource(any(BoardDto.class)))
        .thenReturn(new BoardResource(new BoardDto()));

    this.mockMvc.perform(delete("/board"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(HAL_JSON_CONTENT_TYPE));

    verify(boardService).deleteAllBoards();
  }

  @Test
  public void deleteBoardTest() throws Exception {
    doNothing().when(boardService).deleteBoardById(anyString());
    when(boardResourceAssembler.toResource(any(BoardDto.class)))
        .thenReturn(new BoardResource(new BoardDto()));

    this.mockMvc.perform(delete(String.format("/board/%s", "boardId")))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(HAL_JSON_CONTENT_TYPE));

    verify(boardService).deleteBoardById(anyString());
  }

  @Test
  public void deleteAllNotesTest() throws Exception {
    doNothing().when(boardService).deleteAllNotesByBoardId(anyString());
    when(noteResourceAssembler.toResource(any(NoteDto.class)))
        .thenReturn(new NoteResource(new NoteDto()));

    this.mockMvc.perform(delete(String.format("/board/%s/note", "boardId")))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(HAL_JSON_CONTENT_TYPE));

    verify(boardService).deleteAllNotesByBoardId(anyString());
  }

  @Test
  public void deleteNoteTest() throws Exception {
    doNothing().when(boardService).deleteNoteByBoardAndNoteId(anyString(), anyString());
    when(noteResourceAssembler.toResource(any(NoteDto.class)))
        .thenReturn(new NoteResource(new NoteDto()));

    this.mockMvc.perform(delete(String.format("/board/%s/note/%s", "boardId", "noteId")))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(HAL_JSON_CONTENT_TYPE));

    verify(boardService).deleteNoteByBoardAndNoteId(anyString(), anyString());
  }

  private static BoardDto generateBoardDto() {
    BoardDto boardDto = new BoardDto();
    boardDto.setBoardId("5cd601825b36631c48036439");
    boardDto.setTitle("Test board title");
    boardDto.setDesc("Test board description");
    boardDto.setMaxSize(1);

    return boardDto;
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

  private static BoardResource generateBoardResource() {
    return new BoardResource(generateBoardDto());
  }

  private static NoteResource generateNoteResource() {
    return new NoteResource(generateNoteDto());
  }

  private static Resources<BoardResource> generateBoardResources() {
    return new Resources<>(Collections.singletonList(new BoardResource(generateBoardDto())));
  }

  private static Resources<NoteResource> generateNoteResources() {
    return new Resources<>(Collections.singletonList(new NoteResource(generateNoteDto())));
  }

}