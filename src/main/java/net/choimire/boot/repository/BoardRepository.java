package net.choimire.boot.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import net.choimire.boot.entity.Board;
import java.util.List;


@Repository
public interface BoardRepository extends JpaRepository<Board,Long>{
    List<Board> findByWriterContaining(String writer);
    List<Board> findByTitleContaining(String title);
    List<Board> findByContentContaining(String content);
    
    
}
