package chess.controller;

import chess.service.ChessService;
import chess.view.InputView;
import chess.view.OutputView;

public class ChessGameController {
    private final InputView inputView;
    private final OutputView outputView;
    private final ChessService chessService;

    public ChessGameController(final InputView inputView, final OutputView outputView, ChessService chessService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.chessService = chessService;
    }

    public void run() {
        outputView.printWelcomeMessage();
        waitGameInitializationCommand();
        outputView.printBoard(chessService.collectBoard());
        startChessGame();
    }

    private void waitGameInitializationCommand() {
        final String command = inputView.readCommand();
        if ("start".equals(command)) {
            chessService.clearGame();
            return;
        }
        if ("continue".equals(command)) {
            chessService.loadPreviousGame();
            return;
        }
        outputView.printGuidanceForStart();
        waitGameInitializationCommand();
    }

    private void startChessGame() {
        try {
            doOneRound();
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
        }
        outputView.printBoard(chessService.collectBoard());
        startChessGame();
    }

    private void doOneRound() {
        final String command = inputView.readCommand();
        if (command.startsWith("move") && chessService.isPlayingState()) {
            chessService.movePiece(command);
            printWinnerIfGameFinished();
            return;
        }
        if (command.equals("status")) {
            printStatus();
            return;
        }
        if (command.equals("end")) {
            System.exit(0);
        }
        throw new IllegalArgumentException("올바른 명령어를 입력해 주세요.");
    }

    private void printWinnerIfGameFinished() {
        if (!chessService.isPlayingState()) {
            printWinner();
        }
    }

    private void printStatus() {
        outputView.printScores(chessService.calculateWhiteScore(), chessService.calculateBlackScore());
        if (chessService.isPlayingState()) {
            outputView.printGamePlaying();
            return;
        }
        printWinner();
    }

    private void printWinner() {
        if (chessService.isWhiteWin()) {
            outputView.printWhiteWin();
            return;
        }
        outputView.printBlackWin();
    }
}
