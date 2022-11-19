package tdl2.entrega3.DAO;

import tdl2.entrega3.DAO.implJDBC.*;

public class FactoryDAO {
    public static PaisDAOjdbc getPaisDAO() {
        return new PaisDAOjdbc();
    }

    public static FutbolistaDAOjdbc getFutbolistaDAO() {
        return new FutbolistaDAOjdbc();
    }
}
