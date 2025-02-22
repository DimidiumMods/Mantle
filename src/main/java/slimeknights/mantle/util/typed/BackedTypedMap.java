package slimeknights.mantle.util.typed;

import javax.annotation.Nullable;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Typed map backed by a map. It is the callers responsibility to ensure the passed map contains valid key to value pairs.
 * Note this class implements {@link MutableTypedMap}, but it will throw if the backing map is not mutable. It is the caller's responsibility to use the correct interface for non-mutable maps.
 */
@SuppressWarnings("unchecked")  // the nature of this map means we inherently have unchecked operations
public record BackedTypedMap<T>(Map<Key<? extends T>, T> map) implements MutableTypedMap<T> {
  /** Creates a new mutable backed map */
  public BackedTypedMap() {
    // using identity as keys are typically identity objects, if you have non-identity keys you can use the regular constructor.
    this(new IdentityHashMap<>());
  }

  @Override
  public int size() {
    return map.size();
  }

  @Override
  public boolean isEmpty() {
    return map.isEmpty();
  }

  @Override
  public boolean containsKey(Key<? extends T> key) {
    return map.containsKey(key);
  }

  @Nullable
  @Override
  public <K extends T> K get(Key<K> key) {
    return (K) map.get(key);
  }

  @Override
  public Set<Key<? extends T>> keySet() {
    return map.keySet();
  }

  @Nullable
  @Override
  public <R extends T, K extends R> R getOrDefault(Key<K> key, @Nullable R defaultValue) {
    return (R) map.getOrDefault(key, defaultValue);
  }

  @Override
  public <K extends T> void put(Key<K> key, K value) {
    map.put(key, value);
  }

  @Override
  public void remove(Key<? extends T> key) {
    map.remove(key);
  }

  @Override
  public void clear() {
    map.clear();
  }

  @Override
  public <K extends T> K computeIfAbsent(ComputingKey<K> key) {
    return (K) map.computeIfAbsent(key, k -> key.get());
  }
}
