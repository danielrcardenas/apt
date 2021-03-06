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

package uniol.apt.analysis.bicf;

import uniol.apt.module.AbstractModule;
import uniol.apt.module.Category;
import uniol.apt.module.ModuleInput;
import uniol.apt.module.ModuleInputSpec;
import uniol.apt.module.ModuleOutput;
import uniol.apt.module.ModuleOutputSpec;
import uniol.apt.module.exception.ModuleException;

import uniol.apt.analysis.language.FiringSequence;
import uniol.apt.analysis.separation.FiringSequenceLabels;
import uniol.apt.adt.pn.Marking;
import uniol.apt.adt.pn.PetriNet;
import uniol.apt.adt.pn.Transition;

/**
 * Provide the BiCF test as a module.
 * @author Uli Schlachter, vsp
 */
public class BiCFModule extends AbstractModule {

	@Override
	public String getShortDescription() {
		return "Check if a Petri net is binary conflict free (BiCF)";
	}

	@Override
	public String getName() {
		return "bicf";
	}

	@Override
	public void require(ModuleInputSpec inputSpec) {
		inputSpec.addParameter("pn", PetriNet.class, "The Petri net that should be examined");
	}

	@Override
	public void provide(ModuleOutputSpec outputSpec) {
		outputSpec.addReturnValue("bicf", Boolean.class, ModuleOutputSpec.PROPERTY_SUCCESS);
		outputSpec.addReturnValue("witness_marking", Marking.class);
		outputSpec.addReturnValue("witness_firing_sequence", FiringSequenceLabels.class);
		outputSpec.addReturnValue("witness_transition1", Transition.class);
		outputSpec.addReturnValue("witness_transition2", Transition.class);
	}

	@Override
	public void run(ModuleInput input, ModuleOutput output) throws ModuleException {
		PetriNet pn = input.getParameter("pn", PetriNet.class);
		BiCF.Result result = new BiCF().check(pn);
		output.setReturnValue("bicf", Boolean.class, result == null);
		if (result != null) {
			FiringSequence firingSequence = new FiringSequence(result.sequence);

			output.setReturnValue("witness_marking", Marking.class, result.m);
			output.setReturnValue("witness_firing_sequence", FiringSequenceLabels.class,
				firingSequence.toFiringSequenceLabels());
			output.setReturnValue("witness_transition1", Transition.class, result.t1);
			output.setReturnValue("witness_transition2", Transition.class, result.t2);
		}
	}

	@Override
	public Category[] getCategories() {
		return new Category[]{Category.PN};
	}
}

// vim: ft=java:noet:sw=8:sts=8:ts=8:tw=120
