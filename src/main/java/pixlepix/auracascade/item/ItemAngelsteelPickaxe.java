package pixlepix.auracascade.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import pixlepix.auracascade.data.IAngelsteelTool;
import pixlepix.auracascade.registry.BlockRegistry;
import pixlepix.auracascade.registry.CraftingBenchRecipe;
import pixlepix.auracascade.registry.ITTinkererItem;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pixlepix on 12/21/14.
 */
public class ItemAngelsteelPickaxe extends ItemPickaxe implements ITTinkererItem, IAngelsteelTool {
    public static final String name = "angelsteelPickaxe";
    public int degree = 0;

    public ItemAngelsteelPickaxe(Integer i) {
        super(AngelsteelToolHelper.materials[i]);
        this.degree = i;

        setCreativeTab(null);
    }

    public ItemAngelsteelPickaxe() {
        this(0);
    }

    @Override
    public void registerIcons(IIconRegister register) {
        itemIcon = register.registerIcon("aura:angel_pickaxe");
    }

    @Override
    public ArrayList<Object> getSpecialParameters() {
        return AngelsteelToolHelper.getDegreeList();
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_) {
        super.addInformation(stack, player, list, p_77624_4_);
        if (AngelsteelToolHelper.hasValidBuffs(stack)) {
            int[] buffs = AngelsteelToolHelper.readFromNBT(stack.stackTagCompound);
            list.add("Efficiency: " + buffs[0]);
            list.add("Fortune: " + buffs[1]);
            list.add("Shatter: " + buffs[2]);
            list.add("Disintegrate: " + buffs[3]);
        }
    }

    @Override
    public String getItemName() {
        return name + degree;
    }

    @Override
    public boolean shouldRegister() {
        return true;
    }

    @Override
    public boolean shouldDisplayInTab() {
        return degree == 0 || degree == AngelsteelToolHelper.MAX_DEGREE;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int p_77663_4_, boolean p_77663_5_) {

        if (!world.isRemote && stack.stackTagCompound == null) {
            stack.stackTagCompound = AngelsteelToolHelper.getRandomBuffCompound(degree);
        }
        super.onUpdate(stack, world, entity, p_77663_4_, p_77663_5_);
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return new CraftingBenchRecipe(new ItemStack(this, 1, degree), "AAA", " S ", " S ", 'A', new ItemStack(BlockRegistry.getFirstItemFromClass(ItemAngelsteelIngot.class), 1, degree), 'S', new ItemStack(Items.stick));
    }

    @Override
    public int getDegree() {
        return degree;
    }
}
