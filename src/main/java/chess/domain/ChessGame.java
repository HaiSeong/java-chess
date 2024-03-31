package chess.domain;

import chess.domain.color.Color;
import chess.domain.dto.StatusDto;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.piece.Position;
import chess.domain.state.BlankMoveState;
import chess.domain.state.MoveState;
import java.util.Map;

public class ChessGame {
    private MoveState moveState;
    private Color turnColor;

    public ChessGame(final Map<Position, Piece> board) {
        this.moveState = new BlankMoveState(board);
        this.turnColor = Color.WHITE;
    }

    public void move(final Position source, final Position destination) {
        moveState = moveState.changeState(source);
        moveState.move(turnColor, source, destination);
        changeTurnColor();
    }

    private void changeTurnColor() {
        turnColor = turnColor.findOppositeColor();
    }

    public Map<Position, PieceType> collectBoard() {
        return moveState.collectBoard();
    }

    public StatusDto calculateScore() {
        return new StatusDto(
                moveState.calculateScore(Color.WHITE),
                moveState.calculateScore(Color.BLACK)
        );
    }

    public GameState checkGameState() {
        if (moveState.isKingDead(Color.WHITE)) {
            return GameState.BLACK_WIN;
        }
        if (moveState.isKingDead(Color.BLACK)) {
            return GameState.WHITE_WIN;
        }
        return GameState.PLAYING;
    }
}
