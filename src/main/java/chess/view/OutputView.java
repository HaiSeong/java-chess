package chess.view;

import chess.domain.dto.StatusDto;
import chess.domain.piece.PieceType;
import chess.domain.piece.Position;
import chess.view.display.PieceDisplay;
import java.util.List;
import java.util.Map;

public class OutputView {

    public void printWelcomeMessage() {
        System.out.println("> 체스 게임을 시작합니다.");
        System.out.println("> 게임 시작 (처음부터) : start");
        System.out.println("> 이어하기 : continue");
        System.out.println("> 게임 종료 : end");
        System.out.println("> 게임 이동 : move source위치 target위치 - 예. move b2 b3");
    }

    public void printGuidanceForStart() {
        System.out.println("'start' 또는 'continue'를 입력하면 체스 게임을 시작합니다.");
    }

    public void printBoard(final Map<Position, PieceType> board) {
        List<String> lines = PieceDisplay.makeBoardDisplay(board);
        lines.forEach(System.out::println);
        System.out.println();
    }

    public void printErrorMessage(final String message) {
        System.out.println(message);
    }

    public void printScores(final StatusDto statusDto) {
        System.out.println("흰색 점수 : " + statusDto.whiteScore());
        System.out.println("검은색 점수 : " + statusDto.blackScore());
    }

    public void printGamePlaying() {
        System.out.println("게임이 아직 진행중입니다.");
    }

    public void printWinner(final String winner) {
        System.out.println(winner +"색이 이겼습니다.");
    }
}
