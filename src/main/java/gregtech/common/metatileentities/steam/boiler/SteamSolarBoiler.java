package gregtech.common.metatileentities.steam.boiler;

import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.gui.widgets.ProgressWidget.MoveType;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.render.Textures;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

public class SteamSolarBoiler extends SteamBoiler {

    public SteamSolarBoiler(String metaTileEntityId, boolean isHighPressure) {
        super(metaTileEntityId, isHighPressure, Textures.SOLAR_BOILER_OVERLAY, 55);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder) {
        return new SteamSolarBoiler(metaTileEntityId, isHighPressure);
    }

    protected boolean checkCanSeeSun() {
        BlockPos blockPos = getPos().up();
        if(!getWorld().canBlockSeeSky(blockPos))
            return false;
        return !getWorld().isRaining() && getWorld().isDaytime();
    }

    @Override
    protected void tryConsumeNewFuel() {
        if(checkCanSeeSun()) {
            setFuelMaxBurnTime(5);
        }
    }

    @Override
    protected IItemHandlerModifiable createImportItemHandler() {
        return new ItemStackHandler(1);
    }

    @Override
    protected IItemHandlerModifiable createExportItemHandler() {
        return new ItemStackHandler(1);
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return createUITemplate(entityPlayer)
            .widget(new ProgressWidget(() -> checkCanSeeSun() ? 1.0 : 0.0, 115, 34, 20, 20)
                .setProgressBar(getGuiTexture("boiler_sun"),
                    getGuiTexture("boiler_sun_active"), MoveType.HORIZONTAL))
            .build(getHolder(), entityPlayer);
    }
}
