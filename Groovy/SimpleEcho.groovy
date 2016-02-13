import groovy.util.AntBuilder
import java.util.Properties
import org.apache.log4j.Logger

class SimpleEcho { 

    public void execute(AntBuilder ant, Properties properties, Logger logger){
     
        // lets just call one task
		def mess = ".....Hello World!!!!"
		if(properties != null){
			def tmess = properties.getProperty("message")
			if (tmess!= null) mess = tmess
		}			
        ant.echo(mess)
		logger.debug(getClass().getName() + " is all done");           
    }
}