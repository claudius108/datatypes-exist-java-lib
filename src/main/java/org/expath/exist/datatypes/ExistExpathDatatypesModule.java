/*
 *  eXist Open Source Native XML Database
 *  Copyright (C) 2011 The eXist Project
 *  http://exist-db.org
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public License
 *  as published by the Free Software Foundation; either version 2
 *  of the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 *
 *  $Id$
 */
package org.expath.exist.datatypes;

import java.util.List;
import java.util.Map;

import org.exist.xquery.AbstractInternalModule;
import org.exist.xquery.FunctionDef;

import ro.kuberam.libs.java.xmlDatatypes.ModuleDescription;

/**
 * Implements the module definition.
 * 
 * @author Claudius Teodorescu <claudius.teodorescu@gmail.com>
 */
public class ExistExpathDatatypesModule extends AbstractInternalModule {

	public static String NAMESPACE_URI = "";
	static {
		NAMESPACE_URI = ModuleDescription.NAMESPACE_URI;
	}
	public static String PREFIX = "";
	static {
		PREFIX = ModuleDescription.PREFIX;
	}
	public final static String INCLUSION_DATE = "2011-03-24";
	public final static String RELEASED_IN_VERSION = "eXist-1.5";

	private final static FunctionDef[] functions = { new FunctionDef(Base64Binary.signature, Base64Binary.class) };

	public ExistExpathDatatypesModule(Map<String, List<? extends Object>> parameters) throws Exception {
		super(functions, parameters);
	}

	@Override
	public String getNamespaceURI() {
		return NAMESPACE_URI;
	}

	@Override
	public String getDefaultPrefix() {
		return PREFIX;
	}

	@Override
	public String getDescription() {
		return ModuleDescription.MODULE_DESCRIPTION;
	}

	@Override
	public String getReleaseVersion() {
		return RELEASED_IN_VERSION;
	}
}