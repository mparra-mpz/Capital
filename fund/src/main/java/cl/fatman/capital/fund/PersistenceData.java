package cl.fatman.capital.fund;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;

import java.util.List;

public class PersistenceData {
	
	private static final PersistenceData INSTANCE = new PersistenceData();
	static final Logger logger = Logger.getLogger(PersistenceData.class);
	private EntityManagerFactory entityManagerFactory;
	
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
	
	public void createFomentUnitList(List<FomentUnit> fomentUnitList) {
		logger.debug("createFomentUnitList(List<FomentUnit> fomentUnitList)");
		try {
			logger.debug("Opening a new EntityManager.");
			EntityManager entityManager = entityManagerFactory.createEntityManager();
			for (FomentUnit fomentUnit: fomentUnitList) {
				logger.debug("Beginning the transaction.");
				entityManager.getTransaction().begin();
				logger.debug("Saving the Foment Unit object.");
				entityManager.persist(fomentUnit);
				logger.debug("Commit the transaction.");
				entityManager.getTransaction().commit();
			}
			logger.debug("Closing the EntityManager.");
			entityManager.close();
			logger.debug("Object successfully stored.");
		}
		catch (Exception e) {
			logger.error("Problem storing the Foment Unit list.", e);
		}
	}
	
	public List<FomentUnit> selectAllFomentUnit() {
		logger.debug("selectAllFomentUnit()");
		List<FomentUnit> resultList = null;
		try {
			logger.debug("Opening a new EntityManager.");
			EntityManager entityManager = entityManagerFactory.createEntityManager();
			logger.debug("Beginning the transaction.");
			entityManager.getTransaction().begin();
			logger.debug("Retrieve all the Foment Unit objects.");
			resultList = entityManager.createQuery("from FomentUnit", FomentUnit.class).getResultList();
			logger.debug("Commit the transaction.");
			entityManager.getTransaction().commit();
			logger.debug("Closing the EntityManager.");
			entityManager.close();
			logger.debug("Foment Unit list retrieved successfully.");
		}
		catch (Exception e) {
			logger.error("Problem retrieving Foment Unit list.", e);
			resultList = null;
		}
		return resultList;
	}
	
	public void createFundList(List<Fund> fundList) {
		logger.debug("createFundList(List<Fund> fundList)");
		try {
			logger.debug("Opening a new EntityManager.");
			EntityManager entityManager = entityManagerFactory.createEntityManager();
			for (Fund fund: fundList) {
				logger.debug("Beginning the transaction.");
				entityManager.getTransaction().begin();
				logger.debug("Saving the Fund object.");
				entityManager.persist(fund);
				logger.debug("Commit the transaction.");
				entityManager.getTransaction().commit();
			}
			logger.debug("Closing the EntityManager.");
			entityManager.close();
			logger.debug("Object successfully stored.");
		}
		catch (Exception e) {
			logger.error("Problem storing the Fund list.", e);
		}
	}
	
	public List<Fund> selectAllFund() {
		logger.debug("selectAllFund()");
		List<Fund> resultList = null;
		try {
			logger.debug("Opening a new EntityManager.");
			EntityManager entityManager = entityManagerFactory.createEntityManager();
			logger.debug("Beginning the transaction.");
			entityManager.getTransaction().begin();
			logger.debug("Retrieve all the Fund objects.");
			resultList = entityManager.createQuery("from Fund", Fund.class).getResultList();
			logger.debug("Commit the transaction.");
			entityManager.getTransaction().commit();
			logger.debug("Closing the EntityManager.");
			entityManager.close();
			logger.debug("Fund List retrieved successfully.");
		}
		catch (Exception e) {
			logger.error("Problem retrieving Fund list.", e);
			resultList = null;
		}
		return resultList;
	}
}