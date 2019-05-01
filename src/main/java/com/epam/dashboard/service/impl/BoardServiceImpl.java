package com.epam.dashboard.service.impl;

import com.epam.dashboard.dto.BoardDto;
import com.epam.dashboard.dto.NoteDto;
import com.epam.dashboard.model.Board;
import com.epam.dashboard.model.Note;
import com.epam.dashboard.repository.BoardRepository;
import com.epam.dashboard.service.BoardService;
import com.epam.dashboard.util.BoardMapper;
import com.epam.dashboard.util.NoteMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@AllArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    private final BoardMapper boardMapper = BoardMapper.INSTANCE;
    private final NoteMapper noteMapper = NoteMapper.INSTANCE;

    @Override
    public BoardDto findById(String id) {
        if (Objects.isNull(id)) {
            return null;
        }

        Board board = boardRepository.findById(id).orElse(null);
        return Objects.nonNull(board) ? boardMapper.mapBoardToBoardDto(board) : null;
    }

    @Override
    public BoardDto findByTitle(String title) {
        Board board = boardRepository.findByTitle(title);
        return Objects.nonNull(board) ? boardMapper.mapBoardToBoardDto(board) : null;
    }

    @Override
    public NoteDto findNoteById(String boardId, String noteId) {
        List<Note> notes = boardRepository.findNotesById(boardId);
        if (Objects.isNull(notes) || notes.isEmpty()) {
            throw new RuntimeException("Note is not found by provided id");
        }

        return noteMapper.mapNoteToNoteDto(notes.stream()
                .filter(note -> note.getId().equals(noteId))
                .findFirst().orElseThrow(() -> new RuntimeException("Note is not found by provided id")));
    }

    @Override
    public boolean isBoardExistsWithTitle(String title) {
        return Objects.nonNull(boardRepository.findByTitle(title));
    }

    @Override
    public List<BoardDto> findAll() {
        List<Board> boards = boardRepository.findAll();
        return Objects.nonNull(boards) ? boardMapper.mapBoardsToBoardDTOs(boards) : null;
    }

    @Override
    public List<NoteDto> findNotesByBoardId(String id) {
        List<Note> notes = boardRepository.findNotesById(id);
        return Objects.nonNull(notes) ? noteMapper.mapNotesToNoteDTOs(notes) : null;
    }

    @Override
    public BoardDto create(BoardDto boardDto) {
        Board board = boardMapper.mapBoardDtoToBoard(boardDto);
        boardRepository.insert(board);
        return boardMapper.mapBoardToBoardDto(board);
    }

    @Override
    public NoteDto addNoteByBoardId(String id, NoteDto noteDto) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board is not found by provided id"));
        board.addNote(noteMapper.mapNoteDtoToNote(noteDto));
        boardRepository.save(board);
        return noteDto;
    }

    @Override
    public BoardDto updateBoard(String boardId, BoardDto boardDto) {
        return null;
    }

    @Override
    public NoteDto updateNote(String boardId, String noteId, NoteDto noteDto) {
        return null;
    }

    @Override
    public void deleteAllBoards() {

    }

    @Override
    public void deleteBoardById(String id) {

    }

    @Override
    public void deleteAllNotesByBoardId(String id) {

    }

    @Override
    public void deleteNoteByBoardAndNoteId(String boardId, String noteId) {

    }

}
