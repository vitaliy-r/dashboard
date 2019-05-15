package com.epam.dashboard.controller.resource;

import com.epam.dashboard.dto.BoardDto;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
public class BoardResource extends ResourceSupport {

  @JsonUnwrapped
  private final BoardDto boardDto;

}
