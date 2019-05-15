package com.epam.dashboard.controller.assembler;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.epam.dashboard.controller.BoardController;
import com.epam.dashboard.controller.resource.BoardResource;
import com.epam.dashboard.dto.BoardDto;
import java.util.List;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class BoardResourceAssembler extends ResourceAssemblerSupport<BoardDto, BoardResource> {

  public BoardResourceAssembler() {
    super(BoardController.class, BoardResource.class);
  }

  @Override
  public BoardResource toResource(BoardDto boardDto) {
    BoardResource resource = new BoardResource(boardDto);

    Link self = new Link(ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString(),
        Link.REL_SELF);

    Link get = linkTo(methodOn(BoardController.class).getBoard(boardDto.getBoardId()))
        .withRel("get");
    Link getAll = linkTo(methodOn(BoardController.class).getAllBoards()).withRel("getAll");
    Link create = linkTo(methodOn(BoardController.class).createBoard(null)).withRel("create");
    Link update = linkTo(methodOn(BoardController.class).updateBoard(null)).withRel("update");
    Link delete = linkTo(methodOn(BoardController.class).deleteBoard(boardDto.getBoardId()))
        .withRel("delete");
    Link deleteAll = linkTo(methodOn(BoardController.class).deleteAllBoards()).withRel("deleteAll");

    resource.add(self, get, getAll, create, update, delete, deleteAll);

    return resource;
  }

  public Resources<BoardResource> toResource(List<BoardDto> boards) {
    List<BoardResource> boardResources = boards.stream().map(this::toResource)
        .peek(resource -> resource.getLinks().remove(0))
        .collect(toList());

    Resources<BoardResource> resources = new Resources<>(boardResources);
    resources.add(new Link(ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString(),
        Link.REL_SELF));

    return resources;
  }

}
