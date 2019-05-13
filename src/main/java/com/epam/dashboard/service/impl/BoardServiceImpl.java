package com.epam.dashboard.service.impl;

import com.epam.dashboard.dto.BoardDto;
import com.epam.dashboard.dto.NoteDto;
import com.epam.dashboard.exception.InvalidIdException;
import com.epam.dashboard.exception.RecordIsNotFoundException;
import com.epam.dashboard.mapper.BoardMapper;
import com.epam.dashboard.mapper.NoteMapper;
import com.epam.dashboard.model.Board;
import com.epam.dashboard.model.Note;
import com.epam.dashboard.repository.BoardRepository;
import com.epam.dashboard.service.BoardService;
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
        Board board = getBoardByIdWithValidation(id);
        return boardMapper.mapBoardToBoardDto(board);
    }

    @Override
    public NoteDto findNoteById(String boardId, String noteId) {
        List<Note> notes = getBoardByIdWithValidation(boardId).getNotes();
        Note note = getNoteByIdWithValidation(notes, noteId);

        NoteDto noteDto = noteMapper.mapNoteToNoteDto(note);
        noteDto.setBoardId(boardId);

        return noteDto;
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
        List<Note> notes = getBoardByIdWithValidation(id).getNotes();
        return Objects.nonNull(notes) ? noteMapper.mapNotesToNoteDTOs(notes) : Collections.emptyList();
    }

    @Override
    public BoardDto create(BoardDto boardDto) {
        Board board = boardMapper.mapBoardDtoToBoard(boardDto);
        boardRepository.insert(board);

        boardDto.setBoardId(board.getId());

        return boardDto;
    }

    @Override
    public NoteDto addNoteByBoardId(NoteDto noteDto) {
        Board board = getBoardByIdWithValidation(noteDto.getBoardId());
        Note note = noteMapper.mapNoteDtoToNote(noteDto);
        board.addNote(note);
        boardRepository.save(board);

        noteDto.setNoteId(note.getId());

        return noteDto;
    }

    @Override
    public BoardDto updateBoard(BoardDto boardDto) {
        Board oldBoard = getBoardByIdWithValidation(boardDto.getBoardId());

        Board newBoard = boardMapper.mapBoardDtoToBoard(boardDto);
        newBoard.setNotes(oldBoard.getNotes());
        newBoard.setMetadata(oldBoard.getMetadata());
        newBoard.getMetadata().setLastModifiedDate(LocalDateTime.now());
        boardRepository.save(newBoard);

        return boardDto;
    }

    @Override
    public NoteDto updateNote(NoteDto noteDto) {
        Board board = getBoardByIdWithValidation(noteDto.getBoardId());
        Note noteToReplace = getNoteByIdWithValidation(board.getNotes(), noteDto.getNoteId());
        int elemIndex = board.getNotes().indexOf(noteToReplace);

        Note newNote = noteMapper.mapNoteDtoToNoteUpdateMethod(noteDto);
        newNote.setMetadata(noteToReplace.getMetadata());
        newNote.getMetadata().setLastModifiedDate(LocalDateTime.now());
        board.getNotes().set(elemIndex, newNote);
        boardRepository.save(board);

        return noteDto;
    }

    @Override
    public void deleteAllBoards() {
        boardRepository.deleteAll();
    }

    @Override
    public void deleteBoardById(String id) {
        Board board = getBoardByIdWithValidation(id);
        boardRepository.delete(board);
    }

    @Override
    public void deleteAllNotesByBoardId(String id) {
        Board board = getBoardByIdWithValidation(id);
        board.getNotes().clear();

        boardRepository.save(board);
    }

    @Override
    public void deleteNoteByBoardAndNoteId(String boardId, String noteId) {
        Board board = getBoardByIdWithValidation(boardId);
        Note note = getNoteByIdWithValidation(board.getNotes(), noteId);
        board.getNotes().remove(note);

        boardRepository.save(board);
    }

    private Board getBoardByIdWithValidation(String boardId) {
        if (StringUtils.isBlank(boardId)) {
            throw new InvalidIdException();
        }

        return boardRepository.findById(boardId)
                .orElseThrow(() -> new RecordIsNotFoundException(String.format("Board is not found by id: %s", boardId)));
    }

    private Note getNoteByIdWithValidation(List<Note> notes, String noteId) {
        if (StringUtils.isBlank(noteId)) {
            throw new InvalidIdException();
        }

        if (Objects.isNull(notes) || notes.isEmpty()) {
            throw new RecordIsNotFoundException(String.format("No notes found by id: %s", noteId));
        }

        return notes.stream()
                .filter(note -> StringUtils.equals(note.getId(), noteId))
                .findFirst()
                .orElseThrow(() -> new RecordIsNotFoundException(String.format("Note is not found by id: %s", noteId)));
    }

}
