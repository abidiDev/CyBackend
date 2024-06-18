package com.example.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class Logging {
	/*
	"execution(Modifiers-pattern? Ret-type-pattern Declaring-type-pattern?Name-
pattern(param-pattern) Throws-pattern?)"

	“?” veut dire optionnel
            • Modifiers-pattern? : public, private ...
			• Ret-type-pattern : le type de retour de methode                       (hedhi l'étoile loula).
			• Declaring-type-pattern? : nom de la classe y compris le package.      (com.example.demo.services.*)
			• Name-pattern : nom de la méthode.                                     (.get*(..))
			• Throws-pattern? : l’exception.
            • “..” veut dire, 0 ou plusieurs paramètres

            !!!!!!!@Before("execution(* tn.esprit.esponline..*.*(..))")
*/
	@After("execution(* com.example.demo.services.*.get*(..))")
	public void apres(JoinPoint thisJoinPoint) {

		log.info("Out of the method (After)" + thisJoinPoint.getSignature().getName());
	}
/*
	@Around("execution(* com.example.demo.services.*.get*(..))")
	public Object profile(ProceedingJoinPoint pjp) throws Throwable {
		long start = System.currentTimeMillis();
		Object obj = pjp.proceed();
		long elapsedTime = System.currentTimeMillis() - start;
		log.info("Method execution time: " + elapsedTime + " milliseconds."+ pjp.getSignature().getName());
		return obj;
	}

 */
}