package iDiamondhunter.bows;

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
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class bowEnder extends ItemBow
{
	private int second = 3;
    public static Icon X1;
    public static Icon X2;
    public static Icon X3;
    public static Icon X0;

    public bowEnder(int var1, int var2)
    {
        super(var1);
        this.maxStackSize = 1;
        this.setMaxDamage(215);
        this.bFull3D = true;
        this.setCreativeTab(CreativeTabs.tabCombat);
    }
    public EnumRarity getRarity(ItemStack itemstack)
    {
    	return EnumRarity.epic;
    }
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister var1)
    {
        this.itemIcon = var1.registerIcon("mod/EnderBow1");
        X0 = var1.registerIcon("mod/EnderBow");
        X1 = var1.registerIcon("mod/EnderBow2");
        X2 = var1.registerIcon("mod/EnderBow3");
        X3 = var1.registerIcon("mod/EnderBow4");
    }
    public Icon getIcon(ItemStack par1ItemStack, int par2, EntityPlayer player, ItemStack var4, int var5)
    {
        if(player.isUsingItem() && var4.itemID == iDiamondhunter.bowEnder.itemID)
        {
            if (var4 != null && var4.getItem().itemID == iDiamondhunter.bowEnder.itemID)
            {
                int k = var4.getMaxItemUseDuration() - var5;
            if (k >= 22) return X3;
            else if (k >= 11) return X2;
            else if (k >= 0) return X1;
            return this.itemIcon;
            }
        }
        return itemIcon;
    }
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4)
    {
        boolean flag = par3EntityPlayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, par1ItemStack) > 0;

        if (flag || par3EntityPlayer.inventory.hasItem(Item.arrow.itemID) || second == 3)
        {
            int i = getMaxItemUseDuration(par1ItemStack) - par4;
            float f = (float)i / 22;
            f = (f * f + f * 2.0F) / 3F;

            if ((double)f < 0.1D)
            {
                return;
            }

            if (f > 1.0F)
            {
                f = 1.0F;
            }

            EntityArrow entityarrow = new EntityArrow(par2World, par3EntityPlayer, f * 2.0F);
            EntityArrow entityarrow2 = new EntityArrow(par2World, par3EntityPlayer, 1F);
            EntityArrow entityarrow3= new EntityArrow(par2World, par3EntityPlayer, 1.2F);
            EntityArrow entityarrow4 = new EntityArrow(par2World, par3EntityPlayer, 1.5F);
            EntityArrow entityarrow5 = new EntityArrow(par2World, par3EntityPlayer, 1.75F);
            EntityArrow entityarrow6 = new EntityArrow(par2World, par3EntityPlayer, 1.825F);

            if (f == 1.0F)
            {
                entityarrow.setIsCritical(true);
                entityarrow2.setIsCritical(true);
                entityarrow3.setIsCritical(true);
                entityarrow4.setIsCritical(true);
                entityarrow5.setIsCritical(true);
                entityarrow6.setIsCritical(true);

            }

            int j = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, par1ItemStack);

            if (j > 0)
            {
                entityarrow.setDamage(entityarrow.getDamage() + (double)j * 0.5D + 0.5D);
                entityarrow2.setDamage(entityarrow.getDamage() + (double)j * 0.5D + 0.5D);
                entityarrow3.setDamage(entityarrow.getDamage() + (double)j * 0.5D + 0.5D);
                entityarrow4.setDamage(entityarrow.getDamage() + (double)j * 0.5D + 0.5D);
                entityarrow5.setDamage(entityarrow.getDamage() + (double)j * 0.5D + 0.5D);
                entityarrow6.setDamage(entityarrow.getDamage() + (double)j * 0.5D + 0.5D);
            }

            int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, par1ItemStack);

            if (k > 0)
            {
                entityarrow.setKnockbackStrength(k);
                entityarrow2.setKnockbackStrength(k);
                entityarrow3.setKnockbackStrength(k);
                entityarrow4.setKnockbackStrength(k);
                entityarrow5.setKnockbackStrength(k);
                entityarrow6.setKnockbackStrength(k);
            }

            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, par1ItemStack) > 0)
            {
                entityarrow.setFire(100);
                entityarrow2.setFire(100);
                entityarrow3.setFire(100);
                entityarrow4.setFire(100);
                entityarrow5.setFire(100);
                entityarrow6.setFire(100);
            }

            par1ItemStack.damageItem(1, par3EntityPlayer);
            par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

            if (!flag)
            {
                par3EntityPlayer.inventory.consumeInventoryItem(Item.arrow.itemID);
            }
            else
            {
            	entityarrow.canBePickedUp = 2;
            	entityarrow2.canBePickedUp = 0;
            	entityarrow3.canBePickedUp = 0;
            	entityarrow4.canBePickedUp = 0;
            	entityarrow5.canBePickedUp = 0;
            	entityarrow6.canBePickedUp = 0;
            }

            if (!par2World.isRemote)
            {
            	entityarrow.canBePickedUp = 2;
            	entityarrow2.canBePickedUp = 0;
            	entityarrow3.canBePickedUp = 0;
            	entityarrow4.canBePickedUp = 0;
            	entityarrow5.canBePickedUp = 0;
            	entityarrow6.canBePickedUp = 0;
            	
            	par2World.spawnEntityInWorld(entityarrow);
                entityarrow.setDamage(entityarrow.getDamage() * 1.25D);
                itemIcon = X0;
                while(second > -1)
                {
                	if(second == 3)
                	{
                		second = 2;
                		System.out.println(second);
                	}
                	try
                	{
                		Thread.sleep(1000L);
                	}
                	catch(Exception e){};
                	second--;
                	System.out.println(second);

                }
                if(second == -1)
                {
                	par2World.spawnEntityInWorld(entityarrow2);
                	par2World.spawnEntityInWorld(entityarrow3);
                	entityarrow3.posY++;
                	entityarrow3.posX -= 1.25;
                	entityarrow3.posZ += 1.75;
                	par2World.spawnEntityInWorld(entityarrow4);
                	entityarrow4.posY += 1.45;
                	entityarrow4.posX -= 2.25;
                	entityarrow4.posZ -= 0.75;
                	par2World.spawnEntityInWorld(entityarrow5);
                	entityarrow5.posY += 2;
                	entityarrow5.posX += 0.25;
                	entityarrow5.posZ += 2.5;
                	par2World.spawnEntityInWorld(entityarrow6);
                	entityarrow6.posY += 1.75;
                	entityarrow6.posX += 1.75;
                	entityarrow6.posZ += 1.5;
                	
                	second = 3;
                }
            }
        }
        itemIcon = X0;
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
        return 2;
    }
}
