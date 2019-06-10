/**
 * 
 */
package com.lottery.common.util;

/**
 * 一对值
 *
 *
 */
public class PairValue<L, R> {

	private final L left;
	private final R right;
	
	public PairValue(L left, R right) {
		this.left = left;
		this.right = right;
	}

	
	public L getLeft() {
		return left;
	}


	public R getRight() {
		return right;
	}

	@Override
	public int hashCode() {
		return this.left.hashCode() ^ this.right.hashCode();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (!(o instanceof PairValue<?, ?>)) {
			return false;
		}
		PairValue<L, R> pairo = (PairValue<L, R>) o;
		return this.left.equals(pairo.getLeft())
				&& this.right.equals(pairo.getRight());
	}


	@Override
	public String toString() {
		return "PairValue [left=" + left + ", right=" + right + "]";
	}
	
	
}
