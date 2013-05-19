package iDiamondhunter.bows;

import java.util.Random;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class bowMulti extends ItemBow
{
    public static Icon X1;
    public static Icon X2;
    public static Icon X3;
    public static Icon X0;
    private Random rand = new Random();
    public bowMulti(int var1, int var2)
    {
        super(var1);
        this.maxStackSize = 1;
        this.setMaxDamage(550);
        this.bFull3D = true;
        this.setCreativeTab(CreativeTabs.tabCombat);
    }
    public EnumRarity getRarity(ItemStack par1ItemStack)
    {
        return EnumRarity.rare;
    }
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister var1)
    {
        this.itemIcon = var1.registerIcon("mod/MultiBow1");
        X0 = var1.registerIcon("mod/MultiBow");
        X1 = var1.registerIcon("mod/MultiBow2");
        X2 = var1.registerIcon("mod/MultiBow3");
        X3 = var1.registerIcon("mod/MultiBow4");
    }
    public Icon getIcon(ItemStack par1ItemStack, int par2, EntityPlayer player, ItemStack usingItem, int useRemaining)
    {
        if(player.isUsingItem() && usingItem.itemID == iDiamondhunter.bowMulti.itemID)
        {
            if (usingItem != null && usingItem.getItem().itemID == iDiamondhunter.bowMulti.itemID)
            {
                int var6 = usingItem.getMaxItemUseDuration() - useRemaining;
            if (var6 >= 13) return X3;
            else if (var6 >= 7) return X2;
            else if (var6 >= 1) return X1;
            }
        }
            return this.itemIcon;
    }
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4)
    {
        int j = this.getMaxItemUseDuration(par1ItemStack) - par4;

        ArrowLooseEvent event = new ArrowLooseEvent(par3EntityPlayer, par1ItemStack, j);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled())
        {
            return;
        }
        j = event.charge;

        boolean flag = par3EntityPlayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, par1ItemStack) > 0;
        if (flag || par3EntityPlayer.inventory.hasItem(Item.arrow.itemID))
        {
                float f = (float)j / 13.0F;
                f = (f * f + f * 2.0F) / 3.0F;

                if ((double)f < 0.1D)
                {
                    return;
                }

                if (f > 1.0F)
                {
                    f = 1.0F;
                }

                EntityArrow entityarrow = new EntityArrow(par2World, par3EntityPlayer, f * 2.4F);
                EntityArrow var9 = new EntityArrow(par2World, par3EntityPlayer, f * 1.65F);
                EntityArrow var10 = new EntityArrow(par2World, par3EntityPlayer, f * 1.275F);

                if (f == 1.0F)
                {
                    entityarrow.setIsCritical(true);
                    var9.setIsCritical(true);
                    var10.setIsCritical(true);
                }

                int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, par1ItemStack);

                if (k > 0)
                {
                    entityarrow.setDamage(entityarrow.getDamage() + (double)k * 0.5D + 0.5D);
                    var9.setDamage(entityarrow.getDamage() + (double)k * 0.5D + 0.5D);
                    var10.setDamage(entityarrow.getDamage() + (double)k * 0.5D + 0.5D);
                }

                int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, par1ItemStack);

                if (l > 0)
                {
                    entityarrow.setKnockbackStrength(l);
                    var9.setKnockbackStrength(l);
                    var10.setKnockbackStrength(l);
                }

                if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, par1ItemStack) > 0)
                {
                    entityarrow.setFire(100);
                    var9.setFire(100);
                    var10.setFire(100);
                }

                par1ItemStack.damageItem(1, par3EntityPlayer);
                par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

                if (flag)
                {
                    entityarrow.canBePickedUp = 2;
                	var9.canBePickedUp = 0;
                	var10.canBePickedUp = 0;
                }
                else
                {
                    par3EntityPlayer.inventory.consumeInventoryItem(Item.arrow.itemID);
                }

                if (!par2World.isRemote)
                {
                    par2World.spawnEntityInWorld(entityarrow);
                	par2World.spawnEntityInWorld(var9);
                	if(par3EntityPlayer.rotationYaw > 180)
                	{
                		var9.posX = var9.posX + par3EntityPlayer.rotationYaw / 180;
                	}
                	else
                	{
                		var9.posX = var9.posX + par3EntityPlayer.rotationYaw / 180;
                	}
                	entityarrow.setDamage(entityarrow.getDamage() * 1.5D);
                	var9.setDamage(var9.getDamage() * 1.3D);
                	var10.setDamage(var9.getDamage() * 1.15D);
                    var9.canBePickedUp = 0;
                    if(rand.nextInt(4) == 0)
                    {
                    	par2World.spawnEntityInWorld(var10);
                    	if(par3EntityPlayer.rotationYaw > 180)
                    	{
                    		var10.posX = var10.posX - par3EntityPlayer.rotationYaw / 180;
                    	}
                    	else
                    	{
                    		var10.posX = var10.posX - par3EntityPlayer.rotationYaw / 180;
                    	}
                    }
                }
            }
        }
    

    public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        return par1ItemStack;
    }

    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 72000;
    }

    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.bow;
    }

    public int getItemEnchantability()
    {
        return 1;
    }
}
