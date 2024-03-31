package chess.domain.piece.blank;

import chess.domain.color.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceScore;
import chess.domain.piece.PieceType;
import chess.domain.piece.Position;
import java.util.Set;

public class Blank extends Piece {

    public Blank(final Position position) {
        super(position, Color.NONE);
    }

    @Override
    public Set<Position> findPathTo(final Position destination) {
        throw new UnsupportedOperationException("해당 위치에 말이 없습니다.");
    }

    @Override
    public Piece update(final Position destination) {
        throw new UnsupportedOperationException("해당 위치에 말이 없습니다.");
    }

    @Override
    public PieceType pieceType() {
        return PieceType.BLANK;
    }

    @Override
    public double score() {
        throw new UnsupportedOperationException("빈칸은 점수가 없습니다.");
    }
}
