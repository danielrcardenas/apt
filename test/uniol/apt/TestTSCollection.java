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

package uniol.apt;

import uniol.apt.adt.ts.State;
import uniol.apt.adt.ts.TransitionSystem;

/**
 * A collection of simple test transition systems.
 *
 * TODO: Add some more visualizations
 *
 * @author Vincent Göbel, Renke Grunwald
 *
 */
public class TestTSCollection {
	private TestTSCollection() {
	}

	public static TransitionSystem getSingleStateTS() {
		TransitionSystem ts = new TransitionSystem();

		State s0 = ts.createState("s0");

		ts.setInitialState(s0);

		return ts;
	}

	/**
	 *
	 * The transitions system:
	 * <pre>
	 *   [s]
	 *  a/ \b
	 *  v   v
	 * (t) (v)
	 * </pre>
	 *
	 * @return a ts with three states and two edges
	 */
	public static TransitionSystem getThreeStatesTwoEdgesTS() {
		TransitionSystem ts = new TransitionSystem();

		State s = ts.createState("s");
		State t = ts.createState("t");
		State v = ts.createState("v");

		ts.createArc(s, t, "a");

		ts.createArc(s, v, "b");
		ts.setInitialState(s);

		return ts;
	}

	/**
	 *
	 * The transitions system:
	 * <pre>
	 *       a
	 * [s] <---> (t)
	 * </pre>
	 *
	 * @return a ts with two states (a cycle); both edges have the same label
	 */
	public static TransitionSystem getTwoStateCycleSameLabelTS() {
		TransitionSystem ts = new TransitionSystem();

		State s = ts.createState("s");
		State t = ts.createState("t");

		ts.createArc(s, t, "a");

		ts.createArc(t, s, "a");

		ts.setInitialState(s);

		return ts;
	}

	/**
	 *
	 * The transitions system:
	 * <pre>
	 * [s]-\
	 *  ^  | a
	 *  \--/
	 * </pre>
	 *
	 * @return a ts with a single state loop
	 */
	public static TransitionSystem getSingleStateLoop() {
		TransitionSystem ts = new TransitionSystem();

		State s = ts.createState("s");

		ts.createArc(s, s, "a");

		ts.setInitialState(s);

		return ts;
	}

	public static TransitionSystem getSingleStateWithUnreachableTS() {
		TransitionSystem ts = getSingleStateTS();

		State s1 = ts.createState("s1");
		ts.createArc(s1, s1, "NotA");

		return ts;
	}

	public static TransitionSystem getSingleStateSingleTransitionTS() {
		TransitionSystem ts = new TransitionSystem();

		State s0 = ts.createState("s0");

		ts.setInitialState(s0);

		ts.createArc(s0, s0, "NotA");
		return ts;
	}

	public static TransitionSystem getNonDeterministicTS() {
		TransitionSystem ts = new TransitionSystem();

		State s0 = ts.createState("s0");
		State s1 = ts.createState("s1");
		State s2 = ts.createState("s2");

		ts.setInitialState(s0);

		ts.createArc(s0, s1, "a");
		ts.createArc(s0, s2, "a");
		return ts;
	}

	public static TransitionSystem getPersistentTS() {
		TransitionSystem ts = new TransitionSystem();

		State s0 = ts.createState("s0");
		State left = ts.createState("l");
		State right = ts.createState("r");
		State s1 = ts.createState("s1");

		ts.setInitialState(s0);

		ts.createArc(s0, left, "a");
		ts.createArc(s0, right, "b");
		ts.createArc(left, s1, "b");
		ts.createArc(right, s1, "a");

		return ts;
	}

	public static TransitionSystem getNonPersistentTS() {
		TransitionSystem ts = new TransitionSystem();

		State s0 = ts.createState("s0");
		State left = ts.createState("l");
		State right = ts.createState("r");
		State s1 = ts.createState("s1");
		State right2 = ts.createState("r2");

		ts.setInitialState(s0);

		ts.createArc(s0, left, "a");
		ts.createArc(s0, right, "b");
		ts.createArc(left, s1, "b");
		ts.createArc(right, s1, "a");
		ts.createArc(right, right2, "fail");
		return ts;
	}

	public static TransitionSystem getNotTotallyReachableTS() {
		TransitionSystem ts = new TransitionSystem();

		State s0 = ts.createState("s0");
		State s1 = ts.createState("s1");
		State fail = ts.createState("fail");

		ts.setInitialState(s0);

		ts.createArc(s0, s1, "a");
		ts.createArc(fail, s0, "b");

		return ts;
	}

	public static TransitionSystem getReversibleTS() {
		TransitionSystem ts = new TransitionSystem();

		State s0 = ts.createState("s0");
		State s1 = ts.createState("s1");
		State s2 = ts.createState("s2");

		ts.setInitialState(s0);

		ts.createArc(s0, s1, "a");
		ts.createArc(s1, s2, "b");
		ts.createArc(s2, s0, "c");
		return ts;
	}

	public static TransitionSystem getcc1LTS() {
		TransitionSystem ts = new TransitionSystem();

		ts.createState("s0");
		ts.createState("s1");
		ts.createState("s2");
		ts.createState("s3");

		ts.setInitialState(ts.getNode("s0"));

		ts.createArc(ts.getNode("s0"), ts.getNode("s1"), "a");
		ts.createArc(ts.getNode("s0"), ts.getNode("s2"), "b");
		ts.createArc(ts.getNode("s1"), ts.getNode("s0"), "c");
		ts.createArc(ts.getNode("s1"), ts.getNode("s3"), "b");
		ts.createArc(ts.getNode("s2"), ts.getNode("s0"), "a");
		ts.createArc(ts.getNode("s2"), ts.getNode("s3"), "d");
		ts.createArc(ts.getNode("s3"), ts.getNode("s1"), "d");
		ts.createArc(ts.getNode("s3"), ts.getNode("s2"), "c");

		return ts;
	}
}

// vim: ft=java:noet:sw=8:sts=8:ts=8:tw=120
