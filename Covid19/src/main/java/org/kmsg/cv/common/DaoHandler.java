package org.kmsg.cv.common;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class DaoHandler implements CVLogger
{
	private  static PlatformTransactionManager transactionManager;
	private  TransactionStatus status;
	private  String id ;
		
	static {
		DataSourceTransactionManager manage = new DataSourceTransactionManager(Constants.dataSource);
		transactionManager = manage;
	}
	
	public void start(String methodName)
	{
		id = Float.toString( Math.round(Math.random() * 10000 ));
		logger.info("DaoHandlerObject Started: "+ id+ " for service: "+methodName);
		TransactionDefinition def = new DefaultTransactionDefinition();
		status = transactionManager.getTransaction(def);
	}

	public void rollback(String methodName)
	{
		logger.info("rollback Called: "+ id+" for service: "+methodName);
		transactionManager.rollback(status);
	}

	public void commit(String methodName)
	{
		logger.info("commit Called: "+id+ " for service: "+methodName);
		transactionManager.commit(status);
	}
}
