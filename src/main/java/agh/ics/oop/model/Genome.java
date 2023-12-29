package agh.ics.oop.model;

import java.util.List;
import java.util.Random;

public class Genome {
    private final List<Integer> genes;
    private int activatedGene;

    public Genome(List<Integer> genes) {
        this.genes = genes;
        this.activatedGene = 0;
    }

    /**
     * get current gene and move to the next
     * @return int - current gene
     */
    public int getGene() {
        return genes.get(activatedGene++ % genes.size());
    }

    public void setGene(int idx, int gene) {
        this.genes.set(idx, gene);
    }

    /**
     * special behavioral pattern for crazy animal
     */
    public void aBitOfCraziness() {
        if (Math.random() < 0.2) {
            this.activatedGene = new Random().nextInt() % genes.size();
        }
    }

    public List<Integer> getGenes() {
        return genes;
    }

    public int getGenomeLength() {
        return genes.size();
    }
}
