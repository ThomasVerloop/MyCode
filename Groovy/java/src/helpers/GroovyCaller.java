package helpers;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.util.AntBuilder;

import java.io.File;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.apache.tools.ant.BuildLogger;
import org.apache.tools.ant.NoBannerLogger;
import org.apache.tools.ant.Project;

public class GroovyCaller {
	static private GroovyCaller me;
	private Set<String> methodsToCall = new TreeSet<String>();
	
	public void clearMethodsToCall() {
		methodsToCall.clear();
	}

	public void addMethodToCall(String methodToCall) {
		methodsToCall.add(methodToCall);
	}

	public static void runGroovyScriptFile(File groovyFile){
		if(me == null){
			me = new GroovyCaller();
		}else{
			me.methodsToCall.clear();
		}
		me.methodsToCall.add("run");
		me.runGroovyScript(groovyFile);
	}
	
	public long runGroovyScript(File groovyFile){    
	    String taskName = "runGroovyFile";
        Logger cat = Logger.getLogger(this.getClass());
	    long ltime = (new java.util.Date()).getTime(); 
	    try {
	         GroovyClassLoader loader = new GroovyClassLoader();
	         cat.info(" start parsing script: "+ groovyFile);
	         Class<?> groovyClass = loader.parseClass(groovyFile);
	         runner(taskName, groovyClass);
	     } 
	     catch (Exception e) {
	    	 cat.error("TASK [" + taskName + "] HAS FAILED!!! ",e);
	     }
         return (new java.util.Date()).getTime()-ltime;
	 }

	 public long runGroovyScript(String script) throws Exception {    
		    String taskName = "runGroovyScript";
	        Logger cat = Logger.getLogger(this.getClass());
		    long ltime = (new java.util.Date()).getTime(); 
		    try {
		         GroovyClassLoader loader = new GroovyClassLoader();
		         cat.info(" start parsing script........");
		         Class<?> groovyClass = loader.parseClass(script);
		         runner(taskName, groovyClass);
		         return (new java.util.Date()).getTime()-ltime;
		     } 
		     catch (Exception e) {
		         cat.error("TASK [" + taskName + "] HAS FAILED!!! ",e);
		         throw(e);
		     }
	 }

	 private void runner(String taskName, Class<?> groovyClass) throws Exception {
		Logger logger = Logger.getLogger(this.getClass());
	     GroovyObject groovyObject = (GroovyObject) groovyClass.newInstance();
		 logger.info(" parsed script: " + groovyObject);
		 
		 boolean useExecute = methodsToCall.contains("execute");
		 Object[] args = null;
		 if(methodsToCall.contains("executeWithAnt")){
			 args = new Object[]{getAntBuilder(), System.getProperties(), logger};
			 useExecute = true;
		 }
		 
		 if(useExecute){
		     logger.debug("TASK [" + taskName + "(execute)] ......................");
			 groovyObject.invokeMethod("execute", args);
		 } else if(methodsToCall.isEmpty() || methodsToCall.contains("run")){
		     logger.info("TASK [" + taskName + "(run)] ......................");
			 groovyObject.invokeMethod("run", null);
		 }
		 logger.info("TASK [" + taskName + "] successfully finished");
	}

	private AntBuilder getAntBuilder() {
	    Project antProject = createProject();
	    AntBuilder result = new AntBuilder(antProject);
	    return result;
	 }

	private Project createProject() {
	    Project project = new Project();
	    BuildLogger logger = new NoBannerLogger();

	    logger.setMessageOutputLevel(org.apache.tools.ant.Project.MSG_INFO);
	    logger.setOutputPrintStream(System.out);
	    logger.setErrorPrintStream(System.err);

	    project.addBuildListener(logger);

	    project.init();
	    project.getBaseDir();
	    return project;
	}


}
