package chess.view.display;

import chess.domain.GameState;

public enum WinnerDisplay {
    WHITE_WIN("흰"),
    BLACK_WIN("검은");

    private final String display;

    WinnerDisplay(final String display) {
        this.display = display;
    }


    public static String findWinnerDisplay(final GameState gameState) {
        if (gameState == GameState.WHITE_WIN) {
            return WHITE_WIN.display;
        }
        return BLACK_WIN.display;
    }
}
