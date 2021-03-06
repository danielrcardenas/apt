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

package uniol.apt.analysis.bounded;

import java.util.List;

import uniol.apt.adt.pn.PetriNet;
import uniol.apt.adt.pn.Place;
import uniol.apt.adt.pn.Transition;

/**
 * This class represents results from the {@link Bounded} check.
 * @author Uli Schlachter
 */
public class BoundedResult {
	// The Petri net that was checked.
	public final PetriNet pn;

	// A counterexample for (k-1)-boundedness (which is itself k-bounded). If the net is unbounded, this really is
	// an unbounded place.
	public final Place unboundedPlace;

	// The smallest k for which the Petri net is k-bounded, or null if the net is unbounded.
	public final Integer k;

	// The firing sequence which puts too many token on the place. If the place is unbounded (k is null), then this
	// is a firing sequence which contains two markings that cover each other.
	public final List<Transition> sequence;

	/**
	 * Construct a new BoundedResult instance.
	 * @param pn The Petri net that was examined
	 * @param place Place which serves as a counter-example
	 * @param k The smallest k for which the Petri net is k-bounded.
	 * @param sequence Firing sequence which produces too many token
	 */
	public BoundedResult(PetriNet pn, Place place, Integer k, List<Transition> sequence) {
		this.pn = pn;
		this.unboundedPlace = place;
		this.k = k;
		this.sequence = sequence;
	}

	/**
	 * Was the checked Petri net bounded?
	 * @return true if the Petri net is bounded.
	 */
	public boolean isBounded() {
		return k != null;
	}

	/**
	 * Was the checked Petri net safe?
	 * @return true if the Petri net is safe.
	 */
	public boolean isSafe() {
		return isKBounded(1);
	}

	/**
	 * Was the checked Petri net k-bounded?
	 * @param checkK The k which should be checked.
	 * @return boolean
	 */
	public boolean isKBounded(int checkK) {
		return this.k != null && this.k <= checkK;
	}
}

// vim: ft=java:noet:sw=8:sts=8:ts=8:tw=120
