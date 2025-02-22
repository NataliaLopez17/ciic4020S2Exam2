package ciic4020S2Exam2;

import java.util.Comparator;

public class NearestNeighborsWrapper {

	public interface SortedList<E> {

		// valid position in the list
		// [0, size() - 1]

		public boolean add(E obj);

		public int size();

		public boolean remove(E obj);

		public boolean remove(int index);

		public int removeAll(E obj);

		public E first();

		public E last();

		public E get(int index);

		public void clear();

		public boolean contains(E e);

		public boolean isEmpty();

		public int firstIndex(E e);

		public int lastIndex(E e);

		public SortedList<E> nearestNeighbors(E e);
	}

	public static class CircularSortedDoublyLinkedList<E> implements SortedList<E> {

		private class Node {
			private E data;
			private Node next;
			private Node prev;

			public Node() {
				this.data = null;
				this.next = null;
				this.prev = null;
			}

			public E getValue() {
				return data;
			}

			public void setValue(E data) {
				this.data = data;
			}

			public Node getNext() {
				return next;
			}

			public void setNext(Node next) {
				this.next = next;
			}

			public Node getPrev() {
				return prev;
			}

			public void setPrev(Node prev) {
				this.prev = prev;
			}
		}

		private Node header; // Dummy header
		private int currentSize;
		private Comparator<E> comparator;

		public CircularSortedDoublyLinkedList(Comparator<E> comparator) {
			this.header = new Node();
			this.header.setNext(header);
			this.header.setPrev(header);
			this.currentSize = 0;
			this.comparator = comparator;
		}

		public CircularSortedDoublyLinkedList() {
			this.header = new Node();
			this.header.setNext(header);
			this.header.setPrev(header);
			this.currentSize = 0;
		}

		@Override
		public boolean add(E obj) {
			Node newNode = new Node();
			newNode.setValue(obj);
			this.currentSize++;
			Node temp = null;
			for (temp = header.getNext(); temp != header; temp = temp.getNext()) {
				// if (temp.getValue().compareTo(obj) >= 0){
				if (this.comparator.compare(temp.getValue(), obj) >= 0) {
					// we reach the place
					newNode.setNext(temp);
					newNode.setPrev(temp.getPrev());
					temp.getPrev().setNext(newNode);
					temp.setPrev(newNode);
					return true;
				}
			}
			// obj is the largest so it goes at the
			newNode.setNext(header);
			newNode.setPrev(header.getPrev());
			header.getPrev().setNext(newNode);
			header.setPrev(newNode);
			return true;
		}

		@Override
		public int size() {
			return this.currentSize;
		}

		@Override
		public boolean remove(E obj) {
			for (Node temp = header.getNext(); temp != header; temp = temp.getNext()) {
				if (this.comparator.compare(temp.getValue(), obj) == 0) {
					temp.getNext().setPrev(temp.getPrev());
					temp.getPrev().setNext(temp.getNext());
					temp.setValue(null);
					temp.setNext(null);
					temp.setPrev(null);
					this.currentSize--;
					return true;
				}
			}
			return false;
		}

		@Override
		public boolean remove(int index) {
			if (index < 0 || index >= this.size()) {
				throw new IndexOutOfBoundsException();
			} else {
				int counter = 0;
				Node temp;
				for (temp = header.getNext(); temp != header; temp = temp.getNext(), counter++) {
					if (counter == index) {
						break;
					}
				}
				temp.getNext().setPrev(temp.getPrev());
				temp.getPrev().setNext(temp.getNext());
				temp.setValue(null);
				temp.setNext(null);
				temp.setPrev(null);
				this.currentSize--;
				return true;
			}
		}

		@Override
		public int removeAll(E obj) {
			int count = 0;
			while (this.firstIndex(obj) >= 0) {
				this.remove(obj);
				count++;
			}
			return count;
		}

		@Override
		public E first() {
			return (this.isEmpty() ? null : this.header.getNext().getValue());
		}

