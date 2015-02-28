/**
 * Implements a basic unweighted graph edge class
 * with the restriction that src <= dst,
 * including accesors and no modifiers
 */

public class OrderedEdge {
    private int src;
    private int dst;

    public OrderedEdge(int src, int dst) {
        if (src <= dst) {
            this.src = src;
            this.dst = dst;
        } else {
            this.src = dst;
            this.dst = src;
        }
    }

    public int getSrc() {
        return src;
    }

    public int getDst() {
        return dst;
    }
}
