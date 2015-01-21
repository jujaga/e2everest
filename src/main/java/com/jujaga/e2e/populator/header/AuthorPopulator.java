package com.jujaga.e2e.populator.header;

import java.util.ArrayList;
import java.util.Arrays;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ContextControl;

import com.jujaga.e2e.populator.Populator;

public class AuthorPopulator extends Populator {
	public AuthorPopulator(Integer demographicNo) {
		// TODO Consider AssignedAuthor vs flat Author structure
	}

	@Override
	public void populate() {
		Author author = new Author();
		author.setContextControlCode(ContextControl.OverridingPropagating);

		clinicalDocument.setAuthor(new ArrayList<Author>(Arrays.asList(author)));
	}
}
