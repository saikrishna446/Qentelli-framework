package com.qentelli.automation.managers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.formula.functions.*;
import com.qentelli.automation.annotations.IStep;
import com.qentelli.automation.common.World;
import com.qentelli.automation.factory.ExceptionFactory;

abstract public class StepExecutive implements IStep {
	Logger log = LogManager.getLogger(StepExecutive.class);
	T obj;
	Class<T> page;
	World w = null;

	public StepExecutive() {
	}

	public StepExecutive(T obj2) {
		obj = obj2;
	}

	public StepExecutive(World world) {
		w = world;
	}

	public void run() {
		try {
			step();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExceptionFactory(e).redirectException();
		}
	}

	@Override
	public void step() throws Exception {
		log.info("step!");
		log.error("must override");
	}

    public static void lamdaStep(IStep step) {
      try {
        step.step();
      } catch (Exception e) {
        e.printStackTrace();
      }
	}
}
