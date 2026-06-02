import beans.CentroForense;
import beans.MuestraForense;
import dao.CentroForenseDAOImpl;
import dao.MuestraForenseDAOImpl;
import motores.MotorFactory;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {


        // Instanciar DAOs con MotorFactory
        MuestraForenseDAOImpl muestraDAO = new MuestraForenseDAOImpl(
                MotorFactory.create(MotorFactory.POSTGRE)
        );
        CentroForenseDAOImpl centroForenseDAO = new CentroForenseDAOImpl(
                MotorFactory.create(MotorFactory.POSTGRE)
        );

        System.out.println("========================================");
        System.out.println("  TEST 1 — ADD MUESTRA");
        System.out.println("========================================");
        MuestraForense nuevo = new MuestraForense();
        nuevo.setCodigoCaso(1);
        nuevo.setTipoMuestra("Sangre");
        nuevo.setFechaRecogida("14/02/2026");
        nuevo.setEstadoCustodia("Protegido");
        CentroForense centro = new CentroForense();
        centro.setId(1);   // centro existente en BD
        nuevo.setCentro(centro);
        muestraDAO.add(nuevo);

        System.out.println("\n========================================");
        System.out.println("  TEST 2 — UPDATE MUESTRA");
        System.out.println("========================================");
        nuevo.setCodigoCaso(2);
        nuevo.setEstadoCustodia("Robado");
        muestraDAO.update(1, nuevo);

        System.out.println("\n========================================");
        System.out.println("  TEST 3 — FIND MUESTRA");
        System.out.println("========================================");
        MuestraForense encontrado = muestraDAO.find(1);
        System.out.println(encontrado);

        System.out.println("\n========================================");
        System.out.println("  TEST 4 — FIND ALL MUESTRAS");
        System.out.println("========================================");
        ArrayList<MuestraForense> todos = muestraDAO.findAll();
        todos.forEach(System.out::println);
        System.out.println("Total: " + todos.size());

        System.out.println("\n========================================");
        System.out.println("  TEST 5 — FIND SATELITES BY CENTRO");
        System.out.println("========================================");
        ArrayList<MuestraForense> porCentro = muestraDAO.findByCentro(1);
        porCentro.forEach(System.out::println);
        System.out.println("Total centro 1: " + porCentro.size());

        System.out.println("\n========================================");
        System.out.println("  TEST 6 — FIND MUESTRA CON INFORME");
        System.out.println("========================================");
        MuestraForense conInforme = muestraDAO.findWithDetail(1);
        System.out.println(conInforme);
        System.out.println("  Informe: " + conInforme.getInforme());


    }
}
