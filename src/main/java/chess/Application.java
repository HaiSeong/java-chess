package chess;

import chess.controller.ChessGameController;
import chess.repository.MySqlMoveRepository;
import chess.repository.MoveRepository;
import chess.service.ChessService;
import chess.view.InputView;
import chess.view.OutputView;

public class Application {
    public static void main(String[] args) {
        final InputView inputView = new InputView();
        final OutputView outputView = new OutputView();
        final MoveRepository moveRepository = new MySqlMoveRepository();
        final ChessService chessService = new ChessService(moveRepository);
        final ChessGameController chessGameController = new ChessGameController(inputView, outputView, chessService);
        chessGameController.run();
    }
}
