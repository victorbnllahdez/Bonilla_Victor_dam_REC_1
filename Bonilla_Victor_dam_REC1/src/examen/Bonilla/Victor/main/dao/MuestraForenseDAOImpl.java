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

    private static final String SQL_FIND_BY_AGENCIA =
            "SELECT * FROM MUESTRAS_FORENSES WHERE AGENCIA_ID=? ORDER BY CODIGO_CASO";


    private static final String SQL_FIND_WITH_DETAIL =
            "SELECT M.ID, M.CODIGO_CASO, M.TIPO_MUESTRA, M.FECHA_RECOGIDA, M.ESTADO_CUSTODIA, M.CENTRO_ID, " +
                    "I.ID, I.ADN_POSITIVO, I.NIVEL_RIESGO, I.CONCLUSION" +
                    "FROM MUESTRAS_FORENSES M" +
                    "INNER JOIN INFORMES_FORENSES I ON M.ID = I.MUESTRA_ID " +
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
        c.setId(rs.getInt(8));
        m.setCentro(c);
        // Estilo del proyecto de práctica: getDetalle() activa el lazy init
        s.getDetalle().setId(rs.getInt(9));
        s.getDetalle().setVelocidadMaxima(rs.getDouble(10));
        s.getDetalle().setCombustible(rs.getString(11));
        s.getDetalle().setVidaUtil(rs.getInt(12));
        s.getDetalle().setTemperaturaMaxima(rs.getDouble(13));
        return s;
    }

    // -------------------------------------------------------
    //  MAPPING — triple JOIN: satélite + agencia + detalle
    // -------------------------------------------------------
    private Satelite mapSateliteCompleto(ResultSet rs) throws Exception {
        Satelite s = new Satelite();
        s.setId(rs.getInt(1));
        s.setNombre(rs.getString(2));
        s.setOrbita(rs.getString(3));
        s.setPeso(rs.getDouble(4));
        s.setCoste(rs.getDouble(5));
        s.setActivo(rs.getBoolean(6));
        s.setFechaLanzamiento(rs.getString(7));
        // Agencia completa (nombre y pais del JOIN)
        Agencia a = new Agencia();
        a.setId(rs.getInt(8));
        a.setNombre(rs.getString(9));
        a.setPais(rs.getString(10));
        s.setAgencia(a);
        // Detalle con lazy init
        s.getDetalle().setId(rs.getInt(11));
        s.getDetalle().setVelocidadMaxima(rs.getDouble(12));
        s.getDetalle().setCombustible(rs.getString(13));
        s.getDetalle().setVidaUtil(rs.getInt(14));
        s.getDetalle().setTemperaturaMaxima(rs.getDouble(15));
        return s;
    }

    // -------------------------------------------------------
    //  CRUD
    // -------------------------------------------------------
    @Override
    public void add(Satelite s) {
        try {
            motorSQL.connect();
            motorSQL.prepare(SQL_INSERT);
            motorSQL.getPs().setString(1, s.getNombre());
            motorSQL.getPs().setString(2, s.getOrbita());
            motorSQL.getPs().setDouble(3, s.getPeso());
            motorSQL.getPs().setDouble(4, s.getCoste());
            motorSQL.getPs().setBoolean(5, s.isActivo());
            motorSQL.getPs().setString(6, s.getFechaLanzamiento());
            motorSQL.getPs().setInt(7, s.getAgencia().getId()); // FK como objeto
            motorSQL.getPs().setString(8, AUTOR);
            int rows = motorSQL.executeUpdate();
            System.out.println("[ADD SATELITE] Filas insertadas: " + rows);
        } catch (Exception e) {
            printError(e);
        } finally {
            motorSQL.close();
        }
    }

    @Override
    public void update(int id, Satelite s) {
        try {
            motorSQL.connect();
            motorSQL.prepare(SQL_UPDATE);
            motorSQL.getPs().setString(1, s.getNombre());
            motorSQL.getPs().setString(2, s.getOrbita());
            motorSQL.getPs().setDouble(3, s.getPeso());
            motorSQL.getPs().setDouble(4, s.getCoste());
            motorSQL.getPs().setBoolean(5, s.isActivo());
            motorSQL.getPs().setString(6, s.getFechaLanzamiento());
            motorSQL.getPs().setInt(7, s.getAgencia().getId());
            motorSQL.getPs().setInt(8, id); // id siempre el último
            int rows = motorSQL.executeUpdate();
            System.out.println("[UPDATE SATELITE] Filas actualizadas: " + rows);
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
            System.out.println("[DELETE SATELITE] Filas eliminadas: " + rows);
        } catch (Exception e) {
            printError(e);
        } finally {
            motorSQL.close();
        }
    }

    @Override
    public Satelite find(int id) {
        Satelite s = null;
        try {
            motorSQL.connect();
            motorSQL.prepare(SQL_FIND);
            motorSQL.getPs().setInt(1, id);
            ResultSet rs = motorSQL.executeQuery();
            if (rs.next()) s = mapSatelite(rs); // una fila → if
        } catch (Exception e) {
            printError(e);
        } finally {
            motorSQL.close();
        }
        return s;
    }

    @Override
    public ArrayList<Satelite> findAll() {
        ArrayList<Satelite> lista = new ArrayList<>();
        try {
            motorSQL.connect();
            motorSQL.prepare(SQL_FIND_ALL);
            ResultSet rs = motorSQL.executeQuery();
            while (rs.next()) lista.add(mapSatelite(rs)); // varias filas → while
        } catch (Exception e) {
            printError(e);
        } finally {
            motorSQL.close();
        }
        return lista;
    }

    // -------------------------------------------------------
    //  Consultas específicas de Satelite
    // -------------------------------------------------------

    // TEST 5 — satélites de una agencia concreta
    public ArrayList<Satelite> findByAgencia(int agenciaId) {
        ArrayList<Satelite> lista = new ArrayList<>();
        try {
            motorSQL.connect();
            motorSQL.prepare(SQL_FIND_BY_AGENCIA);
            motorSQL.getPs().setInt(1, agenciaId);
            ResultSet rs = motorSQL.executeQuery();
            while (rs.next()) lista.add(mapSatelite(rs));
        } catch (Exception e) {
            printError(e);
        } finally {
            motorSQL.close();
        }
        return lista;
    }

    // TEST 6 — satélite con su detalle (JOIN 1:1)
    public Satelite findWithDetail(int id) {
        Satelite s = null;
        try {
            motorSQL.connect();
            motorSQL.prepare(SQL_FIND_WITH_DETAIL);
            motorSQL.getPs().setInt(1, id);
            ResultSet rs = motorSQL.executeQuery();
            if (rs.next()) s = mapSateliteConDetalle(rs);
        } catch (Exception e) {
            printError(e);
        } finally {
            motorSQL.close();
        }
        return s;
    }

    // BONUS — satélites activos con agencia y detalle completos (triple JOIN)
    public ArrayList<Satelite> findActivosConTodo() {
        ArrayList<Satelite> lista = new ArrayList<>();
        try {
            motorSQL.connect();
            motorSQL.prepare(SQL_ACTIVOS_CON_TODO);
            ResultSet rs = motorSQL.executeQuery();
            while (rs.next()) lista.add(mapSateliteCompleto(rs));
        } catch (Exception e) {
            printError(e);
        } finally {
            motorSQL.close();
        }
        return lista;
    }
}
