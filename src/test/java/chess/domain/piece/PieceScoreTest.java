package chess.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PieceScoreTest {

    @ParameterizedTest
    @CsvSource({"'WHITE_QUEEN', 9.0", "'WHITE_ROOK', 5.0", "'WHITE_BISHOP', 3.0",
            "'WHITE_KNIGHT', 2.5", "'WHITE_KING', 0.0", "'WHITE_PAWN', 1.0"})
    @DisplayName("각 피스에 대한 점수를 찾는다.")
    void calculateScoreSinglePiece(PieceType pieceType, double expected) {
        final double score = PieceScore.findScore(pieceType);
        assertThat(score).isEqualTo(expected);
    }
}