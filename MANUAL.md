# Introduction

The E2Everest project is a working proof-of-concept E2E-DTC 1.4 implementation that is compatible for use with the Physicians Data Collaborative network. The code here is  originally built for the OSCAR EMR, but the framework itself is designed to be as EMR agnostic as possible.

This Manual is aimed at developers, system engineers and integrators looking to add E2E-DTC support to their Electronic Medical Record system. Since the underlying MARC-HI Everest Framework models the complex Clinical Document Architecture used in E2E, using this framework reduces the amount of time and effort required in order to properly implement E2E.

Ultimately, the E2Everest framework is a great stepping stone for quickly implementing a compatible E2E document export. Using this framework can rapidly accelerate the user's understanding of the CDA model. In turn, users can then look to extend and implement other CDA based documents from this framework.

## Prequisites

Since this framework was written in Java, at the bare minimum, the user should be able to work with and understand Java code. As well, the user should be familiar with Object Oriented Programming concepts, as well as a working knowledge of their EMR's data model.

Many Java applications utilize the Java Persistence API and its related ecosystem in order to interface with a database such as MySQL. As a consequence of this, E2Everest users are expected to know how to use the JPA framework and have a working understanding of common JPA frameworks such as Spring and Hibernate.

Lastly, E2Everest is only effective if the user has at least a conceptural level of understanding of HL7v3 and CDA. Users should be somewhat familiar with the RIM (Reference Information Model),  R-MIM (Refined Message Information Model), ITS (Implementable Technology Specification), and HL7 Data Types. Knowledge of HL7 and CDA is crucial for understanding the E2E specification since E2E is a specific implementation of the CDA.

## Resources

There exists plenty of resources available online which will cover many of the prerequisite topics. However, there are a few resources that have proven themselves to be invaluable when working with E2Everest:

- MARC-HI Everest Forums [http://te.marc-hi.ca/forums/default.aspx?g=topics&f=2](http://te.marc-hi.ca/forums/default.aspx?g=topics&f=2)
- Everest JavaDocs [http://te.marc-hi.ca/library/en/jdoc/jev/index.html?overview-summary.html](http://te.marc-hi.ca/library/en/jdoc/jev/index.html?overview-summary.html)
- Advanced Everest Developer's Handbook [http://www.lulu.com/shop/justin-fyfe/advanced-everest-developers-handbook/hardcover/product-21300345.html](http://www.lulu.com/shop/justin-fyfe/advanced-everest-developers-handbook/hardcover/product-21300345.html)

The Everest Forums contain many threads from the Everest community. Many of the issues and concerns that you may encounter are likely to have been addressed at one point or another in these forums. The Everest JavaDocs are self-explanatory; they tell you how the Everest data models work. Lastly, the eBook could be a useful reference for understanding the nuances of Everest.

# Design

The main goal for this framework is to make it quick and easy to create an E2E document. E2Everest leverages the Everest framework in order to enforce CDA standard compliance as well as making it as easy as possible to learn how to properly use the complex CDA framework. Ultimately, the E2Everest library should operate mostly "under the hood" in the sense that most of the complexity of generating an E2E document should be behind the scenes.

The key behind making E2Everest easy to use is that it should do two relatively straightforward things: create a Clinical Document for a given patient demographic, and generate the xml from the Clinical Document. For example:

	// Populate Clinical Document
	ClinicalDocument clinicalDocument = E2ECreator.createEmrConversionDocument(demographicNo);

	// Output Clinical Document as String
	String output = EverestUtils.generateDocumentToString(clinicalDocument, true);

The complexity of generating an E2E document is put away "under the hood" so that the entry point of creating E2E documents is clean and easy to understand. Once E2Everest is implemented in an EMR's ecosystem, it should not take any more than a couple of lines such as the above example in order to generate an E2E document. In this fashion, other developers can utilize E2Everest without having to deeply understand the behind-the-scenes work.

## Everest

The MARC-HI Everest framework is designed to facilitate the creation, transmission, and consumption of HL7v3 structures. It contains raw datatypes defined in HL7 as building blocks for constructing documents, as well as object structures which have a one to one mapping with the XML representation of an HL7v3 structured message.

The key data structure that needs to be understood for implementing and handling an E2E document is the ClinicalDocument object. This object is defined under R-MIM, and is the basis for what E2E is built on top of. As such, any patient data that needs to be put into an E2E document must be converted into a ClinicalDocument object.

The ClinicalDocument object contains many attributes and sub-objects such as Author, Custodian, RecordTarget, Informant, and etc. The way the ClinicalDocument object is structured and nested in Everest is a direct mapping to how those elements will be represented in the XML version of the ClinicalDocument. The key thing to understand from the ClinicalDocument is that it is split up into two main components - the header, and the body.

Header information includes information directly associated to the patient such as their name, age, gender and address. It also includes things such as the author of the document (usually the patient's main doctor), who/what maintains the patient record, and where the E2E document is intended to go. The body includes specific data pieces regarding the patient such as their medications, family history, labs, and allergies.

Every one of these sub-objects is represented either directly by a specific object model, or by a standard template object such as an Observation, Organizer, Encounter, Act, or SubstanceAdministration. This is the essence of a CDA document. These objects come together in order to encapsulate and represent data regarding the patient.

Since E2E is a kind of CDA document, it makes sense to build an E2E document from the Everest framework.

E2E 

## E2Everest

# Implementation

## EMR Refactoring

