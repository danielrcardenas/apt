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

package uniol.apt.ui.impl.returns;

import java.util.Set;

import uniol.apt.adt.pn.Place;
import uniol.apt.analysis.trapsAndSiphons.TrapsSiphonsList;
import uniol.apt.ui.ReturnValueTransformation;

/**
 * Transform a list of traps/siphons into a string.
 * @author Uli Schlachter
 */
public class TrapsSiphonsListReturnValueTransformation implements ReturnValueTransformation<TrapsSiphonsList> {
	@Override
	public String transform(TrapsSiphonsList tsList) {
		StringBuilder sb = new StringBuilder("{");
		boolean firstList = true;

		for (Set<Place> places : tsList) {
			if (!firstList)
				sb.append(", ");
			firstList = false;

			sb.append("{");

			boolean firstPlace = true;
			for (Place place : places) {
				if (!firstPlace)
					sb.append(", ");
				firstPlace = false;
				sb.append(place.getId());
			}

			sb.append("}");
		}
		sb.append("}");

		return sb.toString();
	}
}

// vim: ft=java:noet:sw=8:sts=8:ts=8:tw=120
