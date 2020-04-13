package ciic4020S2Exam2;

import java.io.PrintStream;
import java.util.ArrayList;

public class QPredecessorWrapper {

	public interface Queue<E> {
		public int size();

		public boolean isEmpty();

		public E front();

		public void enqueue(E e);

		public E dequeue();

		public void makeEmpty();

		public void print(PrintStream P);
	}

	public static class DoublyLinkedQueue<E> implements Queue<E> {

		private static class Node<E> {
			private E element;
			private Node<E> next, prev;

			public Node() {
				this.element = null;
				this.next = this.prev = null;

			}

			public E getElement() {
				return element;
			}

			public void setElement(E element) {
				this.element = element;
			}

			public Node<E> getNext() {
				return next;
			}

			public void setNext(Node<E> next) {
				this.next = next;
			}

			public Node<E> getPrev() {
				return prev;
			}

			public void setPrev(Node<E> prev) {
				this.prev = prev;
			}
		}

		private Node<E> header, trailer;
		private int currentSize;

		public DoublyLinkedQueue() {
			this.currentSize = 0;
			this.header = new Node<>();
			this.trailer = new Node<>();
			this.header.setNext(this.trailer);
			this.trailer.setPrev(this.header);
		}

		@Override
		public int size() {
			return this.currentSize;
		}

		@Override
		public boolean isEmpty() {
			return this.size() == 0;
		}

		@Override
		public E front() {
			return (this.isEmpty() ? null : this.header.getNext().getElement());
		}

		@Override
		public E dequeue() {
			if (this.isEmpty())
				return null;
			else {
				Node<E> target = null;
				target = this.header.getNext();
				E result = target.getElement();
				this.header.setNext(target.getNext());
				target.getNext().setPrev(this.header);
				target.setNext(null);
				target.setPrev(null);
				target.setElement(null);
				this.currentSize--;
				return result;
			}
		}

		@Override
		public void enqueue(E e) {
			Node<E> newNode = new Node<E>();
			newNode.setElement(e);
			newNode.setNext(this.trailer);
			newNode.setPrev(this.trailer.getPrev());
			this.trailer.setPrev(newNode);
			newNode.getPrev().setNext(newNode);
			this.currentSize++;
		}

		@Override
		public void makeEmpty() {
			while (this.dequeue() != null)
				;

		}

		@Override
		public void print(PrintStream P) {
			Node<E> temp = this.header.getNext();
			while (temp != this.trailer) {
				P.println(temp.getElement());
				temp = temp.getNext();
			}
		}
	}

	/*
	 * Question text Extend the functionality of the Queue implemented with a Doubly
	 * Linked List by adding a non-member method named predecessor. This method
	 * returns the element that goes right before another element e in the queue
	 * (i.e. the element that would get dequeued just before the first occurrence of
	 * e). For example, if the queue Q = {Joe, Joe, Ned, Bob, Kim}, where Joe is at
	 * the front, then a call to predecessor(Q, "Bob") returns Ned, while
	 * predecessor(Q, "Joe") returns null. The method returns null if e is not
	 * found. Note: After the operation, the queue must have the same elements, in
	 * the same order, as before the operation.
	 */
	public static <E> E predecessor(Queue<E> Q, E e) {
		Queue<E> copyQ = new DoublyLinkedQueue<E>();
		ArrayList<E> k = new ArrayList<E>();
		E val = null;
		while (!Q.isEmpty()) {
			if (Q.front() == e && !k.contains(e)) {
				for (int i = 0; i < copyQ.size(); i++) {
					val = copyQ.front();
					copyQ.enqueue(copyQ.dequeue());
				}
			}
			k.add(Q.front());
			copyQ.enqueue(Q.dequeue());
		}
		while (!copyQ.isEmpty()) {
			Q.enqueue(copyQ.dequeue());
		}
		return val;

	}

	public static void main(String[] args) {
		Queue<String> newQ = new DoublyLinkedQueue<String>();

		newQ.enqueue("Joe");
		newQ.enqueue("Joe");
		newQ.enqueue("Ned");
		newQ.enqueue("Bob");
		newQ.enqueue("Kim");

		String ree = predecessor(newQ, "Bob");
		System.out.println(ree);

	}
}