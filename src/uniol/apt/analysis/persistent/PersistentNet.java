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

package uniol.apt.analysis.persistent;

import uniol.apt.adt.pn.Marking;
import uniol.apt.adt.pn.PetriNet;
import uniol.apt.adt.ts.TransitionSystem;
import uniol.apt.analysis.coverability.CoverabilityGraph;

/**
 * Check if a given Petri net is persistent.
 *
 * @author Vincent Göbel
 */
public class PersistentNet {

	private PetriNet pn_;
	private boolean deterministic_ = false;
	private Marking marking_ = null;
	private String label1 = null;
	private String label2 = null;

	public PersistentNet(PetriNet pn) {
		pn_ = pn;
	}

	/**
	 * This method
	 * 1) generates the reachability LTS and
	 * 2) checks whether it is persistent.
	 *
	 * If the reachability LTS is persistent, so is the original PN.
	 */
	public void check() {

		TransitionSystem ts;
		ts = new CoverabilityGraph(pn_).toCoverabilityLTS();

		PersistentTS ltsPersistent = new PersistentTS(ts);
		deterministic_ = ltsPersistent.isPersistent();
		if (ltsPersistent.getNode() != null)
			marking_ = (Marking) ltsPersistent.getNode().getExtension(Marking.class.getName());
		label1 = ltsPersistent.getLabel1();
		label2 = ltsPersistent.getLabel2();
		return;
	}

	/**
	 * @return true, if the PN is persistent, false otherwise
	 */
	public boolean isPersistent() {
		return deterministic_;
	}

	/**
	 * @return A non-persistent marking. If no such marking exists, null is returned instead.
	 */
	public Marking getMarking() {
		return marking_;
	}

	/**
	 * @return The Label/ID of a non-persistent transition. If no such transition exists, null is returned instead.
	 */
	public String getLabel1() {
		return label1;
	}

	/**
	 * @return The Label/ID of a non-persistent transition. If no such transition exists, null is returned instead.
	 */
	public String getLabel2() {
		return label2;
	}

}

// vim: ft=java:noet:sw=8:sts=8:ts=8:tw=120
