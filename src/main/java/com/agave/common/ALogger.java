package com.agave.common;


public class ALogger  {
	
	
	
	private A4log a4log;
	
	public static synchronized ALogger getLogger(Class<?> pClass) {
		
		ALogger mylog=new ALogger();
		mylog.a4log=new A4log(pClass);
		return mylog ;
		
	}
	
	  /**
	   * Check whether this logger is enabled for the TRACE Level.
	   * @return true if this logger is enabled for level TRACE, false otherwise.
	   */
	  public boolean isTraceEnabled() {
	    return false;
	  }

	  /**
	   * Log a message object with the TRACE level. This method first checks if this
	   * logger is TRACE enabled. If this logger is TRACE enabled, then it converts
	   * the message object (passed as parameter) to a string by invoking toString().
	   * WARNING Note that passing a Throwable to this method will print the name of
	   * the Throwable but no stack trace. To print a stack trace use the
	   * trace(Object, Throwable) form instead.
	   * @param message the message object to log.
	   */
	  public void trace(Object message) {
	  }

	  /**
	   * Log a message object with the TRACE level including the stack trace of the
	   * Throwable t passed as parameter.
	   * See Logger.trace(Object) form for more detailed information.
	   * @param message the message object to log.
	   * @param t the exception to log, including its stack trace.
	   */
	  public void trace(Object message, Throwable t) {
	  }

	  /**
	   * Check whether this logger is enabled for the DEBUG Level.
	   * @return true if this logger is enabled for level DEBUG, false otherwise.
	   */
	  public boolean isDebugEnabled() {
			  return false;
	  }

	  /**
	   * Log a message object with the DEBUG level.
	   * See Logger.trace(Object) form for more detailed information.
	   * @param message the message object to log.
	   */
	  public void debug(Object message) {
	  }

	  /**
	   * Log a message object with the DEBUG level including the stack trace of the
	   * Throwable t passed as parameter.
	   * See Logger.trace(Object, Throwable) form for more detailed information.
	   * @param message the message object to log.
	   * @param t the exception to log, including its stack trace.
	   */
	  public void debug(Object message, Throwable t) {
		  
	  }

	  /**
	   * Check whether this logger is enabled for the INFO Level.
	   * @return true if this logger is enabled for level INFO, false otherwise.
	   */
	  public boolean isInfoEnabled() {
		  return false;
	  }

	  /**
	   * Log a message object with the INFO level.
	   * See Logger.trace(Object) form for more detailed information.
	   * @param message the message object to log.
	   */
	  public void info(Object message) {
		  this.a4log.info(message);
		  
	  }

	  /**
	   * Log a message object with the WARN level including the stack trace of the
	   * Throwable t passed as parameter.
	   * See Logger.trace(Object, Throwable) form for more detailed information.
	   * @param message the message object to log.
	   * @param t the exception to log, including its stack trace.
	   */
	  public void info(Object message, Throwable t) {
		  this.a4log.info(message);
	  }
	  /**
	   * Log a message object with the WARN level.
	   * See Logger.trace(Object) form for more detailed information.
	   * @param message the message object to log.
	   */
	  public void warn(Object message) {
	  }

	  /**
	   * Log a message object with the ERROR level including the stack trace of the
	   * Throwable t passed as parameter.
	   * See Logger.trace(Object, Throwable) form for more detailed information.
	   * @param message the message object to log.
	   * @param t the exception to log, including its stack trace.
	   */
	  public void warn(Object message, Throwable t) {
	  }

	  /**
	   * Log a message object with the ERROR level.
	   * See Logger.trace(Object) form for more detailed information.
	   * @param message the message object to log.
	   */
	  public void error(Object message) {
	  }

	  /**
	   * Log a message object with the DEBUG level including the stack trace of the
	   * Throwable t passed as parameter.
	   * See Logger.trace(Object, Throwable) form for more detailed information.
	   * @param message the message object to log.
	   * @param t the exception to log, including its stack trace.
	   */
	  public void error(Object message, Throwable t) {
	  }

	  /**
	   * Log a message object with the FATAL level.
	   * See Logger.trace(Object) form for more detailed information.
	   * @param message the message object to log.
	   */
	  public void fatal(Object message) {
	  }

	  /**
	   * Log a message object with the FATAL level including the stack trace of the
	   * Throwable t passed as parameter.
	   * See Logger.trace(Object, Throwable) form for more detailed information.
	   * @param message the message object to log.
	   * @param t the exception to log, including its stack trace.
	   */
	  public void fatal(Object message, Throwable t) {
	  }

}
