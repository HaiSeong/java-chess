package chess.domain;

import java.util.Set;

public abstract class Piece {
    protected final Position position;
    protected final Color color;

    protected Piece(Position position, Color color) {
        this.position = position;
        this.color = color;
    }

    public abstract Set<Position> findMovablePositions(Position destination);

    public boolean isSameColor(Color other) {
        return color == other;
    }

    public abstract Piece update(Position destination);

    public abstract PieceType pieceType();
}
