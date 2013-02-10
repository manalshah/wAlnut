package model.theory;

import model.Column;
import model.Region;
import java.util.HashSet;
import java.util.Set;

public class TestMemoryClassifier
    extends junit.framework.TestCase
{
    private Region           region;

    private Set<Column>      regionOutput;
    private Column           column00;
    private Column           column11;
    private Column           column22;
    private Idea             idea;
    private Memory           memory;

    private MemoryClassifier memoryClassifer;


    public void setUp()
    {
        this.region = new Region(30, 40, 6, 8, 4.0f, 1, 2);
        this.regionOutput = new HashSet<Column>();
        this.column00 = this.region.getColumn(0, 0);
        this.column11 = this.region.getColumn(1, 1);
        this.column22 = this.region.getColumn(2, 2);
        this.regionOutput.add(this.column00);
        this.regionOutput.add(this.column11);
        this.regionOutput.add(this.column22);
        this.idea = new Idea("Infinity", this.regionOutput);
        this.memory = new Memory();
        this.memory.addIdea(this.idea);
        this.memoryClassifer = new MemoryClassifier(this.memory);
    }


    public void testGetRegionOutput()
    {
        Set<Column> regionOutput = new HashSet<Column>();
        regionOutput.add(this.column00);
        regionOutput.add(this.column11);
        regionOutput.add(this.column22);
        assertEquals(regionOutput, this.regionOutput);
    }


    public void testGetMemory()
    {
        assertEquals(this.memory, this.memoryClassifer.getMemory());
    }


    public void testUpdateIdeas()
    {
        Set<Column> regionOutput = new HashSet<Column>();
        // simulate training by making a region output instance
        // have only one of the original Column objects of Idea Infinity
        regionOutput.add(this.column00);
        this.memoryClassifer.updateIdeas(regionOutput);
        // Idea Infinity will now have an attention percentage of ~= 33.3%
        assertEquals(33.3f, this.memoryClassifer.getMemory()
            .getIdea("Infinity").getAttentionPercentage(), 0.1f);

        regionOutput.add(this.column11);
        this.memoryClassifer.updateIdeas(regionOutput);
        // Idea Infinity will now have an attention percentage of ~= 66.6%
        assertEquals(66.6f, this.memoryClassifer.getMemory()
            .getIdea("Infinity").getAttentionPercentage(), 0.1f);

        regionOutput.add(this.column22);
        this.memoryClassifer.updateIdeas(regionOutput);
        // Idea Infinity will now have an attention percentage of ~= 99.9%
        assertEquals(99.9f, this.memoryClassifer.getMemory()
            .getIdea("Infinity").getAttentionPercentage(), 0.1f);
    }
}