package net.mediavrog.ruli;

/**
 * A simple left-right hand comparison. Takes `Value`s as arguments and a `SimpleRule.Comparator`.
 */
public class SimpleRule<T extends Comparable<T>> extends Rule {
    public static final String TAG = SimpleRule.class.getSimpleName();

    /**
     * Common comparison types:
     * <ul>
     * <li>`EQ` - equal</li>
     * <li>`NEQ` - not equal</li>
     * <li>`GT` - greater than</li>
     * <li>`GT_EQ` - greater than or equal</li>
     * <li>`LT` - lesser than</li>
     * <li>`LT_EQ` - lesser than or equal</li>
     * </ul>
     */
    public enum Comparator {
        EQ,
        NEQ,
        GT,
        GT_EQ,
        LT,
        LT_EQ
    }

    final private Value<T> lhs;
    final private Value<T> rhs;
    final private Comparator comparator;

    public SimpleRule(T lhs, Comparator c, T rhs) {
        this(Value.as(lhs), c, Value.as(rhs));
    }

    public SimpleRule(Value<T> lhs, Comparator c, T rhs) {
        this(lhs, c, Value.as(rhs));
    }

    public SimpleRule(T lhs, Comparator c, Value<T> rhs) {
        this(Value.as(lhs), c, rhs);
    }

    public SimpleRule(Value<T> lhs, Comparator c, Value<T> rhs) {
        this.comparator = c;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public boolean evaluate() {
        T l = this.lhs.get();
        T r = this.rhs.get();

        switch (comparator) {
            case EQ:
                return l.compareTo(r) == 0;
            case NEQ:
                return l.compareTo(r) != 0;
            case LT:
                return l.compareTo(r) < 0;
            case GT:
                return l.compareTo(r) > 0;
            case LT_EQ:
                return l.compareTo(r) <= 0;
            case GT_EQ:
                return l.compareTo(r) >= 0;
            default:
                throw new RuntimeException("Given comparator " + comparator + " not supported.");
        }
    }

    @Override
    public String toString(boolean evaluate) {
        return "Is " +
                lhs.describe() + " [" + String.valueOf(lhs.get()) + "] " +
                comparator.toString().toLowerCase() + " to " +
                rhs.describe() + " [" + String.valueOf(rhs.get()) + "] ?" +
                (evaluate ? " " + evaluate() + "!" : "")
                ;
    }
}