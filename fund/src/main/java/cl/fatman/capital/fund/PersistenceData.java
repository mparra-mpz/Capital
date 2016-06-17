package cl.fatman.capital.fund;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import java.util.List;

public class PersistenceData {
	
	private static final PersistenceData INSTANCE = new PersistenceData();
	private EntityManagerFactory entityManagerFactory;
	static final Logger logger = Logger.getLogger(PersistenceData.class);
	
	private PersistenceData() {
		
	}
	
	public static PersistenceData getInstance() {
		return INSTANCE;
	}
	
	public void setUp() {
		logger.debug("setUp()");
		try {
			logger.debug("Creating EntityManagerFactory.");
			entityManagerFactory = Persistence.createEntityManagerFactory("cl.fatman.capital.fund.jpa");
			logger.debug("EntityManagerFactory successfully created.");
		}
		catch (Exception e) {
			logger.error("Problem in the EntityManagerFactory creation.", e);
		}
	}
	
	public void tearDown() {
		logger.debug("tearDown()");
		try {
			logger.debug("Closing the EntityManagerFactory.");
			entityManagerFactory.close();
			logger.debug("EntityManagerFactory successfully closed.");
		}
		catch (Exception e) {
			logger.error("Problem in the EntityManagerFactory closing.", e);
		}
	}
	
	public void insertObjectList(List<?> objectList) {
		logger.debug("insertObjectList(List<?> objectList)");
		try {
			logger.debug("Creating a new EntityManager.");
			EntityManager entityManager = entityManagerFactory.createEntityManager();
			for (Object object: objectList) {
				entityManager.getTransaction().begin();
				entityManager.persist(object);
				entityManager.getTransaction().commit();
				logger.debug("Transaction commited.");
			}
			logger.debug("Closing the EntityManager.");
			entityManager.close();
			logger.debug("Object list successfully stored.");
		}
		catch (Exception e) {
			logger.error("Problem storing the object list.", e);
		}
	}
	
	public List<?> selectAllObjects(String fromTable, Class<?> resultClass) {
		logger.debug("selectAllObjects()");
		List<?> resultList = null;
		try {
			logger.debug("Opening a new EntityManager.");
			EntityManager entityManager = entityManagerFactory.createEntityManager();
			entityManager.getTransaction().begin();
			resultList = entityManager.createQuery(fromTable, resultClass).getResultList();
			entityManager.getTransaction().commit();
			logger.debug("Transaction commited.");
			logger.debug("Closing the EntityManager.");
			entityManager.close();
			logger.debug("Object list retrieved successfully.");
		}
		catch (Exception e) {
			logger.error("Problem retrieving object list.", e);
			resultList = null;
		}
		return resultList;
	}
	
	public FundType selectFundType(int id) {
		logger.debug("selectFundType(int id)");
		FundType type = null;
		try {
			logger.debug("Opening a new EntityManager.");
			EntityManager entityManager = entityManagerFactory.createEntityManager();
			entityManager.getTransaction().begin();
			logger.info("Executing query: get_fund_type_by_id.");
			Query query = entityManager.createNamedQuery("get_fund_type_by_id");
			query.setParameter("id", id);
			type = (FundType) query.getSingleResult();
			entityManager.getTransaction().commit();
			logger.debug("Transaction commited.");
			logger.debug("Closing the EntityManager.");
			entityManager.close();
			logger.debug("Fund Type object retrieve successfully.");
		}
		catch (Exception e) {
			logger.error("Problem retrieving Fund Type object.", e);
			type = null;
		}
		return type;
	}
	
	public Fund selectFund(String run, String series) {
		logger.debug("selectFund(String run, String series)");
		Fund fund = null;
		try {
			logger.debug("Opening a new EntityManager.");
			EntityManager entityManager = entityManagerFactory.createEntityManager();
			entityManager.getTransaction().begin();
			logger.info("Executing query: get_fund_by_run_series");
			Query query = entityManager.createNamedQuery("get_fund_by_run_series");
			query.setParameter("run", run);
			query.setParameter("series", series);
			fund = (Fund) query.getSingleResult();
			entityManager.getTransaction().commit();
			logger.debug("Transaction commited.");
			logger.debug("Closing the EntityManager.");
			entityManager.close();
			logger.debug("Fund object retrieve successfully.");
		}
		catch (Exception e) {
			logger.error("Problem retrieving Fund object.", e);
			fund = null;
		}
		return fund;
	}
}