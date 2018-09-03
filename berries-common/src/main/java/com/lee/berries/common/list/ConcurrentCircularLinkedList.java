package com.lee.berries.common.list;

import java.util.Collection;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 实现一个线程安全的循环链表
 * @author Liuxianwei
 *
 * @param <E>
 */
public class ConcurrentCircularLinkedList<E> implements CircularLinkedList<E> {

	static class Node<E>{
		E item;
		Node<E> next;
		Node(E item){
			this.item = item;
		}
	}
	
	final ReentrantLock lock = new ReentrantLock();
	
	private Node<E> first;
	
	private Node<E> last;
	
	private Node<E> current;
	
	private int currentIndex;
	
	private int count = 0;
	
	private int capacity;
	
	public ConcurrentCircularLinkedList(){
		this(Integer.MAX_VALUE);
	}
	
	public ConcurrentCircularLinkedList(int capacity){
		this.capacity = capacity;
		current = first = last = new Node<E>(null);
		currentIndex = -1;
	}
	
	public ConcurrentCircularLinkedList(Collection<? extends E> c){
		this(Integer.MAX_VALUE);
		for(E item:c){
			addLast(item);
		}
	}
	
	@Override
	public void add(E item) {
		addLast(item);
	}

	@Override
	public void add(int index, E item) {
		lock.lock();
		if(index < 0 || index > size()){
			throw new ArrayIndexOutOfBoundsException();
		}
		if(count >= capacity){
			throw new IllegalArgumentException();
		}
		try{
			Node<E> node = new Node<E>(item);
			/**
			 * 链表为null时,first,last,current都指向第一个元素
			 */
			if(this.isEmpty()){
				first = node;
				last = node;
				current = first;
				last.next = first;
				currentIndex = 0;
			}
			else{
				/**
				 * 头部插入的时候
				 */
				if(index == 0){
					node.next = first;
					first = node;
					last.next = node;
				}
				/**
				 * 尾部插入
				 */
				else if(index == size()){ 
					last.next = node;
					node.next = first;
					last = node;
				}
				else{
					Node<E> n = this.first;
					for(int i = 0; i < index; i++){
						n = n.next;
					}
					node.next = n.next;
					n.next = node;
				}
				if(index <= this.currentIndex){
					this.currentIndex ++;
				}
			}
			count++;
		}
		finally{
			lock.unlock();
		}
	}

	@Override
	public void addLast(E item) {
		if(count == 0){
			add(0, item);
		}
		else{
			add(count, item);
		}
	}

	@Override
	public void addFirst(E item) {
		add(0, item);
	}
	
	private Node<E> getNode(int index){
		if(index < 0 || index > size()){
			throw new ArrayIndexOutOfBoundsException();
		}
		Node<E> node = first;
		for(int i = 0; i < index; i++){
			node = node.next;
		}
		return node;
	}

	@Override
	public E remove() {
		return remove(currentIndex);
	}

	@Override
	public void remove(E item) {
		lock.lock();
		try{
			Node<E> n = this.first;
			for(int i = 0; i < size(); i++){
				if(n.item.equals(item)){
					remove(i);
					break;
				}
			}
		}
		finally{
			lock.unlock();
		}
	}

	@Override
	public E remove(int index) {
		E item = null;
		lock.lock();
		try{
			if(index < 0 || index > size()){
				throw new ArrayIndexOutOfBoundsException();
			}
			if(count == 0){
				throw new IllegalArgumentException();
			}
			/**
			 * 链表里面只剩下一个元素了
			 */
			if(first.next == first){
				current = first = last = new Node<E>(null);
				currentIndex = -1;
			}
			else{
				/**
				 * 删除头部
				 */
				if(index == 0){
					item = first.item;
					if(current == first){
						current = first.next;
					}
					Node<E> node = first;
					first = first.next;
					last.next = first;
					node.next = null;
				}
				/**
				 * 删除尾部
				 */
				else if(index == (size() - 1)){
					item = last.item;
					Node<E> pre = getNode(index - 1);
					if(current == last){
						current = pre;
						currentIndex--;
					}
					pre.next = first;
					last.next = null;
					last = pre;
				}
				else{
					Node<E> pre = getNode(index - 1);
					Node<E> node = pre.next;
					item = node.item;
					if(node == current){
						current = node.next;
					}
					pre.next = node.next;
					node.next = null;
					if(index < currentIndex){
						currentIndex --;
					}
				}
				
			}
			count --;
		}
		finally{
			lock.unlock();
		}
		return item;
	}

	@Override
	public E removeFirst() {
		return remove(0);
	}

	@Override
	public E removeLast() {
		return remove(size()-1);
	}

	@Override
	public void next() {
		lock.lock();
		try{
			current = current.next;
			currentIndex++;
			if(current == first){
				currentIndex = 0;
			}
		}
		finally{
			lock.unlock();
		}
	}

	@Override
	public int currentIndex() {
		return this.currentIndex;
	}

	@Override
	public E current() {
		return get(currentIndex);
	}

	@Override
	public E first() {
		return first.item;
	}

	@Override
	public E last() {
		return last.item;
	}
	
	@Override
	public E get(int index){
		return null;
	}

	@Override
	public void clear() {
		
	}

	@Override
	public int size() {
		return count;
	}

	@Override
	public boolean isEmpty() {
		return count == 0;
	}

	@Override
	public boolean isFirst() {
		return this.currentIndex == 0;
	}

	@Override
	public boolean isLast() {
		return this.currentIndex == (size() - 1);
	}

	@Override
	public String toString() {
		if(isEmpty()){
			return "[]";
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("[");
		Node<E> node = first;
		while(true){
			buffer.append(node.item);
			buffer.append(", ");
			node = node.next;
			if(node.next == first){
				if(node != first){
					buffer.append(node.item);
				}
				break;
			}
		}
		buffer.append("]");
		return buffer.toString();
		
	}

}
