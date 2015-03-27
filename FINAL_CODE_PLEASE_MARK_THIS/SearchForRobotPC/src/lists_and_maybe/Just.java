package lists_and_maybe;


/**
* Implementation of Just
* (using the "composite pattern").
*/

public class Just<A> implements Maybe<A> {

private final A something; 

public Just(A something) { 
 this.something = something;
}              

public boolean isNothing() { 
 return false;
}

public int size() {
 return 1;
}

public String toString() { 
 return "Just("+this.something+")";
}

public boolean has(A a) {
 return this.something.equals(a);
}

// Higher-order functions:

public Maybe<A> filter(Predicate<A> p) {
 if (p.holds(this.something))
 {
 	return this;
 }
 return new Nothing<A>();
}

public <B> Maybe<B> map(Function<A,B> f) {
 return new Just<B>(f.apply(this.something));
}

public <B> B fold(Function<A,B> f, B b) {
 return f.apply(this.something);
}

public boolean all(Predicate<A> p) {
 return p.holds(this.something);
}

public boolean some(Predicate<A> p) {
 return p.holds(this.something);
}

public void forEach(Action<A> f) {
 f.apply(this.something);
}

// Unsafe operation:
public A fromMaybe() {
 return this.something;
}
}
