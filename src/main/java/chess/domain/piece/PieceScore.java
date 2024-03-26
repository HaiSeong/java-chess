package chess.domain.piece;

import java.util.Arrays;
import java.util.List;

public enum PieceScore {
    PAWN(List.of(PieceType.WHITE_PAWN, PieceType.BLACK_PAWN), 1.0),
    KNIGHT(List.of(PieceType.WHITE_KNIGHT, PieceType.BLACK_KNIGHT), 2.5),
    BISHOP(List.of(PieceType.WHITE_BISHOP, PieceType.BLACK_BISHOP), 3),
    ROOK(List.of(PieceType.WHITE_ROOK, PieceType.BLACK_ROOK), 5),
    QUEEN(List.of(PieceType.WHITE_QUEEN, PieceType.BLACK_QUEEN), 9),
    KING(List.of(PieceType.WHITE_KING, PieceType.BLACK_KING), 0);

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
