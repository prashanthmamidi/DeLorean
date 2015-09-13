package com.daiwa;

import java.util.*;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

public class InMemoryStoreService implements InMemoryStore {

    @Override
    public void create(Map<Integer, List<Observation>> inMemoryDataStore, Observation observation) {

        if (inMemoryDataStore.containsKey(observation.getId())) {
            System.out.println("ERR A history already exists for identifier " + observation.getId());
        } else {
            List<Observation> list = new ArrayList<>(1000);
            list.add(observation);
            inMemoryDataStore.put(observation.getId(), list);
            System.out.println("OK " + observation.getData());
        }
    }

    @Override
    public void update(Map<Integer, List<Observation>> inMemoryDataStore, Observation observation) {
        if (inMemoryDataStore.containsKey(observation.getId())) {
            List<Observation> observations = inMemoryDataStore.get(observation.getId());

            String data = getData(observation, observations);

            List<Observation> updatedObservations = observations.stream()
                    .filter(ob -> ob.getTimeStamp() != observation.getTimeStamp())
                    .collect(toList());

            updatedObservations.add(observation);
            inMemoryDataStore.put(observation.getId(), updatedObservations);
            System.out.println("OK " + data);
        } else {
            System.out.println("ERR There's no history exists for identifier " + observation.getId() + " to update");
        }
    }

    @Override
    public void delete(Map<Integer, List<Observation>> inMemoryDataStore, Observation observation) {
        if (inMemoryDataStore.containsKey(observation.getId())) {
            performDelete(inMemoryDataStore, observation);
        } else {
            System.out.println("Err There's no history exists for identifier " + observation.getId() + " to delete");
        }
    }

    @Override
    public void get(Map<Integer, List<Observation>> inMemoryDataStore, Observation observation) {
        String errorMessage = format("Err There's no history exists for identifier %s with timestamp %s ", observation.getId(), observation.getTimeStamp());
        if (inMemoryDataStore.containsKey(observation.getId())) {
            List<Observation> observations = inMemoryDataStore.get(observation.getId());
            String data = observations.stream()
                    .filter(observ -> observ.getTimeStamp() <= observation.getTimeStamp())
                    .max((ob1, ob2) -> ob1.getTimeStamp().compareTo(ob2.getTimeStamp()))
                    .map(Observation::getData)
                    .orElse(errorMessage);
            System.out.println("OK " + data);
        } else {
            System.out.println(errorMessage);
        }
    }

    @Override
    public void latest(Map<Integer, List<Observation>> inMemoryDataStore, Observation observation) {
        if (inMemoryDataStore.containsKey(observation.getId())) {
            List<Observation> observations = inMemoryDataStore.get(observation.getId());
            Observation latestObservation = observations.stream()
                    .max((ob1, ob2) -> ob1.getTimeStamp().compareTo(ob2.getTimeStamp()))
                    .get();
            System.out.println(format("OK %s %s", latestObservation.getTimeStamp(), latestObservation.getData()));
        } else {
            System.out.println("Err There's no history exists for identifier " + observation.getId());
        }

    }

    private String getData(Observation observation, List<Observation> observations) {
        Optional<Observation> sameObservation = observations.stream()
                .filter(ob -> ob.getTimeStamp() == observation.getTimeStamp())
                .findFirst();

        if (sameObservation.isPresent()) {
            return sameObservation.get().getData();
        } else {
            return observations.get(observations.size() - 1).getData();
        }
    }

    private static void performDelete(Map<Integer, List<Observation>> inMemoryDataStore, Observation observation) {
        if (null != observation.getTimeStamp()) {
            deleteByIdAndTimestamp(inMemoryDataStore, observation);
        } else {
            deleteById(inMemoryDataStore, observation);
        }
    }

    private static void deleteById(Map<Integer, List<Observation>> inMemoryDataStore, Observation observation) {
        List<Observation> observations = inMemoryDataStore.get(observation.getId());
        String data = observations.stream()
                .max((ob1, ob2) -> ob1.getTimeStamp().compareTo(ob2.getTimeStamp()))
                .get().getData();
        inMemoryDataStore.remove(observation.getId());
        System.out.println("OK " + data);
    }

    private static void deleteByIdAndTimestamp(Map<Integer, List<Observation>> inMemoryDataStore, Observation observation) {
        List<Observation> filteredList = inMemoryDataStore.get(observation.getId()).stream()
                .filter(ds -> observation.getTimeStamp() > ds.getTimeStamp())
                .collect(toList());
        inMemoryDataStore.put(observation.getId(), filteredList);

        List<Observation> updatedObservationList = inMemoryDataStore.get(observation.getId());
        if (!updatedObservationList.isEmpty()) {
            System.out.println("OK " + updatedObservationList.get(updatedObservationList.size() - 1).getData());
        } else {
            System.out.println("Err There's no available observation");
            inMemoryDataStore.remove(observation.getId());
        }
    }
}
