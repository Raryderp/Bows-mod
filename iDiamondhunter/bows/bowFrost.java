package iDiamondhunter.bows;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentArrowFire;
import net.minecraft.enchantment.EnchantmentArrowKnockback;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.NpcMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemSnow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class bowFrost extends ItemBow
{
    public static Icon X1;
    public static Icon X2;
    public static Icon X3;
    public static Icon X0;

    public bowFrost(int var1, int var2)
    {
        super(var1);
        this.maxStackSize = 1;
        this.setMaxDamage(550);
        this.bFull3D = true;
        this.setCreativeTab(CreativeTabs.tabCombat);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister var1)
    {
        this.itemIcon = var1.registerIcon("mod/FrostBow1");
        X0 = var1.registerIcon("mod/FrostBow");
        X1 = var1.registerIcon("mod/FrostBow2");
        X2 = var1.registerIcon("mod/FrostBow3");
        X3 = var1.registerIcon("mod/FrostBow4");
    }
    public Icon getIcon(ItemStack par1ItemStack, int par2, EntityPlayer player, ItemStack var4, int var5)
    {
        if(player.isUsingItem() && var4.itemID == iDiamondhunter.bowFrost.itemID)
        {
            if (var4 != null && var4.getItem().itemID == iDiamondhunter.bowFrost.itemID)
            {
                int k = var4.getMaxItemUseDuration() - var5;
            if (k >= 26) return X3;
            else if (k >= 13) return X2;
            else if (k >= 0) return X1;
            return this.itemIcon;
            }
        }
        return itemIcon;
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
                float f = (float)j / 26.0F;
                f = (f * f + f * 2.0F) / 3.0F;

                if ((double)f < 0.1D)
                {
                    return;
                }

                if (f > 1.0F)
                {
                    f = 1.0F;
                }

                EntityFrostArrow entityarrow = new EntityFrostArrow(par2World, par3EntityPlayer, f * 2.4F);

                if (f == 1.0F)
                {
                    entityarrow.setIsCritical(true);
                }

                int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, par1ItemStack);

                if (k > 0)
                {
                    entityarrow.setDamage(entityarrow.getDamage() + (double)k * 0.5D + 0.5D);
                }

                int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, par1ItemStack);

                if (l > 0)
                {
                    entityarrow.setKnockbackStrength(l);
                }

                if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, par1ItemStack) > 0)
                {
                    entityarrow.setFire(100);
                }

                par1ItemStack.damageItem(1, par3EntityPlayer);
                par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

                if (flag)
                {
                    entityarrow.canBePickedUp = 2;
                }
                else
                {
                    par3EntityPlayer.inventory.consumeInventoryItem(Item.arrow.itemID);
                }
                    par2World.spawnEntityInWorld(entityarrow);
                
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
