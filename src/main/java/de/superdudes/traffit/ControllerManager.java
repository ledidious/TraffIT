package de.superdudes.traffit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import de.superdudes.traffit.controller.AbstractController;
import de.superdudes.traffit.dto.SimulationObject;

// Do not instantiate
public abstract class ControllerManager {

	private ControllerManager() {
		// do not instantiate
	}
	
	/**
	 * 
	 * @param SO              Simulation object
	 * @param C               controller
	 * @param controllerClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <SO extends SimulationObject, C extends AbstractController<SO>> C get(Class<C> controllerClass) {

		try {
			final Method factory = controllerClass.getDeclaredMethod("instance");
			return (C) factory.invoke(null);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("Controller does not implement static factory instance()", e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Factory instance() must be public", e);
		} catch (InvocationTargetException e) {
			throw new InternalError("Something wrent wrong!", e);
		}
	}
}
