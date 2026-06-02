package dao;

import beans.MuestraForense;
import motores.MotorSQL;
import beans.CentroForense;
import beans.InformeForense;

import java.sql.ResultSet;
import java.util.ArrayList;

public class MuestraForenseDAOImpl extends AbstractDAO<MuestraForense> {


    private static final String AUTOR = "Victor_Bonilla";

    // --- Consultas SQL ---
    private static final String SQL_INSERT =
            "INSERT INTO MUESTRAS_FORENSES (CODIGO_CASO, TIPO_MUESTRA, FECHA_RECOGIDA, ESTADO_CUSTODIA, CENTRO_ID, " +
                    "AUTOR_EXAMEN) VALUES (?,?,?,?,?,?)";

    private static final String SQL_UPDATE =
            "UPDATE MUESTRAS_FORENSES SET CODIGO_CASO=?, TIPO_MUESTRA=?, FECHA_RECOGIDA=?, ESTADO_CUSTODIA=?, " +
                    "CENTRO_ID=? WHERE ID=?";

    private static final String SQL_DELETE =
            "DELETE FROM MUESTRAS_FORENSES WHERE ID=?";

    private static final String SQL_FIND =
            "SELECT * FROM MUESTRAS_FORENSES WHERE ID=?";

    private static final String SQL_FIND_ALL =
            "SELECT * FROM MUESTRAS_FORENSES ORDER BY ID";

    private static final String SQL_FIND_BY_CENTRO =
            "SELECT * FROM MUESTRAS_FORENSES WHERE CENTRO_ID=? ORDER BY CODIGO_CASO";


    private static final String SQL_FIND_WITH_DETAIL =
            "SELECT M.ID, M.CODIGO_CASO, M.TIPO_MUESTRA, M.FECHA_RECOGIDA, M.ESTADO_CUSTODIA, M.CENTRO_ID, " +
                    "I.ID, I.ADN_POSITIVO, I.NIVEL_RIESGO, I.CONCLUSION " +
                    " FROM MUESTRAS_FORENSES M " +
                    " INNER JOIN INFORMES_FORENSES I ON M.ID = I.MUESTRA_ID " +
                    "WHERE M.ID=?";



    public MuestraForenseDAOImpl(MotorSQL motorSQL) {
        super(motorSQL);
    }

    // -------------------------------------------------------
    //  MAPPING — sin JOIN (usa nombres de columna)
    // -------------------------------------------------------
    private MuestraForense mapMuestra(ResultSet rs) throws Exception {
        MuestraForense m = new MuestraForense();
        m.setId(rs.getInt("id"));
        m.setCodigoCaso(rs.getInt("codigo_caso"));
        m.setTipoMuestra(rs.getString("tipo_muestra"));
        m.setFechaRecogida(rs.getString("fecha_recogida"));
        m.setEstadoCustodia(rs.getString("estado_custodia"));
        CentroForense c = new CentroForense();
        c.setId(rs.getInt("centro_id"));
        m.setCentro(c);
        return m;
    }

    // -------------------------------------------------------
    //  MAPPING — JOIN satélite + detalle (usa ÍNDICES)
    //  Estilo del proyecto de clase: getDetalle() con lazy init
    // -------------------------------------------------------
    private MuestraForense mapInformeForense(ResultSet rs) throws Exception {
        MuestraForense m = new MuestraForense();
        m.setId(rs.getInt(1));
        m.setCodigoCaso(rs.getInt(2));
        m.setTipoMuestra(rs.getString(3));
        m.setFechaRecogida(rs.getString(4));
        m.setEstadoCustodia(rs.getString(5));
        CentroForense c = new CentroForense();
        c.setId(rs.getInt(6));
        m.setCentro(c);
        // Estilo del proyecto de práctica: getDetalle() activa el lazy init
        m.getInforme().setId(rs.getInt(7));
        m.getInforme().setAdnPositivo(rs.getBoolean(8));
        m.getInforme().setNivelRiesgo(rs.getInt(9));
        m.getInforme().setConclusion(rs.getString(10));
        return m;
    }

    // -------------------------------------------------------
    //  MAPPING — triple JOIN: muestra + centro + informe
    // -------------------------------------------------------
    private MuestraForense mapMuestraCompleto(ResultSet rs) throws Exception {
        MuestraForense m = new MuestraForense();
        m.setId(rs.getInt(1));
        m.setCodigoCaso(rs.getInt(2));
        m.setTipoMuestra(rs.getString(3));
        m.setFechaRecogida(rs.getString(4));
        m.setEstadoCustodia(rs.getString(5));

        CentroForense c = new CentroForense();
        c.setId(rs.getInt(6));
        c.setNombre(rs.getString(7));
        c.setPais(rs.getString(8));
        m.setCentro(c);
        // Detalle con lazy init
        m.getInforme().setId(rs.getInt(9));
        m.getInforme().setAdnPositivo(rs.getBoolean(10));
        m.getInforme().setNivelRiesgo(rs.getInt(11));
        m.getInforme().setConclusion(rs.getString(12));
        return m;
    }

