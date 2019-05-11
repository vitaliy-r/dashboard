package com.epam.dashboard.controller.resource;

import com.epam.dashboard.dto.NoteDto;
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
public class NoteResource extends ResourceSupport {

    @JsonUnwrapped
    private final NoteDto noteDto;

}
