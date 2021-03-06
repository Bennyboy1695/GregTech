package gregtech.common.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class StoneItemBlock<T extends StoneBlock<?>> extends ItemBlock {

    private final T genericBlock;

    public StoneItemBlock(T block) {
        super(block);
        this.genericBlock = block;
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @SuppressWarnings("deprecation")
    protected IBlockState getBlockState(ItemStack stack) {
        return block.getStateFromMeta(getMetadata(stack.getItemDamage()));
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        IBlockState blockState = getBlockState(stack);
        return super.getUnlocalizedName(stack) + '.' +
                genericBlock.getVariant(blockState).getName() + "." +
                genericBlock.getChiselingVariant(blockState).getName();
    }

}

