package com.epam.dashboard.service.impl;

import com.epam.dashboard.dto.BoardDto;
import com.epam.dashboard.dto.NoteDto;
import com.epam.dashboard.model.Board;
import com.epam.dashboard.model.Note;
import com.epam.dashboard.repository.BoardRepository;
import com.epam.dashboard.service.BoardService;
import com.epam.dashboard.util.BoardMapper;
import com.epam.dashboard.util.NoteMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    private static final BoardMapper boardMapper = BoardMapper.INSTANCE;
    private static final NoteMapper noteMapper = NoteMapper.INSTANCE;

    @Override
    public BoardDto findById(String id) {
        if (Objects.isNull(id)) {
            return null;
        }

        Board board = boardRepository.findById(id).orElse(null);
        return Objects.nonNull(board) ? boardMapper.mapBoardToBoardDto(board) : null;
    }

    @Override
    public NoteDto findNoteById(String boardId, String noteId) {
        validateIDs(boardId, noteId);

        List<Note> notes = boardRepository.findNotesById(boardId);
        if (Objects.isNull(notes) || notes.isEmpty()) {
            throw new RuntimeException("Note is not found by provided id");
        }

        return noteMapper.mapNoteToNoteDto(getNoteByIdOrThrowException(notes, noteId));
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
        if (StringUtils.isBlank(id)) {
            return Collections.emptyList();
        }

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
    public BoardDto addNoteByBoardId(String boardId, NoteDto noteDto) {
        Board board = getBoardByIdOrThrowException(boardId);
        board.addNote(noteMapper.mapNoteDtoToNote(noteDto));

        return boardMapper.mapBoardToBoardDto(boardRepository.save(board));
    }

    @Override
    public BoardDto updateBoard(String boardId, BoardDto boardDto) {
        Board board = getBoardByIdOrThrowException(boardId);
        board.setTitle(boardDto.getTitle());
        board.setDesc(boardDto.getDesc());
        board.setMaxSize(boardDto.getMaxSize());
        board.getMetadata().setLastModifiedDate(LocalDateTime.now());

        return boardMapper.mapBoardToBoardDto(boardRepository.save(board));
    }

    @Override
    public BoardDto updateNote(String boardId, String noteId, NoteDto noteDto) {
        Board board = getBoardByIdOrThrowException(boardId);
        Note noteToReplace = getNoteByIdOrThrowException(board.getNotes(), noteId);
        int elemIndex = board.getNotes().indexOf(noteToReplace);

        Note newNote = noteMapper.mapNoteDtoToNoteUpdateMethod(noteDto);
        newNote.setMetadata(noteToReplace.getMetadata());
        newNote.getMetadata().setLastModifiedDate(LocalDateTime.now());
        board.getNotes().set(elemIndex, newNote);

        return boardMapper.mapBoardToBoardDto(boardRepository.save(board));
    }

    @Override
    public void deleteAllBoards() {
        boardRepository.deleteAll();
    }

    @Override
    public void deleteBoardById(String id) {
        validateIDs(id);

        boardRepository.deleteById(id);
    }

    @Override
    public void deleteAllNotesByBoardId(String id) {
        validateIDs(id);

        Board board = getBoardByIdOrThrowException(id);
        board.getNotes().clear();

        boardRepository.save(board);
    }

    @Override
    public void deleteNoteByBoardAndNoteId(String boardId, String noteId) {
        validateIDs(boardId, noteId);

        Board board = getBoardByIdOrThrowException(boardId);
        Note note = getNoteByIdOrThrowException(board.getNotes(), noteId);
        board.getNotes().remove(note);

        boardRepository.save(board);
    }

    private void validateIDs(String... ids) {
        for (String id : ids) {
            if (StringUtils.isBlank(id)) {
                throw new InvalidParameterException("Id cannot be null or empty");
            }
        }
    }

    private Board getBoardByIdOrThrowException(String boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("Board is not found by provided id"));
    }

    private Note getNoteByIdOrThrowException(List<Note> notes, String noteId) {
        return notes.stream()
                .filter(note -> StringUtils.equals(note.getId(), noteId))
                .findFirst().orElseThrow(() -> new RuntimeException("Note is not found by provided id"));
    }

}
