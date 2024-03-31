package chess.domain.piece;

import java.util.Arrays;
import java.util.List;

public enum PieceScore {
    PAWN(1.0),
    KNIGHT(2.5),
    BISHOP(3),
    ROOK(5),
    QUEEN(9),
    KING(0);

    private final double score;

    PieceScore(final double score) {
        this.score = score;
    }

    public double score() {
        return score;
    }
}
