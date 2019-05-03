package com.epam.dashboard.util;

import com.epam.dashboard.dto.BoardDto;
import com.epam.dashboard.model.Board;
import com.epam.dashboard.model.Metadata;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface BoardMapper {

    BoardMapper INSTANCE = Mappers.getMapper(BoardMapper.class);

    @Mapping(target = "boardId", ignore = true)
    BoardDto mapBoardToBoardDto(Board board);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "notes", ignore = true)
    @Mapping(target = "metadata", expression = "java(formCreationMetadata())")
    Board mapBoardDtoToBoard(BoardDto boardDto);

    List<BoardDto> mapBoardsToBoardDTOs(List<Board> boards);

    default Metadata formCreationMetadata() {
        LocalDateTime dateNow = LocalDateTime.now();
        return new Metadata(null, dateNow, dateNow);
    }

}
