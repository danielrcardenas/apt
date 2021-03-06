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

package uniol.apt.adt.matcher;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import uniol.apt.adt.pn.Marking;

import uniol.apt.analysis.coverability.CoverabilityGraphNode;

/**
 * Matcher to verify that a node has a matching marking
 *
 * @author vsp
 */
public class CoverNodeMarkingThatMatcher extends TypeSafeMatcher<CoverabilityGraphNode> {
	private final Matcher<Marking> matcher;

	private CoverNodeMarkingThatMatcher(Matcher<Marking> matcher) {
		this.matcher = matcher;
	}

	@Override
	public boolean matchesSafely(CoverabilityGraphNode node) {
		Marking nodeMark = node.getMarking();
		return matcher.matches(nodeMark);
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("Node with marking that ");
		matcher.describeTo(description);
	}

	@Factory
	public static <T> Matcher<CoverabilityGraphNode> nodeMarkingThat(Matcher<Marking> matcher) {
		return new CoverNodeMarkingThatMatcher(matcher);
	}
}

// vim: ft=java:noet:sw=8:sts=8:ts=8:tw=120
