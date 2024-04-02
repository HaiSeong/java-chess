package chess.repository;

import chess.repository.dto.Movement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlMovementRepository implements MovementRepository {

    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "chess";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String TABLE = "movement";
    private static final int SOURCE_COLUMN_INDEX = 1;
    private static final int SOURCE_RANK_INDEX = 2;
    private static final int DESTINATION_COLUMN_INDEX = 3;
    private static final int DESTINATION_RANK_INDEX = 4;

    public Connection getConnection() {
        final String sql = "jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION;
        try {
            return DriverManager.getConnection(sql, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("커넥션을 생성하지 못했습니다.");
        }
    }

    @Override
    public void save(Movement movement) {
        final String sql = "INSERT INTO " + TABLE
                + " (source_column, source_rank, destination_column, destination_rank) VALUES (?, ?, ?, ?)";
        try (final Connection connection = getConnection();
             final PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(SOURCE_COLUMN_INDEX, movement.source_column());
            statement.setInt(SOURCE_RANK_INDEX, movement.source_rank());
            statement.setInt(DESTINATION_COLUMN_INDEX, movement.destination_column());
            statement.setInt(DESTINATION_RANK_INDEX, movement.destination_rank());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("음직임을 저장하는데 실패했습니다.");
        }
    }

    @Override
    public List<Movement> findAll() {
        final String sql = "SELECT * FROM " + TABLE;
        final ArrayList<Movement> movements = new ArrayList<>();
        try (final Connection connection = getConnection();
             final PreparedStatement statement = connection.prepareStatement(sql);
             final ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                final int source_column = resultSet.getInt(SOURCE_COLUMN_INDEX);
                final int source_rank = resultSet.getInt(SOURCE_RANK_INDEX);
                final int destination_column = resultSet.getInt(DESTINATION_COLUMN_INDEX);
                final int destination_rank = resultSet.getInt(DESTINATION_RANK_INDEX);
                final Movement movement = new Movement(source_column, source_rank, destination_column,
                        destination_rank);
                movements.add(movement);
            }
        } catch (SQLException e) {
            throw new RuntimeException("음직임을 불러오는데 실패했습니다.");
        }
        return movements;
    }

    @Override
    public void clear() {
        final String sql = "DELETE FROM " + TABLE;
        try (final Connection connection = getConnection();
             final PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("음직임 테이블을 지우는데 실패했습니다.");
        }
    }
}
