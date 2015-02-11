package com.jujaga.e2e.model.export.body;

import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.generic.SET;

import com.jujaga.e2e.constant.Constants;
import com.jujaga.e2e.constant.Constants.IdPrefixes;

class BodyUtils {
	public static SET<II> buildUniqueId(IdPrefixes prefix, Integer id) {
		StringBuilder sb = new StringBuilder();
		sb.append(prefix).append("-").append(id.toString());
		
		II ii = new II();
		ii.setRoot(Constants.EMR.EMR_OID);
		ii.setAssigningAuthorityName(Constants.EMR.EMR_VERSION);
		ii.setExtension(sb.toString());
		return new SET<II>(ii);
	}
}
