package com.epam.dashboard.api;

import com.epam.dashboard.controller.resource.BoardResource;
import com.epam.dashboard.controller.resource.NoteResource;
import com.epam.dashboard.dto.BoardDto;
import com.epam.dashboard.dto.NoteDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.hateoas.Resources;

@Api(tags = "Board management REST API")
@ApiResponses({
    @ApiResponse(code = 404, message = "Not found"),
    @ApiResponse(code = 500, message = "Internal server error")
})
public interface BoardApi {

  @ApiOperation(value = "Get all boards from database")
  @ApiResponse(code = 200, message = "OK", response = BoardResource[].class)
  Resources<BoardResource> getAllBoards();

  @ApiOperation(value = "Get one board from database",
      notes = "Search for and return board by provided id")
  @ApiResponse(code = 200, message = "OK", response = BoardResource.class)
  BoardResource getBoard(String boardId);

  @ApiOperation(value = "Get all notes from board by provided id")
  @ApiResponse(code = 200, message = "OK", response = NoteResource[].class)
  Resources<NoteResource> getAllNotes(String boardId);

  @ApiOperation(value = "Get note by provided board&note id")
  @ApiResponse(code = 200, message = "OK", response = NoteResource.class)
  NoteResource getNote(String boardId, String noteId);

  @ApiOperation(value = "Create board from provided request parameter")
  @ApiResponses({
      @ApiResponse(code = 201, message = "Created", response = BoardResource.class),
      @ApiResponse(code = 400, message = "Bad request")
  })
  BoardResource createBoard(BoardDto boardDto);

  @ApiOperation(value = "Create note from provided request parameter")
  @ApiResponses({
      @ApiResponse(code = 201, message = "Created", response = NoteResource.class),
      @ApiResponse(code = 400, message = "Bad request")
  })
  NoteResource createNote(NoteDto noteDto);

  @ApiOperation(value = "Update board from provided request dto parameter")
  @ApiResponses({
      @ApiResponse(code = 201, message = "Created", response = BoardResource.class),
      @ApiResponse(code = 400, message = "Bad request")
  })
  BoardResource updateBoard(BoardDto newBoardDto);

  @ApiOperation(value = "Update note from provided request dto parameter")
  @ApiResponses({
      @ApiResponse(code = 201, message = "Created", response = NoteResource.class),
      @ApiResponse(code = 400, message = "Bad request")
  })
  NoteResource updateNote(NoteDto newNoteDto);

  @ApiOperation(value = "Delete all boards from database")
  @ApiResponse(code = 200, message = "OK")
  BoardResource deleteAllBoards();

  @ApiOperation(value = "Delete one board from database by provided id")
  @ApiResponse(code = 200, message = "OK")
  BoardResource deleteBoard(String boardId);

  @ApiOperation(value = "Delete all notes from board by provided id")
  @ApiResponse(code = 200, message = "OK")
  NoteResource deleteAllNotes(String boardId);

  @ApiOperation(value = "Delete one note from board by provided board&note id")
  @ApiResponse(code = 200, message = "OK")
  NoteResource deleteNote(String boardId, String noteId);

}
