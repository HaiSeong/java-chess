package chess.domain.state;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.byLessThan;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import chess.domain.color.Color;
import chess.domain.piece.nonsliding.King;
import chess.domain.piece.Piece;
import chess.domain.piece.Position;
import chess.domain.TestBoardFactory;
import chess.domain.piece.nonsliding.Knight;
import chess.domain.piece.pawn.BlackPawn;
import chess.domain.piece.pawn.WhiteFirstPawn;
import chess.domain.piece.sliding.Bishop;
import chess.domain.piece.sliding.Queen;
import chess.domain.piece.sliding.Rook;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MoveStateTest {

    @TestFactory
    @DisplayName("선택된 말에 따라 전략을 선택한다.")
    Collection<DynamicTest> changeState() {
        Map<Position, Piece> board = new TestBoardFactory().getTestBoard(Map.of(
                new Position(4, 4), new King(new Position(4, 4), Color.WHITE),
                new Position(4, 3), new WhiteFirstPawn(new Position(4, 3))
        ));

        return List.of(
                dynamicTest("빈칸을 선택하면 BlankMoveState를 반환한다.", () -> {
                    MoveState moveState = new GeneralMoveState(board);
                    assertThat(moveState.changeState(new Position(3, 3)))
                            .isInstanceOf(BlankMoveState.class);
                }),
                dynamicTest("폰을 제외한 기물을 선택하면 GeneralMoveState를 반환한다.", () -> {
                    MoveState moveState = new BlankMoveState(board);
                    assertThat(moveState.changeState(new Position(4, 4)))
                            .isInstanceOf(GeneralMoveState.class);
                }),
                dynamicTest("폰을 선택하면 PawnMoveState를 반환한다.", () -> {
                    MoveState moveState = new BlankMoveState(board);
                    assertThat(moveState.changeState(new Position(4, 3)))
                            .isInstanceOf(PawnMoveState.class);
                })
        );
    }

    @Test
    @DisplayName("색깔별로 점수 점수를 계산한다.")
    void calculateScore() {
        Map<Position, Piece> board = new TestBoardFactory().getTestBoard(Map.of(
                // 5 + 2.5 + 0 + 1 = 8.5
                new Position(1, 1), new Rook(new Position(1, 1), Color.WHITE),
                new Position(2, 1), new Knight(new Position(2, 1), Color.WHITE),
                new Position(3, 1), new King(new Position(3, 1), Color.WHITE),
                new Position(4, 1), new WhiteFirstPawn(new Position(4, 1)),

                // 9 + 3 + 0 + 1 = 13.0
                new Position(1, 8), new Queen(new Position(1, 8), Color.BLACK),
                new Position(2, 8), new Bishop(new Position(2, 8), Color.BLACK),
                new Position(3, 8), new King(new Position(3, 8), Color.BLACK),
                new Position(4, 8), new BlackPawn(new Position(4, 8))
        ));
        MoveState moveState = new GeneralMoveState(board);

        assertAll(
                () -> assertThat(moveState.calculateScore(Color.WHITE)).isEqualTo(8.5),
                () -> assertThat(moveState.calculateScore(Color.BLACK)).isEqualTo(13.0)
        );
    }

    @Test
    @DisplayName("폰이 같은 Column에 있을때 각 0.5점으로 계산한다.")
    void calculateScorePawnInSameColumn() {
        Map<Position, Piece> board = new TestBoardFactory().getTestBoard(Map.of(
                // 1 + 0.5 + 0.5 + 0.5 = 2.5
                new Position(1, 4), new WhiteFirstPawn(new Position(1, 4)),
                new Position(3, 3), new WhiteFirstPawn(new Position(3, 3)),
                new Position(3, 4), new WhiteFirstPawn(new Position(3, 4)),
                new Position(3, 5), new WhiteFirstPawn(new Position(3, 5))
        ));
        MoveState moveState = new GeneralMoveState(board);

        assertThat(moveState.calculateScore(Color.WHITE)).isEqualTo(2.5);
    }
}