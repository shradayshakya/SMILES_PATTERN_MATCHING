import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IChemModel;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.isomorphism.Pattern;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smarts.SmartsPattern;
import org.openscience.cdk.smiles.SmiFlavor;
import org.openscience.cdk.smiles.SmilesGenerator;
import org.openscience.cdk.smiles.SmilesParser;
import org.openscience.cdk.smiles.smarts.SMARTSQueryTool;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


public class EntryPoint {
    public static void main(String[] args) {
        var smiles = "CCOC(=O)C1(CNC(=O)OC(C)(C)C)CCNC1";

//        var ethylEsterPattern = "O=COCC";
        var ethylEsterPattern = "O=COCC";

        var boc = "NC(=O)OC(C)(C)C";

        var result = doesPatternExistInSMILES(smiles, boc, ethylEsterPattern);
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


    public static Boolean doesPatternExistInSMILES(String smilesString, String patternString, String ignoreString) {
        var processedSmiles = removeSubStructureIfExist(smilesString, ignoreString);
        Pattern pattern = SmartsPattern.create(patternString);

        if (pattern.matches(processedSmiles)) {
            return true;
        }

        return false;
    }

    public static IAtomContainer removeSubStructureIfExist(String smilesString, String subStructure){
        IAtomContainer atomContainer = parseSmiles(smilesString);

        var builder = DefaultChemObjectBuilder.getInstance();
        SMARTSQueryTool querytool = new SMARTSQueryTool(subStructure, builder);
        boolean status = false;
        try {
            status = querytool.matches(atomContainer);
        } catch (CDKException e) {
            throw new InternalError(e.getMessage());
        }
        if (status) {
            List mappings = querytool.getUniqueMatchingAtoms();

            for (int i = 0; i < mappings.size(); i++) {
                List<Integer> atomIndices = (List<Integer>) mappings.get(i);

                Collections.sort(atomIndices, Collections.reverseOrder());

                atomIndices.forEach(atomIndex -> atomContainer.removeAtom(atomIndex));
            }
        }

        return atomContainer;
    }



    public static String getSMILESString(IAtomContainer molecule) throws CDKException, ClassNotFoundException, IOException, CloneNotSupportedException {
        SmilesGenerator smilesGenerator = new SmilesGenerator(SmiFlavor.Isomeric);

        return smilesGenerator.create(molecule);
    }

}
