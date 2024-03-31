package chess.repository;

import chess.dto.Movement;
import java.util.List;

public interface MoveRepository {

    void save(Movement movement);

    List<Movement> findAll();

    void clear();
}
