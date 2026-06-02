package dao;

import beans.CentroForense;
import beans.InformeForense;
import motores.MotorSQL;

import java.sql.ResultSet;
import java.util.ArrayList;

public class InformeForenseDAOImpl extends AbstractDAO<InformeForense>{

    private static final String AUTOR = "Victor_Bonilla";

    private static final String SQL_INSERT =
            "INSERT INTO INFORMES_FORENSES (ADN_POSITIVO, NIVEL_RIESGO, CONCLUSION, MUESTRA_ID, AUTOR_EXAMEN) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE =
            "UPDATE INFORMES_FORENSES SET ADN_POSITIVO=?, NIVEL_RIESGO=?, CONCLUSION=?,MUESTRA_ID=?  WHERE ID=?";
    private static final String SQL_DELETE =
            "DELETE FROM INFORMES_FORENSES WHERE ID=?";
    private static final String SQL_FIND =
            "SELECT * FROM INFORMES_FORENSES WHERE ID=?";
    private static final String SQL_FIND_ALL =
            "SELECT * FROM INFORMES_FORENSES ORDER BY ID";

    public InformeForenseDAOImpl(MotorSQL motorSQL) {
        super(motorSQL);
    }

    private InformeForense mapInformeForense(ResultSet rs) throws Exception {
        InformeForense i = new InformeForense();
        i.setId(rs.getInt("id"));
        i.setAdnPositivo(rs.getBoolean("adn_positivo"));
        i.setNivelRiesgo(rs.getInt("nivel_riesgo"));
        i.setConclusion(rs.getString("conclusion"));
        return i;
    }

    @Override
    public void add(InformeForense i) {
        try {
            motorSQL.connect();
            motorSQL.prepare(SQL_INSERT);
            motorSQL.getPs().setBoolean(1, i.isAdnPositivo());
            motorSQL.getPs().setInt(2, i.getNivelRiesgo());
            motorSQL.getPs().setString(3, i.getConclusion());
            motorSQL.getPs().setString(4, AUTOR);
            int rows = motorSQL.executeUpdate();
            System.out.println("[ADD INFORMES_FORENSES] Filas insertadas: " + rows);
        } catch (Exception e) {
            printError(e);
        } finally {
            motorSQL.close();
        }
    }

    @Override
    public void update(int id, InformeForense i) {
        try {
            motorSQL.connect();
            motorSQL.prepare(SQL_UPDATE);
            motorSQL.getPs().setBoolean(1, i.isAdnPositivo());
            motorSQL.getPs().setInt(2, i.getNivelRiesgo());
            motorSQL.getPs().setString(3, i.getConclusion());
            motorSQL.getPs().setInt(4, id);
            int rows = motorSQL.executeUpdate();
            System.out.println("[UPDATE INFORMES_FORENSES] Filas actualizadas: " + rows);
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
            System.out.println("[DELETE INFORMES_FORENSES] Filas eliminadas: " + rows);
        } catch (Exception e) {
            printError(e);
        } finally {
            motorSQL.close();
        }
    }

    @Override
    public InformeForense find(int id) {
        InformeForense i = null;
        try {
            motorSQL.connect();
            motorSQL.prepare(SQL_FIND);
            motorSQL.getPs().setInt(1, id);
            ResultSet rs = motorSQL.executeQuery();
            if (rs.next()) i = mapInformeForense(rs);   // una sola fila → if
        } catch (Exception e) {
            printError(e);
        } finally {
            motorSQL.close();
        }
        return i;
    }

    @Override
    public ArrayList<InformeForense> findAll() {
        ArrayList<InformeForense> lista = new ArrayList<>();
        try {
            motorSQL.connect();
            motorSQL.prepare(SQL_FIND_ALL);
            ResultSet rs = motorSQL.executeQuery();
            while (rs.next()) lista.add(mapInformeForense(rs));  // varias filas → while
        } catch (Exception e) {
            printError(e);
        } finally {
            motorSQL.close();
        }
        return lista;
    }
}
