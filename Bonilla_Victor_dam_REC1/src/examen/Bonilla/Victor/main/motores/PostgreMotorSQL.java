package motores;

import java.sql.DriverManager;

public class PostgreMotorSQL extends MotorSQL {

    private static final String DRIVER   = "org.postgresql.Driver";
    private static final String URL      = "jdbc:postgresql://bonilla-victor-dam-recuperacion.clugbyfnus07.us-east-1.rds.amazonaws.com:5432/postgres";
    private static final String USER     = "postgres";
    private static final String PASSWORD = "postgres";

    public PostgreMotorSQL() {
        super(URL, USER, PASSWORD);
        this.driver = DRIVER;
    }

    @Override
    public void connect() throws Exception {
        Class.forName(driver);
        conn = DriverManager.getConnection(url, user, password);
    }
}