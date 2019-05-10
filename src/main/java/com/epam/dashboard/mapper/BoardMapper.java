package com.epam.dashboard.mapper;

import com.epam.dashboard.dto.BoardDto;
import com.epam.dashboard.model.Board;
import com.epam.dashboard.model.Metadata;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface BoardMapper {

    @Mapping(target = "boardId", source = "id")
    BoardDto mapBoardToBoardDto(Board board);

    @Mapping(target = "id", source = "boardId")
    @Mapping(target = "metadata", expression = "java(formCreationMetadata())")
    @Mapping(target = "notes", ignore = true)
    Board mapBoardDtoToBoard(BoardDto boardDto);

    List<BoardDto> mapBoardsToBoardDTOs(List<Board> boards);

    default Metadata formCreationMetadata() {
        LocalDateTime dateNow = LocalDateTime.now();
        return new Metadata(null, dateNow, dateNow);
    }

}
