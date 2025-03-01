package org.benchmarx.examples.familiestopersons.testsuite;

import java.util.Arrays;
import java.util.Collection;

import org.benchmarx.BXTool;
import org.benchmarx.emf.Comparator;
import org.benchmarx.examples.familiestopersons.implementations.bxtend.UbtXtendFamiliesToPersons;
import org.benchmarx.examples.familiestopersons.implementations.emoflon.EMoflonFamiliesToPersons;
import org.benchmarx.examples.familiestopersons.implementations.ibextgg.IBeXTGGFamiliesToPersons;
import org.benchmarx.examples.familiestopersons.implementations.jtl.JTLFamiliesToPersons;
import org.benchmarx.examples.familiestopersons.implementations.medini.MediniQVTFamiliesToPersons;
import org.benchmarx.examples.familiestopersons.implementations.medini.MediniQVTFamiliesToPersonsConfig;
import org.benchmarx.families.core.FamiliesComparator;
import org.benchmarx.families.core.FamilyHelper;
import org.benchmarx.persons.core.PersonHelper;
import org.benchmarx.persons.core.PersonsComparator;
import org.benchmarx.util.BenchmarxUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import Families.FamiliesPackage;
import Families.FamilyRegister;
import Persons.PersonRegister;
import Persons.PersonsPackage;

@RunWith(Parameterized.class)
public abstract class FamiliesToPersonsTestCase {

	protected BXTool<FamilyRegister, PersonRegister, Decisions> tool;
	protected Comparator<FamilyRegister> familiesComparator;
	protected Comparator<PersonRegister> personsComparator;
	protected BenchmarxUtil<FamilyRegister, PersonRegister, Decisions> util;
	protected FamilyHelper helperFamily;
	protected PersonHelper helperPerson;

	@Before
	public void initialise() {
		// Make sure packages are registered
		FamiliesPackage.eINSTANCE.getName();
		PersonsPackage.eINSTANCE.getName();

		// Initialise all helpers
		familiesComparator = new FamiliesComparator();
		personsComparator = new PersonsComparator();
		util = new BenchmarxUtil<>(tool);
		helperFamily = new FamilyHelper();
		helperPerson = new PersonHelper();

		// Initialise the bx tool
		tool.initiateSynchronisationDialogue();
	}

	@After
	public void terminate() {
		tool.terminateSynchronisationDialogue();
	}

	// Solutions requiring additional setup are commented out.
	@Parameters(name = "{0}")
	public static Collection<BXTool<FamilyRegister, PersonRegister, Decisions>> tools() {
		return Arrays.asList(
				/*
				 * See setup instructions: /implementations/bigul/README-SETUP
				 */
				// new BiGULFamiliesToPersons() // Currently 9 failures
				// ,
				new EMoflonFamiliesToPersons() // Currently 6 failures
				, new MediniQVTFamiliesToPersons() // Currently 19 failures
				, new MediniQVTFamiliesToPersonsConfig() // Currently 12 failures
				, new UbtXtendFamiliesToPersons() // Currently 0 failures
				,
				/*
				 * Excluded due to problems with Closure
				 */
				// new FunnyQTFamiliesToPerson() // Currently 10 failures
				// ,
				/*
				 * See setup instructions: /implementations/nmf/README-SETUP
				 */
				// new NMFFamiliesToPersonsIncremental() // Currently 3 failures
				// ,
				new IBeXTGGFamiliesToPersons() // Currently 5 failures
				, new JTLFamiliesToPersons() // Currently 11 failures
		);
	}

	protected FamiliesToPersonsTestCase(BXTool<FamilyRegister, PersonRegister, Decisions> tool) {
		this.tool = tool;
	}
}
