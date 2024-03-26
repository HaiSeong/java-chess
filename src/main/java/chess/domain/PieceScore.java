package chess.domain;

import chess.domain.piece.PieceType;
import java.util.Arrays;
import java.util.List;

public enum PieceScore {
    PAWN(List.of(), 9),
    KNIGHT(List.of(), 9),
    BISHOP(List.of(), 9),
    ROOK(List.of(), 9),
    QUEEN(List.of(PieceType.WHITE_QUEEN, PieceType.BLACK_QUEEN), 9),
    KING(List.of(), 9);

    private final List<PieceType> pieceTypes;
    private final double score;

    PieceScore(final List<PieceType> pieceTypes, final double score) {
        this.pieceTypes = pieceTypes;
        this.score = score;
    }

    public static double findScore(final PieceType pieceType) {
        return Arrays.stream(PieceScore.values())
                .filter(pieceScore -> pieceScore.pieceTypes.contains(pieceType))
                .map(pieceScore -> pieceScore.score)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기물입니다."));
    }
}
