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

package uniol.apt.generator.philnet;

import uniol.apt.adt.pn.PetriNet;
import uniol.apt.adt.pn.Place;
import uniol.apt.adt.pn.Transition;

/**
 * Generate a philosophers net
 *
 * Each philosopher has two states:
 * thinking, where he don't hold forks
 * eating, where he hold his and his neighbors forks
 *
 * @author vsp
 */
public class BistatePhilNetGenerator extends AbstractPhilNetGenerator {

	@Override
	protected Philosopher addPhilPT(PetriNet pn, int i) {
		Place pfork = pn.createPlace("fork" + Integer.toString(i));
		Place peat = pn.createPlace("eating" + Integer.toString(i));
		Place pthink = pn.createPlace("thinking" + Integer.toString(i));

		pthink.setInitialToken(1);
		pfork.setInitialToken(1);

		Transition tput = pn.createTransition("put" + Integer.toString(i));
		Transition ttake = pn.createTransition("take" + Integer.toString(i));

		Philosopher phil = new Philosopher(pfork,
				new Place[]{pthink, peat},
				new Transition[]{ttake, tput});
		pfork.putExtension("philosopher", phil);
		pthink.putExtension("philosopher", phil);
		peat.putExtension("philosopher", phil);
		ttake.putExtension("philosopher", phil);
		tput.putExtension("philosopher", phil);

		return phil;
	}

	@Override
	protected void addPhilA(PetriNet pn, int i, int next) {
		Place pforki = pn.getPlace("fork" + Integer.toString(i));
		Place pforkn = pn.getPlace("fork" + Integer.toString(next));
		Place peat = pn.getPlace("eating" + Integer.toString(i));
		Place pthink = pn.getPlace("thinking" + Integer.toString(i));

		Transition tput = pn.getTransition("put" + Integer.toString(i));
		Transition ttake = pn.getTransition("take" + Integer.toString(i));

		pn.createFlow(pforki, ttake);
		pn.createFlow(pforkn, ttake);
		pn.createFlow(pthink, ttake);
		pn.createFlow(ttake, peat);
		pn.createFlow(peat, tput);
		pn.createFlow(tput, pthink);
		pn.createFlow(tput, pforki);
		pn.createFlow(tput, pforkn);
	}
}

// vim: ft=java:noet:sw=8:sts=8:ts=8:tw=120
