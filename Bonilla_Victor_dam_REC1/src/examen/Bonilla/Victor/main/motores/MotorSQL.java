package motores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class MotorSQL {

    protected String url;
    protected String user;
    protected String password;
    protected String driver;
    protected Connection conn;
    protected PreparedStatement ps;

    public MotorSQL(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.driver = driver;
    }

    // Cada BD implementa su propia conexión
    public abstract void connect() throws Exception;

    public void prepare(String SQL) throws Exception {
        ps = conn.prepareStatement(SQL);
    }

    public ResultSet executeQuery() throws Exception {
        return ps.executeQuery();
    }

    public int executeUpdate() throws Exception {
        return ps.executeUpdate();
    }

    public PreparedStatement getPs() {
        return ps;
    }

    public void close() {
        try { if (ps   != null) ps.close();   } catch (Exception e) { System.out.println("[CLOSE ERROR] " + e.getMessage()); }
        try { if (conn != null) conn.close();  } catch (Exception e) { System.out.println("[CLOSE ERROR] " + e.getMessage()); }
    }
}
