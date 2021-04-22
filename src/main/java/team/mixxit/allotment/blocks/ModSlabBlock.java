package team.mixxit.allotment.blocks;

import net.minecraft.block.SlabBlock;

public class ModSlabBlock extends SlabBlock {
    public String ForBlock;
    public String WithTextureTop;
    public String WithTextureBottom;
    public String WithTextureSides;
    public ModSlabBlock(Properties properties, String forBlock) {
        super(properties);
        ForBlock = forBlock;
        WithTextureTop = forBlock;
        WithTextureBottom = forBlock;
        WithTextureSides = forBlock;
    }
    public ModSlabBlock(Properties properties, String forBlock, String withTexture) {
        super(properties);
        ForBlock = forBlock;
        WithTextureTop = withTexture;
        WithTextureBottom = withTexture;
        WithTextureSides = withTexture;
    }
    public ModSlabBlock(Properties properties, String forBlock, String withTextureTopBottom, String withTextureSides) {
        super(properties);
        ForBlock = forBlock;
        WithTextureTop = withTextureTopBottom;
        WithTextureBottom = withTextureTopBottom;
        WithTextureSides = withTextureSides;
    }
    public ModSlabBlock(Properties properties, String forBlock, String withTextureTop, String withTextureBottom, String withTextureSides) {
        super(properties);
        ForBlock = forBlock;
        WithTextureTop = withTextureTop;
        WithTextureBottom = withTextureBottom;
        WithTextureSides = withTextureSides;
    }
}