		@Override
		public E last() {
			return (this.isEmpty() ? null : this.header.getPrev().getValue());
		}

		@Override
		public E get(int index) {
			if (index < 0 || index >= this.currentSize)
				throw new IndexOutOfBoundsException();
			else {
				int counter = 0;
				E result = null;
				for (Node temp = header.getNext(); temp != header; temp = temp.getNext(), counter++) {
					if (counter == index)
						result = temp.getValue();
				}
				return result;
			}
		}

		@Override
		public void clear() {
			while (!this.isEmpty())
				this.remove(0);
		}

		@Override
		public boolean contains(E e) {
			for (Node temp = header.getNext(); temp != header; temp = temp.getNext()) {
				if (this.comparator.compare(temp.getValue(), e) == 0)
					return true;
			}
			return false;
		}

		@Override
		public boolean isEmpty() {
			return this.size() == 0;
		}

		@Override
		public int firstIndex(E e) {
			int counter = 0;
			for (Node temp = header.getNext(); temp != header; temp = temp.getNext(), counter++) {
				if (this.comparator.compare(temp.getValue(), e) == 0)
					return counter;
			}
			return -1;
		}

		@Override
		public int lastIndex(E e) {
			int counter = this.size() - 1;
			for (Node temp = header.getPrev(); temp != header; temp = temp.getPrev(), counter--) {
				if (this.comparator.compare(temp.getValue(), e) == 0)
					return counter;
			}
			return -1;
		}

		/*
		 * Extend the functionality of the CircularSortedDoublyLinkedList by adding a
		 * method named nearestNeighbors. This method receives an object e, and find the
		 * nearest neighbors of the first occurrence of e. The nearest neighbors are
		 * defined as: up to two elements that go immediately before e (in the list),
		 * and up to two elements immediately after e (in the list). The neighbors are
		 * returned as a sorted list. If the element e is not present, the method
		 * returns an empty list. For example, if L = {Apu, Bob, Cal, Dan, Ed, Ed, Jim,
		 * Kim, Ned}, then a call to L.nearestNeighbors("Ed") returns {Cal, Dan, Ed,
		 * Jim}. Similarly, a call to L.nearestNeighbors("Kim") returns {Ed, Jim, Ned}.
		 * 
		 * Note: The original sorted list must remain unaltered after the operation has
		 * completed.
		 * 
		 */
		@Override
		public SortedList<E> nearestNeighbors(E e) {
			/* ADD CODE HERE */
			/*
			 * Note that this class uses a comparator object for comparisons, which must be
			 * sent to the constructor. The list you return should use the same comparator
			 * as the current list. See lastIndex() above for an example on how to use it
			 * for comparisons.
			 * 
			 */
			SortedList<E> result = new CircularSortedDoublyLinkedList<E>(comparator);
			int counter = 0;
			for (Node temp = header.getNext(); temp != header; temp = temp.getNext(), counter++) {
				if (header.next.data == e) {
					result.add(header.next.next.data);
					result.add(header.next.next.next.data);
					return result;
				} else if (header.prev.data == e) {
					result.add(header.prev.prev.data);
					result.add(header.prev.prev.prev.data);
					return result;
				} else {
					if (temp.getNext().getValue() == e) {
						result.add(temp.getPrev().getPrev().getValue());
						result.add(temp.getPrev().getValue());
						result.add(temp.getNext().getValue());
						result.add(temp.getNext().getNext().getValue());
						return result;
					}
				}
			}
			return result;
		}

		public static void main(String[] args) {
			SortedList<String> result = new CircularSortedDoublyLinkedList<String>();

			result.add("Apu");
			result.add("Bob");
			result.add("Cal");
			result.add("Dan");
			result.add("Ed");
			result.add("Ed");
			result.add("Jim");
			result.add("Kim");
			result.add("Ned");

			// SortedList<String> ree = nearestNeighbors("Ed");

		}

	}
}