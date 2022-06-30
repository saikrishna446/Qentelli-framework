package com.qentelli.automation.factory;

import java.util.HashSet;
import java.util.Set;

import com.qentelli.automation.exceptions.base.AppIssueException;
import com.qentelli.automation.exceptions.base.AutomationIssueException;
import com.qentelli.automation.exceptions.base.BaseException;
import com.qentelli.automation.exceptions.base.EnvironmentException;
import com.qentelli.automation.exceptions.base.MissingElementException;
import com.qentelli.automation.exceptions.base.PerformanceIssueException;
import com.qentelli.automation.exceptions.base.UnknownException;

public class ExceptionFactory {
	public Set<BaseException> traps = new HashSet<BaseException>();
	Throwable e = null;

	public ExceptionFactory(Exception exception) {
		e = exception;
		init();
	}

	public ExceptionFactory(Throwable error) {
		e = error;
		init();
	}

	public ExceptionFactory(String message, Throwable exception) {
		e = exception;
		init(message, exception);
	}

	private void init(String message, Throwable exception) {
		traps.add(new AppIssueException(message, exception)); // Application or Validation Failure (Probably App Bug)
		traps.add(new AutomationIssueException(message, exception)); // Selenium issue where element is not interactable
																		// programmatically
		traps.add(new EnvironmentException(message, exception)); // Environment/Sauce/Mongo/Influx/Grafana issue(s)
		traps.add(new MissingElementException(message, exception)); // Issue in locating Web element
		traps.add(new PerformanceIssueException(message, exception)); // Issue in Performance
	}

	public void init() {

		traps.add(new AppIssueException(e)); // Application or Validation Failure (Probably App Bug)
		traps.add(new AutomationIssueException(e)); // Selenium issue where element is not interactable programmatically
		traps.add(new EnvironmentException(e)); // Environment/Sauce/Mongo/Influx/Grafana issue(s)
		traps.add(new MissingElementException(e)); // Issue in locating Web element
		traps.add(new PerformanceIssueException(e)); // Issue in Performance
	}

	public BaseException redirectException() {
		for (BaseException base : traps) {
			try {
				if (Class.forName(base.getClass().getName()).isInstance(e)) {
					return base;
				}
			} catch (ClassNotFoundException ex) {
				ex.printStackTrace();
			}
		}

		for (BaseException base : traps) {
			if (base.isMyException())
				return base;
		}
		return new UnknownException(e);

	}
}
