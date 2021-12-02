package foodie.wrapper;

import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {
    Logger logger = org.apache.logging.log4j.LogManager.getLogger(StartupListener.class.getName());
    private static boolean once = true;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (once) {
            once = false;

            try {
                ApplicationContext applicationContext = event.getApplicationContext();
            } catch (Exception e) {
                logger.fatal("onApplicationEvent failed", e);
            }
        }
    }

}
