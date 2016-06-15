package cl.fatman.capital.fund;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
	
	public void connect() {
		logger.debug("connect()");
		try {
			logger.debug("Creating EntityManagerFactory.");
			entityManagerFactory = Persistence.createEntityManagerFactory("cl.fatman.capital.fund.jpa");
			logger.debug("EntityManagerFactory successfully created.");
		}
		catch (Exception e) {
			logger.error("Problem in the EntityManagerFactory creation.", e);
		}
	}
	
	public void close() {
		logger.debug("close()");
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
			logger.debug("Opening a new EntityManager.");
			EntityManager entityManager = entityManagerFactory.createEntityManager();
			for (Object object: objectList) {
				logger.debug("Beginning the transaction.");
				entityManager.getTransaction().begin();
				logger.debug("Saving the object.");
				entityManager.persist(object);
				logger.debug("Commit the transaction.");
				entityManager.getTransaction().commit();
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
			logger.debug("Beginning the transaction.");
			entityManager.getTransaction().begin();
			logger.debug("Retrieve all objects " + fromTable + ".");
			resultList = entityManager.createQuery(fromTable, resultClass).getResultList();
			logger.debug("Commit the transaction.");
			entityManager.getTransaction().commit();
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
}