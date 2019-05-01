package com.epam.dashboard.service;

import com.epam.dashboard.dto.BoardDto;
import com.epam.dashboard.dto.NoteDto;
import com.epam.dashboard.model.Board;
import com.epam.dashboard.model.Note;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

public interface BoardService {

    BoardDto findById(String id);

    BoardDto findByTitle(String title);

    NoteDto findNoteById(String boardId, String noteId);

    List<NoteDto> findNotesByBoardId(String title);

    List<BoardDto> findAll();

    boolean isBoardExistsWithTitle(String title);

    BoardDto create(BoardDto boardDto);

    NoteDto addNoteByBoardId(String id, NoteDto note);

    BoardDto updateBoard(String boardId, BoardDto boardDto);

    NoteDto updateNote(String boardId, String noteId, NoteDto noteDto);

    void deleteAllBoards();

    void deleteBoardById(String id);

    void deleteAllNotesByBoardId(String id);

    void deleteNoteByBoardAndNoteId(String boardId, String noteId);

}
