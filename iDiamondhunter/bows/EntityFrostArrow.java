package iDiamondhunter.bows;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentThorns;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet70GameEvent;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityFrostArrow extends Entity implements IProjectile
{
    Random random;
    private int xTile = -1;
    private int yTile = -1;
    private int zTile = -1;
    private int inTile = 0;
    private int inData = 0;
    private boolean inGround = false;
    public int canBePickedUp = 0;
    public int arrowShake = 0;
    public Entity shootingEntity;
    private int ticksInGround;
    private int ticksIn;
    private int ticksInAir = 0;
    private double damage = 2.0D;
    private int knockbackStrength;

    public EntityFrostArrow(World var1)
    {
        super(var1);
        this.setSize(0.5F, 0.5F);
    }

    public EntityFrostArrow(World var1, double var2, double var4, double var6)
    {
        super(var1);
        this.setSize(0.5F, 0.5F);
        this.setPosition(var2, var4, var6);
        this.yOffset = 0.0F;
    }

    public EntityFrostArrow(World var1, EntityLiving var2, EntityLiving var3, float var4, float var5)
    {
        super(var1);
        this.shootingEntity = var2;

        if (var2 instanceof EntityPlayer)
        {
            this.canBePickedUp = 1;
        }

        this.posY = var2.posY + (double)var2.getEyeHeight() - 0.10000000149011612D;
        double var6 = var3.posX - var2.posX;
        double var8 = var3.posY + (double)var3.getEyeHeight() - 0.699999988079071D - this.posY;
        double var10 = var3.posZ - var2.posZ;
        double var12 = (double)MathHelper.sqrt_double(var6 * var6 + var10 * var10);

        if (var12 >= 1.0E-7D)
        {
            float var14 = (float)(Math.atan2(var10, var6) * 180.0D / Math.PI) - 90.0F;
            float var15 = (float)(-(Math.atan2(var8, var12) * 180.0D / Math.PI));
            double var16 = var6 / var12;
            double var18 = var10 / var12;
            this.setLocationAndAngles(var2.posX + var16, this.posY, var2.posZ + var18, var14, var15);
            this.yOffset = 0.0F;
            float var20 = (float)var12 * 0.2F;
            this.setThrowableHeading(var6, var8 + (double)var20, var10, var4, var5);
        }
    }

    public EntityFrostArrow(World var1, EntityLiving var2, float var3)
    {
        super(var1);
        this.shootingEntity = var2;

        if (var2 instanceof EntityPlayer)
        {
            this.canBePickedUp = 1;
        }

        this.setSize(0.5F, 0.5F);
        this.setLocationAndAngles(var2.posX, var2.posY + (double)var2.getEyeHeight(), var2.posZ, var2.rotationYaw, var2.rotationPitch);
        this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        this.posY -= 0.10000000149011612D;
        this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0F;
        this.motionX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI));
        this.motionZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI));
        this.motionY = (double)(-MathHelper.sin(this.rotationPitch / 180.0F * (float)Math.PI));
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, var3 * 1.5F, 1.0F);
    }

    protected void entityInit()
    {
        this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
    }

    /**
     * Similar to setArrowHeading, it's point the throwable entity to a x, y, z direction.
     */
    public void setThrowableHeading(double var1, double var3, double var5, float var7, float var8)
    {
        float var9 = MathHelper.sqrt_double(var1 * var1 + var3 * var3 + var5 * var5);
        var1 /= (double)var9;
        var3 /= (double)var9;
        var5 /= (double)var9;
        var1 += this.rand.nextGaussian() * 0.007499999832361937D * (double)var8;
        var3 += this.rand.nextGaussian() * 0.007499999832361937D * (double)var8;
        var5 += this.rand.nextGaussian() * 0.007499999832361937D * (double)var8;
        var1 *= (double)var7;
        var3 *= (double)var7;
        var5 *= (double)var7;
        this.motionX = var1;
        this.motionY = var3;
        this.motionZ = var5;
        float var10 = MathHelper.sqrt_double(var1 * var1 + var5 * var5);
        this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(var1, var5) * 180.0D / Math.PI);
        this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(var3, (double)var10) * 180.0D / Math.PI);
        this.ticksInGround = 0;
    }

    public static DamageSource causeFrostArrowDamage(EntityFrostArrow var0, Entity var1)
    {
        return (new EntityDamageSourceIndirect("arrow", var0, var1)).setProjectile();
    }

    /**
     * Sets the position and rotation. Only difference from the other one is no bounding on the rotation. Args: posX,
     * posY, posZ, yaw, pitch
     */
    public void setPositionAndRotation2(double var1, double var3, double var5, float var7, float var8, int var9)
    {
        this.setPosition(var1, var3, var5);
        this.setRotation(var7, var8);
    }

    /**
     * Sets the velocity to the args. Args: x, y, z
     */
    public void setVelocity(double var1, double var3, double var5)
    {
        this.motionX = var1;
        this.motionY = var3;
        this.motionZ = var5;

        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
        {
            float var7 = MathHelper.sqrt_double(var1 * var1 + var5 * var5);
            this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(var1, var5) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(var3, (double)var7) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch;
            this.prevRotationYaw = this.rotationYaw;
            this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            this.ticksInGround = 4;
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();

        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
        {
            float var1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(this.motionY, (double)var1) * 180.0D / Math.PI);
        }

        int var16 = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);

        if (var16 > 0)
        {
            Block.blocksList[var16].setBlockBoundsBasedOnState(this.worldObj, this.xTile, this.yTile, this.zTile);
            AxisAlignedBB var2 = Block.blocksList[var16].getCollisionBoundingBoxFromPool(this.worldObj, this.xTile, this.yTile, this.zTile);

            if (var2 != null && var2.isVecInside(this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ)))
            {
                this.inGround = true;
            }
        }

        if (this.arrowShake > 0)
        {
            --this.arrowShake;
        }

        int var17;

        if (this.inGround)
        {
            int var3 = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);
            int var4 = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);
            boolean var5 = false;
            int var6 = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);
            int var7 = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);
            int var8 = MathHelper.floor_double(this.posX);
            int var9 = MathHelper.floor_double(this.posY);
            var17 = MathHelper.floor_double(this.posZ);
            boolean var10 = false;

            if (this.worldObj.getBlockMaterial(var8, var9, var17) == Material.water && this.worldObj.getBlockMetadata(var8, var9, var17) == 0)
            {
                this.worldObj.setBlock(var8, var9, var17, Block.ice.blockID, 0, 3);
                var10 = true;
            }

            if (this.ticksInGround >= 64 && this.ticksInGround <= 65)
            {
                if (this.worldObj.getBlockId(var8, var9, var17) == 0)
                {
                    this.worldObj.setBlock(var8, var9, var17, Block.snow.blockID, 0, 3);
                }

                if (this.worldObj.getBlockId(var8, var9, var17) == Block.waterStill.blockID)
                {
                    this.worldObj.setBlock(var8, var9, var17, Block.ice.blockID, 0, 3);
                }
            }

            if (var3 == this.inTile && var4 == this.inData)
            {
                boolean var11 = true;
                ++this.ticksInGround;
                --this.ticksIn;

                if (this.ticksInGround <= 2)
                {
                    this.worldObj.spawnParticle("snowballpoof", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
                }

                this.setSize(0.1F, 0.1F);

                if (this.ticksInGround <= 30)
                {
                    this.setSize(0.1F, 0.1F);
                    this.worldObj.spawnParticle("dripWater", this.posX, this.posY - 0.3D, this.posZ, 0.0D, 0.0D, 0.0D);
                }

                if (this.ticksInGround > 80)
                {
                    this.setDead();
                }
            }
            else
            {
                this.inGround = false;
                this.motionX *= (double)(this.rand.nextFloat() * 0.2F);
                this.motionY *= (double)(this.rand.nextFloat() * 0.2F);
                this.motionZ *= (double)(this.rand.nextFloat() * 0.2F);
                this.ticksInGround = 0;
                this.ticksInAir = 0;
            }
        }
        else
        {
            ++this.ticksInAir;
            Vec3 var18 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
            Vec3 var19 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            MovingObjectPosition var20 = this.worldObj.rayTraceBlocks_do_do(var18, var19, false, true);
            var18 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
            var19 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

            if (var20 != null)
            {
                var19 = this.worldObj.getWorldVec3Pool().getVecFromPool(var20.hitVec.xCoord, var20.hitVec.yCoord, var20.hitVec.zCoord);
            }

            Entity var21 = null;
            List var22 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
            double var23 = 0.0D;
            MovingObjectPosition var25;
            float var24;

            for (var17 = 0; var17 < var22.size(); ++var17)
            {
                Entity var12 = (Entity)var22.get(var17);

                if (var12.canBeCollidedWith() && (var12 != this.shootingEntity || this.ticksInAir >= 5))
                {
                    var24 = 0.3F;
                    AxisAlignedBB var13 = var12.boundingBox.expand((double)var24, (double)var24, (double)var24);
                    var25 = var13.calculateIntercept(var18, var19);

                    if (var25 != null)
                    {
                        double var14 = var18.distanceTo(var25.hitVec);

                        if (var14 < var23 || var23 == 0.0D)
                        {
                            var21 = var12;
                            var23 = var14;
                        }
                    }
                }
            }

            if (var21 != null)
            {
                var20 = new MovingObjectPosition(var21);
            }

            float var26;

            if (var20 != null)
            {
                if (var20.entityHit != null)
                {
                    var26 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                    int var27 = MathHelper.ceiling_double_int((double)var26 * this.damage);

                    if (this.getIsCritical())
                    {
                        var27 += this.rand.nextInt(var27 / 2 + 2);
                    }

                    var25 = null;
                    DamageSource var30;

                    if (this.shootingEntity == null)
                    {
                        var30 = DamageSource.causeThrownDamage(this, this);
                    }
                    else
                    {
                        var30 = DamageSource.causeThrownDamage(this, this.shootingEntity);
                    }

                    if (var20.entityHit.attackEntityFrom(var30, var27))
                    {
                        if (var20.entityHit instanceof EntityLiving)
                        {
                            byte var15 = 10;
                            ((EntityLiving)var21).addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), 30 * var15, 2));
                        }
                    }
                    else
                    {
                        this.setDead();
                    }

                    this.setDead();

                    if (this.isBurning())
                    {
                        var20.entityHit.setFire(5);
                    }

                    if (var20.entityHit.attackEntityFrom(var30, var27))
                    {
                        if (var20.entityHit instanceof EntityLiving)
                        {
                            EntityLiving var28 = (EntityLiving)var20.entityHit;

                            if (!this.worldObj.isRemote)
                            {
                                var28.setArrowCountInEntity(var28.getArrowCountInEntity() + 1);
                            }

                            if (this.knockbackStrength > 0)
                            {
                                var26 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);

                                if ((float)var27 > 0.0F)
                                {
                                    var20.entityHit.addVelocity(this.motionX * (double)this.knockbackStrength * 0.6000000238418579D / (double)var27, 0.1D, this.motionZ * (double)this.knockbackStrength * 0.6000000238418579D / (double)var27);
                                }
                            }

                            EnchantmentThorns.func_92096_a(this.shootingEntity, var28, this.rand);

                            if (this.shootingEntity != null && var20.entityHit != this.shootingEntity && var20.entityHit instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP)
                            {
                                ((EntityPlayerMP)this.shootingEntity).playerNetServerHandler.sendPacketToPlayer(new Packet70GameEvent(6, 0));
                            }
                        }

                        this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
                        this.setDead();
                    }
                    else
                    {
                        this.motionX *= -0.10000000149011612D;
                        this.motionY *= -0.10000000149011612D;
                        this.motionZ *= -0.10000000149011612D;
                        this.rotationYaw += 180.0F;
                        this.prevRotationYaw += 180.0F;
                        this.ticksInAir = 0;
                    }
                }
                else
                {
                    this.xTile = var20.blockX;
                    this.yTile = var20.blockY;
                    this.zTile = var20.blockZ;
                    this.inTile = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);
                    this.inData = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);
                    this.motionX = (double)((float)(var20.hitVec.xCoord - this.posX));
                    this.motionY = (double)((float)(var20.hitVec.yCoord - this.posY));
                    this.motionZ = (double)((float)(var20.hitVec.zCoord - this.posZ));
                    var26 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                    this.posX -= this.motionX / (double)var26 * 0.05000000074505806D;
                    this.posY -= this.motionY / (double)var26 * 0.05000000074505806D;
                    this.posZ -= this.motionZ / (double)var26 * 0.05000000074505806D;
                    this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
                    this.inGround = true;
                    this.arrowShake = 7;
                    this.setIsCritical(false);

                    if (this.inTile != 0)
                    {
                        Block.blocksList[this.inTile].onEntityCollidedWithBlock(this.worldObj, this.xTile, this.yTile, this.zTile, this);
                    }
                }
            }

            if (this.getIsCritical())
            {
                for (var17 = 0; var17 < 4; ++var17)
                {
                    this.worldObj.spawnParticle("splash", this.posX + this.motionX * (double)var17 / 4.0D, this.posY + this.motionY * (double)var17 / 4.0D, this.posZ + this.motionZ * (double)var17 / 4.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ);
                }
            }

            this.posX += this.motionX;
            this.posY += this.motionY;
            this.posZ += this.motionZ;
            var26 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

            for (this.rotationPitch = (float)(Math.atan2(this.motionY, (double)var26) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
            {
                ;
            }

            while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
            {
                this.prevRotationPitch += 360.0F;
            }

            while (this.rotationYaw - this.prevRotationYaw < -180.0F)
            {
                this.prevRotationYaw -= 360.0F;
            }

            while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
            {
                this.prevRotationYaw += 360.0F;
            }

            this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
            this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
            float var29 = 0.99F;
            var24 = 0.05F;

            if (this.isInWater())
            {
                this.setDead();
            }

            this.motionX *= (double)var29;
            this.motionY *= (double)var29;
            this.motionZ *= (double)var29;
            this.motionY -= (double)var24;
            this.setPosition(this.posX, this.posY, this.posZ);
            this.doBlockCollisions();
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound var1)
    {
        var1.setShort("xTile", (short)this.xTile);
        var1.setShort("yTile", (short)this.yTile);
        var1.setShort("zTile", (short)this.zTile);
        var1.setByte("inTile", (byte)this.inTile);
        var1.setByte("inData", (byte)this.inData);
        var1.setByte("shake", (byte)this.arrowShake);
        var1.setByte("inGround", (byte)(this.inGround ? 1 : 0));
        var1.setByte("pickup", (byte)this.canBePickedUp);
        var1.setDouble("damage", this.damage);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound var1)
    {
        this.xTile = var1.getShort("xTile");
        this.yTile = var1.getShort("yTile");
        this.zTile = var1.getShort("zTile");
        this.inTile = var1.getByte("inTile") & 255;
        this.inData = var1.getByte("inData") & 255;
        this.arrowShake = var1.getByte("shake") & 255;
        this.inGround = var1.getByte("inGround") == 1;

        if (var1.hasKey("damage"))
        {
            this.damage = var1.getDouble("damage");
        }

        if (var1.hasKey("pickup"))
        {
            this.canBePickedUp = var1.getByte("pickup");
        }
        else if (var1.hasKey("player"))
        {
            this.canBePickedUp = var1.getBoolean("player") ? 1 : 0;
        }
    }

    /**
     * Called by a player entity when they collide with an entity
     */
    public void onCollideWithPlayer(EntityPlayer var1)
    {
        if (!this.worldObj.isRemote && this.inGround && this.arrowShake <= 0)
        {
            boolean var2 = this.canBePickedUp == 1 || this.canBePickedUp == 2 && var1.capabilities.isCreativeMode;

            if (this.canBePickedUp == 1 && !var1.inventory.addItemStackToInventory(new ItemStack(Item.arrow, 1)))
            {
                var2 = false;
            }

            if (var2)
            {
                this.playSound("random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                var1.onItemPickup(this, 1);
                this.setDead();
            }
        }
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean canTriggerWalking()
    {
        return false;
    }

    public float getShadowSize()
    {
        return 0.0F;
    }

    public void setDamage(double var1)
    {
        this.damage = var1;
    }

    public double getDamage()
    {
        return this.damage;
    }

    public void setKnockbackStrength(int var1)
    {
        this.knockbackStrength = 0;
    }

    /**
     * If returns false, the item will not inflict any damage against entities.
     */
    public boolean canAttackWithItem()
    {
        return false;
    }

    public void hitGround(int var1, int var2, int var3, int var4, int var5)
    {
        this.worldObj.createExplosion(this.shootingEntity, this.posX, this.posY, this.posZ, 3.0F, false);
        this.setDead();
    }

    public void setIsCritical(boolean var1)
    {
        byte var2 = this.dataWatcher.getWatchableObjectByte(16);

        if (var1)
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 1)));
        }
        else
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & -2)));
        }
    }

    public boolean getIsCritical()
    {
        byte var1 = this.dataWatcher.getWatchableObjectByte(16);
        return (var1 & 1) != 0;
    }
}