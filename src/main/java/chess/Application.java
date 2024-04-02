package chess;

import chess.controller.ChessGameController;
import chess.repository.MySqlMovementRepository;
import chess.repository.MovementRepository;
import chess.service.ChessService;
import chess.view.InputView;
import chess.view.OutputView;

public class Application {
    public static void main(String[] args) {
        final InputView inputView = new InputView();
        final OutputView outputView = new OutputView();
        final MovementRepository movementRepository = new MySqlMovementRepository();
        final ChessService chessService = new ChessService(movementRepository);
        final ChessGameController chessGameController = new ChessGameController(inputView, outputView, chessService);
        chessGameController.run();
    }
}
