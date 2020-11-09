package dev.rakshit.tictactoeapi.repositories;

import dev.rakshit.tictactoeapi.models.Game;
import dev.rakshit.tictactoeapi.models.Move;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoveRepository extends JpaRepository<Move, Long> {

    List<Move> findAllByGame(Game game);

}