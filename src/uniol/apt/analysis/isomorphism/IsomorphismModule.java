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

package uniol.apt.analysis.isomorphism;

import uniol.apt.adt.PetriNetOrTransitionSystem;
import uniol.apt.adt.ts.TransitionSystem;
import uniol.apt.analysis.coverability.CoverabilityGraph;
import uniol.apt.module.AbstractModule;
import uniol.apt.module.Category;
import uniol.apt.module.ModuleInput;
import uniol.apt.module.ModuleInputSpec;
import uniol.apt.module.ModuleOutput;
import uniol.apt.module.ModuleOutputSpec;
import uniol.apt.module.exception.ModuleException;

/**
 * Provide the isomorphism check for petri net's reachability graph as a module.
 * @author Maike Schwammberger, Uli Schlachter
 */
public class IsomorphismModule extends AbstractModule {

	@Override
	public String getShortDescription() {
		return "Check if two Petri nets have isomorphic reachability graphs";
	}

	@Override
	public String getName() {
		return "isomorphism";
	}

	@Override
	public void require(ModuleInputSpec inputSpec) {
		inputSpec.addParameter("pn_or_ts1", PetriNetOrTransitionSystem.class,
			"The first Petri net or transition system that should be examined");
		inputSpec.addParameter("pn_or_ts2", PetriNetOrTransitionSystem.class,
			"The second Petri net or transition system that should be examined");
		inputSpec.addOptionalParameter("dontCheckLabels", String.class, null,
					       "do not check arc labels (default is to check labels)");
	}

	@Override
	public void provide(ModuleOutputSpec outputSpec) {
		outputSpec.addReturnValue("isomorphic_reachability_graphs", Boolean.class,
			ModuleOutputSpec.PROPERTY_SUCCESS);
		// TODO: Is it possible to find some witnesses?
	}

	@Override
	public void run(ModuleInput input, ModuleOutput output) throws ModuleException {
		PetriNetOrTransitionSystem arg1 = input.getParameter("pn_or_ts1", PetriNetOrTransitionSystem.class);
		PetriNetOrTransitionSystem arg2 = input.getParameter("pn_or_ts2", PetriNetOrTransitionSystem.class);
		TransitionSystem lts1 = arg1.getTs();
		TransitionSystem lts2 = arg2.getTs();
		if (lts1 == null) {
			lts1 = new CoverabilityGraph(arg1.getNet()).toReachabilityLTS();
		}
		if (lts2 == null) {
			lts2 = new CoverabilityGraph(arg2.getNet()).toReachabilityLTS();
		}

		boolean checkLabels = true;
		if(input.getParameter("dontCheckLabels", String.class) != null)
		    checkLabels = false;

		boolean result = new IsomorphismLogic(lts1, lts2, checkLabels).isIsomorphic();
		output.setReturnValue("isomorphic_reachability_graphs", Boolean.class, result);
	}

	@Override
	public Category[] getCategories() {
		return new Category[]{Category.PN, Category.LTS};
	}
}

// vim: ft=java:noet:sw=8:sts=8:ts=8:tw=120
