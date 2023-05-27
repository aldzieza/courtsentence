package pl.olointeria.wyrok.database.dbutils;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import pl.olointeria.wyrok.database.models.Sentence;
import pl.olointeria.wyrok.database.models.Paragraph;
import pl.olointeria.wyrok.database.models.Sygnature;

import java.io.IOException;
import java.sql.SQLException;

public class DbManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(DbManager.class);

    //private static final String JDBC_DRIVER_HD = "jdbc:h2:./wyrokDBH2";
     private static final String JDBC_DRIVER_HD = "jdbc:sqlite:library.db";
    //sqlite
    private static final String USER = "wyrok";
    private static final String PASS = "wyrok";

    private static ConnectionSource connectionSource;

    public static void initDatabase(){
        createConnectionSource();
        dropTable(); //zakomentuj, żeby nie kasować za każym razem tabel w bazie
        createTable();
        closeConnectionSource();
    }

    private static void createConnectionSource(){
        try {
            connectionSource = new JdbcConnectionSource(JDBC_DRIVER_HD);
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
        }
    }

    public static ConnectionSource getConnectionSource(){
        if(connectionSource == null){
            createConnectionSource();
        }
        return connectionSource;
    }

    public static void closeConnectionSource(){
        if(connectionSource!=null){
            try {
                connectionSource.close();
            } catch (IOException e) {
                LOGGER.warn(e.getMessage());
            }
        }
    }

    private static void createTable(){
        try {
            TableUtils.createTableIfNotExists(connectionSource, Sygnature.class);
            TableUtils.createTableIfNotExists(connectionSource, Sentence.class);
            TableUtils.createTableIfNotExists(connectionSource, Paragraph.class);
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
        }
    }

    private  static  void  dropTable(){
        try {
            TableUtils.dropTable(connectionSource, Sygnature.class, true);
            TableUtils.dropTable(connectionSource, Sentence.class, true);
            TableUtils.dropTable(connectionSource, Paragraph.class, true);
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
        }
    }
}
