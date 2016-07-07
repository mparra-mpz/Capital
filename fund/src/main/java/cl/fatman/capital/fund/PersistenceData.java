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
		EntityManager entityManager = null;
		try {
			logger.debug("Creating a new EntityManager.");
			entityManager = entityManagerFactory.createEntityManager();
			logger.debug("Beginning a new transaction.");
			entityManager.getTransaction().begin();
			for (Object object: objectList) {
				logger.debug("Saving object.");
				entityManager.persist(object);
			}
			logger.debug("Committing and closing the transaction.");
			entityManager.getTransaction().commit();
			logger.debug("Object list successfully stored.");
		}
		catch (Exception e) {
			if (entityManager != null && entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
				logger.debug("Finish transaction rollback.");
			}
			logger.error("Problem storing the object list.", e);
		}
		finally {
			if (entityManager != null && entityManager.isOpen()) {
				entityManager.close();
				logger.debug("EntityManager closed.");
			}
		}
	}
	
	public List<?> selectAllObjects(String fromTable, Class<?> resultClass) {
		logger.debug("selectAllObjects()");
		List<?> resultList = null;
		EntityManager entityManager = null;
		try {
			logger.debug("Opening a new EntityManager.");
			entityManager = entityManagerFactory.createEntityManager();
			logger.debug("Beginning a new transaction.");
			entityManager.getTransaction().begin();
			logger.debug("Executing query: " + fromTable);
			resultList = entityManager.createQuery(fromTable, resultClass).getResultList();
			logger.debug("Committing and closing the transaction.");
			entityManager.getTransaction().commit();
			logger.debug("Object list retrieved successfully.");
		}
		catch (Exception e) {
			if (entityManager != null && entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
				logger.error("Finish transaction rollback.");
			}
			logger.error("Problem retrieving object list.", e);
			resultList = null;
		}
		finally {
			if (entityManager != null && entityManager.isOpen()) {
				entityManager.close();
				logger.debug("EntityManager closed.");
			}
		}
		return resultList;
	}
	
	public List<?> getFundByType(FundType type) {
		logger.debug("selectFundByType(FundType type)");
		List<?> resultList = null;
		EntityManager entityManager = null;
		try {
			logger.debug("Opening a new EntityManager.");
			entityManager = entityManagerFactory.createEntityManager();
			logger.debug("Beginning a new transaction.");
			entityManager.getTransaction().begin();
			logger.debug("Executing query: get_fund_by_type");
			Query query = entityManager.createNamedQuery("get_fund_by_type");
			query.setParameter("type", type);
			resultList = query.getResultList();
			logger.debug("Committing and closing the transaction.");
			entityManager.getTransaction().commit();
			logger.debug("Object list retrieved successfully.");
		}
		catch (Exception e) {
			if (entityManager != null && entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
				logger.debug("Finish transaction rollback.");
			}
			logger.error("Problem retrieving Fund object.", e);
			resultList = null;
		}
		finally {
			if (entityManager != null && entityManager.isOpen()) {
				entityManager.close();
				logger.debug("EntityManager closed.");
			}
		}
		return resultList;
	}
	
	public List<?> getFundUpdateDate() {
		logger.debug("getUpdateDate()");
		List<?> resultList = null;
		EntityManager entityManager = null;
		try {
			logger.debug("Opening a new EntityManager.");
			entityManager = entityManagerFactory.createEntityManager();
			logger.debug("Beginning a new transaction.");
			entityManager.getTransaction().begin();
			logger.debug("Executing query: get_fund_update_date");
			Query query = entityManager.createNamedQuery("get_fund_update_date");
			resultList = query.getResultList();
			logger.debug("Committing and closing the transaction.");
			entityManager.getTransaction().commit();
			logger.debug("Object list retrieved successfully.");
		}
		catch (Exception e) {
			if (entityManager != null && entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
				logger.debug("Finish transaction rollback.");
			}
			logger.error("Problem retrieving Fund object.", e);
			resultList = null;
		}
		finally {
			if (entityManager != null && entityManager.isOpen()) {
				entityManager.close();
				logger.debug("EntityManager closed.");
			}
		}
		return resultList;
	}
}