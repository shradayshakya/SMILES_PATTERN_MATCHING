import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.isomorphism.Pattern;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smarts.SmartsPattern;
import org.openscience.cdk.smiles.SmilesParser;


public class EntryPoint {
    public static void main(String[] args) {
        var esterHydrolysisBuildingBlockStructure = "COC(=O)CCOC1=CC=C(N)C=C1";

        var methylEsterStructure = "O=COC";

        var didPatternMatchForMethylEsterStructure = doesPatternExistInSMILES(esterHydrolysisBuildingBlockStructure, methylEsterStructure);

        var invalidMethylEsterStructureInvalid = "OO=COC";

        var didPatternMatchForInvalidMethylEsterStructure = doesPatternExistInSMILES(esterHydrolysisBuildingBlockStructure, invalidMethylEsterStructureInvalid);

        var invertedMethylEsterStructure = "COC=O";

        var didPatternMatchForInvertedMethylEsterStructure = doesPatternExistInSMILES(esterHydrolysisBuildingBlockStructure, invertedMethylEsterStructure);

        System.out.println(didPatternMatchForMethylEsterStructure);
        System.out.println(didPatternMatchForInvalidMethylEsterStructure);
        System.out.println(didPatternMatchForInvertedMethylEsterStructure);



        var suzukiCouplingBuildingBlockStructure = "OB(O)C1=CC(=CC=C1F)C(=O)N1CCCC1";

        var boronicAcidStructure = "OB(O)";

        var didPatternMatchForBoronicAcidStructure = doesPatternExistInSMILES(suzukiCouplingBuildingBlockStructure, boronicAcidStructure);

        System.out.println(didPatternMatchForBoronicAcidStructure);


        var acylationBuildingBlockStructure = "OC(=O)CN1C(=O)CCC2=CC(OC3CN(C3)C(=O)OCC3C4=C(C=CC=C4)C4=C3C=CC=C4)=C(Br)C=C12";

        var bromideStructure = "Br";

        var didPatternMatchForBromideStructure = doesPatternExistInSMILES(acylationBuildingBlockStructure, bromideStructure);

        System.out.println(didPatternMatchForBromideStructure);



        var cxSmilesString = "CNC(=O)C1=NN2CCCN(CC2=C1C1=CC=CC(CCC#N)=C1)C(=O)C(NC(=O)CCl)C1=CC=CC(=C1)C(F)(F)F |c:13,18,24,37,39,t:4,16,35,lp:1:1,3:2,5:1,6:1,10:1,22:1,25:2,27:1,29:2,31:3,39:3,40:3,41:3|";

        var cxSmilesStringPattern = "CCCN";
        var invalidCxSmilesStringPattern = "OB(O)";
        var invertedCxSmilesStringPattern = "NCCC";

        var didPatternMatchForCxString = doesPatternExistInSMILES(cxSmilesString, cxSmilesStringPattern);
        var didPatternMatchForInvalidCxString = doesPatternExistInSMILES(cxSmilesString, invalidCxSmilesStringPattern);
        var didPatternMatchForInvertedCxString = doesPatternExistInSMILES(cxSmilesString, invertedCxSmilesStringPattern);

        System.out.println(didPatternMatchForCxString);
        System.out.println(didPatternMatchForInvalidCxString);
        System.out.println(didPatternMatchForInvertedCxString);




        var weirdStructure = "CN(C)C(=O)C1=CC(=CC=C1F)C(O)O";

        var weirdPattern = "OCO";

        var didPatternMatchForWeirdPattern = doesPatternExistInSMILES(weirdStructure, weirdPattern);

        System.out.println(didPatternMatchForWeirdPattern);



        var weirdStructure2 = "C(=O)=O";

        var weirdPattern2 = "[C-]#[O+]";

        var didPatternMatchForWeirdPattern2 = doesPatternExistInSMILES(weirdStructure2, weirdPattern2);

        System.out.println(didPatternMatchForWeirdPattern2);


        var weirdStructure3 = "CNC(=O)C1=NN2CCCN(CC2=C1C1=CC=CC(CCC#N)=C1)C(=O)C(NC(=O)CCl)C1=CC=CC(=C1)C(F)(F)F |c:13,18,24,37,39,t:4,16,35,lp:1:1,3:2,5:1,6:1,10:1,22:1,25:2,27:1,29:2,31:3,39:3,40:3,41:3|";

        var weirdPattern3 = "CCNC";

        var didPatternMatchForWeirdPattern3 = doesPatternExistInSMILES(weirdStructure3, weirdPattern3);

        System.out.println(didPatternMatchForWeirdPattern3);



        var weirdStructure4 = "C(=O)=O";

        var weirdPattern4 = "CO";

        var didPatternMatchForWeirdPattern4 = doesPatternExistInSMILES(weirdStructure4, weirdPattern4);

        System.out.println(didPatternMatchForWeirdPattern4);


        var weirdStructure5 = "CN(C)C(=O)C1=CC(=CC=C1F)C(O)O";

        var weirdPattern5 = "OOC";

        var didPatternMatchForWeirdPattern5 = doesPatternExistInSMILES(weirdStructure4, weirdPattern4);

        System.out.println(didPatternMatchForWeirdPattern5);


        var weirdStructure6 = "CN(C)C(=O)C1=CC(=CC=C1F)C(O)O";

        var weirdPattern6 = "COO";

        var didPatternMatchForWeirdPattern6 = doesPatternExistInSMILES(weirdStructure6, weirdPattern6);

        System.out.println(didPatternMatchForWeirdPattern6);

    }

    /**
     * Parses a SMILES string and returns a structure ({@link IAtomContainer}).
     *
     * @param smiles A SMILES string
     * @return A structure representing the provided SMILES
     */
    public static IAtomContainer parseSmiles(String smiles) {
        IAtomContainer molecule = null;

        try {
            SmilesParser smilesParser = new SmilesParser(SilentChemObjectBuilder.getInstance());

            molecule = smilesParser.parseSmiles(smiles);
        } catch (InvalidSmilesException ise) {
            var message = String.format("Could not parse smile string: %s", smiles);

            throw new InternalError(message);
        }

        return molecule;
    }


    public static Boolean doesPatternExistInSMILES(String smilesString, String patternString) {
        var smiles = parseSmiles(smilesString);
        Pattern pattern = SmartsPattern.create(patternString);

        if (pattern.matches(smiles)) {
            return true;
        }

        return false;
    }
}
