package com.atlassian.pageobjects.elements.query;

import java.util.List;

import com.google.common.base.Supplier;

import org.apache.commons.lang.ArrayUtils;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.atlassian.pageobjects.elements.util.StringConcat.asString;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Utilities to create miscellaneous {@link TimedCondition}s.
 *
 * @since v4.2
 */
public final class Conditions
{
    private static final Logger log = LoggerFactory.getLogger(Conditions.class);

    private static final int DEFAULT_TIMEOUT = 100;


    private Conditions()
    {
        throw new AssertionError("No way");
    }

    /**
     * Return new timed condition that is a negation of <tt>condition</tt>.
     * 
     * @param condition condition to be negated
     * @return negated {@link TimedCondition} instance.
     */
    public static TimedQuery<Boolean> not(TimedQuery<Boolean> condition)
    {
        if (condition instanceof Not)
        {
            return asDecorator(condition).wrapped;
        }
        return new Not(condition);
    }

    /**
     * <p>
     * Return new combinable condition that is logical product of <tt>conditions</tt>.
     *
     * <p>
     * The resulting condition will have interval of the first condition
     * in the <tt>conditions</tt> array,
     *
     * @param conditions conditions to conjoin
     * @return product of <tt>conditions</tt>
     * @throws IllegalArgumentException if <tt>conditions</tt> array is <code>null</code> or empty
     *
     * @see TimedCondition#interval()
     */
    public static CombinableCondition and(TimedQuery<Boolean>... conditions)
    {
        return new And(conditions);
    }

    /**
     * <p>
     * Return new combinable condition that is logical product of <tt>conditions</tt>.
     *
     * <p>
     * The resulting condition will have interval of the first condition
     * in the <tt>conditions</tt> array,
     *
     * @param conditions conditions to conjoin
     * @return product of <tt>conditions</tt>
     * @throws IllegalArgumentException if <tt>conditions</tt> array is <code>null</code> or empty
     *
     * @see TimedCondition#interval()
     */
    public static CombinableCondition and(List<TimedQuery<Boolean>> conditions)
    {
        return and(conditions.toArray(new TimedCondition[conditions.size()]));
    }

    /**
     * <p>
     * Return new combinable condition that is logical sum of <tt>conditions</tt>.
     *
     * <p>
     * The resulting condition will have interval of the first condition
     * in the <tt>conditions</tt> array,
     *
     * @param conditions conditions to sum
     * @return logical sum of <tt>conditions</tt>
     * @throws IllegalArgumentException if <tt>conditions</tt> array is <code>null</code> or empty
     * 
     * @see TimedCondition#interval()
     */
    public static CombinableCondition or(TimedQuery<Boolean>... conditions)
    {
        return new Or(conditions);
    }

    /**
     * <p>
     * Return new combinable condition that is logical sum of <tt>conditions</tt>.
     *
     * <p>
     * The resulting condition will have interval of the first condition
     * in the <tt>conditions</tt> array,
     *
     * @param conditions conditions to sum
     * @return logical sum of <tt>conditions</tt>
     * @throws IllegalArgumentException if <tt>conditions</tt> array is <code>null</code> or empty
     *
     * @see TimedCondition#interval()
     */
    public static CombinableCondition or(List<TimedQuery<Boolean>> conditions)
    {
        return or(conditions.toArray(new TimedCondition[conditions.size()]));
    }


    /**
     * Condition that always returns <code>false<code>. Its interval will be equal to the default timeout.
     *
     * @param defaultTimeout default timeout
     * @return false condition
     */
    public static TimedCondition falseCondition(long defaultTimeout)
    {
        return new AbstractTimedCondition(defaultTimeout, defaultTimeout)
        {
            @Override
            public Boolean currentValue()
            {
                return false;
            }
        };
    }

    /**
     * Condition that always returns <code>false<code>, with default timeout of 100ms.
     *
     * @return false condition
     */
    public static TimedCondition falseCondition()
    {
        return falseCondition(DEFAULT_TIMEOUT);
    }

    // TODO true condition

