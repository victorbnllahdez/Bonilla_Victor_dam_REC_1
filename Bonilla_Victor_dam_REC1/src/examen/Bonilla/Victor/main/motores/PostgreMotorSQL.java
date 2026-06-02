package motores;

import java.sql.DriverManager;

public class PostgreMotorSQL extends MotorSQL {

    private static final String URL      = "jdbc:postgresql://TU_HOST.rds.amazonaws.com:5432/bonilla-victor-dam-recuperacion";
    private static final String USER     = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String DRIVER   = "org.postgresql.Driver";
    // ============================================================

    public PostgreMotorSQL() {
        super(URL, USER, PASSWORD, DRIVER);
    }

    @Override
    public void connect() throws Exception {
        Class.forName(driver);
        conn = DriverManager.getConnection(url, user, password);
    }
}