package com.epam.dashboard.dto.validation;

import com.epam.dashboard.dto.NoteDto;
import com.epam.dashboard.dto.validation.NoteIdExists.NoteIdValidatorImpl;
import com.epam.dashboard.exception.InvalidIdException;
import com.epam.dashboard.exception.RecordIsNotFoundException;
import com.epam.dashboard.service.BoardService;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

@Documented
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NoteIdValidatorImpl.class)
public @interface NoteIdExists {

  String message() default "{com.epam.dashboard.dto.validation.NoteIdExists.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  class NoteIdValidatorImpl implements ConstraintValidator<NoteIdExists, NoteDto> {

    @Autowired
    private BoardService boardService;

    @Override
    public boolean isValid(NoteDto noteDto, ConstraintValidatorContext context) {
      List<NoteDto> notes;
      try {
        notes = boardService.findNotesByBoardId(noteDto.getBoardId());
      } catch (InvalidIdException | RecordIsNotFoundException e) {
        return true; // already validated by other annotations
      }

      return !notes.isEmpty() && notes.stream()
          .anyMatch(note -> StringUtils.equals(note.getNoteId(), noteDto.getNoteId()));
    }

  }

}
