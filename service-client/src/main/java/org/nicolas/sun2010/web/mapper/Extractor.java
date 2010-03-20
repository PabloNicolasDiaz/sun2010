package org.nicolas.sun2010.web.mapper;

import org.jdom.Document;

public interface Extractor {

	void process(Document doc) throws BadDocumentException;

}