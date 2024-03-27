package chess.controller;

import chess.domain.BoardFactory;
import chess.domain.ChessGame;
import chess.domain.GameState;
import chess.domain.piece.Column;
import chess.domain.piece.Position;
import chess.view.InputView;
import chess.view.OutputView;
import chess.view.display.WinnerDisplay;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChessGameController {
    private static final Pattern MOVE_COMMAND_PATTERN = Pattern.compile("^move\\s+([a-h][1-8]\\s+[a-h][1-8])$");
    private static final int COLUMN_INDEX = 0;
    private static final int RANK_INDEX = 1;
    private static final int SOURCE_INDEX = 0;
    private static final int DESTINATION_INDEX = 1;
    private static final int SOURCE_DESTINATION_INDEX = 1;

    private final InputView inputView;
    private final OutputView outputView;

    public ChessGameController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        outputView.printWelcomeMessage();
        waitStartCommand();
        final ChessGame chessGame = new ChessGame(new BoardFactory().getInitialBoard());
        outputView.printBoard(chessGame.collectBoard());
        startChessGame(chessGame);
    }

    private void waitStartCommand() {
        final String command = inputView.readCommand();
        if ("start".equals(command)) {
            return;
        }
        outputView.printGuidanceForStart();
        waitStartCommand();
    }

    private void startChessGame(final ChessGame chessGame) {
        try {
            doOneRound(chessGame);
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
        }
        startChessGame(chessGame);
    }

    private void doOneRound(final ChessGame chessGame) {
        final String command = inputView.readCommand();
        if (command.startsWith("move") && chessGame.checkGameState() == GameState.PLAYING) {
            movePiece(chessGame, command);
            checkGameFinished(chessGame);
            return;
        }
        if (command.equals("status")) {
            printStatus(chessGame);
            return;
        }
        if (command.equals("end")) {
            System.exit(0);
        }
        throw new IllegalArgumentException("올바른 명령어를 입력해 주세요.");
    }

    private void movePiece(final ChessGame chessGame, final String command) {
        final List<Position> positions = readPositions(command);
        chessGame.move(positions.get(COLUMN_INDEX), positions.get(RANK_INDEX));
        outputView.printBoard(chessGame.collectBoard());
    }

    private void checkGameFinished(final ChessGame chessGame) {
        if (chessGame.checkGameState() == GameState.WHITE_WIN) {
            System.out.println("흰색이 이겼습니다.");
        }
        if (chessGame.checkGameState() == GameState.BLACK_WIN) {
            System.out.println("검은색이 이겼습니다.");
        }
    }

    private void printStatus(final ChessGame chessGame) {
        final GameState gameState = chessGame.checkGameState();
        if (gameState == GameState.PLAYING) {
            outputView.printScores(chessGame.calculateScore());
            return;
        }
        outputView.printWinner(WinnerDisplay.findWinnerDisplay(gameState));
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
            throw new IllegalArgumentException("올바른 명령어를 입력해 주세요.");
        }
        return List.of(matcher.group(SOURCE_DESTINATION_INDEX).split("\\s+"));
    }

    private int parseRank(final String rawRank) {
        try {
            return Integer.parseInt(rawRank);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("올바른 명령어를 입력해주세요.");
        }
    }
}
