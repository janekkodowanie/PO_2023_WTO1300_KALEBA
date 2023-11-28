package agh.ics.oop.model;

import agh.ics.oop.Simulation;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine {

    private static final int THREAD_POOL_SIZE = 4;

    private final Collection<Simulation> simulations;

    private final List<Thread> threads;

    private final ExecutorService executorService;

    public SimulationEngine(Collection<Simulation> simulations) {
        this.simulations = simulations;
        this.executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        threads = simulations.stream().map(Thread::new).toList();
    }

    public void runSync() {
        for(Simulation simulation : simulations) {
            simulation.run();
        }
    }

    public void runAsync() {
        for (Thread thread : threads) {
            thread.start();
        }
    }

    public void runAsyncInThreadPool() {
        for (Thread thread : threads) {
            executorService.submit(thread);
        }
    }

    public void awaitSimulationsEnd() {
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        executorService.shutdown();

        try {
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
