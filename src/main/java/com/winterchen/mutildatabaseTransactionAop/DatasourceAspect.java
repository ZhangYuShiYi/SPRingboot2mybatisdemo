package com.winterchen.mutildatabaseTransactionAop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


/**
 *
 * AOP数据源调用
 * repository@Annotation(AOP)-->
 * DefaultSqlSession-->
 * SimpleExecutor-->
 * BaseExecutor.getConnection()-->
 * SpringManagedTransaction.getConnection()--->连接为空-->
 * AbstractRoutingDataSource.getConnection()-->
 * 拿到beforeAOP中注入的datasource的key,所以每次都会动态切换数据源
 *
 * 数据库事务调用 service注解上@transactional-->
 * TransactionInterceptor.interpter()-->
 * TransactionAspectSupport.createTransactionIfNecessary()-->
 * AbstractPlatformTransactionManager.getTransaction()-->
 * DataSourceTransactionManager.doBegin()-->
 * AbstractRoutingDataSource.determineTargetDataSource()[lookupKey==null去拿默认的Datasource,不为空则使用获取到的连接]-->
 * DataSourceTransactionManager.setTransactional()[将连接设置到TransactionUtils的threadLocal中]--->
 * Repository@Annotation-->执行AOP数据源调用链,
 * 问题在于SpringManagedTransaction.getConnection()-->openConnection()-->
 * DataSourceUtils.getConnection()-->
 * TransactionSynchronizationManager.getResource(dataSource)不为空[从TransactionUtils的threadLocal中获取数据源],
 * 所以不会再去调用DynamicDataSource去获取数据源
 *
 * ① 加入Transaction注解时默认是先执行transaction的doBegin，然后再去找AbstractRoutingDataSource.determineTargetDataSource发现是null
 * 然后就会设置默认的数据源，所以必须在执行transaction的doBegin前通过AOP把切换的数据源进行注入，所以在本过滤器中定义Order的优先级为1
 * ② 在每次切换数据源之后再次清除ThreadLocal中的数据源定义，防止线程重复使用时获取旧的数据源定义。
 *
 */

@Aspect
@Component
/*@Order(1)*/
public class DatasourceAspect {

	private static Logger logger = LoggerFactory.getLogger(DatasourceAspect.class);

	/*@Pointcut("@annotation(MyDataSource)")
	public void annotationPointCut() {
		// APSECT POINT CUT FUNCTION
	}

	@Before(value = "annotationPointCut()&&@annotation(datasource)")
	public void beforeSwitchDS(JoinPoint point, MyDataSource datasource) {
		logger.info("--aspect--before");
		DatabaseHolder.setDatabaseSource(datasource.value());
	}

	@After(value = "annotationPointCut()")
	public void afterSwitchDS(JoinPoint point) {
		logger.info("--aspect--after");
		DatabaseHolder.clearDatabaseSource();
	}*/

	@Before("@annotation(com.winterchen.mutildatabaseTransactionAop.MyDataSource)")
	public void beforeSwitchDS(JoinPoint point){
		//获得当前访问的class
		Class<?> className = point.getTarget().getClass();
		//获得访问的方法名
		String methodName = point.getSignature().getName();
		//得到方法的参数的类型
		Class[] argClass = ((MethodSignature)point.getSignature()).getParameterTypes();
		String dataSource = DatabaseHolder.DEFAULT_DS;
		try {
			// 得到访问的方法对象
			Method method = className.getMethod(methodName, argClass);
			// 判断是否存在@DS注解
			if (method.isAnnotationPresent(MyDataSource.class)) {
				MyDataSource annotation = method.getAnnotation(MyDataSource.class);
				// 取出注解中的数据源名
				dataSource = annotation.value();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 切换数据源
		DatabaseHolder.setDatabaseSource(dataSource);
	}

	@After("@annotation(com.winterchen.mutildatabaseTransactionAop.MyDataSource)")
	public void afterSwitchDS(JoinPoint point){
		DatabaseHolder.clearDatabaseSource();
	}


}
