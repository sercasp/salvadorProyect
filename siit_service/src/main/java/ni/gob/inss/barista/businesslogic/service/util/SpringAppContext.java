package ni.gob.inss.barista.businesslogic.service.util;

import org.springframework.context.ApplicationContext;

/**
 * <b>BARISTA</b></br>
 * <b>Copyright (c) 2014 INSS.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * @author JUAN EVANGELISTA FLETES GARCIA
 * @version 1.0, 17/05/2014
 * @since 1.0 *
 */
public class SpringAppContext {

 private static ApplicationContext context;

 public static void setApplicationContext(ApplicationContext applicationContext) {
 context = applicationContext;
 }

 public static ApplicationContext getApplicationContext() {
 return context;
 }
}

