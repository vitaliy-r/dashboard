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
import org.springframework.http.ResponseEntity;

@Api(tags = "Board management REST API")
@ApiResponses({
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error")
})
public interface BoardApi {

    @ApiOperation(value = "Get all boards from database")
    @ApiResponse(code = 200, message = "OK", response = BoardResource[].class)
    ResponseEntity<Resources<BoardResource>> getAllBoards();

    @ApiOperation(value = "Get one board from database",
            notes = "Search for and return board by provided id")
    @ApiResponse(code = 200, message = "OK", response = BoardResource.class)
    ResponseEntity<BoardResource> getBoard(String boardId);

    @ApiOperation(value = "Get all notes from board by provided id")
    @ApiResponse(code = 200, message = "OK", response = NoteResource[].class)
    ResponseEntity<Resources<NoteResource>> getAllNotes(String boardId);

    @ApiOperation(value = "Get note by provided board&note id")
    @ApiResponse(code = 200, message = "OK", response = NoteResource.class)
    ResponseEntity<NoteResource> getNote(String boardId, String noteId);

    @ApiOperation(value = "Create board from provided request parameter")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created", response = BoardResource.class),
            @ApiResponse(code = 400, message = "Bad request")
    })
    ResponseEntity<BoardResource> createBoard(BoardDto boardDto);

    @ApiOperation(value = "Create note from provided request parameter")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created", response = NoteResource.class),
            @ApiResponse(code = 400, message = "Bad request")
    })
    ResponseEntity<NoteResource> createNote(NoteDto noteDto);

    @ApiOperation(value = "Update board from provided request dto parameter")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created", response = BoardResource.class),
            @ApiResponse(code = 400, message = "Bad request")
    })
    ResponseEntity<BoardResource> updateBoard(BoardDto newBoardDto);

    @ApiOperation(value = "Update note from provided request dto parameter")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created", response = NoteResource.class),
            @ApiResponse(code = 400, message = "Bad request")
    })
    ResponseEntity<NoteResource> updateNote(NoteDto newNoteDto);

    @ApiOperation(value = "Delete all boards from database")
    @ApiResponse(code = 204, message = "No Content")
    ResponseEntity<Void> deleteAllBoards();

    @ApiOperation(value = "Delete one board from database by provided id")
    @ApiResponse(code = 204, message = "No Content")
    ResponseEntity<Void> deleteBoard(String boardId);

    @ApiOperation(value = "Delete all notes from board by provided id")
    @ApiResponse(code = 204, message = "No Content")
    ResponseEntity<Void> deleteAllNotes(String boardId);

    @ApiOperation(value = "Delete one note from board by provided board&note id")
    @ApiResponse(code = 204, message = "No Content")
    ResponseEntity<Void> deleteNote(String boardId, String noteId);

}
