package chess.service;

import chess.domain.BoardFactory;
import chess.domain.ChessGame;
import chess.domain.GameState;
import chess.domain.color.Color;
import chess.domain.piece.Column;
import chess.domain.piece.PieceType;
import chess.domain.piece.Position;
import chess.dto.Movement;
import chess.repository.MoveRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChessService {

    private static final Pattern MOVE_COMMAND_PATTERN = Pattern.compile("^move\\s+([a-h][1-8]\\s+[a-h][1-8])$");
    private static final int COLUMN_INDEX = 0;
    private static final int RANK_INDEX = 1;
    private static final int SOURCE_INDEX = 0;
    private static final int DESTINATION_INDEX = 1;
    private static final int SOURCE_DESTINATION_INDEX = 1;

    private final MoveRepository moveRepository;
    private final ChessGame chessGame;

    public ChessService(final MoveRepository moveRepository) {
        this.chessGame = new ChessGame(new BoardFactory().getInitialBoard());
        this.moveRepository = moveRepository;
    }

    public void clearGame() {
        moveRepository.clear();
    }

    public void loadPreviousGame() {
        final List<Movement> movements = moveRepository.findAll();
        for (final Movement movement : movements) {
            final Position source = new Position(movement.source_column(), movement.source_rank());
            final Position destination = new Position(movement.destination_column(), movement.destination_rank());
            chessGame.move(source, destination);
        }
    }

    public double calculateWhiteScore() {
        return chessGame.calculateScore(Color.WHITE);
    }

    public double calculateBlackScore() {
        return chessGame.calculateScore(Color.BLACK);
    }

    public boolean isPlayingState() {
        return chessGame.checkGameState() == GameState.PLAYING;
    }

    public boolean isWhiteWin() {
        return chessGame.checkGameState() == GameState.WHITE_WIN;
    }

    public GameState checkGameState() {
        return chessGame.checkGameState();
    }

    public void movePiece(final String command) {
        final List<Position> positions = readPositions(command);
        final Position source = positions.get(SOURCE_INDEX);
        final Position destination = positions.get(DESTINATION_INDEX);
        chessGame.move(source, destination);
        moveRepository.save(Movement.of(source, destination));
    }

    private List<Position> readPositions(final String command) {
        final List<Position> positions = new ArrayList<>();
        final List<String> rawPositions = parseSourceDestination(command);
        positions.add(parsePosition(rawPositions.get(SOURCE_INDEX)));
        positions.add(parsePosition(rawPositions.get(DESTINATION_INDEX)));
        return positions;
    }

    private Position parsePosition(final String rawPosition) {
        final int column = Column.findColumn(String.valueOf(rawPosition.charAt(COLUMN_INDEX)));
        final int rank = parseRank(String.valueOf(rawPosition.charAt(RANK_INDEX)));
        return new Position(column, rank);
    }

    private List<String> parseSourceDestination(final String command) {
        final Matcher matcher = MOVE_COMMAND_PATTERN.matcher(command);

        if (!matcher.find()) {
            throw new IllegalArgumentException("올바른 입력 형식이 아닙니다.");
        }
        return List.of(matcher.group(SOURCE_DESTINATION_INDEX).split("\\s+"));
    }

    private int parseRank(final String rawRank) {
        try {
            return Integer.parseInt(rawRank);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("올바른 입력 형식이 아닙니다.");
        }
    }

    public Map<Position, PieceType> collectBoard() {
        return chessGame.collectBoard();
    }
}
