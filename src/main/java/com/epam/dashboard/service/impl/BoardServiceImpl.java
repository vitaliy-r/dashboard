package com.epam.dashboard.service.impl;

import com.epam.dashboard.dto.BoardDto;
import com.epam.dashboard.dto.NoteDto;
import com.epam.dashboard.exception.InvalidIdException;
import com.epam.dashboard.exception.RecordIsNotFoundException;
import com.epam.dashboard.model.Board;
import com.epam.dashboard.model.Note;
import com.epam.dashboard.repository.BoardRepository;
import com.epam.dashboard.service.BoardService;
import com.epam.dashboard.mapper.BoardMapper;
import com.epam.dashboard.mapper.NoteMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private static final BoardMapper boardMapper = Mappers.getMapper(BoardMapper.class);
    private static final NoteMapper noteMapper = Mappers.getMapper(NoteMapper.class);
    private final BoardRepository boardRepository;

    @Override
    public BoardDto findById(String id) {
        validateIDs(id);

        Board board = getBoardByIdOrThrowException(id);

        return boardMapper.mapBoardToBoardDto(board);
    }

    @Override
    public NoteDto findNoteById(String boardId, String noteId) {
        validateIDs(boardId, noteId);

        List<Note> notes = getBoardByIdOrThrowException(boardId).getNotes();
        Note note = getNoteByIdOrThrowException(notes, noteId);

        return noteMapper.mapNoteToNoteDto(note);
    }

    @Override
    public boolean isBoardExistsWithTitle(String title) {
        return boardRepository.findByTitle(title).isPresent();
    }

    @Override
    public List<BoardDto> findAll() {
        List<Board> boards = boardRepository.findAll();
        return Objects.nonNull(boards) ? boardMapper.mapBoardsToBoardDTOs(boards) : Collections.emptyList();
    }

    @Override
    public List<NoteDto> findNotesByBoardId(String id) {
        validateIDs(id);

        List<Note> notes = getBoardByIdOrThrowException(id).getNotes();

        return Objects.nonNull(notes) ? noteMapper.mapNotesToNoteDTOs(notes) : Collections.emptyList();
    }

    @Override
    public BoardDto create(BoardDto boardDto) {
        Board board = boardRepository.insert(boardMapper.mapBoardDtoToBoard(boardDto));
        return boardMapper.mapBoardToBoardDto(board);
    }

    @Override
    public NoteDto addNoteByBoardId(NoteDto noteDto) {
        Board board = getBoardByIdOrThrowException(noteDto.getBoardId());
        Note note = noteMapper.mapNoteDtoToNote(noteDto);
        board.addNote(note);
        boardRepository.save(board);

        return noteMapper.mapNoteToNoteDto(note);
    }

    @Override
    public BoardDto updateBoard(BoardDto boardDto) {
        Board oldBoard = getBoardByIdOrThrowException(boardDto.getBoardId());

        Board newBoard = boardMapper.mapBoardDtoToBoard(boardDto);
        newBoard.setNotes(oldBoard.getNotes());
        newBoard.setMetadata(oldBoard.getMetadata());
        newBoard.getMetadata().setLastModifiedDate(LocalDateTime.now());

        return boardMapper.mapBoardToBoardDto(boardRepository.save(newBoard));
    }

    @Override
    public NoteDto updateNote(NoteDto noteDto) {
        Board board = getBoardByIdOrThrowException(noteDto.getBoardId());
        Note noteToReplace = getNoteByIdOrThrowException(board.getNotes(), noteDto.getNoteId());
        int elemIndex = board.getNotes().indexOf(noteToReplace);

        Note newNote = noteMapper.mapNoteDtoToNoteUpdateMethod(noteDto);
        newNote.setMetadata(noteToReplace.getMetadata());
        newNote.getMetadata().setLastModifiedDate(LocalDateTime.now());
        board.getNotes().set(elemIndex, newNote);
        boardRepository.save(board);

        return noteMapper.mapNoteToNoteDto(newNote);
    }

    @Override
    public void deleteAllBoards() {
        boardRepository.deleteAll();
    }

    @Override
    public void deleteBoardById(String id) {
        validateIDs(id);

        Board board = getBoardByIdOrThrowException(id);
        boardRepository.delete(board);
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
                throw new InvalidIdException();
            }
        }
    }

    private Board getBoardByIdOrThrowException(String boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new RecordIsNotFoundException(String.format("Board is not found by id: %s", boardId)));
    }

    private Note getNoteByIdOrThrowException(List<Note> notes, String noteId) {
        if (Objects.isNull(notes) || notes.isEmpty()) {
            throw new RecordIsNotFoundException(String.format("No notes found by id: %s", noteId));
        }

        return notes.stream()
                .filter(note -> StringUtils.equals(note.getId(), noteId))
                .findFirst()
                .orElseThrow(() -> new RecordIsNotFoundException(String.format("Note is not found by id: %s", noteId)));
    }

}
