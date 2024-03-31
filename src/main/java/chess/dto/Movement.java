package chess.dto;

import chess.domain.piece.Position;

public record Movement(int source_column, int source_rank, int destination_column, int destination_rank) {
    public static Movement of(final Position source, final Position destination) {
        return new Movement(source.getX(), source.getY(), destination.getX(), destination.getY());
    }
}
