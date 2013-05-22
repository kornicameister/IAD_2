package org.kornicameister.iad;

import org.junit.Before;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AbstractTest {

    protected static final int CLUSTER_SIZE = 10;
    protected List<Double[]> locations = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        Random seed = new Random(System.nanoTime());
        for (int i = 0; i < 4; i++) {
            Double[] tmp = new Double[AbstractTest.CLUSTER_SIZE];
            for (int j = 0; j < AbstractTest.CLUSTER_SIZE; j++) {
                tmp[j] = (double) seed.nextInt(i + j + 1);
            }
            this.locations.add(tmp);
        }
    }
}