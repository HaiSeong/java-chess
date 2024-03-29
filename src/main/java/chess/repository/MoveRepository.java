package chess.repository;

import chess.dto.Move;
import java.util.List;

public interface MoveRepository {

    void save(Move move);

    List<Move> findAll();

    void clear();
}
