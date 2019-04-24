package com.epam.dashboard.service;

import com.epam.dashboard.dto.BoardDto;
import com.epam.dashboard.model.Board;
import com.epam.dashboard.model.Note;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface BoardService {

    Board create(BoardDto boardDto) throws InvocationTargetException, IllegalAccessException;

    Board createTestBoard();

    Board findByTitle(String title);

    List<Board> findAll();

    List<Note> findNotesByBoardTitle(String title);

}
