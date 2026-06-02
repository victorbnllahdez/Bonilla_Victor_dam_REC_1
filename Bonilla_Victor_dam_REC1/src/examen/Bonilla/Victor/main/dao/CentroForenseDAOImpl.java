package dao;


import beans.CentroForense;
import motores.MotorSQL;

import java.sql.ResultSet;
import java.util.ArrayList;
public class CentroForenseDAOImpl extends AbstractDAO<CentroForense> {

    private static final String AUTOR = "Victor_Bonilla";

    private static final String SQL_INSERT =
            "INSERT INTO CENTROS_FORENSES (NOMBRE, PAIS, NIVEL_SEGURIDAD, AUTOR_EXAMEN) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE =
            "UPDATE CENTROS_FORENSES SET NOMBRE=?, PAIS=?, NIVEL_SEGURIDAD=? WHERE ID=?";
    private static final String SQL_DELETE =
            "DELETE FROM CENTROS_FORENSES WHERE ID=?";
    private static final String SQL_FIND =
            "SELECT * FROM CENTROS_FORENSES WHERE ID=?";
    private static final String SQL_FIND_ALL =
            "SELECT * FROM CENTROS_FORENSES ORDER BY ID";

    public CentroForenseDAOImpl(MotorSQL motorSQL) {
        super(motorSQL);
    }

    // Convierte una fila del ResultSet en un objeto Agencia
    private CentroForense mapCentroForense(ResultSet rs) throws Exception {
        CentroForense c = new CentroForense();
        c.setId(rs.getInt("id"));
        c.setNombre(rs.getString("nombre"));
        c.setPais(rs.getString("pais"));
        c.setNivelSeguridad(rs.getInt("nivel_seguridad"));
        return c;
    }

    @Override
    public void add(CentroForense a) {
        try {
            motorSQL.connect();
            motorSQL.prepare(SQL_INSERT);
            motorSQL.getPs().setString(1, a.getNombre());
            motorSQL.getPs().setString(2, a.getPais());
            motorSQL.getPs().setInt(3, a.getNivelSeguridad());
            motorSQL.getPs().setString(4, AUTOR);
            int rows = motorSQL.executeUpdate();
            System.out.println("[ADD CENTROS_FORENSES] Filas insertadas: " + rows);
        } catch (Exception e) {
            printError(e);
        } finally {
            motorSQL.close();
        }
    }

    @Override
    public void update(int id, CentroForense c) {
        try {
            motorSQL.connect();
            motorSQL.prepare(SQL_UPDATE);
            motorSQL.getPs().setString(1, c.getNombre());
            motorSQL.getPs().setString(2, c.getPais());
            motorSQL.getPs().setInt(3, c.getNivelSeguridad());
            motorSQL.getPs().setInt(4, id);
            int rows = motorSQL.executeUpdate();
            System.out.println("[UPDATE CENTROS_FORENSES] Filas actualizadas: " + rows);
        } catch (Exception e) {
            printError(e);
        } finally {
            motorSQL.close();
        }
    }

    @Override
    public void delete(int id) {
        try {
            motorSQL.connect();
            motorSQL.prepare(SQL_DELETE);
            motorSQL.getPs().setInt(1, id);
            int rows = motorSQL.executeUpdate();
            System.out.println("[DELETE CENTROS_FORENSES] Filas eliminadas: " + rows);
        } catch (Exception e) {
            printError(e);
        } finally {
            motorSQL.close();
        }
    }

    @Override
    public CentroForense find(int id) {
        CentroForense c = null;
        try {
            motorSQL.connect();
            motorSQL.prepare(SQL_FIND);
            motorSQL.getPs().setInt(1, id);
            ResultSet rs = motorSQL.executeQuery();
            if (rs.next()) c = mapCentroForense(rs);   // una sola fila → if
        } catch (Exception e) {
            printError(e);
        } finally {
            motorSQL.close();
        }
        return c;
    }

    @Override
    public ArrayList<CentroForense> findAll() {
        ArrayList<CentroForense> lista = new ArrayList<>();
        try {
            motorSQL.connect();
            motorSQL.prepare(SQL_FIND_ALL);
            ResultSet rs = motorSQL.executeQuery();
            while (rs.next()) lista.add(mapCentroForense(rs));  // varias filas → while
        } catch (Exception e) {
            printError(e);
        } finally {
            motorSQL.close();
        }
        return lista;
    }
}
