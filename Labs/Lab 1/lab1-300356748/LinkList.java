/**
 * Builds a singly linked list of size 5 and prints it to the console.
 * 
 * @author Jochen Lang
 */

class LinkList<E> {
	GNode<E> llist;

	LinkList(int sz) {
		if (sz <= 0) {
			llist = null;
		} else {
			// start with list of size 1
			llist = new GNode<>(null, null);
			GNode<E> current = llist; // temp node for loop
			// add further nodes
			for (int i = 1; i < sz; ++i) {
				// create node and attach it to the list
				GNode<E> node2Add = new GNode<>(null, null);
				current.setNext(node2Add); // add first node
				current = node2Add;
			}
		}
	}

	/**
	 * Print all the elements of the list assuming that they are Strings
	 */
	public void print() {
		/* Print the list */
		GNode<E> current = llist; // point to the first node
		while (current != null) {
			System.out.print((String) current.getElement() + " ");
			current = current.getNext(); // move to the next
		}
		System.out.println();
	}

	public void deleteFirst() {
		if (llist != null) {
			llist = llist.getNext();
		}
	}

	public void deleteLast() {
		if (llist == null)
			return; // no node
		GNode<E> prev = llist;
		GNode<E> current = prev.getNext();
		if (current == null) { // only 1 node
			llist = null;
			return;
		}
		while (current.getNext() != null) { // more than 1 node
			prev = current;
			current = current.getNext();
		}
		prev.setNext(null);
		return;
	}

	// create and display a linked list
	public static void main(String[] args) {
		/* Create the list */
		LinkList<String> llist = new LinkList<>(5);
		/* Populate the elements of the list */
		GNode<String> current = llist.llist;
		int j = 0;
		while (current != null) {
			current.setElement(Integer.toString(j));
			current = current.getNext();
			j++;
		}
		/* Print the list */
		llist.print();
		/* delete first and print */
		llist.deleteFirst();
		llist.print();
		/* delete last and print 5 times */
		for (int i = 0; i < 5; ++i) {
			llist.deleteLast();
			llist.print();
		}
	}
}