    // -------------------------------------------------------
    //  CRUD
    // -------------------------------------------------------
    @Override
    public void add(MuestraForense m) {
        try {
            motorSQL.connect();
            motorSQL.prepare(SQL_INSERT);
            motorSQL.getPs().setInt(1, m.getCodigoCaso());
            motorSQL.getPs().setString(2, m.getTipoMuestra());
            motorSQL.getPs().setString(3, m.getFechaRecogida());
            motorSQL.getPs().setString(4, m.getEstadoCustodia());
            motorSQL.getPs().setInt(5, m.getCentro().getId()); // FK como objeto
            motorSQL.getPs().setString(6, AUTOR);
            int rows = motorSQL.executeUpdate();
            System.out.println("[ADD MUESTRAS_FORENSES] Filas insertadas: " + rows);
        } catch (Exception e) {
            printError(e);
        } finally {
            motorSQL.close();
        }
    }

    @Override
    public void update(int id, MuestraForense m) {
        try {
            motorSQL.connect();
            motorSQL.prepare(SQL_UPDATE);
            motorSQL.getPs().setInt(1, m.getCodigoCaso());
            motorSQL.getPs().setString(2, m.getTipoMuestra());
            motorSQL.getPs().setString(3, m.getFechaRecogida());
            motorSQL.getPs().setString(4, m.getEstadoCustodia());
            motorSQL.getPs().setInt(5, m.getCentro().getId()); // FK como objeto
            motorSQL.getPs().setInt(6, id); // id siempre el último
            int rows = motorSQL.executeUpdate();
            System.out.println("[UPDATE MUESTRAS_FORENSES] Filas actualizadas: " + rows);
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
            System.out.println("[DELETE MUESTRAS_FORENSES] Filas eliminadas: " + rows);
        } catch (Exception e) {
            printError(e);
        } finally {
            motorSQL.close();
        }
    }

    @Override
    public MuestraForense find(int id) {
        MuestraForense m = null;
        try {
            motorSQL.connect();
            motorSQL.prepare(SQL_FIND);
            motorSQL.getPs().setInt(1, id);
            ResultSet rs = motorSQL.executeQuery();
            if (rs.next()) m = mapMuestra(rs); // una fila → if
        } catch (Exception e) {
            printError(e);
        } finally {
            motorSQL.close();
        }
        return m;
    }

    @Override
    public ArrayList<MuestraForense> findAll() {
        ArrayList<MuestraForense> lista = new ArrayList<>();
        try {
            motorSQL.connect();
            motorSQL.prepare(SQL_FIND_ALL);
            ResultSet rs = motorSQL.executeQuery();
            while (rs.next()) lista.add(mapMuestra(rs)); // varias filas → while
        } catch (Exception e) {
            printError(e);
        } finally {
            motorSQL.close();
        }
        return lista;
    }

    // -------------------------------------------------------
    //  Consultas específicas de Muestras
    // -------------------------------------------------------

    // TEST 5 — muestras de un centro concreto
    public ArrayList<MuestraForense> findByCentro(int centroId) {
        ArrayList<MuestraForense> lista = new ArrayList<>();
        try {
            motorSQL.connect();
            motorSQL.prepare(SQL_FIND_BY_CENTRO);
            motorSQL.getPs().setInt(1, centroId);
            ResultSet rs = motorSQL.executeQuery();
            while (rs.next()) lista.add(mapMuestra(rs));
        } catch (Exception e) {
            printError(e);
        } finally {
            motorSQL.close();
        }
        return lista;
    }

    // TEST 6 — satélite con su detalle (JOIN 1:1)
    public MuestraForense findWithDetail(int id) {
        MuestraForense m = null;
        try {
            motorSQL.connect();
            motorSQL.prepare(SQL_FIND_WITH_DETAIL);
            motorSQL.getPs().setInt(1, id);
            ResultSet rs = motorSQL.executeQuery();
            if (rs.next()) m = mapMuestraCompleto(rs);
        } catch (Exception e) {
            printError(e);
        } finally {
            motorSQL.close();
        }
        return m;
    }


}
