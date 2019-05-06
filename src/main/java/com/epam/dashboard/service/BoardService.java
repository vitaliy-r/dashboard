package com.epam.dashboard.service;

import com.epam.dashboard.dto.BoardDto;
import com.epam.dashboard.dto.NoteDto;

import java.util.List;

public interface BoardService {

    BoardDto findById(String id);

    NoteDto findNoteById(String boardId, String noteId);

    List<NoteDto> findNotesByBoardId(String title);

    List<BoardDto> findAll();

    boolean isBoardExistsWithTitle(String title);

    BoardDto create(BoardDto boardDto);

    BoardDto addNoteByBoardId(NoteDto note);

    BoardDto updateBoard(BoardDto boardDto);

    BoardDto updateNote(NoteDto noteDto);

    void deleteAllBoards();

    void deleteBoardById(String id);

    void deleteAllNotesByBoardId(String id);

    void deleteNoteByBoardAndNoteId(String boardId, String noteId);

}
