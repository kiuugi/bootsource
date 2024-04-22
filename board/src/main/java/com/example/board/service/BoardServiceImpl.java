package com.example.board.service;

import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.board.dto.BoardDto;
import com.example.board.dto.PageRequestDto;
import com.example.board.dto.PageResultDto;
import com.example.board.dto.ReplyDto;
import com.example.board.entity.Board;
import com.example.board.entity.Member;
import com.example.board.entity.Reply;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.MemberRepository;
import com.example.board.repository.ReplyRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;

    @Override
    public PageResultDto<BoardDto, Object[]> getList(PageRequestDto requestDto) {

        Page<Object[]> result = boardRepository.list(requestDto.getType(), requestDto.getKeyword(),
                requestDto.getPageable(Sort.by("bno").descending()));

        Function<Object[], BoardDto> fn = (entity -> entityToDto((Board) entity[0],
                (Member) entity[1], (Long) entity[2]));

        return new PageResultDto<>(result, fn);
    }

    @Override
    public BoardDto getRow(Long bno) {
        Object[] row = boardRepository.getRow(bno);

        return entityToDto((Board) row[0], (Member) row[1], (Long) row[2]);
    }

    @Override
    public void modify(BoardDto dto) {
        Board board = boardRepository.findById(dto.getBno()).get();
        board.setContent(dto.getContent());
        board.setTitle(dto.getTitle());

        boardRepository.save(board);
    }

    @Transactional
    @Override
    public void removeWithReplies(Long bno) {
        replyRepository.deleteByBno(bno);
        boardRepository.deleteById(bno);
    }

    @Override
    public Long create(BoardDto dto) {

        Optional<Member> member = memberRepository.findById(dto.getWriterEmail());

        if (member.isPresent()) {
            Board entity = Board.builder()
                    .title(dto.getTitle())
                    .content(dto.getContent())
                    .writer(member.get())
                    .build();
            entity = boardRepository.save(entity);
            return entity.getBno();
        }

        // return boardRepository.save(dtoToEntity(dto)).getBno(); 위의 전부가 이거랑 같음
        return null;
    }

}
