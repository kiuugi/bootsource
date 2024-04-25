package com.example.board.service;

import java.util.List;

import com.example.board.dto.ReplyDto;
import com.example.board.entity.Board;
import com.example.board.entity.Member;
import com.example.board.entity.Reply;

public interface ReplyService {

    List<ReplyDto> getReplies(Long bno);

    Long create(ReplyDto dto);

    void remove(Long rno);

    ReplyDto getReply(Long rno);

    Long update(ReplyDto dto);

    // entity, dto 형변환
    public default ReplyDto entityToDto(Reply reply) {
        return ReplyDto.builder()
                .rno(reply.getRno())
                .text(reply.getText())
                .writerEmail(reply.getReplyer().getEmail())
                .writerName(reply.getReplyer().getName())
                .boardId(reply.getBoard().getBno())
                .createdDate(reply.getCreatedDate())
                .lastModifiedDate(reply.getLastModifiedDate())
                .build();
    }

    public default Reply dtoToEntity(ReplyDto dto) {
        Board board = Board.builder().bno(dto.getBoardId()).build();
        Member member = Member.builder().email(dto.getWriterEmail()).build();
        return Reply.builder()
                .rno(dto.getRno())
                .text(dto.getText())
                .replyer(member)
                .board(board)
                .build();
    }
}
