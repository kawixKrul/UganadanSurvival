package agh.ics.oop.model;

import agh.ics.oop.interfaces.AbstractFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.stream.IntStream;

public class GenomePattern implements AbstractFactory<Genome> {
    private final int minimumMutations;
    private final int maximumMutations;
    private final int genomeLength;

    public GenomePattern(int minimumMutations, int maximumMutations, int genomeLength) {
        this.minimumMutations = minimumMutations;
        this.maximumMutations = maximumMutations;
        this.genomeLength = genomeLength;
    }

    @Override
    public Genome create() {
        return new Genome(IntStream.range(0, genomeLength)
                .mapToObj(i -> (int) (Math.random() * 8))
                .toList());
    }

    public Genome mutate(Genome genome) {
        var mutator = new Random();
        var mutations = mutator.nextInt() % (maximumMutations-minimumMutations) + minimumMutations;
        var indexes = new ArrayList<>(IntStream.range(0, genomeLength)
                .boxed()
                .toList());
        Collections.shuffle(indexes);
        for (int idx : indexes.subList(0, mutations)) {
            genome.setGene(idx, mutator.nextInt());
        }
        return genome;
    }
}
