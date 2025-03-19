package net.choimire.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.choimire.boot.entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long>{

}