    /**
     * <p>
     * Returns a condition that combines <tt>original</tt> and <tt>dependant</tt> in a manner that dependant condition
     * will only ever be retrieved if the <tt>original</tt> condition is <code>true</code>. This is useful
     * when dependant condition may only be retrieved given the original condition is <code>true</code>.
     *
     * <p>
     * The supplier for dependant condition is allowed to return <code>null</code> or throw exception if the
     * original condition returns false. But it <i>may not</i> do so given the original condition is <code>true</code>,
     * as this will lead to <code>NullPointerException</code> or the raised exception be propagated by
     * this condition respectively.
     *
     * @param original original condition
     * @param dependant supplier for dependant condition that will only be evaluated given the original condition
     * evaluates to <code>true</code>
     * @return new dependant condition
     */
    public static TimedCondition dependantCondition(TimedQuery<Boolean> original, Supplier<TimedQuery<Boolean>> dependant)
    {
        return new DependantCondition(original, dependant);
    }


    /**
     * <p>
     * Return condition that will be <code>true</code>, if given <tt>matcher</tt> will match the  <tt>query</tt>. Any
     * Hamcrest matcher implementation may be used.
     *
     * <p>
     * Example:<br>
     *
     * <code>
     *     TimedCondition textEquals = Conditions.forMatcher(element.getText(), isEqualTo("blah"));
     * </code>
     *
     * @param query timed query to match
     * @param matcher matcher for the query
     * @param <T> type of the result
     * @return new matching condition
     */
    public static <T> TimedCondition forMatcher(TimedQuery<T> query, Matcher<T> matcher)
    {
        return new MatchingCondition<T>(query, matcher);
    }


    private static AbstractConditionDecorator asDecorator(TimedQuery<Boolean> condition)
    {
        return (AbstractConditionDecorator) condition;
    }


    /**
     * A timed condition that may be logically combined with others, by means of basic logical operations: 'and'/'or'. 
     *
     */
    public static interface CombinableCondition extends TimedCondition
    {
        /**
         * Combine <tt>other</tt> condition with this condition logical query, such that the resulting condition
         * represents a logical product of this condition and <tt>other</tt>.
         *
         * @param other condition to combine with this one
         * @return new combined 'and' condition
         */
        CombinableCondition and(TimedCondition other);

        /**
         * Combine <tt>other</tt> condition with this condition logical query, such that the resulting condition
         * represents a logical sum of this condition and <tt>other</tt>.
         *
         * @param other condition to combine with this one
         * @return new combined 'or' condition
         */
        CombinableCondition or(TimedCondition other);

    }


    private abstract static class AbstractConditionDecorator extends AbstractTimedCondition
    {
        protected final TimedQuery<Boolean> wrapped;

        public AbstractConditionDecorator(TimedQuery<Boolean> wrapped)
        {
            super(wrapped);
            this.wrapped = checkNotNull(wrapped, "wrapped");
        }
    }

    private abstract static class AbstractConditionsDecorator extends AbstractTimedCondition implements CombinableCondition
    {
        protected final TimedQuery<Boolean>[] conditions;

        public AbstractConditionsDecorator(TimedQuery<Boolean>... conditions)
        {
            super(conditions[0]);
            this.conditions = conditions;
        }

        @Override
        public String toString()
        {
            StringBuilder answer = new StringBuilder(conditions.length * 20).append(getClass().getName()).append(":\n");
            for (TimedQuery<Boolean> condition : conditions)
            {
                answer.append(" -").append(condition.toString()).append('\n');
            }
            return answer.deleteCharAt(answer.length()-1).toString();
        }
    }

    private static class Not extends AbstractConditionDecorator
    {
        public Not(TimedQuery<Boolean> other)
        {
            super(other);
        }

        public Boolean currentValue()
        {
            return !wrapped.now();
        }

        @Override
        public String toString()
        {
            return asString("Negated: <", wrapped, ">");
        }
    }

    private static class And extends AbstractConditionsDecorator
    {
        public And(TimedQuery<Boolean>... conditions)
        {
            super(conditions);
        }

