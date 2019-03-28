package hologram.Queue;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class InMemoryQueueService<E> {

  private final List<E> queue;

  public InMemoryQueueService() {
    this.queue = new LinkedList<>();
  }

  public void add(E record) {
    queue.add(record);
  }

  public E poll() {
    return queue.get(0);
  }

  public E poll(int i) {
    return queue.get(i);
  }

  public boolean isEmpty() {
    return queue.isEmpty();
  }

  public int size() {
    return queue.size();
  }

  public void clear() {
    queue.clear();
  }
}
