package com.lee.berries.common.list;

/**
 * 循环链表接口定义
 * @author Liuxianwei
 *
 */
public interface CircularLinkedList<E> {

	/**
	 * 向链表插入一个元素，默认在尾部
	 * @param item
	 */
	void add(E item);
	
	/**
	 * 在链表的指定位置插入一个元素
	 * @param index
	 * @param item
	 */
	void add(int index, E item);
	
	/**
	 * 向链表插入一个元素，默认在尾部
	 * @param item
	 */
	void addLast(E item);
	
	/**
	 * 向链表头部插入一个元素
	 * @param item
	 */
	void addFirst(E item);
	
	/**
	 * 删除链表指针的当前位置的元素
	 * @return
	 */
	E remove();
	
	/**
	 * 删除链表中的item元素
	 * @param item
	 */
	void remove(E item);
	
	/**
	 * 删除链表中index位置的元素
	 * @param index
	 * @return
	 */
	E remove(int index);
	
	/**
	 * 删除链表头部元素
	 * @return
	 */
	E removeFirst();
	
	/**
	 * 删除链表尾部元素
	 * @return
	 */
	E removeLast();
	
	/**
	 * 移动链表当前位置指针到下一个位置
	 */
	void next();
	
	/**
	 * 返回链表的当前位置
	 * @return
	 */
	int currentIndex();
	
	/**
	 * 返回链表当前位置元素
	 * @return
	 */
	E current();
	
	/**
	 * 返回链表的头部元素
	 * @return
	 */
	E first();
	
	/**
	 * 返回链表的尾部元素
	 * @return
	 */
	E last();
	
	/**
	 * 获取链表index位置的元素
	 * @param index
	 * @return
	 */
	E get(int index);
	
	/**
	 * 清空链表
	 */
	void clear();
	
	/**
	 * 返回链表的长度
	 * @return
	 */
	int size();
	
	/**
	 * 当前指针是否在头部
	 * @return
	 */
	boolean isFirst();
	
	/**
	 * 当前指针是否在尾部
	 * @return
	 */
	boolean isLast();
	
	/**
	 * 判断链表是否为空
	 * @return
	 */
	boolean isEmpty();
}
