package team.mixxit.allotment.blocks;

import net.minecraft.block.VineBlock;

public class ModVineBlock extends VineBlock {
    private Boolean isTinted;

    public ModVineBlock(Properties properties, Boolean isTinted) {
        super(properties);
        this.isTinted = isTinted;
    }

    public Boolean getIsTinted() {
        return isTinted;
    }
}
