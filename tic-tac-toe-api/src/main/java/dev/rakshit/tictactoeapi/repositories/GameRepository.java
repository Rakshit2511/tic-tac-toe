package dev.rakshit.tictactoeapi.repositories;

import dev.rakshit.tictactoeapi.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

}