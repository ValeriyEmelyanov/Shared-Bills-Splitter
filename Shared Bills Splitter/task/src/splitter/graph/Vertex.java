package splitter.graph;

import splitter.model.Person;

import java.util.Objects;

public class Vertex {
    private final Person person;
    private VisitStatus visited;

    public Vertex(Person person) {
        this.person = person;
        this.visited = VisitStatus.WHITE;
    }

    public Person getPerson() {
        return person;
    }

    public VisitStatus getVisited() {
        return visited;
    }

    public void setVisited(VisitStatus visited) {
        this.visited = visited;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return Objects.equals(getPerson(), vertex.getPerson());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPerson());
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "person=" + person +
                ", visited=" + visited +
                '}';
    }
}
