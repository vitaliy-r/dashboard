package com.epam.dashboard.dto.validation.impl;

import com.epam.dashboard.dto.NoteDto;
import com.epam.dashboard.dto.validation.NoteGeneralValidator;
import com.epam.dashboard.exception.InvalidIdException;
import com.epam.dashboard.exception.ObjectNotFoundInDatabaseException;
import com.epam.dashboard.service.BoardService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class NoteGeneralValidatorImpl implements ConstraintValidator<NoteGeneralValidator, NoteDto> {

    @Autowired
    private BoardService boardService;

    private boolean shouldExistInDatabase;

    @Override
    public boolean isValid(NoteDto noteDto, ConstraintValidatorContext context) {
        List<NoteDto> notes;
        try {
            notes = boardService.findNotesByBoardId(noteDto.getBoardId());
        } catch (InvalidIdException | ObjectNotFoundInDatabaseException e) {
            return true; // already validated by other annotations
        }

        if (shouldExistInDatabase) {
            return !notes.isEmpty() && notes.stream()
                    .anyMatch(note -> StringUtils.equals(note.getNoteId(), noteDto.getNoteId()));
        } else {
            return notes.isEmpty() || notes.stream()
                    .noneMatch(note -> StringUtils.equals(note.getTitle(), noteDto.getTitle()));
        }
    }

    @Override
    public void initialize(NoteGeneralValidator constraintAnnotation) {
        shouldExistInDatabase = constraintAnnotation.exists();
    }

}
