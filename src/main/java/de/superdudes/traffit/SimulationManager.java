package de.superdudes.traffit;

import de.superdudes.traffit.controller.*;
import de.superdudes.traffit.dto.*;
import de.superdudes.traffit.test.TestUtils;
import lombok.NonNull;

import java.util.concurrent.Semaphore;

public class SimulationManager {

	private static final int MAX_RUNNING_SIMULATION = 1;
	private static final int GEN_WAIT = 10;

	private static StartingGrid runningSimulation = new StartingGrid("default");
	private static Thread executingThread = null;
	private static Semaphore semaphore = new Semaphore(MAX_RUNNING_SIMULATION);

	private static boolean genWasRendered = false;

	public static boolean load() {
		runningSimulation = StartingGridController.instance().load();
		return runningSimulation != null;
	}

	public static boolean save() {
		StartingGridController.instance().save(runningSimulation);
		return true;
	}

	public static void start() {
		if (executingThread != null) {
			executingThread = new Thread(SimulationManager::run);
			executingThread.start();
		} else {
			semaphore.release();
		}
	}

	public static void halt() {
		try {
			semaphore.acquire();
		} catch (InterruptedException e) {
			throw new InternalError("Unwanted interrupt", e);
		}
	}

	public static void stop() {
		executingThread.interrupt();
	}

	public static boolean isStarted() {
		return executingThread != null;
	}

	public static boolean isRunning() {
		// If executing thread waits for semaphore, semaphore has queued threads
		return isStarted() && !semaphore.hasQueuedThreads();
	}

	private static void run() {
		try {
			while (true) {
				semaphore.acquire();

				Thread.sleep(GEN_WAIT);

				StartingGridController.instance().render(runningSimulation);
				for (Vehicle vehicle : runningSimulation.getVehicles()) {
					VehicleController.instance().render(vehicle);
				}

				final Street street = runningSimulation.getStreet();
				StreetController.instance().render(street);

				for (Lane lane : street.getLanes()) {
					LaneController.instance().render(lane);

					for (Cell cell : lane.getCells()) {
						CellController.instance().render(cell);

						final StreetSign streetSign = cell.getStreetSign();
						if (streetSign != null) {
							StreetSignController.instance().render(streetSign);
						}
					}
				}

				// Output current simulation
				TestUtils.outputOnConsole(runningSimulation);

				genWasRendered = true;
				semaphore.release();
			}
		} catch (InterruptedException e) {
			System.out.println("Simulation interrupted");
		}
	}

	public static StartingGrid getRunningSimulation() {
		return runningSimulation;
	}

	public static void setRunningSimulation(@NonNull StartingGrid startingGrid) {
		if (isRunning()) {
			throw new IllegalStateException("Not possible during run");
		}

		runningSimulation = startingGrid;
	}

	public static boolean isGenWasRendered() {
		return genWasRendered;
	}

	public static void genWasRendered(boolean genWasRendered) {
		SimulationManager.genWasRendered = genWasRendered;
	}
}
