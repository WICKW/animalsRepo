package app.repository;

import com.animals.app.domain.AnimalMedicalHistory;
import com.animals.app.repository.AnimalMedicalHistoryRepository;
import com.animals.app.repository.Impl.AnimalMedicalHistoryRepositoryImpl;
import com.animals.app.repository.Impl.AnimalRepositoryImpl;
import com.animals.app.repository.Impl.AnimalStatusRepositoryImpl;
import com.animals.app.repository.Impl.UserRepositoryImpl;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import org.apache.ibatis.exceptions.PersistenceException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Rostyslav.Viner on 31.08.2015.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestAnimalMedicalHistoryRepositoryImpl {

    private static AnimalMedicalHistoryRepository animalMedicalHistoryRepository;

    private static AnimalMedicalHistory actual;

    @BeforeClass
    public static void runBeforeClass() {
        configureJNDIForJUnit();

        animalMedicalHistoryRepository = new AnimalMedicalHistoryRepositoryImpl();

        actual = new AnimalMedicalHistory();
        actual.setStatus(new AnimalStatusRepositoryImpl().getAll().get(0));
        actual.setUser(new UserRepositoryImpl().getAll().get(0));
        actual.setAnimalId(new AnimalRepositoryImpl().getAnimalByUserId(actual.getUser().getId()).get(0).getId());
    }

    @AfterClass
    public static void runAfterClass() {
        animalMedicalHistoryRepository = null;
        actual = null;
    }

    @Test
    public void test01Insert() {
        assertNotNull(actual);
        assertNull(actual.getId());

        animalMedicalHistoryRepository.insert(actual);

        assertNotNull(actual.getId());
    }

    @Test(expected = PersistenceException.class)
    public void test02Insert() {
        AnimalMedicalHistory test = new AnimalMedicalHistory();

        animalMedicalHistoryRepository.insert(test);
    }

    @Test
    public void test03GetById() {
        assertNotNull(actual);
        assertNotNull(actual.getId());

        AnimalMedicalHistory expected = animalMedicalHistoryRepository.getById(actual.getId());

        assertNotNull(expected);
    }

    @Test
    public void test04GetById() {
        AnimalMedicalHistory expected = animalMedicalHistoryRepository.getById(-1);

        assertNull(expected);
    }

    @Test
    public void test05GetByAnimalId() {
        assertNotNull(actual);
        assertNotNull(actual.getAnimalId());

        List<AnimalMedicalHistory> expected = animalMedicalHistoryRepository.getByAnimalId(actual.getAnimalId(), 0, 1);

        assertNotNull(expected);
        assertEquals(expected.size(), 1);
    }

    @Test
    public void test06GetByAnimalId() {
        List<AnimalMedicalHistory> expected = animalMedicalHistoryRepository.getByAnimalId(-1, 0, 1);

        assertNotNull(expected);
        assertEquals(expected.size(), 0);
    }

    @Test
    public void test07GetByAnimalIdCount() {
        assertNotNull(actual);
        assertNotNull(actual.getAnimalId());

        long expected = animalMedicalHistoryRepository.getByAnimalIdCount(actual.getAnimalId());

        assertNotEquals(expected, 0);
    }

    @Test
    public void test08GetByAnimalIdCount() {
        assertNotNull(actual);
        assertNotNull(actual.getAnimalId());

        long expected = animalMedicalHistoryRepository.getByAnimalIdCount(-1);

        assertEquals(expected, 0);
    }

    @Test
    public void test09DeleteById() {
        assertNotNull(actual);
        assertNotNull(actual.getId());

        animalMedicalHistoryRepository.deleteById(actual.getId());

        AnimalMedicalHistory expected = animalMedicalHistoryRepository.getById(actual.getId());

        assertNull(expected);
    }

    private static void configureJNDIForJUnit(){
        // rcarver - setup the jndi context and the datasource
        try {
            // Create initial context
            System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                    "org.apache.naming.java.javaURLContextFactory");
            System.setProperty(Context.URL_PKG_PREFIXES,
                    "org.apache.naming");
            InitialContext ic = new InitialContext();

            ic.createSubcontext("java:");
            ic.createSubcontext("java:/comp");
            ic.createSubcontext("java:/comp/env");
            ic.createSubcontext("java:/comp/env/jdbc");

            // Construct DataSource
            MysqlConnectionPoolDataSource ds = new MysqlConnectionPoolDataSource();
            ds.setURL("jdbc:mysql://tym.dp.ua:3306/animals");
            ds.setUser("u_remoteuser");
            ds.setPassword("ZF008NBp");

            ic.bind("java:/comp/env/jdbc/animals", ds);
        } catch (NamingException ex) {
            ex.printStackTrace();
        }
    }

}
