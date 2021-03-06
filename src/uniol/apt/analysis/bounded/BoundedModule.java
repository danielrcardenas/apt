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

import uniol.apt.module.AbstractModule;
import uniol.apt.module.Category;
import uniol.apt.module.ModuleInput;
import uniol.apt.module.ModuleInputSpec;
import uniol.apt.module.ModuleOutput;
import uniol.apt.module.ModuleOutputSpec;
import uniol.apt.module.exception.ModuleException;

import uniol.apt.adt.pn.PetriNet;
import uniol.apt.adt.pn.Place;
import uniol.apt.analysis.language.FiringSequence;
import uniol.apt.analysis.separation.FiringSequenceLabels;

/**
 * Provide the boundedness test as a module.
 * @author Uli Schlachter, vsp
 */
public class BoundedModule extends AbstractModule {

	@Override
	public String getShortDescription() {
		return "Check if a Petri net is bounded or k-bounded";
	}

	@Override
	public String getLongDescription() {
		return getShortDescription()
			+ ". A Petri net is bounded if there is an upper limit for the number of token on each place. "
			+ "It is k-bounded if this limit isn't bigger than k.";
	}

	@Override
	public String getName() {
		return "bounded";
	}

	@Override
	public void require(ModuleInputSpec inputSpec) {
		inputSpec.addParameter("pn", PetriNet.class, "The Petri net that should be examined");
		inputSpec.addOptionalParameter("k", Integer.class, null, "If given, k-boundedness is checked");
	}

	@Override
	public void provide(ModuleOutputSpec outputSpec) {
		outputSpec.addReturnValue("bounded", Boolean.class, ModuleOutputSpec.PROPERTY_SUCCESS);
		outputSpec.addReturnValue("witness_place", Place.class);
		outputSpec.addReturnValue("witness_firing_sequence", FiringSequenceLabels.class);
	}

	@Override
	public void run(ModuleInput input, ModuleOutput output) throws ModuleException {
		PetriNet pn = input.getParameter("pn", PetriNet.class);
		Integer k = input.getParameter("k", Integer.class);
		BoundedResult result = new Bounded().checkBounded(pn);
		boolean boundedResult;
		if (k == null) {
			boundedResult = result.isBounded();
		} else {
			boundedResult = result.isKBounded(k);
		}
		output.setReturnValue("bounded", Boolean.class, boundedResult);
		output.setReturnValue("witness_place", Place.class, result.unboundedPlace);
		output.setReturnValue("witness_firing_sequence", FiringSequenceLabels.class,
			new FiringSequence(result.sequence).toFiringSequenceLabels());
	}

	@Override
	public Category[] getCategories() {
		return new Category[]{Category.PN};
	}
}

// vim: ft=java:noet:sw=8:sts=8:ts=8:tw=120
