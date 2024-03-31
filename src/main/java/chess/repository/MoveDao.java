package chess.repository;

import chess.dto.Movement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MoveDao implements MoveRepository {

    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "chess";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String TABLE = "movement";

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void save(Movement movement) {
        Connection connection = getConnection();
        final String sql =
                "INSERT INTO " + TABLE + " (source_column, source_rank, destination_column, destination_rank) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, movement.source_column());
            statement.setInt(2, movement.source_rank());
            statement.setInt(3, movement.destination_column());
            statement.setInt(4, movement.destination_rank());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Movement> findAll() {
        Connection connection = getConnection();
        final String sql = "SELECT * FROM " + TABLE;
        ArrayList<Movement> movements = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                int source_column = resultSet.getInt(1);
                int source_rank = resultSet.getInt(2);
                int destination_column = resultSet.getInt(3);
                int destination_rank = resultSet.getInt(4);
                Movement movement = new Movement(source_column, source_rank, destination_column, destination_rank);
                movements.add(movement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movements;
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
