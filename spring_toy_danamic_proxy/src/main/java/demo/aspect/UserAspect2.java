package demo.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

@Component("userAspect2")
public class UserAspect2 {
	
	private static final Logger log = Logger.getLogger("UserAspect2");
	
	public void aspectBefore(){
		log.info("aspectBefore2");
	}
	
	
	public void aspectAfter(JoinPoint jPoint){
		log.info("aspectAfter2");
		log.info("jPoint2 class name :" + jPoint.getTarget().getClass().getName());
		log.info("jpoint2 method name : " + jPoint.getSignature().getName() );
	}

	public void aspectAround(ProceedingJoinPoint pJoinPoint) throws Throwable{
		log.info("aspectRound2");
		log.info("before2 pJoinPoint proceeding");
		pJoinPoint.proceed(); //执行
		log.info("after2 pJointPoint proceeded");
	}
	
}
