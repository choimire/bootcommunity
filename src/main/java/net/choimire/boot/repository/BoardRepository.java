package net.choimire.boot.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import net.choimire.boot.entity.Board;
;


@Repository
public interface BoardRepository extends JpaRepository<Board,Long>{
    Page<Board> findAll(Pageable pageable);
    Page<Board> findByWriterContaining(String writer,Pageable pageable);
    Page<Board> findByTitleContaining(String title,Pageable pageable);
    Page<Board> findByContentContaining(String content,Pageable pageable);
    
    
}