        And(TimedQuery<Boolean>[] somes, TimedQuery<Boolean>[] more)
        {
            super((TimedCondition[]) ArrayUtils.addAll(somes, more));
        }

        And(TimedQuery<Boolean>[] somes, TimedQuery<Boolean> oneMore)
        {
            super((TimedCondition[]) ArrayUtils.add(somes, oneMore));
        }

        public Boolean currentValue()
        {
            boolean result = true;
            for (TimedQuery<Boolean> condition : conditions)
            {
                // null should not really happen if TimedCondition contract is observed
                final boolean next = condition.now() != null ? condition.now() : false;
                result &= next;
                if (!result)
                {
                    log.debug(asString("[And] Condition <",condition,"> returned false"));
                    break;
                }
            }
            return result;
        }

        public CombinableCondition and(TimedCondition other)
        {
            if (other.getClass().equals(And.class))
            {
                return new And(this.conditions, ((And) other).conditions);
            }
            return new And(this.conditions, other);
        }

        public CombinableCondition or(TimedCondition other)
        {
            if (other instanceof Or)
            {
                return ((Or)other).or(this);
            }
            return new Or(this, other);
        }
    }

    private static class Or extends AbstractConditionsDecorator
    {
        public Or(TimedQuery<Boolean>... conditions)
        {
            super(conditions);
        }

        Or(TimedQuery<Boolean>[] somes, TimedQuery<Boolean>[] more)
        {
            super((TimedCondition[]) ArrayUtils.addAll(somes, more));
        }

        Or(TimedQuery<Boolean>[] somes, TimedQuery<Boolean> oneMore)
        {
            super((TimedCondition[]) ArrayUtils.add(somes, oneMore));
        }

        public Boolean currentValue()
        {
            boolean result = false;
            for (TimedQuery<Boolean> condition : conditions)
            {
                // null should not really happen if TimedCondition contract is observed
                final boolean next = condition.now() != null ? condition.now() : false;
                result |= next;
                if (result)
                {
                    break;
                }
                log.debug(asString("[Or] Condition <",condition,"> returned false"));
            }
            return result;
        }

        public CombinableCondition and(TimedCondition other)
        {
            if (other instanceof And)
            {
                return ((And)other).and(this);
            }
            return new And(this, other);
        }

        public CombinableCondition or(TimedCondition other)
        {
            if (other.getClass().equals(Or.class))
            {
                return new Or(this.conditions, ((Or) other).conditions);
            }
            return new Or(this.conditions, other);
        }
    }

    private static final class DependantCondition extends AbstractConditionDecorator
    {
        private final Supplier<TimedQuery<Boolean>> dependant;

        DependantCondition(TimedQuery<Boolean> original, Supplier<TimedQuery<Boolean>> dependant)
        {
            super(original);
            this.dependant = checkNotNull(dependant, "dependant");
        }

        @Override
        public Boolean currentValue()
        {
            return wrapped.now() && dependant.get().now();
        }

        @Override
        public String toString()
        {
            if (wrapped.now())
            {
                TimedQuery<Boolean> dep = dependant.get();
                return asString("DependantCondition[original=",wrapped,",dependant=",dep,"]");
            }
            return asString("DependantCondition[original=",wrapped,"]");
        }
    }


    static final class MatchingCondition<T> extends AbstractTimedCondition
    {
        final TimedQuery<T> query;
        final Matcher<T> matcher;

        T lastValue;

        public MatchingCondition(final TimedQuery<T> query, final Matcher<T> matcher)
        {
            super(query);
            this.query = checkNotNull(query);
            this.matcher = checkNotNull(matcher);
        }

        @Override
        protected Boolean currentValue()
        {
            lastValue = query.now();
            return matcher.matches(lastValue);
        }

        @Override
        public String toString()
        {
            return super.toString() + new StringDescription().appendText("[query=").appendValue(query)
                    .appendText("][matcher=").appendDescriptionOf(matcher).appendText("]");
        }
    }
}
