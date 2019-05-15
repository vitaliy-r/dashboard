package com.epam.dashboard.controller.assembler;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.epam.dashboard.controller.BoardController;
import com.epam.dashboard.controller.resource.NoteResource;
import com.epam.dashboard.dto.NoteDto;
import java.util.List;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class NoteResourceAssembler extends ResourceAssemblerSupport<NoteDto, NoteResource> {

  public NoteResourceAssembler() {
    super(BoardController.class, NoteResource.class);
  }

  @Override
  public NoteResource toResource(NoteDto noteDto) {
    NoteResource resource = new NoteResource(noteDto);

    Link self = new Link(ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString(), Link.REL_SELF);

    Link get = linkTo(methodOn(BoardController.class)
        .getNote(noteDto.getBoardId(), noteDto.getNoteId())).withRel("get");
    Link getAll = linkTo(methodOn(BoardController.class).getAllNotes(noteDto.getBoardId())).withRel("getAll");
    Link create = linkTo(methodOn(BoardController.class).createNote(null)).withRel("create");
    Link update = linkTo(methodOn(BoardController.class).updateNote(null)).withRel("update");
    Link delete = linkTo(methodOn(BoardController.class).deleteNote(noteDto.getBoardId(),
        noteDto.getNoteId())).withRel("delete");
    Link deleteAll = linkTo(methodOn(BoardController.class).deleteAllNotes(noteDto.getBoardId())).withRel("deleteAll");

    resource.add(self, get, getAll, create, update, delete, deleteAll);

    return resource;
  }

  public Resources<NoteResource> toResource(List<NoteDto> noteDTOs) {
    final Resources<NoteResource> resources = new Resources<>(
        noteDTOs.stream().map(this::toResource).collect(toList()));
    resources.add(new Link(ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString(),
        Link.REL_SELF));

    return resources;
  }

}
