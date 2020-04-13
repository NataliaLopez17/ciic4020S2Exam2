package ciic4020S2Exam2;

public class AfterInStackWrapper {
	public interface Stack<E> {
		public int size();

		public boolean isEmpty();

		public E top();

		public E pop();

		public void push(E e);

		public void clear();
	}

	public static class SingleLinkedStack<E> implements Stack<E> {

		// node class
		@SuppressWarnings("hiding")
		private class Node<E> {
			private E element;
			private Node<E> next;

			@SuppressWarnings("unused")
			public Node(E element, Node<E> next) {
				super();
				this.element = element;
				this.next = next;
			}

			public Node() {
				super();
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

		}

		private Node<E> header;
		private int currentSize;

		public SingleLinkedStack() {
			this.header = new Node<>();
			this.currentSize = 0;
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
		public E top() {
			if (this.isEmpty())
				return null;
			else
				return this.header.getNext().getElement();
		}

		@Override
		public E pop() {
			if (this.isEmpty())
				return null;
			else {
				E result = this.header.getNext().getElement();
				Node<E> temp = this.header.getNext();
				this.header.setNext(temp.getNext());
				temp.setNext(null);
				temp.setElement(null);
				this.currentSize--;
				return result;
			}
		}

		@Override
		public void push(E e) {
			Node<E> newNode = new Node<>();
			newNode.setElement(e);
			newNode.setNext(this.header.getNext());
			this.header.setNext(newNode);
			this.currentSize++;
		}

		@Override
		public void clear() {
			while (this.pop() != null)
				;
		}
	}

	/*
	 * Question text Write a non-member function afterInStack which returns a new
	 * stack with all the elements that go after an element e in a stack S (i.e. the
	 * "sub-stack" below e). The function receives as parameters the target stack S
	 * and the element to find. If the element e is not in the stack, the function
	 * returns an empty stack. You can assume that the element appears at most once
	 * in the stack. After completion, the original stack must have the same
	 * elements in the same order as it had at the beginning. In addition, the
	 * relative structure of the resulting stack must be same at that in the
	 * original stack. For example, if the original stack S = {Joe, Ned, Jil, Bob,
	 * Apu}, where Joe is the top, then a call to afterInStack(S, "Jil") will return
	 * the stack {Bob, Apu}, and a call to afterInStack(S, "Mel") will return an
	 * empty stack.
	 */
	public static Stack<String> afterInStack(Stack<String> S, String e) {
		Stack<String> newStack = new SingleLinkedStack<String>();
		Stack<String> copyStack = new SingleLinkedStack<String>();
		Stack<String> newStack2 = new SingleLinkedStack<String>();
		Stack<String> emptyStack = new SingleLinkedStack<String>();
		int count = 0;
		while (!S.isEmpty()) {
			if (S.top() == e) {
				count++;
				newStack2.push(S.top());
			} else if (S.top() != e && count > 0) {
				newStack.push(S.top());
			}
			copyStack.push(S.pop());
		}
		while (!copyStack.isEmpty()) {
			S.push(copyStack.pop());
		}

		if (count == 0) {
			return emptyStack;
		}
		Stack<String> resultStack = new SingleLinkedStack<String>();
		while (!newStack.isEmpty()) {
			resultStack.push(newStack.pop());
		}
		return resultStack;
	}

}