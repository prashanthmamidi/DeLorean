package com.daiwa;

import java.util.List;
import java.util.Map;

public interface InMemoryStore {
    void create(Map<Integer, List<Observation>> inMemoryDataStore, Observation observation);
    void update(Map<Integer, List<Observation>> inMemoryDataStore, Observation observation);
    void delete(Map<Integer, List<Observation>> inMemoryDataStore, Observation observation);
    void get(Map<Integer, List<Observation>> inMemoryDataStore, Observation observation);
    void latest(Map<Integer, List<Observation>> inMemoryDataStore, Observation observation);
}
