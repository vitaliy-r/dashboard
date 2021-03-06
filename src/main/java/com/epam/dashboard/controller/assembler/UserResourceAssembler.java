package com.epam.dashboard.controller.assembler;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.epam.dashboard.controller.UserController;
import com.epam.dashboard.controller.resource.UserResource;
import com.epam.dashboard.dto.UserDto;
import java.util.List;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class UserResourceAssembler extends ResourceAssemblerSupport<UserDto, UserResource> {

  public UserResourceAssembler() {
    super(UserController.class, UserResource.class);
  }

  @Override
  public UserResource toResource(UserDto userDto) {
    UserResource resource = new UserResource(userDto);

    Link self = new Link(ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString(), Link.REL_SELF);

    Link get = linkTo(methodOn(UserController.class).getUser(userDto.getId())).withRel("get");
    Link getAll = linkTo(methodOn(UserController.class).getAllUsers()).withRel("getAll");
    Link create = linkTo(methodOn(UserController.class).createUser(null)).withRel("create");
    Link update = linkTo(methodOn(UserController.class).updateUser(null)).withRel("update");
    Link delete = linkTo(methodOn(UserController.class).deleteById(userDto.getId())).withRel("delete");

    resource.add(self, get, getAll, create, update, delete);

    return resource;
  }

  public Resources<UserResource> toResource(List<UserDto> users) {
    List<UserResource> userResources = users.stream()
        .map(this::toResource)
        .peek(resource -> resource.getLinks().remove(0))
        .collect(toList());

    Resources<UserResource> resources = new Resources<>(userResources);
    resources.add(new Link(ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString(), Link.REL_SELF));

    return resources;
  }

}
