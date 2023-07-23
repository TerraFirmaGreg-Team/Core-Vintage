package net.dries007.tfc.api.util;

import java.io.Serializable;
import java.util.Objects;

/**
 * Класс Pair представляет тройку значений.
 *
 * @param <L> тип левого элемента
 * @param <M> тип среднего элемента
 * @param <R> тип правого элемента
 */
public final class Triple<L, M, R> implements Serializable {

	private static final long serialVersionUID = 1L;

	private final L left;
	private final M middle;
	private final R right;

	/**
	 * Создает новую упорядоченную тройку из заданных элементов.
	 *
	 * @param left   левый элемент
	 * @param middle средний элемент
	 * @param right  правый элемент
	 */
	public Triple(L left, M middle, R right) {
		this.left = left;
		this.middle = middle;
		this.right = right;
	}

	/**
	 * Возвращает левый элемент этой тройки.
	 *
	 * @return левый элемент
	 */
	public L getLeft() {
		return left;
	}

	/**
	 * Возвращает средний элемент этой тройки.
	 *
	 * @return средний элемент
	 */
	public M getMiddle() {
		return middle;
	}

	/**
	 * Возвращает правый элемент этой тройки.
	 *
	 * @return правый элемент
	 */
	public R getRight() {
		return right;
	}

	/**
	 * Сравнивает эту тройку с другим объектом на равенство.
	 *
	 * @param obj объект, с которым нужно сравнить
	 * @return true, если этот объект равен указанному объекту; false в противном случае
	 */
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Triple)) {
			return false;
		}
		Triple<?, ?, ?> other = (Triple<?, ?, ?>) obj;
		return Objects.equals(left, other.left) && Objects.equals(middle, other.middle)
				&& Objects.equals(right, other.right);
	}

	/**
	 * Возвращает хэш-код этой тройки.
	 *
	 * @return хэш-код
	 */
	public int hashCode() {
		return Objects.hash(left, middle, right);
	}

	/**
	 * Возвращает строковое представление этой тройки в виде (left,middle,right).
	 *
	 * @return строковое представление
	 */
	public String toString() {
		return "(" + left + "," + middle + "," + right + ")";
	}
}
