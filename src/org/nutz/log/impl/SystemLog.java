package org.nutz.log.impl;

/**
 * 默认的Log,输出到System.err.
 * 
 * @author Young(sunonfire@gmail.com)
 * @author Wendal(wendal1985@gmail.com)
 */
public class SystemLog extends AbstractLog{

	public void debug(Object message, Throwable t) {
		if(isDebugEnabled())
			printOut(message, t);
	}

	public void error(Object message, Throwable t) {
		if(isErrorEnabled())
			printOut(message, t);
	}

	public void fatal(Object message, Throwable t) {
		if(isFatalEnabled())
			printOut(message, t);
	}

	public void info(Object message, Throwable t) {
		if(isInfoEnabled())
			printOut(message, t);
	}

	public void trace(Object message, Throwable t) {
		if(isTraceEnabled())
			printOut(message, t);
	}

	public void warn(Object message, Throwable t) {
		if(isWarnEnabled())
			printOut(message, t);
	}
	
	private void printOut(Object message,Throwable t){
		System.err.println(message);
		if(t != null)
			t.printStackTrace(System.err);
	}

}
