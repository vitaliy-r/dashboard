package com.epam.dashboard.dto.validation.impl;

import com.epam.dashboard.dto.BoardDto;
import com.epam.dashboard.dto.NoteDto;
import com.epam.dashboard.dto.validation.NoteGeneralValidator;
import com.epam.dashboard.service.BoardService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Objects;

public class NoteGeneralValidatorImpl implements ConstraintValidator<NoteGeneralValidator, NoteDto> {

    @Autowired
    private BoardService boardService;

    private boolean shouldExistInDatabase;

    @Override
    public boolean isValid(NoteDto noteDto, ConstraintValidatorContext context) {
        BoardDto boardDto = boardService.findById(noteDto.getBoardId());
        if (Objects.isNull(boardDto)) {
            return true; // if boardId == null or not exists in database(already validated, no need to do next checks)
        }

        List<NoteDto> notes = boardService.findNotesByBoardId(noteDto.getBoardId());
        if (shouldExistInDatabase) {
            return Objects.nonNull(notes) && !notes.isEmpty() && notes.stream()
                    .anyMatch(note -> StringUtils.equals(note.getNoteId(), noteDto.getNoteId()));
        } else {
            return Objects.isNull(notes) || notes.isEmpty() || notes.stream()
                    .noneMatch(note -> StringUtils.equals(note.getTitle(), noteDto.getTitle()));
        }
    }

    @Override
    public void initialize(NoteGeneralValidator constraintAnnotation) {
        shouldExistInDatabase = constraintAnnotation.exists();
    }

}
