package chess.repository;

import chess.dto.Movement;
import java.util.List;

public interface MovementRepository {

    void save(Movement movement);

    List<Movement> findAll();

    void clear();
}
