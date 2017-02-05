package demo.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component("userAspect")
//@Aspect
public class UserAspect {
	
	private static final Logger log = Logger.getLogger("UserAspect");
	
	@Before("execution(* demo.dao.*.save*(..))")
	public void aspectBefore(){
		log.info("aspectBefore");
	}
	
	
	@After("execution(* demo.dao.*.save*(..))||execution(* demo.dao.*.update*(..))")
	public void aspectAfter(JoinPoint jPoint){
		log.info("aspectAfter");
		log.info("jPoint class name :" + jPoint.getTarget().getClass().getName());
		log.info("jpoint method name : " + jPoint.getSignature().getName() );
	}

	@Around("execution(* demo.dao.*.load*(..))||execution(* demo.dao.*.delete*(..))")
	public void aspectAround(ProceedingJoinPoint pJoinPoint) throws Throwable{
		log.info("aspectRound");
		log.info("before pJoinPoint proceeding");
		pJoinPoint.proceed(); //执行
		log.info("after pJointPoint proceeded");
	}
	
}
