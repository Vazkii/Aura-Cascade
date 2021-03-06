package pixlepix.auracascade.main;

import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.data.CoordTuple;
import pixlepix.auracascade.network.PacketBurst;

import java.util.List;

/**
 * Created by pixlepix on 11/29/14.
 */
public class AuraUtil {

    public static void keepAlive(TileEntity te, int range) {
        List<EntityItem> nearbyItems = te.getWorldObj().getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(te.xCoord - range, te.yCoord - range, te.zCoord - range, te.xCoord + range, te.yCoord + range, te.zCoord + range));
        for (EntityItem entityItem : nearbyItems) {
            entityItem.lifespan = Integer.MAX_VALUE;
            entityItem.age = 0;
        }
    }

    public static void diamondBurst(Entity entity, String particle) {
        CoordTuple centerTuple = new CoordTuple((int) entity.posX, (int) entity.posY + 1, (int) entity.posZ);
        CoordTuple topTuple = centerTuple.add(ForgeDirection.UP, 5);
        ForgeDirection[] directions = new ForgeDirection[]{ForgeDirection.EAST, ForgeDirection.NORTH, ForgeDirection.WEST, ForgeDirection.SOUTH};
        for (int i = 0; i < directions.length; i++) {
            ForgeDirection primaryDirection = directions[i];
            ForgeDirection connectingDirection = directions[i + 1 < directions.length ? i + 1 : 0];
            CoordTuple corner = centerTuple.add(primaryDirection, 5);
            CoordTuple connectingCorner = centerTuple.add(connectingDirection, 5);
            AuraCascade.proxy.networkWrapper.sendToAllAround(new PacketBurst(corner, centerTuple, particle, 1, 1, 1, 1), new NetworkRegistry.TargetPoint(entity.worldObj.provider.dimensionId, entity.posX, entity.posY, entity.posZ, 32));
            AuraCascade.proxy.networkWrapper.sendToAllAround(new PacketBurst(corner, connectingCorner, particle, 1, 1, 1, 1), new NetworkRegistry.TargetPoint(entity.worldObj.provider.dimensionId, entity.posX, entity.posY, entity.posZ, 32));
            AuraCascade.proxy.networkWrapper.sendToAllAround(new PacketBurst(corner, topTuple, particle, 1, 1, 1, 1), new NetworkRegistry.TargetPoint(entity.worldObj.provider.dimensionId, entity.posX, entity.posY, entity.posZ, 32));

        }
    }

}
