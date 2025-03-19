package net.choimire.boot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import net.choimire.boot.entity.Board;
import net.choimire.boot.repository.BoardRepository;

@Service
public class BoardService {
    private final BoardRepository boardRepository;
        public BoardService(BoardRepository boardRepository){
            this.boardRepository = boardRepository;
        }
        //글 조회
        public List<Board> findAll(){
            return boardRepository.findAll();
        }
        //글 상세조회 optional t 값이 있을수도 없을수도있을때 명시적으로 쓰는 클래스
        public Optional<Board> findById(long id){
            return boardRepository.findById(id);
        }
        //글 등록
        public Board save(Board board){
            return boardRepository.save(board);
        }
        //글 삭제
        public void deleteById(long id){
           boardRepository.deleteById(id);
        }
}

