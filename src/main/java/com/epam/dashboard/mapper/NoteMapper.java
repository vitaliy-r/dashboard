package com.epam.dashboard.mapper;

import com.epam.dashboard.dto.NoteDto;
import com.epam.dashboard.model.Metadata;
import com.epam.dashboard.model.Note;
import com.epam.dashboard.model.enums.NoteStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(imports = UUID.class)
public interface NoteMapper {

  @Mapping(target = "deadline", source = "deadline", dateFormat = "dd-MM-yyyy")
  @Mapping(target = "noteId", source = "id")
  @Mapping(target = "boardId", ignore = true)
  NoteDto mapNoteToNoteDto(Note note);

  @Mapping(target = "id", expression = "java(UUID.randomUUID().toString().replaceAll(\"-\", \"\").substring(8))")
  @Mapping(target = "status", source = "noteDto", qualifiedByName = "formStatus")
  @Mapping(target = "deadline", source = "deadline", dateFormat = "dd-MM-yyyy")
  @Mapping(target = "metadata", expression = "java(formCreationMetadata())")
  Note mapNoteDtoToNote(NoteDto noteDto);

  @Mapping(target = "id", source = "noteId")
  @Mapping(target = "status", source = "noteDto", qualifiedByName = "formStatus")
  @Mapping(target = "deadline", source = "deadline", dateFormat = "dd-MM-yyyy")
  @Mapping(target = "metadata", ignore = true)
  Note mapNoteDtoToNoteUpdateMethod(NoteDto noteDto);

  List<NoteDto> mapNotesToNoteDTOs(List<Note> notes);

  @Named("formStatus")
  default NoteStatus formStatus(NoteDto noteDto) {
    for (NoteStatus status : NoteStatus.values()) {
      if (status.name().equalsIgnoreCase(noteDto.getStatus())) {
        return status;
      }
    }
    return null;
  }

  default Metadata formCreationMetadata() {
    LocalDateTime dateTimeNow = LocalDateTime.now();
    return new Metadata(null, dateTimeNow, dateTimeNow);
  }

}
