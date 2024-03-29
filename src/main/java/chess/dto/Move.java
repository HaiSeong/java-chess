package chess.dto;

import chess.domain.piece.Position;

public record Move(int source_x, int source_y, int destination_x, int destination_y) {
    public static Move of(final Position source, final Position destination) {
        return new Move(source.getX(), source.getY(), destination.getX(), destination.getY());
    }
}
