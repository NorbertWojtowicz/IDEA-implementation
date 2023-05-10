package idea;

import idea.operators.IDEAModuloAdditionOperator;
import idea.operators.IDEAModuloMultiplierOperator;
import idea.operators.IDEAXorOperator;

import java.util.List;

public class IDEARoundExecutor {
    private IDEAInputBlock ideaInputBlock;

    public IDEARoundExecutor(IDEAInputBlock ideaInputBlock) {
        this.ideaInputBlock = ideaInputBlock;
    }
    public void apply(List<BitArray> subKeys) {
        validateSubKeys(subKeys, false);
        BitArray k1 = subKeys.get(0);
        BitArray k2 = subKeys.get(1);
        BitArray k3 = subKeys.get(2);
        BitArray k4 = subKeys.get(3);
        BitArray k5 = subKeys.get(4);
        BitArray k6 = subKeys.get(5);

        List<BitArray> subBlocks = ideaInputBlock.getSubBlocks();
        BitArray x1 = subBlocks.get(0);
        BitArray x2 = subBlocks.get(1);
        BitArray x3 = subBlocks.get(2);
        BitArray x4 = subBlocks.get(3);
        BitArray xTemp1;
        BitArray xTemp2;
        BitArray xTempSwap;

        IDEAXorOperator xorOperator = new IDEAXorOperator();
        IDEAModuloAdditionOperator additionOperator = new IDEAModuloAdditionOperator();
        IDEAModuloMultiplierOperator multiplierOperator = new IDEAModuloMultiplierOperator();

        x1 = multiplierOperator.apply(x1, k1);
        x2 = additionOperator.apply(x2, k2);
        x3 = additionOperator.apply(x3, k3);
        x4 = multiplierOperator.apply(x4, k4);

        xTemp1 = xorOperator.apply(x1, x3);
        xTemp2 = xorOperator.apply(x2, x4);
        xTemp1 = multiplierOperator.apply(xTemp1, k5);
        xTemp2 = additionOperator.apply(xTemp2, xTemp1);
        xTemp2 = multiplierOperator.apply(xTemp2, k6);
        xTemp1 = additionOperator.apply(xTemp1, xTemp2);

        x1 = xorOperator.apply(x1, xTemp2);
        x3 = xorOperator.apply(x3, xTemp2);
        x2 = xorOperator.apply(x2, xTemp1);
        x4 = xorOperator.apply(x4, xTemp1);

        xTempSwap = x3;
        x3 = x2;
        x2 = xTempSwap;
        ideaInputBlock.assembleResultBitArrays(List.of(x1, x2, x3, x4));
    }

    public void applyOutputTransformation(List<BitArray> subKeys) {
        validateSubKeys(subKeys, true);
        IDEAModuloAdditionOperator additionOperator = new IDEAModuloAdditionOperator();
        IDEAModuloMultiplierOperator multiplierOperator = new IDEAModuloMultiplierOperator();

        BitArray k1 = subKeys.get(0);
        BitArray k2 = subKeys.get(1);
        BitArray k3 = subKeys.get(2);
        BitArray k4 = subKeys.get(3);

        List<BitArray> subBlocks = ideaInputBlock.getSubBlocks();
        BitArray x1 = subBlocks.get(0);
        BitArray x2 = subBlocks.get(2);
        BitArray x3 = subBlocks.get(1);
        BitArray x4 = subBlocks.get(3);

        x1 = multiplierOperator.apply(x1, k1);
        x2 = additionOperator.apply(x2, k2);
        x3 = additionOperator.apply(x3, k3);
        x4 = multiplierOperator.apply(x4, k4);

        ideaInputBlock.assembleResultBitArrays(List.of(x1, x2, x3, x4));
    }

    private void validateSubKeys(List<BitArray> subKeys, boolean outputTransformation) {
        if (subKeys.size() != 6 && !outputTransformation) {
            throw new IllegalArgumentException();
        } else if (subKeys.size() != 4 && outputTransformation) {
            throw new IllegalArgumentException();
        }

        for (BitArray bArr: subKeys) {
            if (bArr.size() != 16) {
                throw new IllegalArgumentException();
            }
        }
    }
}
