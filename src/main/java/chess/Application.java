package chess;

import chess.controller.ChessGameController;
import chess.repository.MoveDao;
import chess.repository.MoveRepository;
import chess.view.InputView;
import chess.view.OutputView;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        MoveRepository moveRepository = new MoveDao();
        ChessGameController chessGameController = new ChessGameController(inputView, outputView, moveRepository);
        chessGameController.run();
    }
}
