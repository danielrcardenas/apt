/*-
 * APT - Analysis of Petri Nets and labeled Transition systems
 * Copyright (C) 2012-2013  Members of the project group APT
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package uniol.apt.adt.pn;

import org.testng.annotations.Test;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.CoreMatchers.*;

/**
 * @author Dennis-Michael Borde, Uli Schlachter, vsp
 */
@Test
public class MarkingTest {

	static private final Integer OMEGA = null;
	private PetriNet pn;

	@BeforeClass
	public void setupPN() {
		pn = new PetriNet("foo");
	}

	@AfterClass
	public void teardownPN() {
		pn = null;
	}

	private Place[] createPlaces(int size) {
		Place[] places = new Place[size];
		for (int i = 0; i < size; i++) {
			String id = Integer.toString(i);
			places[i] = pn.containsPlace(id) ? pn.getPlace(id) : pn.createPlace(id);
		}
		return places;
	}

	private Marking createMarking(Integer... token) {
		Marking mark = new Marking(pn);
		Place[] places = createPlaces(token.length);
		assert places.length == token.length;
		for (int i = 0; i < token.length; i++) {
			if (token[i] == null) {
				mark.setToken(places[i], Token.OMEGA);
			} else {
				mark.setToken(places[i], token[i]);
			}
		}
		return mark;
	}

	private void markingEqual(Integer... token) {
		Marking a = createMarking(token);
		Marking b = createMarking(token);
		assertEquals(a, b);
		assertEquals(a.hashCode(), b.hashCode());
	}

	@Test
	public void testMarkingEqual1() {
		markingEqual();
	}

	@Test
	public void testMarkingEqual2() {
		markingEqual(1, 2, 3);
	}

	@Test
	public void testNotEqual1() {
		Marking a = createMarking(1, 2, 3);
		assertFalse(a == null);
	}

	@Test
	public void testNotEqual2() {
		Marking a = createMarking(1, 2, 3);
		assertFalse(a.equals("foo"));
	}

	@Test
	public void testMarkingNotEqual() {
		Marking a = createMarking(1, 2, 3);
		Marking b = createMarking(1, 5, 3);
		assertFalse(a.equals(b));
	}

	private void coverSucceed(Integer[] cover, Integer[] covered, Integer[] expected) {
		assert cover.length == covered.length;
		assert cover.length == expected.length;
		Marking mcover = createMarking(cover);
		Marking mcovered = createMarking(covered);
		Marking mexpected = createMarking(expected);
		assertTrue(mcover.covers(mcovered));
		assertEquals(mcover, mexpected);
	}

	private void coverFail(Integer[] cover, Integer[] covered) {
		assert cover.length == covered.length;
		Marking mcover = createMarking(cover);
		Marking mcovered = createMarking(covered);
		Marking mexpected = createMarking(cover);
		assertFalse(mcover.covers(mcovered));
		assertEquals(mcover, mexpected);
	}

	@Test
	public void testCover1() {
		Integer covered[] = {1, 2, 2};
		Integer cover[] = {1, 2, 3};
		Integer expected[] = {1, 2, OMEGA};
		coverSucceed(cover, covered, expected);
	}

	@Test
	public void testCover2() {
		Integer covered[] = {1, 1, 1};
		Integer cover[] = {2, 2, 2};
		Integer expected[] = {OMEGA, OMEGA, OMEGA};
		coverSucceed(cover, covered, expected);
	}

	@Test
	public void testCover4() {
		Integer covered[] = {21, OMEGA};
		Integer cover[] = {42, OMEGA};
		Integer expected[] = {OMEGA, OMEGA};
		coverSucceed(cover, covered, expected);
	}

	@Test
	public void testNoCover1() {
		Integer covered[] = {1, 3, 2};
		Integer cover[] = {1, 2, 3};
		coverFail(cover, covered);
	}

	@Test
	public void testNoCover2() {
		Integer cover[] = {};
		coverFail(cover, cover);
	}

	@Test
	public void testNoCover3() {
		Integer covered[] = {42};
		Integer cover[] = {42};
		coverFail(cover, covered);
	}

	@Test
	public void testNoCover4() {
		Integer covered[] = {21, OMEGA};
		Integer cover[] = {42, 0};
		coverFail(cover, covered);
	}

	@Test
	public void testNoCover5() {
		Integer covered[] = {21};
		Integer cover[] = {OMEGA};
		coverFail(cover, covered);
	}

	@Test
	public void testValueHashCode1() {
		Token v1 = new Token(3);
		Token v2 = new Token(3);
		assertEquals(v1, v2);
		assertEquals(v1.hashCode(), v2.hashCode());
	}

	@Test
	public void testValueHashCode2() {
		Token v1 = new Token(0);
		Token v2 = new Token(0);
		assertEquals(v1, v2);
		assertEquals(v1.hashCode(), v2.hashCode());
	}

	@Test
	public void testCopyConstructor() {
		Marking mark = createMarking(1, 2, 3);
		Marking other = new Marking(mark);
		// Change the number of tokens on a random place
		mark.addToken(pn.getPlaces().iterator().next(), 42);

		// Verify that the two Markings aren't equal (=the copy constructor works correctly)
		assertThat(mark, not(equalTo(other)));
	}
}

// vim: ft=java:noet:sw=8:sts=8:ts=8:tw=120
