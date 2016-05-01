import java.util.Arrays;
import java.util.Spliterator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by steven on 22-04-16.
 */


public class Kenny {

    public String encode(String kennySpeak) {
        return kennySpeak.toLowerCase().chars().boxed()
                .flatMap(c -> (c == 'm' ? Stream.of(0) : c == 'p' ? Stream.of(1) : c == 'f' ? Stream.of(2) : Stream.of(-1, -1, c)))
                .reduce(
                        (Function<Integer, Stream<Integer>>) (s) -> {
                            if(s != 0) {
                                throw new IllegalStateException();
                            }
                            return Stream.empty();
                        },
                        (fn, val) -> (s) -> {
                            if ( s == 0) {
                                final int[] token = new int[3];
                                Spliterator<Integer> spliterator = fn.apply(1).spliterator();
                                token[2] = val;
                                spliterator.tryAdvance((t) -> { token[1] = t; });
                                spliterator.tryAdvance((t) -> { token[0] = t; });
                                Supplier<Integer> reduceToken = () -> {
                                    if (Arrays.stream(token).allMatch(t -> t >= 0 && t <= 2)) {
                                        int kennyCode = Arrays.stream(token).reduce(0, (l, r) ->  3 * l + r);
                                        if(kennyCode > 25) {
                                            throw new IllegalStateException();
                                        }
                                        return 'a' + kennyCode;
                                    } else {
                                        return token[2];
                                    }
                                };
                                return Stream.concat(Stream.of(reduceToken.get()), StreamSupport.stream(spliterator, false));
                            } else if ( s == 1 || s == 2) {
                                return Stream.concat(Stream.of(val), fn.apply( (s + 1) % 3));
                            } else {
                                throw new IllegalStateException();
                            }
                        },
                        (l, r) -> (s) -> Stream.concat(l.apply(s), r.apply(s))
                )
                .apply(0)
                .map(Character::toChars)
                .map(String::new)
                .reduce("", (l, r) -> r + l);
    }

    public String decode(String normalSpeak) {
        return normalSpeak.chars().boxed().map(
                c -> c >= 'a' && c <= 'z' ?
                        ("000".substring(Integer.toString(c - 'a', 3).length()) + Integer.toString(c - 'a', 3))
                                .replaceAll("0", "m")
                                .replaceAll("1", "p")
                                .replaceAll("2", "f")
                        : new String(Character.toChars(c))
        ).reduce("", (l, r) -> l + r);
    }
}
