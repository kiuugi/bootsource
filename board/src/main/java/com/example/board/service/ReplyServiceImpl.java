package com.example.board.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.board.dto.ReplyDto;
import com.example.board.entity.Board;
import com.example.board.entity.Reply;
import com.example.board.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;

    @Override
    public List<ReplyDto> getReplies(Long bno) {
        log.info("getReplies 서비스 요청");
        Board board = Board.builder().bno(bno).build();
        List<Reply> replies = replyRepository.getRepliesByBoardOrderByRno(board);
        // entity 리스트 <==> dto 리스트
        return replies.stream().map(reply -> entityToDto(reply)).collect(Collectors.toList());
    }

    @Override
    public Long create(ReplyDto dto) {
        return replyRepository.save(dtoToEntity(dto)).getRno();
    }

    @Override
    public void remove(Long rno) {
        replyRepository.deleteById(rno);
    }

    @Override
    public ReplyDto getReply(Long rno) {
        return entityToDto(replyRepository.findById(rno).get());
    }

    @Override
    public Long update(ReplyDto dto) {
        Reply reply = replyRepository.findById(dto.getRno()).get();
        reply.setText(dto.getText());
        // entityToDto(reply) 쓰던 말던 Rno만 필요하니 똑같을듯
        return replyRepository.save(reply).getRno();
    }

}
