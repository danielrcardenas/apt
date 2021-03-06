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

package uniol.apt.adt.extension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uniol.apt.adt.exception.StructureException;
import uniol.apt.util.Pair;

/**
 * The Extensible class describes an advanced object consisting of an id and a key \rightarrow value property mapping
 * and for every object a flag is saved if the object should by copied (referenzcopy), if the owner object is copied.
 * <p/>
 * @author Dennis-Michael Borde, Manuel Gieseking
 */
public class Extensible implements IExtensible {

	private final HashMap<String, Pair<Object, Boolean>> extensions = new HashMap<>();

	/**
	 * Saves the given value using the key as identifier with the flag to copy this object by coping the owner
	 * object.
	 * <p/>
	 * @param key   An identifying key as string.
	 * @param value Any value.
	 */
	@Override
	public void putExtension(String key, Object value) {
		this.extensions.put(key, new Pair<>(value, true));
	}

	/**
	 * Saves the given value using the key as identifier with the the copy flag.
	 * <p/>
	 * @param key   An identifying key as string.
	 * @param value Any value.
	 * @param copy  The flag if this object should be copied.
	 */
	public void putExtension(String key, Object value, boolean copy) {
		this.extensions.put(key, new Pair<>(value, copy));
	}

	/**
	 * Retrieves the saved value using the key as identifier.
	 * <p/>
	 * @param key An identifying key as string.
	 * <p/>
	 * @return The saved value.
	 * <p/>
	 * @throws StructureException thrown if the key is not found.
	 */
	@Override
	public Object getExtension(String key) {
		Pair<Object, Boolean> pair = this.extensions.get(key);
		if (pair == null) {
			throw new StructureException("Extention '" + key + "' not found.");
		}
		return pair.getFirst();
	}

	/**
	 * Calculates a list of pairs key-value of all extentions. Attention it's a referenzcopy!
	 * <p/>
	 * @return A list of key-value-pairs of all extentions.
	 */
	public List<Pair<String, Object>> getExtensions() {
		ArrayList<Pair<String, Object>> ret = new ArrayList<>();
		for (String key : extensions.keySet()) {
			Pair<Object, Boolean> pair = extensions.get(key);
			ret.add(new Pair<>(key, pair.getFirst()));
		}
		return ret;
	}

	/**
	 * Calculates a list of pairs key-value which should be copied. Attention it's a referenzcopy!
	 * <p/>
	 * @return A list of pair key-value-pairs which should be copied.
	 */
	public List<Pair<String, Object>> getCopyExtensions() {
		ArrayList<Pair<String, Object>> ret = new ArrayList<>();
		for (String key : extensions.keySet()) {
			Pair<Object, Boolean> pair = extensions.get(key);
			if (pair.getSecond()) {
				ret.add(new Pair<>(key, pair.getFirst()));
			}
		}
		return ret;
	}

	/**
	 * Copies the extentions from the given Extensible, marked as to copy, to this extension list. Attention it's
	 * just a reference copy!
	 * <p/>
	 * @param e The Extensible to copy from.
	 */
	public final void copyExtensions(Extensible e) {
		for (Pair<String, Object> pair : e.getCopyExtensions()) {
			putExtension(pair.getFirst(), pair.getSecond());
		}
	}
}

// vim: ft=java:noet:sw=8:sts=8:ts=8:tw=120
