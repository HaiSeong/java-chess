package chess.repository;

import chess.dto.Move;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MoveDao implements MoveRepository {

    private static final String SERVER = "localhost:13306"; // MySQL 서버 주소
    private static final String DATABASE = "chess"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root"; //  MySQL 서버 아이디
    private static final String PASSWORD = "root"; // MySQL 서버 비밀번호
    private static final String TABLE = "move"; // 테이블명

    public Connection getConnection() {
        // 드라이버 연결
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void save(Move move) {
        Connection connection = getConnection();
        final String sql =
                "INSERT INTO " + TABLE + " (source_x, source_y, destination_x, destination_y) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, move.source_x());
            statement.setInt(2, move.source_y());
            statement.setInt(3, move.destination_x());
            statement.setInt(4, move.destination_y());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Move> findAll() {
        Connection connection = getConnection();
        final String sql = "SELECT * FROM " + TABLE;
        ArrayList<Move> moves = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                int source_x = resultSet.getInt(1);
                int source_y = resultSet.getInt(2);
                int destination_x = resultSet.getInt(3);
                int destination_y = resultSet.getInt(4);
                Move move = new Move(source_x, source_y, destination_x, destination_y);
                moves.add(move);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return moves;
    }

    @Override
    public void clear() {
        Connection connection = getConnection();
        final String sql = "DELETE FROM " + TABLE;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}